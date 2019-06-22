package src.java9.server.http2;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.util.Headers;

public class HttpServer {

            private static final int PORT = 9999;
            private static final String HOST = "localhost";

            public static void main(String[] args) {
                Undertow server = Undertow.builder()
                        .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                        .addHttpListener(PORT, HOST)
                        .setHandler(exchange -> {
                            System.out.println("Client address: " + exchange.getConnection().getPeerAddress().toString());
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getRequestReceiver().receiveFullString((e, m) -> e.getResponseSender().send(m));
                        }).build();

        server.start();

    }

}
