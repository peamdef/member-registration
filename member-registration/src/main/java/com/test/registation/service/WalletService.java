package com.test.registation.service;

import com.test.registation.constant.StatusCode;
import com.test.registation.entity.Wallet;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.TopUpParam;
import com.test.registation.repository.WalletRepository;
import com.test.registation.utils.CommonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.test.registation.constant.BusinessCode.WLTE1001;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;
    public CommonResponse<Wallet> topUpAmountInWallet (TopUpParam param){
        Optional<Wallet> dbWallet = walletRepository.findByReferenceCode(param.getReferenceCode());
        Wallet wallet;
        if(dbWallet.isPresent()){
            wallet = dbWallet.get();
            Integer sumAmount = wallet.getAmount() + param.getAmount();
            wallet.setAmount(sumAmount);
            walletRepository.save(wallet);
        }
        else {
            throw new BusinessException(WLTE1001);
        }
        return CommonResponseUtil.createResponse(StatusCode.S0000,wallet);
    }

    public CommonResponse<Wallet> getWalletByReferenceCode (String referenceCode){
        Optional<Wallet> dbWallet = walletRepository.findByReferenceCode(referenceCode);
        if(!dbWallet.isPresent()){
            throw new BusinessException(WLTE1001);
        }
        return CommonResponseUtil.createResponse(StatusCode.S0000,dbWallet.get());
    }
}
