package com.codeWithDurgesh.blog.controllers;

import com.codeWithDurgesh.blog.exceptions.ApiException;
import com.codeWithDurgesh.blog.payloads.UserDto;
import com.codeWithDurgesh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithDurgesh.blog.payloads.JwtAuthRequest;
import com.codeWithDurgesh.blog.payloads.JwtAuthResponse;
import com.codeWithDurgesh.blog.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String generateToken = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse resp = new JwtAuthResponse();
        resp.setToken(generateToken);
        return new ResponseEntity<JwtAuthResponse>(resp, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {

            this.authenticationManager.authenticate(token);

        } catch (BadCredentialsException e) {

            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid username or password !!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {

        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

}
