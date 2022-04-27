package com.test.registation.service;

import com.test.registation.entity.User;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.RegisterForm;
import com.test.registation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RegistrationService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<String> saveRegistrationData (RegisterForm param){
        User user = new User();
        Date date = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
        String stringDate = DateFormat.format(date);
        String encryptedPassword = encoder.encode(param.getPassword());
        String lastFourDigit = param.getPhoneNumber().substring(param.getPhoneNumber().length() - 4);
        user.setReferenceCode(stringDate+lastFourDigit);
        user.setName(param.getName());
        user.setPassword(encryptedPassword);
        user.setPhoneNumber(param.getPhoneNumber());
        user.setUsername(param.getUserName());
        user.setEmail(param.getEmail());
        user.setSalary(param.getSalary());
        user.setMemberType(setMemberType(param.getSalary()));
        userRepository.save(user);
        return ResponseEntity.ok("Success");
    }

    public String setMemberType(Integer salary){
        if(salary > 50000){
            return "Platinum";
        } else if (salary<= 50000 && salary >= 30000) {
            return "Gold";
        } else if (salary<30000 && salary >= 15000) {
            return "Silver";
        }else {
            throw new BusinessException("RGTE1001","salary lower than 15000");
        }
    }
}
