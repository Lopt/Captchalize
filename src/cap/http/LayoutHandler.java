package cap.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class LayoutHandler implements HttpHandler {

    @Override
    public void handle(final HttpExchange httpExchange) {
        TemplateLoader.getInstance().response(httpExchange, "style.css", "text/css");
    }
}
