package com.api.spacestation.controller;


import com.api.spacestation.service.SpaceStationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller interface
 */
@RestController
@RequestMapping("/space-station")

public class SpaceStationController {

    @Autowired
    private SpaceStationService spaceStationService;

    @ApiOperation(
            value = "Return ture if the international space station is currently visible.",
            response = SpaceStationService.class,
            notes = "This interface will return either true or false depending on " +
                    "if the international space station is currently visible")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Boolean value correctly returned based on space station position."),
            @ApiResponse(code = 500, message = "Internal server error. Please try again later.")
    })
    @GetMapping("/is-visible")
    public boolean isVisible() throws IOException {
        return spaceStationService.getStationVisibility();
    }

}
