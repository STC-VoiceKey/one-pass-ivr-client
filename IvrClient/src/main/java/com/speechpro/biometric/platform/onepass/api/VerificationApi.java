package com.speechpro.biometric.platform.onepass.api;

import com.speechpro.biometric.platform.onepass.dto.*;
import com.speechpro.biometric.platform.onepass.rest.OnePassRestClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by sadurtinova on 15.09.2016.
 */
public class VerificationApi {
    private static final Logger LOGGER = Logger.getLogger(PersonApi.class);

    private final String sessionId;
    private final String verificationPassword;

    protected VerificationApi(String password, String sessionId) {
        this.sessionId = sessionId;
        this.verificationPassword = password;
        LOGGER.info(String.format("VerificationApi initialized. SessionId = %s",
                    sessionId));
    }

    protected VerificationApi(String sessionId) {
        this.sessionId = sessionId;
        this.verificationPassword = "";
        LOGGER.info(String.format("VerificationApi initialized. SessionId = %s",
                sessionId));
    }

    public String getSessionId(){
        return sessionId;
    }

    public String getVerificationPassword(){
        try {
            return new String(verificationPassword.getBytes("windows-1251"), Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int sendVerificationVoice(String sound64Data){
        int result = 0;
        try(CloseableHttpResponse response =
                    OnePassRestClient.get().sendVerificationVoiceDynamicFile(
                            sessionId,
                            new SendDynamicFileRequestDto(verificationPassword, sound64Data))){
            result = response.getStatusLine().getStatusCode();

        } catch (IOException e) {
            LOGGER.error("Couldn't send verification sound data for session with id: " + sessionId);
            e.printStackTrace();
        }
        return result;
    }

    public int sendStaticVerificationVoice(String sound64Data){
        int result = 0;
        try(CloseableHttpResponse response =
                    OnePassRestClient.get().sendVerificationVoiceStaticFile(
                            sessionId,
                            new SendStaticFileRequestDto(sound64Data))){
            result = response.getStatusLine().getStatusCode();

        } catch (IOException e) {
            LOGGER.error("Couldn't send verification sound data for session with id: " + sessionId);
            e.printStackTrace();
        }
        return result;
    }

    public double getVerificationScore(){
        double result = 0;
        try(CloseableHttpResponse response = OnePassRestClient.get().getVerificationScore(sessionId)){
            GetDynamicVerificationResultResponseDto verificationScore =
                    DtoHelper.create(response.getEntity().getContent(),
                            GetDynamicVerificationResultResponseDto.class);
            result = verificationScore.score;
            LOGGER.info("getVerificationScore() method called, result is : " + result);
        } catch (IOException e) {
            LOGGER.error("Couldn't get verification result for session with id: " + sessionId);
            e.printStackTrace();
        }
        return result;
    }

    public double getStaticVerificationScore(){
        double result = 0;
        try(CloseableHttpResponse response = OnePassRestClient.get().getVerificationScore(sessionId)){
            GetStaticVerificationResultResponseDto verificationScore =
                    DtoHelper.create(response.getEntity().getContent(),
                            GetStaticVerificationResultResponseDto.class);
            result = verificationScore.score;
            LOGGER.info("getVerificationScore() method called, result is : " + result);
        } catch (IOException e) {
            LOGGER.error("Couldn't get verification result for session with id: " + sessionId);
            e.printStackTrace();
        }
        return result;
    }

    public boolean closeVerificationSession(){
        boolean result = false;
        try (CloseableHttpResponse response = OnePassRestClient.get().closeVerificationSession(sessionId)){
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
                result = true;
                LOGGER.info("closeVerificationSession() method called, result is : " + result);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't close verification session with id: " + sessionId);
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
