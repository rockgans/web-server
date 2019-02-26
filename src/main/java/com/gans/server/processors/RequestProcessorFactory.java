package com.gans.server.processors;

import com.gans.server.constants.HTTPMethods;
import com.gans.server.exeptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton factory that provides a new instance of the {@link RequestProcessorStrategy} based on
 * the method type that is passed
 * Created by gmohan on 24/02/19.
 */
public class RequestProcessorFactory {

    private static final RequestProcessorFactory SELF = new RequestProcessorFactory();
    private static Logger logger = LoggerFactory.getLogger(RequestProcessorFactory.class);

    public static RequestProcessorFactory getInstance() {
        return SELF;
    }

    /**
     *
     * @param methodType request method type supported by HTTP 1.1
     * @return RequestProcessorStrategy implementation that suits the request
     * @throws BadRequestException if the method typs is not supported by http 1.1
     */
    public RequestProcessorStrategy getStrategy(String methodType) throws BadRequestException{

        switch (HTTPMethods.valueOf(methodType)) {

            case GET:
                return new GetRequestprocessor();

            case HEAD:
                return new HeadRequestProcessor();

            case TRACE:
                return new TraceRequestProcessor();
            case POST:
                return new PostRequestprocessor();
            case PUT:
                return new PutRequestprocessor();
            case DELETE:
                return new DeleteRequestProcessor();
            case OPTIONS:
                return new OptionsRequestProcessor();
            case CONNECT:
                return new ConnectRequestProcessor();

            default:
               throw new BadRequestException("Invalid Method");
        }

    }

}
