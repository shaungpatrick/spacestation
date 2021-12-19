package com.api.spacestation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "spacestation")
public class SpaceStationConfig {

    @NotNull
    @NotEmpty
    private String api;

    public String getApi() {
        return this.api;
    }

    public void setApi(String apiIn) {
        this.api = apiIn;
    }

}
