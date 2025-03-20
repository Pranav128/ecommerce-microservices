package com.app.ecommerce.user_service.service.specification;

import com.app.ecommerce.user_service.dto.UserDto;
import com.app.ecommerce.user_service.dto.request.LoginRequest;
import com.app.ecommerce.user_service.dto.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    boolean registerUser(SignupRequest signupRequest) ;
    ResponseEntity<?> login(LoginRequest loginRequest) ;
    boolean resetPassword(String token, String newPassword) ;
    void generateResetToken(String email) ;
    UserDto getCurrentUser(String email) ;

    UserDto updateUser(SignupRequest user);
    void deleteUser(String email);
}
