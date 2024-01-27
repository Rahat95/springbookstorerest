package com.rpp95.bookstorerest.controllers;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rpp95.bookstorerest.entities.LoginRequest;
import com.rpp95.bookstorerest.entities.User;
import com.rpp95.bookstorerest.services.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        return this.authService.registerUser(user);
    }

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        User user = (User)authentication.getPrincipal();

        Instant now = Instant.now();
        long expiry = 3600L; //60mins => 1 hour
        
        String scope = authentication.getAuthorities().stream()
                                                      .map(GrantedAuthority::getAuthority)
                                                      .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("example.com")
                                          .issuedAt(now)
                                          .expiresAt(now.plusSeconds(expiry))
                                          .subject(String.format("%d", user.getId()))
                                          .claim("roles", scope)
                                          .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
