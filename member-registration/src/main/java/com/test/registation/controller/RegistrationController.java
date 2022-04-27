package com.test.registation.controller;

import com.test.registation.config.JwtProvider;
import com.test.registation.config.JwtResponse;
import com.test.registation.constant.StatusCode;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.LoginForm;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.RegisterForm;
import com.test.registation.service.RegistrationService;
import com.test.registation.utils.CommonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    RegistrationService registrationService;
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<String>> postRegisterProfile(@RequestBody RegisterForm param) {
        CommonResponse<String> response;
        try{
             response = registrationService.saveRegistrationData(param);
        }catch (BusinessException be){
            return ResponseEntity.badRequest()
                    .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(CommonResponseUtil.createResponse(StatusCode.S0000,new JwtResponse(jwt)));
    }
}
