package com.app.ecommerce.user_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(name = "Signup",description = "SignupRequest model")
@Data
public class SignupRequest {
    @Schema(name = "firstName", description = "firstName field of to create user"  )
    @NotNull(message = "First name is required")
    private String firstName;

    @Schema(name = "lastName", description = "lastName field of to create user"  )
    @NotNull(message = "Last name is required")
    private String lastName;

    @Schema(name = "email", description = "unique email field of to create user"  )
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(name = "password", description = "password field of to create user"  )
    @NotNull(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Schema(name = "mobileNumber", description = "mobileNumber field of to create user"  )
    @NotNull(message = "Mobile number is required")
    @Length(min = 10, max = 10, message = "Mobile number must be 10 digits")
    private String mobileNumber;
}
