package com.api.spacestation.response;

import com.api.spacestation.data.SpaceStationData;
import com.api.spacestation.data.WeatherData;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Response.class, JSONObject.class, Gson.class, URL.class})
public class ResponseTest {

    private static final String ISS_JSON_RESPONSE = "file:src/test/resources/iss_response.json";

    private static final String WEATHER_JSON_RESPONSE = "file:src/test/resources/weather_response.json";

    private Response response;

    @Before
    public void setUp() {
        response = Response.getInstance();
    }

    @Test
    public void testResponseSpaceStationData() throws Exception {
        URL mockSpaceStationApiUrl = new URL(ISS_JSON_RESPONSE);

        PowerMockito.whenNew(URL.class)
                .withParameterTypes(String.class)
                .withArguments(anyString())
                .thenReturn(mockSpaceStationApiUrl);

        SpaceStationData spaceStationData = response.getJsonResponse("someurl", SpaceStationData.class);

        assertEquals("-15.4926", spaceStationData.getLatitude());
        assertEquals("-41.8004", spaceStationData.getLongitude());
    }

    @Test
    public void testResponseWeatherData() throws Exception {
        URL mockWeatherApiUrl = new URL(WEATHER_JSON_RESPONSE);

        PowerMockito.whenNew(URL.class)
                .withParameterTypes(String.class)
                .withArguments(anyString())
                .thenReturn(mockWeatherApiUrl);

        WeatherData weatherData = response.getJsonResponse("someurl", WeatherData.class);

        assertEquals("-15.49", weatherData.getLatitude());
        assertEquals("-41.8", weatherData.getLongitude());
        assertEquals((Double) 77.0, weatherData.getCloudCoverage());
    }

    @Test (expected = IOException.class)
    public void testResponseIOExceptionFromUrl() throws Exception {
        PowerMockito.whenNew(URL.class)
                .withParameterTypes(String.class)
                .withArguments(anyString())
                .thenThrow(new IOException());

        response.getJsonResponse("someurl", Object.class);
    }

}