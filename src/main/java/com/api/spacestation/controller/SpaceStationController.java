package com.api.spacestation.controller;


import com.api.spacestation.service.SpaceStationService;
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

    @GetMapping("/is-visible")
    public boolean isVisible() throws IOException {
        return spaceStationService.getStationVisibility();
    }

}
