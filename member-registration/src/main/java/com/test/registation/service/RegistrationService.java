package com.test.registation.service;

import com.test.registation.constant.StatusCode;
import com.test.registation.entity.User;
import com.test.registation.entity.Wallet;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.RegisterForm;
import com.test.registation.repository.UserRepository;
import com.test.registation.repository.WalletRepository;
import com.test.registation.utils.CommonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.test.registation.constant.BusinessCode.*;

@Service
public class RegistrationService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;
    public CommonResponse<String> saveRegistrationData (RegisterForm param){
        List<User> dbUsername = userRepository.findAllByUsernameOrEmailOrPhoneNumber(
                param.getUserName(),
                param.getEmail(),
                param.getPhoneNumber());

        if(!dbUsername.isEmpty()){
            throw new BusinessException(RGTE1001);
        }
        User user = new User();
        Date date = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
        String stringDate = DateFormat.format(date);
        String encryptedPassword = encoder.encode(param.getPassword());
        String lastFourDigit = param.getPhoneNumber().substring(param.getPhoneNumber().length() - 4);
        String referenceCode = stringDate+lastFourDigit;
        user.setReferenceCode(referenceCode);
        user.setName(param.getName());
        user.setPassword(encryptedPassword);
        user.setPhoneNumber(param.getPhoneNumber());
        user.setUsername(param.getUserName());
        user.setEmail(param.getEmail());
        user.setSalary(param.getSalary());
        user.setMemberType(setMemberType(param.getSalary()));
        userRepository.save(user);

        createWallet(referenceCode);

        return CommonResponseUtil.createResponse(StatusCode.S0000,"Success");
    }

    public String setMemberType(Integer salary){
        if(salary > 50000){
            return "Platinum";
        } else if (salary<= 50000 && salary >= 30000) {
            return "Gold";
        } else if (salary<30000 && salary >= 15000) {
            return "Silver";
        }else {
            throw new BusinessException(RGTE1002);
        }
    }

    public void createWallet (String referenceCode) {
        Optional<Wallet> wallet = walletRepository.findByReferenceCode(referenceCode);
        if(wallet.isPresent()){
            throw new BusinessException(RGTE1003);
        }
        Wallet initWallet = new Wallet();
        initWallet.setReferenceCode(referenceCode);
        initWallet.setAmount(0);
        initWallet.setPoint(0);
        walletRepository.save(initWallet);
    }
}
