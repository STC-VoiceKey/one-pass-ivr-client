package com.speechpro.biometric.platform.onepass.rest;

import com.speechpro.biometric.platform.onepass.util.JsonSerializer;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
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

    public BasicRestClient(List<Header> defaultHeaders) {
        if (defaultHeaders != null && defaultHeaders.size() > 0) {
            this.defaultHeaders = new Header[defaultHeaders.size()];

            for (int i = 0; i < defaultHeaders.size(); i++) {
                this.defaultHeaders[i] = defaultHeaders.get(i);
            }
        }
    }

    CloseableHttpResponse get(String uri, Header[] headers) {
        HttpGet getRequest = new HttpGet(uri);
        HttpHost proxy = new HttpHost("192.168.8.100", 3128);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        getRequest.setConfig(config);
        System.out.println(uri);
        if (defaultHeaders != null && headers != null) {
            Header[] allHeaders = mergeHeaders(defaultHeaders, headers);
            getRequest.setHeaders(allHeaders);
        } else if (defaultHeaders != null) {
            getRequest.setHeaders(defaultHeaders);
        } else if (headers != null) {
            getRequest.setHeaders(headers);
        }

        CloseableHttpResponse result = null;
        try {
            result = httpClient.execute(getRequest);
        } catch (IOException ex) {
            LOGGER.error("Failed to execute get request", ex);
        }
        return result;
    }

    CloseableHttpResponse post(String uri, Object body, Header[] headers) {
        HttpPost postRequest = new HttpPost(uri);
        System.out.println(uri);
        System.out.println(body.toString());
        HttpHost proxy = new HttpHost("192.168.8.100", 3128);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        postRequest.setConfig(config);
        if (body != null)
            postRequest.setEntity(new StringEntity(JsonSerializer.serialize(body), "utf-8"));
        if (defaultHeaders != null && headers != null) {
            Header[] allHeaders = mergeHeaders(defaultHeaders, headers);
            postRequest.setHeaders(allHeaders);
        } else if (defaultHeaders != null) {
            postRequest.setHeaders(defaultHeaders);
        } else if (headers != null) {
            postRequest.setHeaders(headers);
        }
        CloseableHttpResponse result = null;
        try {
            result = httpClient.execute(postRequest);
           // System.out.println(result.toString());
        } catch (IOException e) {
            LOGGER.error("failed to send post request", e);
            e.printStackTrace();
            LOGGER.error(e.toString());
        }
        return result;
    }

    CloseableHttpResponse delete(String uri, Header[] headers) {
        HttpDelete deleteRequest = new HttpDelete(uri);
        HttpHost proxy = new HttpHost("192.168.8.100", 3128);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        deleteRequest.setConfig(config);
        if (defaultHeaders != null && headers != null) {
            Header[] allHeaders = mergeHeaders(defaultHeaders, headers);
            deleteRequest.setHeaders(allHeaders);
        } else if (defaultHeaders != null) {
            deleteRequest.setHeaders(defaultHeaders);
        } else if (headers != null) {
            deleteRequest.setHeaders(headers);
        }
        CloseableHttpResponse result = null;
        try {
            result = httpClient.execute(deleteRequest);
        } catch (IOException ex) {
            LOGGER.error("Failed to execute deletePerson request", ex);
        }
        return result;
    }

    private Header[] mergeHeaders(Header[] headers1, Header[] headers2){
        Header[] allHeaders = new Header[headers1.length + headers2.length];
        System.arraycopy(headers1, 0, allHeaders, 0, headers1.length);
        System.arraycopy(headers2, 0, allHeaders, headers1.length, headers2.length);
        return allHeaders;
    }

    public void release() {
        if (httpClient != null) {
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
