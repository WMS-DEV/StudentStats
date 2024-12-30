package pl.wmsdev.auth.service;

import pl.wmsdev.auth.dto.AuthRequest;
import pl.wmsdev.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
}
