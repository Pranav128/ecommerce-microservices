package com.app.ecommerce.user_service.controller;

import com.app.ecommerce.user_service.dto.request.LoginRequest;
import com.app.ecommerce.user_service.dto.request.SignupRequest;
import com.app.ecommerce.user_service.dto.response.Response;
import com.app.ecommerce.user_service.service.specification.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth-Controller", description = "Auth-Controller APIs for login and signup " )
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    //User service
    private final UserService userService;

    @Operation(summary = "login request",description = "Login API for user")
    @ApiResponse(responseCode = "400", description = "Invalid user request!")
    @ApiResponse(responseCode = "200", description = "User logged in successfully!")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Parameter(description = "loginRequest", required = true)
                                       @RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/signup")
    @Operation(summary = "signup request", description = "Signup API for user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User registered successfully!"),})
    public ResponseEntity<Response> signup(@Parameter(description = "signupRequest", required = true)
                                            @RequestBody @Valid SignupRequest signupRequest) {
        boolean isRegistered = userService.registerUser(signupRequest);
        return isRegistered ? ResponseEntity.ok(new Response("User registered successfully")) : ResponseEntity.badRequest().body(new Response("Error: Email is already in use!"));
    }

    //simple text request data only email without quotes
    @PostMapping("/forgot-password")
    @Operation(summary = "forgotPassword request",description = "forgotPassword API for user")
    @ApiResponse(responseCode = "200", description = "Password reset link sent!")
    public ResponseEntity<Response> forgotPassword(@Parameter(description = "Email", required = true)
            @RequestBody String email) {
        userService.generateResetToken(email);
        return ResponseEntity.ok(new Response("Password reset link sent!"));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "resetPassword request", description = "resetPassword API for user")
    @ApiResponse(responseCode = "200", description = "Password successfully reset!")
    @ApiResponse(responseCode = "400", description = "Invalid or expired token.")
    public ResponseEntity<Response> resetPassword(@Parameter(description = "token", required = true)
                                                      @RequestParam String token,
                                                  @Parameter(description = "newPassword", required = true)
                                                  @RequestBody String newPassword) {
        boolean success = userService.resetPassword(token, newPassword);
        return success ? ResponseEntity.ok(new Response("Password successfully reset!")) : ResponseEntity.badRequest().body(new Response("Invalid or expired token."));
    }
}