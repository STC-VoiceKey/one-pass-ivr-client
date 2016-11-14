package com.speechpro.biometric.platform.onepass.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sadurtinova on 19.09.2016.
 */
public class ConfigReader {
    private static final Logger LOGGER = Logger.getLogger(ConfigReader.class);

    public String readProperty (String pro){
        Properties properties = new Properties();
        String propFileName = "onepass.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
            properties.load(inputStream);
            LOGGER.info(String.format("Reading config for %s parameter", pro));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error during parameter reading" + e.getMessage());
        }
        return properties.getProperty(pro);
    }

    public static void main (String [] args){
        ConfigReader cr = new ConfigReader();
        System.out.println(cr.readProperty("com.speechpro.biometric.platform.onepass.onepass.api.rest.host"));
    }
}
