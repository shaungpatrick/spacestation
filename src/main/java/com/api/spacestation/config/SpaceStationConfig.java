package com.api.spacestation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "spacestation")
public class SpaceStationConfig {

    @NotNull
    @NotEmpty
    @Value("${SPACE_STATION_API_URL:#{null}}")
    private String apiUrl;

    public String getApiUrl() {
        return this.apiUrl;
    }

    public void setApiUrl(String apiIn) {
        this.apiUrl = apiIn;
    }

}
