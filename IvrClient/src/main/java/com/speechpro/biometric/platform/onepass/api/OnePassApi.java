package com.speechpro.biometric.platform.onepass.api;


import com.speechpro.biometric.platform.onepass.rest.OnePassRestClient;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by sadurtinova on 15.09.2016.
 */
public class OnePassApi {
    private static final Logger LOGGER = Logger.getLogger("OnePassApi");

    /**
     * Creates connection to biometric server
     * @param protocol
     * @param host
     * @param port
     * @param applicationRoot
     */
    public OnePassApi(String protocol, String host, String port, String applicationRoot){
        OnePassRestClient.initialize(protocol, host, port, applicationRoot);
        LOGGER.info("OnePassApi initialized");
    }

    /**
     * Creates object for manipulations with person
     * @param personId person identifier
     * @param sessionId session identifier
     * @return PersonApi
     */
    public PersonApi person(String personId, UUID sessionId){
        return new PersonApi(personId, sessionId);
    }

    public static void release(){
        OnePassRestClient.release();
    }

}
