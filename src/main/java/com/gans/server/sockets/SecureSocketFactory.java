package com.gans.server.sockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.ServerSocket;

/**
 * Created by gmohan on 23/02/19.
 */
public class SecureSocketFactory implements SocketFactory {

    private static final SecureSocketFactory SELF = new SecureSocketFactory();
    private static Logger logger = LoggerFactory.getLogger(SecureSocketFactory.class);

    public static SecureSocketFactory getInstance(){
        return SELF;
    }
    @Override
    public ServerSocket getSocket(int port) {

        throw new NotImplementedException();// TODO change the exception
    }
}
