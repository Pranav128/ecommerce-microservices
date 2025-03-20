package com.app.ecommerce.user_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(name = "Response", description = "Response model")
public class Response {
    @Schema(name = "response", description = "response message")
    private String response;
}
