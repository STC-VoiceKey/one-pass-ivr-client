package com.speechpro.biometric.platform.onepass.rest;

import com.speechpro.biometric.platform.onepass.util.JsonSerializer;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class BasicRestClient {

    private static final Logger LOGGER = Logger.getLogger(BasicRestClient.class);
    private Header[] defaultHeaders = null;
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public BasicRestClient(List<Header> defaultHeaders){
        if(defaultHeaders != null && defaultHeaders.size() > 0){
            this.defaultHeaders = new Header[defaultHeaders.size()];

            for(int i = 0; i < defaultHeaders.size(); i++){
                this.defaultHeaders[i] = defaultHeaders.get(i);
            }
        }
    }

    CloseableHttpResponse get(String uri) {
        HttpGet getRequest = new HttpGet(uri);
        if (defaultHeaders != null)
            getRequest.setHeaders(defaultHeaders);
        CloseableHttpResponse result = null;
        try {
            result = httpClient.execute(getRequest);
        } catch (IOException ex) {
            LOGGER.error("Failed to execute get request", ex);
        }
        return result;
    }

    CloseableHttpResponse post(String uri, Object body){
        HttpPost postRequest = new HttpPost(uri);
        if(body != null)
            postRequest.setEntity(new StringEntity(JsonSerializer.serialize(body), "utf-8"));
        if(defaultHeaders != null)
            postRequest.setHeaders(defaultHeaders);
        CloseableHttpResponse result = null;
        try {
            result = httpClient.execute(postRequest);
        } catch (IOException e) {
            LOGGER.error("failed to send post request", e);
            e.printStackTrace();
            LOGGER.error(e.toString());
        }
        return result;
    }

    CloseableHttpResponse delete(String uri){
        HttpDelete deleteRequest = new HttpDelete(uri);
        if(defaultHeaders != null)
            deleteRequest.setHeaders(defaultHeaders);
        CloseableHttpResponse result = null;
        try {
            result = httpClient.execute(deleteRequest);
        }catch (IOException ex){
            LOGGER.error("Failed to execute delete request", ex);
        }
        return result;
    }

    public void release() {
        if(httpClient != null) {
            LOGGER.info("httpClient is not null");
            try {
                LOGGER.info("Trying to close httpClient");
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("Couldn't close httpclient");
                e.printStackTrace();
            }
        }
    }
}
