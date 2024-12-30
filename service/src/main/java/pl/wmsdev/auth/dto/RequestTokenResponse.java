package pl.wmsdev.auth.dto;

import lombok.Builder;

@Builder
public record RequestTokenResponse(String requestToken, String tokenSecret, String authorizationUrl) {}
