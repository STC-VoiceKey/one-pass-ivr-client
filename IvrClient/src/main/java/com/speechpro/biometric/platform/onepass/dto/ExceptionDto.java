package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 07.05.2018.
 */
public class ExceptionDto {

    @JsonProperty
    public String reason;

    @JsonProperty
    public String message;

    @JsonCreator
    public ExceptionDto(@JsonProperty("reason") String reason,
                        @JsonProperty("message")String message) {
        this.reason = reason;
        this.message = message;
    }


}
