package com.github.viniciusffj.wiremock;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpClient {
    public void get(String url) throws UnirestException {
        Unirest.get(url).asString();
    }
}
