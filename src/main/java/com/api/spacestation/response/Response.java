package com.api.spacestation.response;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class Response {

    private final static Logger logger = LoggerFactory.getLogger(Response.class.getName());

    private Response() {}

    private static volatile Response instance;

    public static Response getInstance() {
        if (instance == null) {
            synchronized (Response.class) {
                if (instance == null) {
                    instance = new Response();
                }
            }
        }
        return instance;
    }

    public <T> T getJsonResponse(String url, Class<T> dataClass) throws IOException {
        String response;
        try {
            URL apiUrl = new URL(url);
            String apiStringResponse = IOUtils.toString(apiUrl, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(apiStringResponse);
            response = jsonObject.toString();
        } catch (IOException e) {
            logger.error("Unable to get response from url=" + url);
            throw new IOException();
        }
        return new Gson().fromJson(response, dataClass);
    }

}
