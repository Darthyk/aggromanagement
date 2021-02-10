package com.darthyk.aggromanagement.controller;

import com.darthyk.aggromanagement.model.entity.Culture;
import com.darthyk.aggromanagement.model.entity.Peas;
import com.darthyk.aggromanagement.repository.CultureRepository;
import com.darthyk.aggromanagement.repository.PeasRepository;
import com.darthyk.aggromanagement.service.CalculationService;
import com.darthyk.aggromanagement.service.CommonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MaintananceController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private PeasRepository peasRepository;

    @GetMapping("/health")
    public ResponseEntity<String> getHealthStatus() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/fillWorkTypes")
    public ResponseEntity<String> fillWorkTypes() {
        commonService.fillWorkTypes();
        return ResponseEntity.ok("Work types are filled");
    }

    @GetMapping("/getPeriods")
    public void getPeriods() {
        calculationService.calcultate();
    }

}
