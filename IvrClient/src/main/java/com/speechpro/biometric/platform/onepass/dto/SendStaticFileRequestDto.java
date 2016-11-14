package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 13.09.2016.
 */
public class SendStaticFileRequestDto {

    @JsonProperty("data")
    public String dataBase64;

    public SendStaticFileRequestDto (String dataBase64){
        this.dataBase64 = dataBase64;
    }

    @Override
    public String toString() {
        return "SendDynamicFileRequestDto{" +
                "dataBase64='" + dataBase64 + '\'' +
                '}';
    }
}
