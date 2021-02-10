package com.darthyk.aggromanagement.controller;

import com.darthyk.aggromanagement.service.ParserService;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parse")
public class ParseController {

    @Autowired
    private ParserService parserService;

    @GetMapping("/peas")
    public ResponseEntity<String> parsePeas() throws IOException, URISyntaxException {
        parserService.startPeasParse();
        return ResponseEntity.ok("Peas parsing started");
    }

    @GetMapping("/winterWheat")
    public ResponseEntity<String> parseWinterWheat() throws IOException, URISyntaxException {
        parserService.startWinterWheatParse();
        return ResponseEntity.ok("Winter wheat parsing started");
    }

    @GetMapping("/springWheat")
    public ResponseEntity<String> parseSpringWheat() throws IOException, URISyntaxException {
        parserService.startSpringWheatParse();
        return ResponseEntity.ok("Spring wheat parsing started");
    }

    @GetMapping("/sugarBeet")
    public ResponseEntity<String> parseSugarBeet() throws IOException, URISyntaxException {
        parserService.startSugarBeetParse();
        return ResponseEntity.ok("Sugar beet parsing started");
    }

    @GetMapping("/productionRate")
    public ResponseEntity<String> parseProductionRate() throws IOException, URISyntaxException {
        parserService.startProductionRateParse();
        return ResponseEntity.ok("Production rate parsing started");
    }

    @GetMapping("/sowingStructure")
    public ResponseEntity<String> parseSowingStructure() throws IOException, URISyntaxException {
        parserService.startSowingStructureParse();
        return ResponseEntity.ok("Sowing structure parsing started");
    }

    @GetMapping("/workshiftNumber")
    public ResponseEntity<String> parseWorkshiftNumber() throws IOException, URISyntaxException {
        parserService.startWorkshiftNumberParse();
        return ResponseEntity.ok("Workshift number parsing started");
    }

    @GetMapping("/vehicle")
    public ResponseEntity<String> parseVehicle() throws IOException, URISyntaxException {
        parserService.startVehicleParse();
        return ResponseEntity.ok("Vehicle parsing started");
    }

    @GetMapping("/trailer")
    public ResponseEntity<String> parseTrailer() throws IOException, URISyntaxException {
        parserService.startTrailerParse();
        return ResponseEntity.ok("Trailer parsing started");
    }
}
