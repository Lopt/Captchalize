package cap.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import cap.RunArguments;
import cap.db.jpa.CaptchaImage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ij.ImagePlus;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

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

                    //Managers.captchaSampleManager.create();
                    //ImagePlus image = new ImagePlus("Test", ImageIO.read(stream));

                    //this.server.addCaptchaSample(); TODO
                }
            }
        } catch(FileUploadException exception) {
            exception.printStackTrace();
        }
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
