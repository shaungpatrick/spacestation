package com.api.spacestation.service;

import com.api.spacestation.config.SpaceStationConfig;
import com.api.spacestation.config.WeatherConfig;
import com.api.spacestation.data.SpaceStationData;
import com.api.spacestation.data.WeatherData;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SpaceStationService {

    private final Logger logger = LoggerFactory.getLogger(SpaceStationService.class.getName());

    @Autowired
    private SpaceStationConfig spaceStationConfig;

    @Autowired
    private WeatherConfig weatherConfig;

    /**
     * Check if the International Space Station is currently visible.
     *
     * @return true if the International Space Station is currently visible
     * @throws IOException thrown if we can't get a response from the internal APIs
     */
    public boolean getStationVisibility() throws IOException {
        String spaceStationApiResponse = getJsonResponseFromUrl(spaceStationConfig.getApi());

        SpaceStationData spaceStationData = mapJsonData(spaceStationApiResponse, SpaceStationData.class);

        String weatherApiResponse = getJsonResponseFromUrl(weatherConfig.getApi()
                + "?lat=" + spaceStationData.getLatitude()
                + "&lon=" + spaceStationData.getLongitude()
                + "&key=" + weatherConfig.getApiKey());

        WeatherData weatherData = mapJsonData(weatherApiResponse, WeatherData.class);

        boolean isAfterSunSet = weatherData.isAfterSunSet();
        double cloudCoverage = weatherData.getCloudCoverage();

        return cloudCoverage <= 30.0 && isAfterSunSet;
    }

    /**
     * Map data from a JSON string to a POJO.
     *
     * @param   json json string
     * @param   dataClass Class to map JSON object to
     * @param   <T> Generic class type
     * @return  an object of type T from the json. Returns null if json is null.
     */
    private <T> T mapJsonData(String json, Class<T> dataClass) {
        return new Gson().fromJson(json, dataClass);
    }

    /**
     * Parse JSON response from a given URL.
     *
     * @param   url URL that provides JSON
     * @return  JSONObject for a given URL
     * @throws  IOException thrown if JSON cannot be parsed from URL string, or if we can't connect to the URL
     */
    private String getJsonResponseFromUrl(String url) throws IOException {
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
        return response;
    }
}