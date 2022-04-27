package com.test.registation.controller;

import com.test.registation.entity.User;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.UserProfile;
import com.test.registation.repository.UserRepository;
import com.test.registation.service.UserService;
import com.test.registation.utils.CommonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.test.registation.config.JwtAuthTokenFilter.username;
import static com.test.registation.constant.BusinessCode.RGTE1000;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<UserProfile>>> postRegisterProfile() {
        CommonResponse<List<UserProfile>> response;
        try{
            response = userService.getUserList();
        }catch (BusinessException be){
            return ResponseEntity.badRequest()
                    .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reference-code}")
    public ResponseEntity<CommonResponse<UserProfile>> getUserByReferenceCode(
            @PathVariable("reference-code") @NotEmpty String referenceCode
    ) {
        CommonResponse<UserProfile> response;
        try{
            response = userService.getUserByReferenceCode(referenceCode);
        }catch (BusinessException be){
            return ResponseEntity.badRequest()
                    .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponse<User>> updateUserProfile(
            HttpServletRequest request,
            @RequestBody UserProfile param
    ) {
        logger.info(username);
        Optional<User> user = userRepository.findByReferenceCode(param.getReferenceCode());
        CommonResponse<User> response = null;
        try{
            if(user.isPresent()){
            if(username.equals(user.get().getUsername())){
                try{
                    response = userService.updateUserProfile(param);
                }catch (BusinessException be){
                    return ResponseEntity.badRequest()
                            .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
                }
            }else {
                throw new BusinessException(RGTE1000);
            }
        }else {
                throw new BusinessException(RGTE1000);
        }
        }catch (BusinessException be){
            return ResponseEntity.badRequest()
                    .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
        }

        return ResponseEntity.ok(response);
    }
}
