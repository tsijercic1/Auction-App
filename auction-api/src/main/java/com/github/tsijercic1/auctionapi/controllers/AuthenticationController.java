package com.github.tsijercic1.auctionapi.controllers;

import com.github.tsijercic1.auctionapi.exceptions.AppException;
import com.github.tsijercic1.auctionapi.models.Role;
import com.github.tsijercic1.auctionapi.models.RoleType;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.payload.ApiResponse;
import com.github.tsijercic1.auctionapi.payload.JwtAuthenticationResponse;
import com.github.tsijercic1.auctionapi.payload.LoginRequest;
import com.github.tsijercic1.auctionapi.payload.RegistrationRequest;
import com.github.tsijercic1.auctionapi.repositories.RoleRepository;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import com.github.tsijercic1.auctionapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     *
     * @param registrationRequest
     * body of the registration username, email, password, name, surname
     *
     * checks if the username and email are unique
     * creates a user and sets his role of type USER which is default
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        // the request body gets mapped into the RegistrationRequest class
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(registrationRequest.getName(), registrationRequest.getSurname(), registrationRequest.getUsername(),
                registrationRequest.getEmail(), registrationRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
