package pl.wmsdev.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (isJwtHeaderMissing(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtToken jwt = extractJWT(authorizationHeader);

        try {
            String userName = jwtService.extractUsername(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (isAuthenticationEmpty() && jwtService.isTokenValid(jwt, userDetails)) {
                authenticateUser(userDetails, request, jwt);
            }
        } catch (Exception ignored) {
            request.logout(); // login fail
        }
        filterChain.doFilter(request, response);
    }

    private boolean isJwtHeaderMissing(String authorizationHeader) {
        return authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
    }

    private JwtToken extractJWT(String authorizationHeader) {
        return new JwtToken(authorizationHeader.substring(7));
    }

    private boolean isAuthenticationEmpty() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void authenticateUser(UserDetails userDetails, HttpServletRequest request, JwtToken token) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                token.jwt(),
                userDetails.getAuthorities());

        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}

