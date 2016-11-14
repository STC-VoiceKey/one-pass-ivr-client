package com.speechpro.biometric.platform.onepass.api;

import com.speechpro.biometric.platform.onepass.rest.OnePassRestClient;
import org.apache.log4j.Logger;

/**
 * Created by sadurtinova on 15.09.2016.
 */
public class OnePassApi {
    private static final Logger LOGGER = Logger.getLogger(OnePassApi.class);

    public OnePassApi(String serverIpAddress, String serverPort, String mode){
        OnePassRestClient.initialize(serverIpAddress, serverPort, mode.equals("dynamic") ? true : false);
        LOGGER.info("OnePassApi initialized");
    }

    public PersonApi person(String personId){
        return new PersonApi(personId);
    }

    public static void release(){
        OnePassRestClient.release();
    }

}
