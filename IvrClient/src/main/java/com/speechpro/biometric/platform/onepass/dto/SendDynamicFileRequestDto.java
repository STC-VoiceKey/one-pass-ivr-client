package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class SendDynamicFileRequestDto {

    public SendDynamicFileRequestDto(){}

    @JsonProperty("data")
    public String dataBase64;

    @JsonProperty("password")
    public String password;

    @JsonCreator
    public SendDynamicFileRequestDto(@JsonProperty("password") String password, @JsonProperty("data") String dataBase64){
        this.password = password;
        this.dataBase64 = dataBase64;
    }
    @Override
    public String toString() {
        return "SendDynamicFileRequestDto{" +
                "dataBase64='" + dataBase64 + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
