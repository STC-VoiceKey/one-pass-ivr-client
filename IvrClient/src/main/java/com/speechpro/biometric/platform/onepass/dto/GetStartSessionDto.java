package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 01.05.2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetStartSessionDto {

    @JsonProperty
    public String sessionId;

    @JsonCreator
    public GetStartSessionDto(@JsonProperty("sessionId") String sessionId) {

        this.sessionId = sessionId;
    }

}
