package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 07.11.2016.
 */
public class StartStaticVerificationRequestDto {

    @JsonProperty
    public String password;

    @JsonProperty
    public String verificationId;

    @JsonCreator
    public StartStaticVerificationRequestDto(
            @JsonProperty("verificationId") String verificationId) {
        this.verificationId = verificationId;
    }
}
