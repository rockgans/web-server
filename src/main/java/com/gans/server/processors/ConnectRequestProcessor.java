package com.gans.server.processors;

import com.gans.server.model.HttpRequest;
import com.gans.server.constants.HttpStatus;
import com.gans.server.utility.HttpResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Handles the connect requests
 * Created by gmohan on 23/02/19.
 */
public class ConnectRequestProcessor implements RequestProcessorStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ConnectRequestProcessor.class);
    @Override
    public void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException {

        writer.writeResponse(HttpStatus.METHOD_NOT_SUPPORTED, new HashMap<>(),"Method not implemented");
    }
}
