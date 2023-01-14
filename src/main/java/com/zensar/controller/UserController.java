package com.zensar.controller;


import com.zensar.dto.AuthenticationRequest;
import com.zensar.dto.AuthenticationResponse;
import com.zensar.dto.RegisterRequest;
import com.zensar.service.UserServiceDemo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    UserServiceDemo serviceDemo;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(serviceDemo.registerUser(registerRequest));
    }

    @PostMapping("/authenticated")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest registerRequest){
       return ResponseEntity.ok(serviceDemo.authenticate(registerRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("HelloWorld");
    }

}
