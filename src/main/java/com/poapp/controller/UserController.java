package com.poapp.controller;

import com.poapp.common.ApiResponse;
// import com.poapp.config.CustomAuthenticationManager;
import com.poapp.dto.AuthRequest;
import com.poapp.dto.AuthResponse;
import com.poapp.dto.UserDTO;
import com.poapp.model.User;
import com.poapp.service.UserDetailsServiceImpl;
import com.poapp.service.UserService;
import com.poapp.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping("/register")
public ResponseEntity<ApiResponse<?>> registerUser(@Validated @RequestBody User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // Gather validation errors
    String errorType = "Validation Error";
    String stackTrace = bindingResult.getAllErrors().toString();  // Collect validation error details
    
    ApiResponse<UserDTO> response = new ApiResponse<>(
        "Invalid Input", 
        HttpStatus.BAD_REQUEST.value(), 
        errorType, 
        stackTrace, 
        null,  // No data in error case
        false  // Error, so success is false
    );

    return ResponseEntity.badRequest().body(response);  // Return a bad request response

    }

    if (userService.existsByUsername(user.getUsername())) {
        ApiResponse<String> response = new ApiResponse<>(
            "Conflict", 
            HttpStatus.CONFLICT.value(), 
            "UsernameAlreadyTakenError", 
            "The username you tried to register is already in use",
            null,  
            false  
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        
        
    }

    User registeredUser = userService.registerUser(user);
    ApiResponse<User> response = new ApiResponse<>(
        "Success", 
        HttpStatus.OK.value(), 
        registeredUser,  
        true  
    );
    
    return ResponseEntity.ok(response);
    
}






    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    
   @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AuthResponse>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        
        if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Bad Request", HttpStatus.BAD_REQUEST.value(), "ValidationError", "Username is required", null, false));
        }

        if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Bad Request", HttpStatus.BAD_REQUEST.value(), "ValidationError", "Password is required", null, false));
        }

        try {
            User user = userService.findByUsername(authRequest.getUsername());

            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Unauthorized", HttpStatus.UNAUTHORIZED.value(), "AuthenticationError", "Incorrect username or password", null, false));
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(), user.getRole(), user.getRoleName(),user.getId());

            AuthResponse authResponse = new AuthResponse(jwt, user);

            ApiResponse<AuthResponse> response = new ApiResponse<>("Success", HttpStatus.OK.value(), authResponse, true);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Unauthorized", HttpStatus.UNAUTHORIZED.value(), "AuthenticationError", "Incorrect username or password", null, false));
        }
    }



    // GET endpoint to retrieve users by role
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUsersByRole(@RequestParam String role) {
        List<User> users = userService.getUsersByRole(role);
    
        if (users.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>(
                "No users found",            
                HttpStatus.NO_CONTENT.value(),
                "NoDataError",               
                "No users available for the provided role", 
                null,                        
                false                     
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    
        ApiResponse<List<?>> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            users,  
            true  
        );
    
        return ResponseEntity.ok(response);
    }
    


}

