package com.gans.server.processors;

import com.gans.server.model.HttpRequest;
import com.gans.server.utility.HttpResponseWriter;

import java.io.IOException;

/**
 * Holds the context variables for a particular http request. Identifies the method and fetches the corresponding processor
 * and a writer which writes response back to the client
 * Created by gmohan on 23/02/19.
 */
public class RequestProcessorContext {

    private RequestProcessorStrategy strategy;
    private HttpRequest httpRequest;
    private HttpResponseWriter writer;

    public RequestProcessorContext(HttpRequest httpRequest, HttpResponseWriter writer) {
        this.httpRequest = httpRequest;
        this.strategy = RequestProcessorFactory.getInstance().getStrategy(httpRequest.getMethod());
        this.writer = writer;
    }

    public void execute() throws IOException {
        strategy.process(httpRequest, writer);
    }

}
