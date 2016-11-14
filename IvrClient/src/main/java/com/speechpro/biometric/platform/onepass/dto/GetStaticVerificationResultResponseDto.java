package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 08.11.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetStaticVerificationResultResponseDto {
    @JsonProperty
    public double score;

    @JsonCreator
    public GetStaticVerificationResultResponseDto (@JsonProperty("staticVoice") double score) {
        this.score = score;
    }
}
