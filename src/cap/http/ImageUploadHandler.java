package cap.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.persistence.EntityTransaction;

import cap.db.jpa.Managers;
import ij.ImagePlus;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import cap.CaptchaSample;
import cap.CaptchalizeRunner;
import cap.RunArguments;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ImageUploadHandler implements HttpHandler {

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        RequestContext request = new HttpExchangeRequestContext(httpExchange);

        // fehlerhaftes oder manipuliertes Formular für den Fileupload ausschließen
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            httpExchange.getResponseHeaders().add("Content-type", "multipart/form-data");
            httpExchange.sendResponseHeaders(406, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.close();
            return;
        }

        RunArguments args = RunArguments.getInstance();
        CaptchalizeRunner runner = CaptchalizeRunner.getInstance();
        Map<String, String> data = new HashMap<String, String>();

        EntityTransaction action = Managers.captchaSampleManager.getEntityManager().getTransaction();

        ServletFileUpload upload = new ServletFileUpload();
        upload.setFileSizeMax(args.getMaxImageSize());
        upload.setSizeMax(args.getMaxRequestSize());

        try {
            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();

                if (item.isFormField()) {
                    if (args.isDebugMode()) {
                        System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");
                    }
                } else {
                    if (args.isDebugMode()) {
                        System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
                    }

                    action.begin();
                    CaptchaImage image = new CaptchaImage(new ImagePlus("Unnamed", ImageIO.read(stream)));
                    CaptchaSample sample = new CaptchaSample(image);
                    action.commit();

                    runner.addCaptchaSample(sample);
                    data.put("id", sample.getModel().getServerOrder().toString());
                }
            }
        } catch(FileUploadException exception) {
            exception.printStackTrace();
        }

        TemplateLoader.getInstance().response(httpExchange, "uploaded.html", data);
    }

    private class HttpExchangeRequestContext implements RequestContext {
        private HttpExchange exchange = null;
        private Headers headers = null;

        public HttpExchangeRequestContext(HttpExchange httpExchange) {
            this.exchange = httpExchange;
            this.headers = httpExchange.getRequestHeaders();
        }

        @Override
        public String getCharacterEncoding() {
            return "UTF-8";
        }

        @Override
        public String getContentType() {
            return this.headers.getFirst("Content-type");
        }

        @Override
        @Deprecated
        public int getContentLength() {
            return Integer.parseInt(this.headers.getFirst("Content-length"));
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return this.exchange.getRequestBody();
        }
    }
}
