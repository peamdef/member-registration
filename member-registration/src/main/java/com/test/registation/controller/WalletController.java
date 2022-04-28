package com.test.registation.controller;

import com.test.registation.entity.User;
import com.test.registation.entity.Wallet;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.TopUpParam;
import com.test.registation.repository.UserRepository;
import com.test.registation.service.WalletService;
import com.test.registation.utils.CommonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

import static com.test.registation.config.JwtAuthTokenFilter.username;
import static com.test.registation.constant.BusinessCode.RGTE1000;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/top-up")
    public ResponseEntity<CommonResponse<Wallet>> topUpWallet (@RequestBody TopUpParam param) {
        Optional<User> user = userRepository.findByReferenceCode(param.getReferenceCode());
        CommonResponse<Wallet> response;
        try{
            if (user.isPresent() && username.equals(user.get().getUsername())) {
                try {
                    response = walletService.topUpAmountInWallet(param);
                } catch (BusinessException be) {
                    return ResponseEntity.badRequest()
                            .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
                }
            } else {
                throw new BusinessException(RGTE1000);
            }
        }catch (BusinessException be){
            return ResponseEntity.badRequest()
                    .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reference-code}")
    public ResponseEntity<CommonResponse<Wallet>> getWalletByReferenceCode (
            @PathVariable("reference-code") @NotEmpty String referenceCode
    ) {
        Optional<User> user = userRepository.findByReferenceCode(referenceCode);
        CommonResponse<Wallet> response;
        try{
            if (user.isPresent() && username.equals(user.get().getUsername())) {
                try {
                    response = walletService.getWalletByReferenceCode(referenceCode);
                } catch (BusinessException be) {
                    return ResponseEntity.badRequest()
                            .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
                }
            } else {
                throw new BusinessException(RGTE1000);
            }
        }catch (BusinessException be){

            return ResponseEntity.badRequest()
                    .body(CommonResponseUtil.createResponse(be.getCode(), be.getMessage()));
        }
        return ResponseEntity.ok(response);
    }
}
