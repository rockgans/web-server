package com.gans.server.processors;

import com.gans.server.model.HttpRequest;
import com.gans.server.constants.HttpStatus;
import com.gans.server.utility.HttpResponseWriter;

import java.io.IOException;
import java.util.HashMap;

/**
 * Handles all the Options Request
 * Created by gmohan on 23/02/19.
 */
public class OptionsRequestProcessor implements RequestProcessorStrategy {

    @Override
    public void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException {
        //TODO to be implemented
        writer.writeResponse(HttpStatus.METHOD_NOT_SUPPORTED, new HashMap<>(),"Method not implemented");
    }
}
