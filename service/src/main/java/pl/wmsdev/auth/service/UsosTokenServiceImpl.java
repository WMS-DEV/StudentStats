package pl.wmsdev.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wmsdev.auth.dto.RequestTokenResponse;
import pl.wmsdev.usos.factory.UsosFactory;
import pl.wmsdev.usos4j.model.auth.UsosAccessToken;
import pl.wmsdev.usos4j.model.auth.UsosRequestToken;

@Component
@RequiredArgsConstructor
public class UsosTokenServiceImpl implements UsosTokenService {

    private final UsosFactory usosFactory;

    @Override
    public RequestTokenResponse getRequestToken(String universityId, String callbackUrl) {
        var usos = usosFactory.createUsos(universityId, callbackUrl);

        var requestToken = usos.getRequestToken();
        var authorizationUrl = usos.getAuthorizationUrl(requestToken);

        return RequestTokenResponse.builder()
                .requestToken(requestToken.getToken())
                .tokenSecret(requestToken.getTokenSecret())
                .authorizationUrl(authorizationUrl)
                .build();
    }

    @Override
    public UsosAccessToken getAccessToken(UsosRequestToken requestToken, String verifier, String universityId) {
        var usos = usosFactory.createUsos(universityId);

	    return usos.getAccessToken(requestToken, verifier);
    }
}
