package com.api.spacestation.service;

import com.api.spacestation.config.SpaceStationConfig;
import com.api.spacestation.config.WeatherConfig;
import com.api.spacestation.data.SpaceStationData;
import com.api.spacestation.data.WeatherData;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpaceStationService.class, IOUtils.class, JSONObject.class, SpaceStationConfig.class,
        WeatherConfig.class, SpaceStationData.class, WeatherData.class, URL.class, Gson.class})
public class SpaceStationServiceTest {

    private static final String ISS_JSON_RESPONSE = "file:src/test/resources/iss_response.json";

    private static final String WEATHER_JSON_RESPONSE = "file:src/test/resources/weather_response.json";

    private SpaceStationConfig mockSpaceStationConfig;

    private WeatherConfig mockWeatherConfig;

    private SpaceStationData mockSpaceStationData;

    private WeatherData mockWeatherData;

    private Gson mockGson;

    @InjectMocks
    private SpaceStationService mockSpaceStationService;


    @Before
    public void setUp() {
        mockSpaceStationService = new SpaceStationService();
        mockSpaceStationConfig = PowerMockito.mock(SpaceStationConfig.class);
        mockWeatherConfig = PowerMockito.mock(WeatherConfig.class);
        mockSpaceStationData = PowerMockito.mock(SpaceStationData.class);
        mockWeatherData = PowerMockito.mock(WeatherData.class);
        mockGson = PowerMockito.mock(Gson.class);
        Whitebox.setInternalState(mockSpaceStationService, "spaceStationConfig", mockSpaceStationConfig);
        Whitebox.setInternalState(mockSpaceStationService, "weatherConfig", mockWeatherConfig);

    }

    @Test
    public void testSpaceStationVisibilityIsTrue() throws Exception {
        URL mockSpaceStationApiUrl = new URL(ISS_JSON_RESPONSE);
        URL mockWeatherApiUrl = new URL(WEATHER_JSON_RESPONSE);
        PowerMockito.when(mockSpaceStationConfig.getApi()).thenReturn("");
        PowerMockito.when(mockWeatherConfig.getApi()).thenReturn("");

        PowerMockito.whenNew(URL.class)
                .withParameterTypes(String.class)
                .withArguments(anyString())
                .thenReturn(mockSpaceStationApiUrl)
                .thenReturn(mockWeatherApiUrl);

        PowerMockito.whenNew(Gson.class).withAnyArguments().thenReturn(mockGson);
        PowerMockito.when(mockGson.fromJson(anyString(), eq(SpaceStationData.class))).thenReturn(mockSpaceStationData);
        PowerMockito.when(mockGson.fromJson(anyString(), eq(WeatherData.class))).thenReturn(mockWeatherData);

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(true);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(30.0);

        assertTrue(mockSpaceStationService.getStationVisibility());
    }

    @Test
    public void testSpaceStationVisibilityIsFalse() throws Exception {
        URL mockSpaceStationApiUrl = new URL(ISS_JSON_RESPONSE);
        URL mockWeatherApiUrl = new URL(WEATHER_JSON_RESPONSE);
        PowerMockito.when(mockSpaceStationConfig.getApi()).thenReturn("");
        PowerMockito.when(mockWeatherConfig.getApi()).thenReturn("");

        PowerMockito.whenNew(URL.class)
                .withParameterTypes(String.class)
                .withArguments(anyString())
                .thenReturn(mockSpaceStationApiUrl)
                .thenReturn(mockWeatherApiUrl);

        PowerMockito.whenNew(Gson.class).withAnyArguments().thenReturn(mockGson);
        PowerMockito.when(mockGson.fromJson(anyString(), eq(SpaceStationData.class))).thenReturn(mockSpaceStationData);
        PowerMockito.when(mockGson.fromJson(anyString(), eq(WeatherData.class))).thenReturn(mockWeatherData);

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(false);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(30.0);

        assertFalse(mockSpaceStationService.getStationVisibility());

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(true);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(40.0);

        assertFalse(mockSpaceStationService.getStationVisibility());
    }

    @Test (expected = IOException.class)
    public void testSpaceStationVisibilityIOExceptionFromUrl() throws Exception {
        PowerMockito.when(mockSpaceStationConfig.getApi()).thenReturn("");
        PowerMockito.when(mockWeatherConfig.getApi()).thenReturn("");

        PowerMockito.whenNew(URL.class)
                .withParameterTypes(String.class)
                .withArguments(anyString())
                .thenThrow(new IOException());

        mockSpaceStationService.getStationVisibility();
    }

}