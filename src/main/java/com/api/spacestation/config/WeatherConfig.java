package com.api.spacestation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@Component
@ConfigurationProperties(prefix = "weather")
public class WeatherConfig {

    @NotNull
    @NotEmpty
    @Value("${WEATHER_API_URL:#{null}}")
    private String apiUrl;

    @NotNull
    @NotEmpty
    @Value("${WEATHER_API_KEY:#{null}}")
    private String apiKey;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
