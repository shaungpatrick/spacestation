package com.api.spacestation.data;

import com.api.spacestation.keys.WeatherKeys;
import com.google.gson.annotations.SerializedName;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class WeatherData {

    @SerializedName("data")
    private List<Map<Object, Object>> weatherDataList;

    public List<Map<Object, Object>> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<Map<Object, Object>> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    public String getSunSetTime() {
        return getSunriseSunsetCalculator().getOfficialSunsetForDate(Calendar.getInstance());
    }

    public String getSunRiseTime() {
        return getSunriseSunsetCalculator().getOfficialSunriseForDate(Calendar.getInstance());
    }

    public Double getCloudCoverage() {
        return Double.parseDouble(weatherDataList.get(0).get(WeatherKeys.CLOUDS).toString());
    }

    public String getTimeZone() {
        return weatherDataList.get(0).get(WeatherKeys.TIMEZONE).toString();
    }

    public String getLatitude() {
        return weatherDataList.get(0).get(WeatherKeys.LATITUDE).toString();
    }

    public String getLongitude() {
        return weatherDataList.get(0).get(WeatherKeys.LONGITUDE).toString();
    }

    /**
     * Check if zoned time is after sunset time.
     *
     * @return true if zoned time is greater than sunset time.
     */
    public boolean isAfterSunSet() {
        DateTimeFormatter sunSetSunRiseTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        ZonedDateTime localZonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of(getTimeZone()));

        LocalTime zonedSunSetTime = LocalTime.parse(getSunSetTime(), sunSetSunRiseTimeFormatter);
        LocalTime zonedSunRiseTime = LocalTime.parse(getSunRiseTime(), sunSetSunRiseTimeFormatter);

        LocalTime zonedTime = getHourMinuteTime(zonedDateTime.getHour(), zonedDateTime.getMinute());

        // Must be after sunset and before sunrise.
        return zonedTime.compareTo(zonedSunSetTime) >= 1 || zonedTime.compareTo(zonedSunRiseTime) < 0;
    }

    /**
     * Append zeros to hours and minutes.
     *
     * @param hour hour as integer.
     * @param minute minute as integer.
     * @return HourMinute time.
     */
    private LocalTime getHourMinuteTime(int hour, int minute) {
        return LocalTime.parse(String.format("%02d", hour) + ":" + String.format("%02d", minute));
    }

    /**
     * Create new SunriseSunsetCalculator object.
     *
     * @return new SunriseSunsetCalculator
     */
    private SunriseSunsetCalculator getSunriseSunsetCalculator() {
        Location location = new Location(getLatitude(), getLongitude());
        return new SunriseSunsetCalculator(location, getTimeZone());
    }

}
