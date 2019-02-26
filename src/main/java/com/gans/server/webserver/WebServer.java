package com.gans.server.webserver;

import com.gans.server.config.ServerConfig;
import com.gans.server.handlers.RequestHandler;
import com.gans.server.sockets.DefaultSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Webserver Implementation that acts as a dispatcher of the incoming request to the corresponding Request processors.
 * Created by gmohan on 23/02/19.
 */
public class WebServer implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(WebServer.class);
    public static final String HTTP_VERSION = "HTTP/1.1";

    private ServerSocket server;

    /**
     * Creates a separate thread for every incoming request and hands it to the executor service pool.
     * the next available thread will pick it up and execute it.
     */
    @Override
    public void run() {

        int port = ServerConfig.getInstance().getPort();
        int maxThreadCount = ServerConfig.getInstance().getMaxThreadCount();
        try {
            server = DefaultSocketFactory.getInstance().getSocket(port);
        } catch (IOException e) {
            logger.error("Unable to get server socket");
        }
        // service that manages the thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount); // thread pool executor can be used for thread count fine tuning.

        while (true) {
            try {
                executorService.execute(new Thread(new RequestHandler(server.accept())));
            } catch (IOException e) {
                logger.error("IO Error while accepting socket connection at port {}", port);
            } catch (RuntimeException e) {
                logger.error("Unknown error while accepting socket connection at port {}", port);
            }
        }
    }
}
