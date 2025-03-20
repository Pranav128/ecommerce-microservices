package com.app.ecommerce.user_service.service.implementation;

import com.app.ecommerce.user_service.dto.UserDto;
import com.app.ecommerce.user_service.dto.request.LoginRequest;
import com.app.ecommerce.user_service.dto.request.SignupRequest;
import com.app.ecommerce.user_service.dto.response.ErrorResponse;
import com.app.ecommerce.user_service.dto.response.JwtResponse;
import com.app.ecommerce.user_service.entity.Role;
import com.app.ecommerce.user_service.entity.User;
import com.app.ecommerce.user_service.exception.NoUserFoundException;
import com.app.ecommerce.user_service.repository.UserRepository;
import com.app.ecommerce.user_service.service.email.EmailService;
import com.app.ecommerce.user_service.service.specification.UserService;
import com.app.ecommerce.user_service.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtil;
    private final EmailService emailService;

    @Override
    public boolean registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return false;
        }
        User user=modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(Role.USER);
        log.info("User registered successfully: {}", user);
        userRepository.save(user);
        return true;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities().toString());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid user request!", "400"), HttpStatusCode.valueOf(400));
        }
    }

    // Generate reset token and send email
    @Override
    public void generateResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setRefreshToken(token);
            user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30)); // Token valid for 30 minus
            System.out.println(user.getRefreshToken());
            userRepository.save(user);

            // Send reset link via email
            String resetLink = "http://localhost:3000/reset?token=" + token;
            String text="Use this token to reset your password: " + token+"\nToken will be expired after 30 minutes" + "\n" +
                        "Use this link to reset your password: " + resetLink+"\nToken will be expired after 30 minutes";
            emailService.sendEmail(user.getEmail(), "Password Reset Request", text );
        }else{
            throw new RuntimeException("No user found to send reset link");
        }
    }

    // Reset password using token
    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByRefreshToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Check if token is expired
            if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
                return false; // Token expired
            }

            user.setPassword(passwordEncoder.encode(newPassword)); // Hash password before saving in real apps
            user.setRefreshToken(null); // Clear refresh token(null); // Clear token after successful reset
            user.setResetTokenExpiry(null);
            userRepository.save(user);
            emailService.sendEmail(user.getEmail(), "Password Reset Successfully", "User password has been successfully reset");
            return true;
        }
        return false;
    }

    @Override
    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoUserFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(SignupRequest updateRequest) {
        User user = userRepository.findByEmail(updateRequest.getEmail())
                .orElseThrow(()-> new NoUserFoundException("User not found"));
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NoUserFoundException("User not found"));
        userRepository.delete(user);
    }
}
