package com.gans.server.config;

import com.gans.server.exeptions.InvalidServerConfigException;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * Singleton that holds the configuration for the web server. On start up loads the value
 * from web-server.properties and caches in the memory.
 * Created by gmohan on 24/02/19.
 */
public class ServerConfig {

    private static final String PORT = "port";
    private static final String THREADS = "threads";
    private static final String ROOT = "root";
    // port number in which server will connect
    private int port;
    // maximum number of threads
    private int maxThreadCount;
    // root folder where the static files are stored
    private String webRoot;

    private static final ServerConfig instance = new ServerConfig();

    private ServerConfig() {
    }

    public static ServerConfig getInstance() {
        return instance;
    }

    public void initialize(Properties properties) throws InvalidServerConfigException{

        if (StringUtils.isNotEmpty(properties.getProperty(PORT))
                && StringUtils.isNotEmpty(properties.getProperty(THREADS))
                && StringUtils.isNotEmpty(properties.getProperty(ROOT))) {

            port = Integer.parseInt(properties.getProperty(PORT));
            maxThreadCount = Integer.parseInt(properties.getProperty(THREADS));
            webRoot = properties.getProperty(ROOT);
        }else{
            throw new InvalidServerConfigException();
        }

    }

    public int getPort() {
        return port;
    }

    public int getMaxThreadCount() {
        return maxThreadCount;
    }

    public String getWebRoot() {
        return webRoot;
    }
}
