package cap.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cap.RunArguments;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class TemplateLoader {
    private static File serverDataDirectory = new File("./serverdata/");
    private static TemplateLoader instance = new TemplateLoader();

    private byte[] fileNotFoundSite = null;
    private byte[] internalServerErrorSite = null;

    public static TemplateLoader getInstance() {
        return instance;
    }

    public static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        try {
            is = new BufferedInputStream(is);
            is.read();
            return IOUtils.toByteArray(is);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static byte[] loadFile(File file, boolean quietly) {
        try {
            return loadFile(file);
        } catch (IOException exception) {
            if (!quietly) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public void response(final HttpExchange httpExchange, String filename) {
        OutputStream os = null;
        try {
            byte[] response = TemplateLoader.loadFile(new File(serverDataDirectory, filename));
            httpExchange.getResponseHeaders().add("Content-type", "text/html");
            httpExchange.sendResponseHeaders(200, response.length);
            os = httpExchange.getResponseBody();
            os.write(response);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException p) {
                p.printStackTrace();
            }
        }
    }

    public void responseFileNotFound(final HttpExchange httpExchange) {
        OutputStream os = null;
        try {
            httpExchange.sendResponseHeaders(404, this.fileNotFoundSite.length);
            os = httpExchange.getResponseBody();
            os.write(this.fileNotFoundSite);
        } catch (IOException e) {
            try {
                try {
                    os.close();
                } catch (IOException x) {

                }
                httpExchange.sendResponseHeaders(500, this.internalServerErrorSite.length);
                os = httpExchange.getResponseBody();
                os.write(this.internalServerErrorSite);
            } catch (IOException c) {

            }
        } finally {
            try {
                os.close();
            } catch (IOException p) {
                p.printStackTrace();
            }
        }
    }

    private TemplateLoader() {
        boolean quietly = !RunArguments.getInstance().isDebugMode();

        this.fileNotFoundSite = loadFile(new File(serverDataDirectory, "404.html"), quietly);
        this.internalServerErrorSite = loadFile(new File(serverDataDirectory, "500.html"), quietly);
    }
}
