package com.gans.server.handlers;

import com.gans.server.model.HttpRequest;
import com.gans.server.constants.HttpStatus;
import com.gans.server.utility.HttpResponseWriter;
import com.gans.server.exeptions.BadRequestException;
import com.gans.server.exeptions.ConnectionClosedException;
import com.gans.server.processors.RequestProcessorContext;
import com.gans.server.utility.HttpRequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Receives all the requests in a new thread, parses the input from the stream,
 * executes the request and serves the response back.
 * Created by gmohan on 23/02/19.
 */
public class RequestHandler implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     *
     */
    @Override
    public void run() {

        HttpResponseWriter writer = null;
        HttpRequest request;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            request = HttpRequestParser.parse(inputStream);
            writer = new HttpResponseWriter(outputStream);

            RequestProcessorContext context = new RequestProcessorContext(request, writer);
            context.execute();

        } catch (IOException e) {
            logger.error("Error: while performing IO operation");
        } catch (BadRequestException e) {

                new HttpResponseWriter(outputStream).writeResponse(HttpStatus.BAD_REQUEST, new HashMap<>(), e.getMessage());

        } catch (ConnectionClosedException e) {
            logger.error("Empty or invalid request");
        } catch (RuntimeException e) {
            if(writer !=null){
                    writer.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, new HashMap<String, String>(),e.getMessage());
            }
            logger.error("Unknown error at Request Handler");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("Error: while closing input stream.");
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error("Error: while closing output stream.");
            }
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Error: while closing client socket.");
            }
        }
    }
}
