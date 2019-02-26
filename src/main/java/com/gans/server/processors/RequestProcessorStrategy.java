package com.gans.server.processors;

import com.gans.server.model.HttpRequest;
import com.gans.server.utility.HttpResponseWriter;

import java.io.IOException;

/**
 * Interface to be implemented by every Request Processor based on the method type.
 * @author gmohan on 23/02/19.
 */
public interface RequestProcessorStrategy {

    void process(HttpRequest httpRequest, HttpResponseWriter writer) throws IOException;
}
