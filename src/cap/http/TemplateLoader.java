package cap.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Map;

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

    public static byte[] loadFile(File file, Map<String, String> data) throws IOException {
        InputStream is = new FileInputStream(file);
        try {
            is = new BufferedInputStream(is);

            if (data == null || data.isEmpty()) {
                is.read();
                return IOUtils.toByteArray(is);

            } else {
                return loadTemplate(is, data);
            }
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static byte[] loadFile(File file, Map<String, String> data, boolean quietly) {
        try {
            return loadFile(file, data);
        } catch (IOException exception) {
            if (!quietly) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public void response(final HttpExchange httpExchange, String filename) {
        response(httpExchange, filename, null, "text/html", 200);
    }

    public void response(final HttpExchange httpExchange, String filename, String contentType) {
        response(httpExchange, filename, null, contentType, 200);
    }

    public void response(final HttpExchange httpExchange, String filename, Map<String, String> data) {
        response(httpExchange, filename, data, "text/html", 200);
    }

    public void response( final HttpExchange httpExchange,
                          String filename,
                          Map<String, String> data,
                          String contentType,
                          int httpResponseCode) {

        OutputStream os = null;
        try {
            byte[] response = TemplateLoader.loadFile(new File(serverDataDirectory, filename), data);
            httpExchange.getResponseHeaders().add("Content-type", contentType);
            httpExchange.sendResponseHeaders(httpResponseCode, response.length);
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

    private static byte[] loadTemplate(InputStream is, Map<String, String> data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
        StringBuffer dataName = new StringBuffer();

        boolean opened = false;
        char first = '_';
        char second = '_';
        char openedValue = '{';
        char closedValue = '}';

        while(is.available() > 0) {
            first = (char)is.read();
            if (!opened && first == openedValue) {
                second = (char)is.read();
                if (second == openedValue) {
                    opened = true;
                } else {
                    os.write(first);
                    os.write(second);
                }
            } else if (opened && first == closedValue) {
                second = (char)is.read();
                if (second == closedValue) {
                    opened = false;

                    if (data.containsKey(dataName.toString())) {
                        os.write(data.get(dataName.toString()).getBytes());
                    }
                    dataName.setLength(0);

                } else {
                    os.write(first);
                    os.write(second);
                }
            } else if (opened) {
                if (first != ' ') {
                    dataName.append(first);
                }
            } else {
                os.write(first);
            }
        }

        return os.toByteArray();
    }

    private TemplateLoader() {
        boolean quietly = !RunArguments.getInstance().isDebugMode();

        this.fileNotFoundSite = loadFile(new File(serverDataDirectory, "404.html"), null, quietly);
        this.internalServerErrorSite = loadFile(new File(serverDataDirectory, "500.html"), null, quietly);
    }
}
