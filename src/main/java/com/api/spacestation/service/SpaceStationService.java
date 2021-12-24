package com.api.spacestation.service;

import com.api.spacestation.config.SpaceStationConfig;
import com.api.spacestation.config.WeatherConfig;
import com.api.spacestation.data.SpaceStationData;
import com.api.spacestation.data.WeatherData;
import com.api.spacestation.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
public class SpaceStationService {

    @Autowired
    private SpaceStationConfig spaceStationConfig;

    @Autowired
    private WeatherConfig weatherConfig;

    private Response response;

    @PostConstruct
    public void init() {
        response = Response.getInstance();
    }

    /**
     * Check if the International Space Station is currently visible.
     *
     * @return true if the International Space Station is currently visible
     * @throws IOException thrown if we can't get a response from the internal APIs
     */
    public boolean getStationVisibility() throws IOException {
        SpaceStationData spaceStationData = response.getJsonResponse(
                spaceStationConfig.getApiUrl(),
                SpaceStationData.class);

        WeatherData weatherData = response.getJsonResponse(weatherConfig.getApiUrl()
                + "?lat=" + spaceStationData.getLatitude()
                + "&lon=" + spaceStationData.getLongitude()
                + "&key=" + weatherConfig.getApiKey(),
                WeatherData.class);

        boolean isAfterSunSet = weatherData.isAfterSunSet();
        double cloudCoverage = weatherData.getCloudCoverage();

        return cloudCoverage <= 30.0 && isAfterSunSet;
    }
}