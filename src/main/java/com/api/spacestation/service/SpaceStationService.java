package com.api.spacestation.service;

import com.api.spacestation.config.SpaceStationConfig;
import com.api.spacestation.config.WeatherConfig;
import com.api.spacestation.data.SpaceStationData;
import com.api.spacestation.data.WeatherData;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class SpaceStationService {

    @Autowired
    private SpaceStationConfig spaceStationConfig;

    @Autowired
    private WeatherConfig weatherConfig;


    public boolean getStationVisibility() {
        String spaceStationApiResponse = getJsonResponseFromUrl(spaceStationConfig.getApi());

        SpaceStationData spaceStationData = new Gson().fromJson(spaceStationApiResponse, SpaceStationData.class);

        String weatherApiResponse = getJsonResponseFromUrl(weatherConfig.getApi()
                + "?lat=" + spaceStationData.getLatitude()
                + "&lon=" + spaceStationData.getLongitude()
                + "&key=" + weatherConfig.getApiKey());

        WeatherData weatherData = new Gson().fromJson(weatherApiResponse, WeatherData.class);

        boolean isAfterSunSet = weatherData.isAfterSunSet();
        double cloudCoverage = weatherData.getCloudCoverage();

        return cloudCoverage <= 30.0 && isAfterSunSet;
    }

    /**
     * Parse JSON response from a given URL.
     *
     * @param url URL that provides JSON.
     * @return JSONObject for a given URL.
     */
    private String getJsonResponseFromUrl(String url) {
        String response = null;
        try {
            URL apiUrl = new URL(url);
            String apiStringResponse = IOUtils.toString(apiUrl, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(apiStringResponse);
            response = jsonObject.toString();
        } catch (IOException e) {
            System.out.println(e);
        }
        return response;
    }
}