package pl.wmsdev.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wmsdev.auth.dto.*;
import pl.wmsdev.auth.service.AuthService;
import pl.wmsdev.auth.service.UsosTokenService;
import pl.wmsdev.usos4j.model.auth.UsosAccessToken;
import pl.wmsdev.usos4j.model.auth.UsosRequestToken;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsosTokenService usosTokenService;

    @PostMapping( "/request-token")
    public ResponseEntity<RequestTokenResponse> requestToken(HttpServletRequest request,
                                                             @RequestBody RequestTokenRequest tokenRequest) {
        return ResponseEntity.ok(usosTokenService.getRequestToken(tokenRequest.universityId(), request.getHeader("Origin")));
    }

    @PostMapping("/access-token")
    public ResponseEntity<AuthResponse> accessToken(@RequestBody AccessRequest accessRequest) {
        UsosRequestToken usosRequestToken = new UsosRequestToken(accessRequest.requestToken(), accessRequest.tokenSecret());
        UsosAccessToken accessToken = usosTokenService.getAccessToken(usosRequestToken, accessRequest.verifier(), accessRequest.universityId());

        return ResponseEntity.ok(authService.authenticate(
                AuthRequest.builder()
                        .accessToken(accessToken.getToken())
                        .accessTokenSecret(accessToken.getTokenSecret())
                        .universityId(accessRequest.universityId())
                        .build()
                ));
    }
}
