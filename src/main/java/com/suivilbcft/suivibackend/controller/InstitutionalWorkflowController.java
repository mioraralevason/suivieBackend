package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.dto.response.InstitutionalInfoResponse;
import com.suivilbcft.suivibackend.service.InstitutionalWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/institution/workflow")
public class InstitutionalWorkflowController {

    private final InstitutionalWorkflowService institutionalWorkflowService;

    public InstitutionalWorkflowController(InstitutionalWorkflowService institutionalWorkflowService) {
        this.institutionalWorkflowService = institutionalWorkflowService;
    }

    @GetMapping("/status")
    public ResponseEntity<InstitutionalInfoResponse> getCurrentUserStatus() {
        InstitutionalInfoResponse response = institutionalWorkflowService.checkCurrentUserStatus();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{institutionId}")
    public ResponseEntity<InstitutionalInfoResponse> getInstitutionStatus(@PathVariable String institutionId) {
        InstitutionalInfoResponse response = institutionalWorkflowService.checkInstitutionStatus(institutionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/can-access-features/{institutionId}")
    public ResponseEntity<Boolean> canAccessNormalFeatures(@PathVariable String institutionId) {
        boolean canAccess = institutionalWorkflowService.canAccessNormalFeatures(institutionId);
        return ResponseEntity.ok(canAccess);
    }

    @GetMapping("/is-restricted-to-basic/{institutionId}")
    public ResponseEntity<Boolean> isRestrictedToBasicInfo(@PathVariable String institutionId) {
        boolean isRestricted = institutionalWorkflowService.restrictedToBasicInfo(institutionId);
        return ResponseEntity.ok(isRestricted);
    }
}