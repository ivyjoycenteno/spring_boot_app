package com.rijio.blog.controller;

import java.util.Collections;

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

import com.rijio.blog.entity.Role;
import com.rijio.blog.entity.User;
import com.rijio.blog.payload.JWTAuthResponse;
import com.rijio.blog.payload.SignupDto;
import com.rijio.blog.payload.loginDto;
import com.rijio.blog.repository.RoleRepository;
import com.rijio.blog.repository.UserRepository;
import com.rijio.blog.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody loginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token from tokenProvider
        String accessToken = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(accessToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody SignupDto signupDto) {
        // check username existence
        if(userRepository.existsByUsername(signupDto.getUsername())) {
            return new ResponseEntity<>("Username is already existing.", HttpStatus.BAD_REQUEST);
        }

        // check email existence
        if(userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email is already existing.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);

    }
    
}
