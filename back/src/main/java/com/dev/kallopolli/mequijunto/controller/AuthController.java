package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.dto.LoginRequest;
import com.dev.kallopolli.mequijunto.dto.LoginResponse;
import com.dev.kallopolli.mequijunto.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        String token = jwtService.gerarToken(authentication);
        return ResponseEntity.ok(new LoginResponse(
                token,
                "Bearer",
                JwtService.EXPIRACAO_SEGUNDOS
        ));
    }
}
