package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 01.05.2018.
 */
public class StartSessionRequestDto {

    @JsonProperty("username")
    public String username;

    @JsonProperty("password")
    public String password;

    @JsonProperty("domainId")
    public int domainId;

    public StartSessionRequestDto(String username, String password, int domainId){
        this.username = username;
        this.password = password;
        this.domainId = domainId;
    }
    @Override
    public String toString() {
        return "StartSessionRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", domainId='" + domainId + '\'' +
                '}';
    }

}
