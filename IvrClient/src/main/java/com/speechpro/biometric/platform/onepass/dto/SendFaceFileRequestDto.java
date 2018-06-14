package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 28.04.2018.
 */
public class SendFaceFileRequestDto {
    @JsonProperty("data")
    public String data;

    public SendFaceFileRequestDto(String data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "SendFaceFileRequestDto{" +
                "data='" + data + '\'' +
                '}';
    }
}
