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
    private String api;

    @NotNull
    @NotEmpty
    @Value("${WEATHER_API_KEY}")
    private String apiKey;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
