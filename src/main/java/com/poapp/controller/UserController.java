package com.poapp.controller;

import com.poapp.common.ApiResponse;
// import com.poapp.config.CustomAuthenticationManager;
import com.poapp.dto.AuthRequest;
import com.poapp.dto.AuthResponse;
import com.poapp.dto.PurchaseOrderDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;



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
    
    ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
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
            "UsernameAlreadyTakenError",  // Error type for clarity
            "The username you tried to register is already in use", // Error message or stack trace
            null,  // No data as it's an error case
            false  // Error, so success is false
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        
        
    }

    User registeredUser = userService.registerUser(user);
    ApiResponse<User> response = new ApiResponse<>(
        "Success", 
        HttpStatus.OK.value(), 
        registeredUser,  // Data object for the successfully registered user
        true  // Success status is true
    );
    
    return ResponseEntity.ok(response);
    
}






    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<ApiResponse<AuthResponse>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
    try {
        // Authenticate the user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
    } catch (BadCredentialsException e) {
        ApiResponse<AuthResponse> response = new ApiResponse<>(
            "Unauthorized", 
            HttpStatus.UNAUTHORIZED.value(), 
            "AuthenticationError",  // Error type for clarity
            "Incorrect username or password",  // Detailed error message
            null,  // No data as it's an error case
            false  // Error, so success is false
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Load user details
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    // Generate JWT
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());

    // Fetch the complete User object from the UserService
    User user = userService.findByUsername(authRequest.getUsername());

    // Prepare the AuthResponse with JWT and user details
    AuthResponse authResponse = new AuthResponse(jwt, user);

    // Prepare the API response
ApiResponse<AuthResponse> response = new ApiResponse<>(
    "Success", 
    HttpStatus.OK.value(), 
    authResponse,  // Data object that includes authentication details (like tokens)
    true  // Success flag is true
);

return ResponseEntity.ok(response);

}


}

