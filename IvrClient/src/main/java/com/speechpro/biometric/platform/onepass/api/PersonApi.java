package com.speechpro.biometric.platform.onepass.api;

import com.speechpro.biometric.platform.onepass.dto.DtoHelper;
import com.speechpro.biometric.platform.onepass.dto.GetPersonResponseDto;
import com.speechpro.biometric.platform.onepass.exceptions.ExceptionMapper;
import com.speechpro.biometric.platform.onepass.rest.OnePassRestClient;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.UUID;


/**
 * Created by sadurtinova on 15.09.2016.
 */
public class PersonApi {

    private final String personId;
    private final String sessionId;
    private static final Logger LOGGER = Logger.getLogger("PersonApi");

    /**
     * Provides person manipulation
     * @param personId person identifier
     * @param sessionId session identifier
     *
     */
    public PersonApi(String personId, String sessionId) {
        this.sessionId = sessionId;
        LOGGER.info("PersonApi initialized for the person " + personId);
        this.personId = personId;
    }

    /**
     * Checks if person exists in biometric system
     * @return true if person exists
     */
    public boolean exists() {
        boolean result = false;
        try (CloseableHttpResponse response = OnePassRestClient.get().getPerson(personId,
                new Header[]{new BasicHeader("X-Session-Id", sessionId)})) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = true;
                LOGGER.info(String.format("Person with personId %s exists", personId));
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                LOGGER.info(String.format("Person with personId %s does not exists", personId));
            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Couldn't check if the person with personId " + personId + " exists");
        }
        return result;
    }

    /**
     * Checks if person fully enrolled in biometric system
     * @return true if person fully enrolled in biometric system
     */
    public boolean isFullEnrolled() {
        boolean result = false;
        try (CloseableHttpResponse response = OnePassRestClient.get().getPerson(personId,
                new Header[]{new BasicHeader("X-Session-Id", sessionId)})) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                GetPersonResponseDto personDto = DtoHelper.create(response.getEntity().getContent(), GetPersonResponseDto.class);
                result = personDto.isFullEnroll;
            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't check if the person with personId: " + personId + " is fully enrolled");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if person fully enrolled in biometric system
     * @return true if person fully enrolled in biometric system
     */
    public int getDynamicModelsNumber() {
        int result = 0;
        try (CloseableHttpResponse response = OnePassRestClient.get().getPerson(personId,
                new Header[]{new BasicHeader("X-Session-Id", sessionId)})) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                GetPersonResponseDto personDto = DtoHelper.create(response.getEntity().getContent(), GetPersonResponseDto.class);
                final int[] modelNumber = new int[1];
                personDto.models.forEach(model -> {
                    if("DYNAMIC_VOICE_KEY".equals(model.modelType)){
                        modelNumber[0] = model.samplesCount;
                    }
                });
                result = modelNumber[0];
            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't check if the person with personId: " + personId + " is fully enrolled");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if person fully enrolled in biometric system
     * @return true if person fully enrolled in biometric system
     */
    public int getStaticModelsNumber() {
        int result = 0;
        try (CloseableHttpResponse response = OnePassRestClient.get().getPerson(personId,
                new Header[]{new BasicHeader("X-Session-Id", sessionId)})) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                GetPersonResponseDto personDto = DtoHelper.create(response.getEntity().getContent(), GetPersonResponseDto.class);
                final int[] modelNumber = new int[1];
                personDto.models.forEach(model -> {
                    if("STATIC_VOICE_KEY".equals(model.modelType)){
                        modelNumber[0] = model.samplesCount;
                    }
                });
                result = modelNumber[0];
            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't check if the person with personId: " + personId + " is fully enrolled");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Deletes person from biometric system
     * @return true if person deleted successfully
     */
    public boolean delete() {
        boolean deleted = false;
        try (CloseableHttpResponse response = OnePassRestClient.get().deletePerson(personId,
                new Header[]{new BasicHeader("X-Session-Id", sessionId)})) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                deleted = true;
            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't delete person with personId: " + personId, e);
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Starts enrollment (registration) transaction
     * @param sessionId session identifier
     * @return RegistrationApi object with registration operations
     */
    public RegistrationApi startRegistration(UUID sessionId) {
        return new RegistrationApi(personId, sessionId);
    }

    /**
     * Starts verification transaction
     * @param sessionId session identifier
     * @return VerificationApi object with verification operations
     */
    public VerificationApi startVerification(String sessionId) {
        return new VerificationApi(personId, sessionId);
    }
}
