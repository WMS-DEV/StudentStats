package pl.wmsdev.auth.dto;

import lombok.Builder;

@Builder
public record AuthRequest(String accessToken, String accessTokenSecret, String universityId) {}

