package pl.wmsdev.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wmsdev.auth.dto.AuthRequest;
import pl.wmsdev.auth.dto.AuthResponse;
import pl.wmsdev.security.JwtService;
import pl.wmsdev.user.model.User;
import pl.wmsdev.user.repository.UserRepository;
import pl.wmsdev.usos.factory.UsosFactory;
import pl.wmsdev.usos4j.model.auth.UsosAccessToken;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsosFactory usosFactory;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        var accessToken = request.accessToken();
        var accessTokenSecret = request.accessTokenSecret();
        var universityId = request.universityId();

        var userId = tryGetIdFromUsos(accessToken, accessTokenSecret, universityId);
        var user = getOrCreateUser(userId);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, userId));

        var jwtToken = jwtService.generateToken(accessToken, accessTokenSecret, universityId, user);

        return new AuthResponse(jwtToken);
    }

    private User getOrCreateUser(String userId) {
        return repository.findUserByUsername(userId).orElseGet(() -> createNewUser(userId));
    }

    private User createNewUser(String userId) {
        return repository.save(User.builder()
                .username(userId)
                .password(passwordEncoder.encode(userId))
                .build());
    }

    @SneakyThrows
    private String tryGetIdFromUsos(String token, String tokenSecret, String universityId) {
        try {
            var usos = usosFactory.getUsosUserApi(new UsosAccessToken(token, tokenSecret), universityId);
            return String.valueOf(usos.users().user().id());
        } catch (Exception ignored) {
            throw new CredentialNotFoundException("Incorrect access jwt. Login and try again");
        }
    }
}
