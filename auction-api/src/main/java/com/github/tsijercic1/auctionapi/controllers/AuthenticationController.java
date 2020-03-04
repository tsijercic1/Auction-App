package com.github.tsijercic1.auctionapi.controllers;

import com.github.tsijercic1.auctionapi.exceptions.AppException;
import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.models.Role;
import com.github.tsijercic1.auctionapi.models.RoleType;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.payload.ApiResponse;
import com.github.tsijercic1.auctionapi.repositories.RoleRepository;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import com.github.tsijercic1.auctionapi.request.LoginRequest;
import com.github.tsijercic1.auctionapi.request.RegistrationRequest;
import com.github.tsijercic1.auctionapi.response.UserDataResponse;
import com.github.tsijercic1.auctionapi.security.CurrentUser;
import com.github.tsijercic1.auctionapi.security.JwtTokenProvider;
import com.github.tsijercic1.auctionapi.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final UserRepository userRepository,
                                    final RoleRepository roleRepository,
                                    final PasswordEncoder passwordEncoder,
                                    final JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return authenticateUser(email, password);
    }

    private ResponseEntity<?> authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserDataResponse userDataResponse = getUserDataResponse(email, jwt);

        return ResponseEntity.ok(userDataResponse);
    }

    private UserDataResponse getUserDataResponse(String email, String jwt) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("User", "Email", email);
        }
        User user = optional.get();

        UserDataResponse userDataResponse = new UserDataResponse();
        userDataResponse.setId(user.getId());
        userDataResponse.setEmail(user.getEmail());
        userDataResponse.setName(user.getName());
        userDataResponse.setSurname(user.getSurname());
        userDataResponse.setToken(jwt);
        return userDataResponse;
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
    @PermitAll
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        Role userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRole(userRole);
        System.out.println(user.getId());
        userRepository.save(user);

        return authenticateUser(user.getEmail(), registrationRequest.getPassword());
    }

    @PostMapping("/refresh")
    @PermitAll
    public ResponseEntity<?> refreshUserData(@CurrentUser UserPrincipal userPrincipal) {
        String jwt = tokenProvider.generateToken(userPrincipal);
        UserDataResponse userDataResponse = getUserDataResponse(userPrincipal.getEmail(), jwt);
        return ResponseEntity.ok(userDataResponse);
    }
}
