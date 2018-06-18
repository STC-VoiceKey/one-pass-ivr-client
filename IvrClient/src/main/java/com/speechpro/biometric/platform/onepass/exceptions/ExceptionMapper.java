package com.speechpro.biometric.platform.onepass.exceptions;


import com.speechpro.biometric.platform.exception.platform.*;
import com.speechpro.biometric.platform.onepass.dto.DtoHelper;
import com.speechpro.biometric.platform.onepass.dto.ExceptionDto;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by sadurtinova on 07.05.2018.
 */
public class ExceptionMapper {

    private static final Logger LOGGER = Logger.getLogger("ExceptionMapper");
    public static void map(CloseableHttpResponse response) {
        ExceptionDto exception;
        try {
            exception =
                    DtoHelper.create(response.getEntity().getContent(),
                            ExceptionDto.class);
        } catch (com.fasterxml.jackson.core.JsonParseException e) {
            exception = new ExceptionDto(ErrorReason.UNDEFINED_ERROR, "Couldn't parse response from web service");
        } catch (IOException e) {
            e.printStackTrace();
            exception = new ExceptionDto(ErrorReason.UNDEFINED_ERROR, "Couldn't parse response from web service");
        }
        System.out.println("Status code: " + response.getStatusLine().getStatusCode());
        switch (response.getStatusLine().getStatusCode()) {

            case 401:
                LOGGER.info(String.format("AccessViolationException. Message: %s, reason: %s", exception.message, exception.reason));
                throw new AccessViolationException(exception.reason, exception.message);
            case 403:
                LOGGER.info(String.format("OperationForbiddenException. Message: %s, reason: %s", exception.message, exception.reason));
                throw new OperationForbiddenException(exception.reason, exception.message);
            case 400:
                LOGGER.info(String.format("BadRequestException. Message: %s, reason: %s", exception.message, exception.reason));
                throw new BadRequestException(exception.reason, exception.message);
            case 404:
                LOGGER.info(String.format("NotFoundException. Message: %s, reason: %s", exception.message, exception.reason));
                throw new NotFoundException(exception.reason, exception.message);
            case 500:
                LOGGER.info(String.format("BadRequestException. Message: %s, reason: %s", exception.message, exception.reason));
                throw new BadRequestException(exception.reason, exception.message);
        }
        LOGGER.info(String.format("InternalSdkException. Message: %s, reason: %s", exception.message, exception.reason));
        throw new InternalSdkException(ErrorReason.UNDEFINED_ERROR, "Not mapped exception");
    }
}
