package com.gans.server.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Singleton Factory that instantiates a new server socket on the port and returns it back.
 * Created by gmohan on 23/02/19.
 */
public class DefaultSocketFactory implements SocketFactory{

    private static final DefaultSocketFactory SELF = new DefaultSocketFactory();
    private static Logger logger = LoggerFactory.getLogger(DefaultSocketFactory.class);

    public static DefaultSocketFactory getInstance(){
       return SELF;
    }

    @Override
    public ServerSocket getSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}
