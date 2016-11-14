package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 16.09.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetDynamicVerificationResultResponseDto {


    @JsonProperty
    public double score;

    @JsonCreator
    public GetDynamicVerificationResultResponseDto(@JsonProperty("dynamicVoice") double score) {
        this.score = score;
    }
}
