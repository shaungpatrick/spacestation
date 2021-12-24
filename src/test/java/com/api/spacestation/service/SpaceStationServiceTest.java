package com.api.spacestation.service;

import com.api.spacestation.config.SpaceStationConfig;
import com.api.spacestation.config.WeatherConfig;
import com.api.spacestation.data.SpaceStationData;
import com.api.spacestation.data.WeatherData;
import com.api.spacestation.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpaceStationService.class, SpaceStationConfig.class, WeatherConfig.class, SpaceStationData.class,
        WeatherData.class, Response.class})
public class SpaceStationServiceTest {

    private SpaceStationConfig mockSpaceStationConfig;

    private WeatherConfig mockWeatherConfig;

    private SpaceStationData mockSpaceStationData;

    private WeatherData mockWeatherData;

    private Response mockResponse;

    @InjectMocks
    private SpaceStationService mockSpaceStationService;

    @Before
    public void setUp() {
        mockResponse = PowerMockito.mock(Response.class);
        this.mockSpaceStationService.init();
        mockSpaceStationConfig = PowerMockito.mock(SpaceStationConfig.class);
        mockWeatherConfig = PowerMockito.mock(WeatherConfig.class);
        mockSpaceStationData = PowerMockito.mock(SpaceStationData.class);
        mockWeatherData = PowerMockito.mock(WeatherData.class);
        Whitebox.setInternalState(mockSpaceStationService, "spaceStationConfig", mockSpaceStationConfig);
        Whitebox.setInternalState(mockSpaceStationService, "weatherConfig", mockWeatherConfig);
        Whitebox.setInternalState(mockSpaceStationService, "response", mockResponse);

    }

    @Test
    public void testSpaceStationVisibilityIsTrue() throws Exception {
        PowerMockito.when(mockSpaceStationConfig.getApiUrl()).thenReturn("");
        PowerMockito.when(mockWeatherConfig.getApiUrl()).thenReturn("");

        PowerMockito.when(mockResponse.getJsonResponse(anyString(), eq(SpaceStationData.class)))
                .thenReturn(mockSpaceStationData);
        PowerMockito.when(mockResponse.getJsonResponse(anyString(), eq(WeatherData.class))).
                thenReturn(mockWeatherData);

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(true);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(30.0);

        assertTrue(mockSpaceStationService.getStationVisibility());

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(true);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(10.0);

        assertTrue(mockSpaceStationService.getStationVisibility());
    }

    @Test
    public void testSpaceStationVisibilityIsFalse() throws Exception {
        PowerMockito.when(mockSpaceStationConfig.getApiUrl()).thenReturn("");
        PowerMockito.when(mockWeatherConfig.getApiUrl()).thenReturn("");

        PowerMockito.when(mockResponse.getJsonResponse(anyString(), eq(SpaceStationData.class)))
                .thenReturn(mockSpaceStationData);
        PowerMockito.when(mockResponse.getJsonResponse(anyString(), eq(WeatherData.class))).
                thenReturn(mockWeatherData);

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(false);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(30.0);

        assertFalse(mockSpaceStationService.getStationVisibility());

        PowerMockito.when(mockWeatherData.isAfterSunSet()).thenReturn(true);
        PowerMockito.when(mockWeatherData.getCloudCoverage()).thenReturn(40.0);

        assertFalse(mockSpaceStationService.getStationVisibility());
    }

}