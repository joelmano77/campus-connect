package com.example.campus_connect.controller;

import com.example.campus_connect.dto.AuthRequest;
import com.example.campus_connect.model.User;
import com.example.campus_connect.security.JwtUtil;
import com.example.campus_connect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "userId", registeredUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            logger.info("Attempting login for username: {}", authRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            User user = userService.findByUsername(authRequest.getUsername()).orElse(null);
            logger.info("Login successful for username: {}", authRequest.getUsername());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", Map.of(
                            "username", userDetails.getUsername(),
                            "role", user != null ? user.getRole() : null
                    )
            ));
        } catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for username: {}", authRequest.getUsername());
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        } catch (UsernameNotFoundException e) {
            logger.warn("User not found: {}", authRequest.getUsername());
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        } catch (AuthenticationException e) {
            logger.error("Authentication error for username: {}: {}", authRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Authentication failed"));
        } catch (Exception e) {
            logger.error("Unexpected error during login for username: {}: {}", authRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Login failed"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(jwtToken);
            User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return ResponseEntity.ok(Map.of(
                    "username", user.getUsername(),
                    "role", user.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
        }
    }
}
