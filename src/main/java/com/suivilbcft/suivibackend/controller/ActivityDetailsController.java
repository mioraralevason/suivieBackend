package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.service.ActivityDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/institution/activity")
public class ActivityDetailsController {

    private final ActivityDetailsService activityDetailsService;

    public ActivityDetailsController(ActivityDetailsService activityDetailsService) {
        this.activityDetailsService = activityDetailsService;
    }

    @GetMapping("/is-basic-info-completed/{institutionId}")
    public ResponseEntity<Boolean> isBasicInfoCompleted(@PathVariable String institutionId) {
        boolean isCompleted = activityDetailsService.isBasicInfoCompleted(institutionId);
        return ResponseEntity.ok(isCompleted);
    }
}