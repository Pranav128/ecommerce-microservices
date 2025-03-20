package com.app.ecommerce.product_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "ErrorResponse", description = "error response model for product service")
public class ErrorResponse {
    @Schema(name = "message", description = "error message"  )
    private String message;

    @Schema(name = "code", description = "error code"  )
    private String code;
}
