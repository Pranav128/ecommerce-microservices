package com.app.ecommerce.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

@Tag(name="User Dto",description = "UserDto details of user")
@Getter
@Setter
public class UserDto {

    @Schema(name = "id", description = "id field for unique user")
    Long id;

    @Schema(name = "email", description = "email field for unique user" )
    String email;

    @Schema(name = "firstName", description = "firstName field of user" )
    String firstName;

    @Schema(name = "lastName", description = "lastName field of user" )
    String lastName;

    @Schema(name = "mobileNumber", description = "mobileNumber field of user" )
    private String mobileNumber;

    @Schema(name = "provider", description = "provider field of user either local or social account" )
    String provider;
}