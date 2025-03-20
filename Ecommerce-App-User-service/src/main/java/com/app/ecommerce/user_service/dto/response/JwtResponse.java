package com.app.ecommerce.user_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(name = "JwtResponse", description = "JwtResponse model")
@AllArgsConstructor
public class JwtResponse {
    @Schema(name = "token", description = "JWT token"  )
    private String token;
}
