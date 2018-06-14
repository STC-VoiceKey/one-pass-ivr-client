package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sadurtinova on 15.09.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPersonResponseDto {
    @JsonProperty
    public boolean isFullEnroll;

    @JsonProperty
    public List<ModelDto> models;

    @JsonProperty
    public String id;

    @JsonCreator
    public GetPersonResponseDto(@JsonProperty("isFullEnroll") boolean isFullEnroll,
                                @JsonProperty("id") String id,
                                @JsonProperty("models") List<ModelDto> models){
        this.isFullEnroll = isFullEnroll;
        this.id = id;
        this.models = models;
    }
}
