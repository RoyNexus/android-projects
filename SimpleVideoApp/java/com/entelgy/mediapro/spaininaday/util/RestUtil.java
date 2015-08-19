package com.entelgy.mediapro.spaininaday.util;

import android.util.Log;

import com.entelgy.mediapro.spaininaday.rest.UserRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class RestUtil {
    public static final String HOST = "https://213.98.255.173/";
    private static final String TAG = "RestUtil";

    private static RestUtil ourInstance = new RestUtil();
    private static RestTemplate restTemplate;

    public static RestUtil getInstance() {
        return ourInstance;
    }


    private RestUtil() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public Object put(String url, Object body, String... params) {
        Log.d(TAG, "PUT to URL (" + url + ") with requestObject: " + body.toString());
        initHttps();
        HttpHeaders requestHeaders = new HttpHeaders();
        // requestHeaders.add("Cookie", "JSESSIONID=" + session.getValue());
        HttpEntity requestEntity = new HttpEntity(body, requestHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                UserRequest.class,
                params[0]);

        return responseEntity.getBody();
    }

    public Object get(String url, Class classType, String... params) throws RestException {
        Object result = null;
        Log.d(TAG, "GET to URL (" + url + ") with params: " + params);
        initHttps();
        try {
            if (params == null) {
                result = restTemplate.getForObject(url, classType);
            } else {
                result = restTemplate.getForObject(url, classType, (Object[]) params);
            }
        } catch (RestClientException exception) {
            exception.printStackTrace();
            String errorMessage = ((HttpClientErrorException) exception).getResponseBodyAsString();
            Log.e(TAG, "ResponseBodyAsString: " + errorMessage);
            if (errorMessage != null && errorMessage.length() > 0) {
                throw new RestException(errorMessage);
            } else {
                throw exception;
            }
        }
        return result;
    }

    public Object post(String url, Object requestObject, Class classType) throws RestException {
        Object result = null;
        Log.d(TAG, "POST to URL (" + url + ") with requestObject: " + requestObject.toString());
        initHttps();
        try {
            result = restTemplate.postForObject(url, requestObject, classType);
        } catch (RestClientException exception) {
            exception.printStackTrace();
            String errorMessage = ((HttpClientErrorException) exception).getResponseBodyAsString();
            Log.e(TAG, "ResponseBodyAsString: " + errorMessage);
            if (errorMessage != null && errorMessage.length() > 0) {
                throw new RestException(errorMessage);
            } else {
                throw exception;
            }
        }
        return result;
    }

    private void initHttps() {
        try {
            SSLUtil.turnOffSslChecking();
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext context = SSLContext.getInstance("TLS");
            //context.init(null, new X509TrustManager[]{new NullX509TrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
