package com.app.ecommerce.user_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Error Response model")
public class ErrorResponse {
    @Schema(name = "message", description = "error message"  )
    private String message;
    @Schema(name = "status code", description = "error status code"  )
    private String code;
}