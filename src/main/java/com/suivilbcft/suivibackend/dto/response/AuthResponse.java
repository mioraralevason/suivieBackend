package com.suivilbcft.suivibackend.dto.response;

public class AuthResponse {
    private String token;
    private String role;
    private boolean needsCompletion;
    private boolean isCompleted;
    private boolean isValidated;

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;

        // Parse role to determine completion status
        if (role != null && role.contains(":")) {
            String[] parts = role.split(":");
            this.role = parts[0]; // actual role
            if (parts.length > 1) {
                this.needsCompletion = "needs_completion".equals(parts[1]);
                this.isCompleted = !this.needsCompletion;
            }
        } else {
            this.needsCompletion = false;
            this.isCompleted = true;
        }
        this.isValidated = false; // Will be set based on additional logic if needed
    }

    // Getters/Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isNeedsCompletion() { return needsCompletion; }
    public void setNeedsCompletion(boolean needsCompletion) { this.needsCompletion = needsCompletion; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { this.isCompleted = completed; }
    public boolean isValidated() { return isValidated; }
    public void setValidated(boolean validated) { this.isValidated = validated; }
}