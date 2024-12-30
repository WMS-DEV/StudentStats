package pl.wmsdev.auth.dto;

public record AccessRequest(String requestToken, String tokenSecret, String verifier, String universityId) {}
