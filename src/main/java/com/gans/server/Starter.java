package com.gans.server;

import com.gans.server.exeptions.InvalidServerConfigException;
import com.gans.server.config.ServerConfig;
import com.gans.server.webserver.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Starter class which triggers the server start.
 * Created by gmohan on 23/02/19.
 */
public class Starter {

    // properties file to be placed in the running folder of the jar.
    private static final String PROPERTIES_PATH = "./web-server.properties";
    private static Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {

        Properties properties = new Properties();

        FileInputStream file = null;

        try {
            logger.info("Loading server properties");
            file = new FileInputStream(PROPERTIES_PATH);
            properties.load(file);
            logger.info("Initializing server parameters");
            ServerConfig.getInstance().initialize(properties);
            logger.info("starting server");
            new Thread(new WebServer()).start();

        } catch (InvalidServerConfigException e) {
            logger.error("Invalid or missing properties. host, port, thread count & root are required for server to start");
        } catch (FileNotFoundException e) {
            logger.error("web-server.properties file not found");
        } catch (NumberFormatException e) {
            logger.error("Invalid port or max number of threads");
            System.out.println("Invalid or missing properties. host, port, thread count & root are required for server to start");
        } catch (IOException e) {
            logger.error("unable to read web-server.properties file");
        }catch (Exception e) {
            logger.error("Unknown error happened at server start");
        } finally {
            try {
                if(file != null)
                file.close();
            } catch (IOException e) {
                logger.error("error while closing input stream.");
            }
        }
    }
}
