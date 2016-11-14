package com.speechpro.biometric.platform.onepass.exceptions;

/**
 * Created by sadurtinova on 15.09.2016.
 */
public class NotInitializedException extends RuntimeException {
    public NotInitializedException(){
        super();
    }

    public NotInitializedException(String message){
        super(message);
    }
}
