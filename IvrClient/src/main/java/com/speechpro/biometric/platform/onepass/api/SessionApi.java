package com.speechpro.biometric.platform.onepass.api;


import com.speechpro.biometric.platform.onepass.dto.DtoHelper;
import com.speechpro.biometric.platform.onepass.dto.GetStartSessionDto;
import com.speechpro.biometric.platform.onepass.dto.StartSessionRequestDto;
import com.speechpro.biometric.platform.onepass.exceptions.ExceptionMapper;
import com.speechpro.biometric.platform.onepass.rest.OnePassRestClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by sadurtinova on 01.05.2018.
 */
public class SessionApi {

    private static final Logger LOGGER = Logger.getLogger("SessionApi");
    private String username;
    private String password;
    private int domainId;

    public SessionApi(String username, String passwoord, int domainId) {
        this.username = username;
        this.password = passwoord;
        this.domainId = domainId;
    }

    public UUID startSession() {
        UUID sessionId = null;
        try (CloseableHttpResponse response = OnePassRestClient.get().startSession(new StartSessionRequestDto(username, password, domainId))) {
            //System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                GetStartSessionDto session =
                        DtoHelper.create(response.getEntity().getContent(),
                                GetStartSessionDto.class);
                sessionId = UUID.fromString(session.sessionId);
                //System.out.println(sessionId);

            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't start session: " + e.getMessage());
            e.printStackTrace();
        }
        return sessionId;
    }

}
