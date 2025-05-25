package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.ltdt.SocialNetwork.dtos.auth.JWTToken;
import vn.ltdt.SocialNetwork.dtos.auth.LoginRequest;
import vn.ltdt.SocialNetwork.dtos.auth.RegisterRequest;
import vn.ltdt.SocialNetwork.models.User;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public JWTToken login(LoginRequest loginRequest) {
        log.info("Login request: {}", loginRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  jwtService.generateToken((User) authentication.getPrincipal());
    }

    public JWTToken register(RegisterRequest registerRequest) {
        return jwtService.generateToken(userService.createUser(registerRequest));
    }
}
