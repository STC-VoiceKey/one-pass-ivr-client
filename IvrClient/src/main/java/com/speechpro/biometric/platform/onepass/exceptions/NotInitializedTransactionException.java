package com.speechpro.biometric.platform.onepass.exceptions;

import com.speechpro.biometric.platform.exception.platform.ErrorCode;
import com.speechpro.biometric.platform.exception.platform.ErrorReason;
import com.speechpro.biometric.platform.exception.platform.PlatformException;

/**
 * Created by sadurtinova on 07.05.2018.
 */
public class NotInitializedTransactionException extends PlatformException {
    public NotInitializedTransactionException(String message){

        super(ErrorCode.BAD_REQUEST, ErrorReason.UNDEFINED_ERROR, message);
    }
}
