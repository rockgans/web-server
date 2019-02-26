package com.gans.server.exeptions;

/**
 * Created by Mihail on 10/24/2015.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message){
        super(message);
    }
}
