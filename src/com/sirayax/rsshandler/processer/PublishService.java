package com.sirayax.rsshandler.processer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PublishService {
    private HttpClient httpClient;
    private HttpRequest httpRequest;
    private HttpResponse<String> httpResponse;

    public PublishService() {
        // HttpClient is immutable
        // httpClient = HttpClient.newHttpClient();
        httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).version(HttpClient.Version.HTTP_2).build();
    }

    public void setRequest(String httpMethod, String accessURL, HttpRequest.BodyPublisher reqBody, String contentType, String authToken) {
        try {
            httpRequest = HttpRequest.newBuilder(new URI(accessURL)).headers("content-type", contentType, "Authorization", "Bearer " + authToken).method(httpMethod, reqBody).timeout(Duration.ofSeconds(120)).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest() {
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(httpResponse.body());
        //System.out.println("Return code: " + httpResponse.statusCode());
        //System.out.println(httpResponse.headers().toString());
    }

}

