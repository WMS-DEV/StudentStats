package pl.wmsdev.auth.service;

import pl.wmsdev.auth.dto.RequestTokenResponse;
import pl.wmsdev.usos4j.model.auth.UsosAccessToken;
import pl.wmsdev.usos4j.model.auth.UsosRequestToken;

public interface UsosTokenService {
    RequestTokenResponse getRequestToken(String universityId, String callbackUrl);

    UsosAccessToken getAccessToken(UsosRequestToken requestToken, String verifier, String universityId);
}
