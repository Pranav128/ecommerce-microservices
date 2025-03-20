package com.app.ecommerce.user_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(name = "LoginRequest", description = "LoginRequest model")
@Data
public class LoginRequest {
    @Schema(name = "email", description = "unique email field for login")
    @NotNull(message = "Email is required")
    private String email;

    @Schema(name = "password", description = "password field for login")
    @NotNull(message = "Password is required")
    private String password;
}