package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 13.09.2016.
 */
public class CreatePersonRequestDto {

    @JsonProperty("personId")
    public String personId;

    public CreatePersonRequestDto(String personId){
        this.personId = personId;
    }
}
