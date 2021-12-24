package com.api.spacestation.response;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Response {

    private final static Logger logger = LoggerFactory.getLogger(Response.class.getName());

    public static <T> T getJsonResponse(String url, Class<T> dataClass) throws IOException {
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
