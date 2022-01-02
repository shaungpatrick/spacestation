package com.api.spacestation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    public static final String DESCRIPTION_TAG = "SpaceStationController";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(""))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(DESCRIPTION_TAG, "Details on interface for space station visibility"))
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Space Station")
                .description("API for positional and visibility data from the International Space Station")
                .contact(new Contact("ShaunGPatrick",
                        "https://github.com/ShaunGPatrick/spacestation",
                        "ShaunGPatrick@gmail.com")).build();
    }

}
