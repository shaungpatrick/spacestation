package com.api.spacestation.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

import java.time.*;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class WeatherDataTest {

    private LocalTime mockZonedSunSetTime;

    private LocalTime mockZonedSunRiseTime;

    @Before
    public void setup() {
        mockZonedSunSetTime = LocalTime.of(17,0);
        mockZonedSunRiseTime = LocalTime.of(6,0);
    }

    @Test
    public void testIsAfterSunSetIsTrue() {
        LocalTime mockZonedTime = LocalTime.of(20,0);

        boolean result = mockZonedTime.compareTo(mockZonedSunSetTime) >= 1
                || mockZonedTime.compareTo(mockZonedSunRiseTime) < 0;

        assertTrue(result);

        mockZonedTime = LocalTime.of(3,0);

        result = mockZonedTime.compareTo(mockZonedSunSetTime) >= 1
                || mockZonedTime.compareTo(mockZonedSunRiseTime) < 0;

        assertTrue(result);
    }

    @Test
    public void testIsAfterSunSetIsFalse() {
        LocalTime mockZonedTime = LocalTime.of(10,0);

        boolean result = mockZonedTime.compareTo(mockZonedSunSetTime) >= 1
                || mockZonedTime.compareTo(mockZonedSunRiseTime) < 0;

        assertFalse(result);
    }
}