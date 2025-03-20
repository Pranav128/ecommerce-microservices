package com.app.ecommerce.user_service.controller;

import com.app.ecommerce.user_service.dto.UserDto;
import com.app.ecommerce.user_service.dto.request.SignupRequest;
import com.app.ecommerce.user_service.service.specification.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "APIs for managing users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get the current user", description = "Returns the details of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader ("X-User-Email") String email) {
        return ResponseEntity.ok(userService.getCurrentUser(email));
    }

    @PutMapping
    @Operation(summary = "Update the current user", description = "Updates the details of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignupRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public UserDto updateUser(@RequestBody @Valid SignupRequest user) {
        return userService.updateUser(user);
    }

    @Operation(summary = "Delete the current user", description = "Deletes the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}