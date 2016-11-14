package com.speechpro.biometric.platform.onepass;

import com.speechpro.biometric.platform.onepass.api.OnePassApi;
import com.speechpro.biometric.platform.onepass.api.PersonApi;
import com.speechpro.biometric.platform.onepass.api.VerificationApi;

/**
 * Created by sadurtinova on 07.11.2016.
 */
public class Sample {

    public static final String  SERVER_NAME = "192.168.29.169";
    public static final String  SERVER_PORT = "8078";

    public static void main (String [] args){

        String personId = "123";
        String mode = "static";
        OnePassApi api = new OnePassApi(SERVER_NAME, SERVER_PORT, mode);
        PersonApi personApi = api.person(personId);
        //personApi.createPerson();
        //VerificationApi verificationApi = api.person(personId).startStaticVerification();
        System.out.println(personApi.exists());

    }
}
