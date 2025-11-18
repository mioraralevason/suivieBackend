package com.suivilbcft.suivibackend.dto.response;

import java.time.LocalDateTime;

public class InstitutionalInfoResponse {
    private boolean needsCompletion;
    private boolean isCompleted;
    private boolean isValidated;
    private LocalDateTime completedInfoAt;
    private LocalDateTime validatedAt;
    private String message;

    public InstitutionalInfoResponse(boolean needsCompletion, boolean isCompleted, boolean isValidated, 
                                    LocalDateTime completedInfoAt, LocalDateTime validatedAt, String message) {
        this.needsCompletion = needsCompletion;
        this.isCompleted = isCompleted;
        this.isValidated = isValidated;
        this.completedInfoAt = completedInfoAt;
        this.validatedAt = validatedAt;
        this.message = message;
    }

    // Getters and setters
    public boolean isNeedsCompletion() {
        return needsCompletion;
    }

    public void setNeedsCompletion(boolean needsCompletion) {
        this.needsCompletion = needsCompletion;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        this.isValidated = validated;
    }

    public LocalDateTime getCompletedInfoAt() {
        return completedInfoAt;
    }

    public void setCompletedInfoAt(LocalDateTime completedInfoAt) {
        this.completedInfoAt = completedInfoAt;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}