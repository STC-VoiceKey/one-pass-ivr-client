package com.speechpro.biometric.platform.onepass.api;

import com.speechpro.biometric.platform.onepass.dto.*;
import com.speechpro.biometric.platform.onepass.rest.OnePassRestClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by sadurtinova on 15.09.2016.
 */
public class PersonApi {

    private final String id;
    private static final Logger LOGGER = Logger.getLogger(PersonApi.class);
    protected PersonApi(String personId){
        LOGGER.info("PersonApi initialized for the person " + personId);
        this.id = personId;
    }

    public boolean exists(){
        boolean result = false;
        try(CloseableHttpResponse response = OnePassRestClient.get().getPerson(id)){
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = true;
                LOGGER.info(String.format("Person with id %s exists", id));
            }
            else {
                LOGGER.info(String.format("Person with id %s does not exists", id));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Couldn't check if the person with id " + id + " exists");
        }
        return result;
    }

    public boolean createPerson(){
        boolean result = false;
        try(CloseableHttpResponse response = OnePassRestClient.get().createPerson(new CreatePersonRequestDto(id))){
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                result = true;
            }
            LOGGER.info(String.format("Status code of createPerson() with id %s is %s : "
                    , id, response.getStatusLine().getStatusCode()));
            LOGGER.info("Result of createPerson() is: " + result);

        } catch (IOException e) {
            LOGGER.error("Couldn't create person with id: " + id);
            e.printStackTrace();
        }
        return result;
    }

    public boolean isFullEnrolled(){
        boolean result = false;
        try(CloseableHttpResponse response = OnePassRestClient.get().getPerson(id)){
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                GetPersonResponseDto personDto = DtoHelper.create(response.getEntity().getContent(), GetPersonResponseDto.class);
                result = personDto.isFullEnroll;
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't check if the person with id: " + id + " is fully enrolled");
            e.printStackTrace();
        }
        return result;
    }

    public boolean sendRegistrationVoice(String password, String sound64Data){
        LOGGER.info("Sending registration voice");
        boolean sent = false;
        try (CloseableHttpResponse response = OnePassRestClient.get().sendPersonVoiceDynamicFile(id,
                new SendDynamicFileRequestDto(URLDecoder.decode(password, "UTF-8"), sound64Data))){
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
              sent = true;
            }
            //result = response.getStatusLine().getStatusCode();
            LOGGER.info("Result of sendRegistrationVoice is " + sent);
        } catch (IOException e) {
            LOGGER.error("Couldn't send sound for registration for person with id: " + id);
            e.printStackTrace();
        }
        return sent;
    }

    public boolean sendRegistrationVoice(String sound64Data){
        LOGGER.info("Sending static registration voice");
        boolean sent = false;
        try (CloseableHttpResponse response = OnePassRestClient.get().sendPersonVoiceStaticFile(id,
                new SendStaticFileRequestDto(sound64Data))){
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
                sent = true;
            }
            LOGGER.info("Result of sendRegistrationVoice is " + sent);
        } catch (IOException e) {
            LOGGER.error("Couldn't send sound for static registration for person with id: " + id);
            e.printStackTrace();
        }
        return sent;
    }

    public boolean delete(){
        boolean deleted = false;
         try(CloseableHttpResponse response = OnePassRestClient.get().deletePerson(id)){
             LOGGER.info("Deleting person with id: " + id);
             LOGGER.info("Result: " + response.getStatusLine().getStatusCode());
             if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
                 deleted = true;
             }
         } catch (IOException e) {
             LOGGER.error("Couldn't delete person with id: " + id, e);
             e.printStackTrace();
         }
         return deleted;
    }

    public VerificationApi startVerification(){
        String sessionId = null;
        String password = null;

        try(CloseableHttpResponse response = OnePassRestClient.get().startVerification(id)) {
            StartDynamicVerificationRequestDto verification = DtoHelper.create(response.getEntity().getContent(), StartDynamicVerificationRequestDto.class);
            sessionId = verification.verificationId;
            password = verification.password;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new VerificationApi(password, sessionId);
    }

    public VerificationApi startStaticVerification(){
        String sessionId = null;
        String password = null;

        try(CloseableHttpResponse response = OnePassRestClient.get().startVerification(id)) {
            StartStaticVerificationRequestDto verification =
                    DtoHelper.create(response.getEntity().getContent(), StartStaticVerificationRequestDto.class);
            sessionId = verification.verificationId;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new VerificationApi(sessionId);
    }

    public VerificationApi verification (String password, String sessionId ){
        return new VerificationApi(password, sessionId);
    }

    public VerificationApi verification (String sessionId ){
        return new VerificationApi(sessionId);
    }
}
