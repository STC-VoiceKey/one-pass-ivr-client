package com.speechpro.biometric.platform.onepass.exceptions;


import com.speechpro.biometric.platform.exception.platform.*;
import com.speechpro.biometric.platform.onepass.dto.DtoHelper;
import com.speechpro.biometric.platform.onepass.dto.ExceptionDto;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

/**
 * Created by sadurtinova on 07.05.2018.
 */
public class ExceptionMapper {
    public static void map(CloseableHttpResponse response) {
        ExceptionDto exception;
        try {
            exception =
                    DtoHelper.create(response.getEntity().getContent(),
                            ExceptionDto.class);
        }catch (com.fasterxml.jackson.core.JsonParseException e){
            exception = new ExceptionDto(ErrorReason.UNDEFINED_ERROR, "Couldn't parse response from web service");
        } catch (IOException e) {
            e.printStackTrace();
            exception = new ExceptionDto(ErrorReason.UNDEFINED_ERROR, "Couldn't parse response from web service");
        }
        System.out.println("Status code: " + response.getStatusLine().getStatusCode());
        switch (response.getStatusLine().getStatusCode()) {

            case 401:
                throw  new AccessViolationException(exception.reason, exception.message);
            case 403:
                throw new OperationForbiddenException(exception.reason, exception.message);
            case 400:
                throw new BadRequestException(exception.reason, exception.message);
            case 404:
                throw new NotFoundException(exception.reason, exception.message);
            case 500:
                throw new BadRequestException(exception.reason, exception.message);
        }
        throw new InternalSdkException(ErrorReason.UNDEFINED_ERROR, "Not mapped exception");
    }
}
