package com.speechpro.biometric.platform.onepass.rest;

import com.speechpro.biometric.platform.onepass.dto.SendDynamicFileRequestDto;
import com.speechpro.biometric.platform.onepass.dto.SendFaceFileRequestDto;
import com.speechpro.biometric.platform.onepass.dto.SendStaticFileRequestDto;
import com.speechpro.biometric.platform.onepass.dto.StartSessionRequestDto;
import com.speechpro.biometric.platform.onepass.exceptions.NotInitializedException;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;

import java.util.Collections;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class OnePassRestClient {

    private static OnePassRestClient instance;

    public static void initialize(String protocol, String host, String port, String root) {
        if (instance == null) {
            instance = new OnePassRestClient(protocol, host, port, root);
        }
    }

    public static void release() {
        if (instance != null) {
            instance.releaseInternal();
        }
    }

    public static OnePassRestClient get() {
        if (instance != null) //here you can init Client
            return instance;
        else
            throw new NotInitializedException("OnePass REST API client is not initialized. Call initialize() method first");

    }

    private BasicRestClient client;
    private OnePassRestPaths paths;

    private OnePassRestClient(String protocol, String host, String port, String root) {
        client = new BasicRestClient(Collections.<Header>singletonList(
                new BasicHeader("Content-Type", "application/json; charset=UTF-8")
        ));
        paths = new OnePassRestPaths(protocol, host, port, root);
    }

    public CloseableHttpResponse startSession(StartSessionRequestDto data) {
        System.out.println(paths.getStartSessionUri());
        System.out.println(data);
        return client.post(paths.getStartSessionUri(), data, null);
    }

    public CloseableHttpResponse sendPersonVoiceDynamicFile(String personId, SendDynamicFileRequestDto data, Header[] headers) {
        return client.post(paths.getPersonVoiceDynamicFileUri(personId), data, headers);
    }

    public CloseableHttpResponse sendPersonVoiceStaticFile(String personId, SendStaticFileRequestDto data, Header[] headers) {
        return client.post(paths.getPersonVoiceStaticFileUri(personId), data, headers);
    }

    public CloseableHttpResponse sendPersonPhotoFile(SendFaceFileRequestDto data, Header[] headers) {
        return client.post(paths.getPersonPhotoFileUri(), data, headers);
    }

    public CloseableHttpResponse sendVerificationVoiceDynamicFile(String sessionId, SendDynamicFileRequestDto data, Header[] headers) {
        return client.post(paths.getVerificationVoiceDynamicFileUri(sessionId), data, headers);
    }

    public CloseableHttpResponse sendVerificationVoiceStaticFile(String personId, SendStaticFileRequestDto data, Header[] headers) {
        return client.post(paths.getVerificationVoiceStaticFileUri(personId), data, headers);
    }

    public CloseableHttpResponse getPerson(String personId, Header[] headers) {
        return client.get(paths.getPersonUri(personId), headers);
    }

    public CloseableHttpResponse deletePerson(String personId, Header[] headers) {
        return client.delete(paths.getPersonUri(personId), headers);
    }

    public CloseableHttpResponse startVerification(String personId, Header[] headers) {
        return client.get(paths.getVerificationStartUri(personId), headers);
    }

    public CloseableHttpResponse getVerificationScore(String sessionID, Header[] headers) {
        return client.get(paths.getVerificationScoreCloseSessionFalseUri(sessionID), headers);
    }

    public CloseableHttpResponse createPerson(String personId, Header[] headers) {

        return client.get(paths.getCreatePersonUri(personId), headers);
    }

    public CloseableHttpResponse closeVerificationSession(String sessionId, Header[] headers) {
        return client.delete(paths.getCloseVerificationUri(sessionId), headers);
    }

    public void releaseInternal() {
        client.release();
    }

}

