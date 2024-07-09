package com.madeeasy.controller;


import com.madeeasy.dto.request.AuthRequest;
import com.madeeasy.dto.request.LogOutRequest;
import com.madeeasy.dto.request.SignInRequestDTO;
import com.madeeasy.dto.request.UserRequest;
import com.madeeasy.dto.response.AuthResponse;
import com.madeeasy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth-service")
public class AuthController {

    private final AuthService authService;


    @PostMapping(path = "/sign-up")
    public ResponseEntity<?> singUp(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = this.authService.singUp(authRequest);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> singIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        AuthResponse authResponse = this.authService.singIn(signInRequestDTO);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping(path = "/log-out")
    public ResponseEntity<?> logOut(@RequestBody LogOutRequest logOutRequest) {
        this.authService.logOut(logOutRequest);
        return ResponseEntity.ok().body("Logged out");
    }

    @PatchMapping(path = "/partial-update/{emailId}")
    public ResponseEntity<?> partiallyUpdateUser(@PathVariable("emailId") String emailId,@Valid @RequestBody UserRequest userRequest) {
        System.out.println("inside partial update auth-service: " + userRequest);
        AuthResponse authResponse = this.authService.partiallyUpdateUser(emailId, userRequest);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping(path = "/validate-access-token/{accessToken}")
    public ResponseEntity<?> validateAccessToken(@PathVariable("accessToken") String accessToken) {
        boolean flag = this.authService.validateAccessToken(accessToken);
        if (flag) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}
