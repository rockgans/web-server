package com.gans.server.sockets;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *  Interface to be implemented to provide a server socket when requested.
 * Created by gmohan on 23/02/19.
 */
interface SocketFactory {

    ServerSocket getSocket(int port) throws IOException;
}
