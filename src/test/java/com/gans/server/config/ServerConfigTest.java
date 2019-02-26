package com.gans.server.config;

import com.gans.server.exeptions.InvalidServerConfigException;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by gmohan on 26/02/19.
 */
public class ServerConfigTest {


    @Test(expected = InvalidServerConfigException.class)
    public void shouldThrowInvalidSErverConfigException3(){
        Properties properties = new Properties();
        properties.setProperty("port","9090");
        properties.setProperty("root","/data");
        ServerConfig.getInstance().initialize(properties);
    }

    @Test(expected = InvalidServerConfigException.class)
    public void shouldThrowInvalidSErverConfigException1(){
        Properties properties = new Properties();
        properties.setProperty("threads","10");
        properties.setProperty("root","/data");
        ServerConfig.getInstance().initialize(properties);
    }

    @Test(expected = InvalidServerConfigException.class)
    public void shouldThrowInvalidSErverConfigException2(){
        Properties properties = new Properties();
        properties.setProperty("port","9090");
        properties.setProperty("threads","10");
        ServerConfig.getInstance().initialize(properties);
    }

    @Test
    public void shouldCreateInstanceAndContinue(){
        Properties properties = new Properties();
        properties.setProperty("port","9090");
        properties.setProperty("threads","10");
        properties.setProperty("root","/data");
        ServerConfig.getInstance().initialize(properties);
    }
}
