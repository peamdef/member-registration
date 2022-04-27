package com.test.registation.service;

import com.test.registation.constant.StatusCode;
import com.test.registation.entity.User;
import com.test.registation.exception.BusinessException;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.UserProfile;
import com.test.registation.repository.UserRepository;
import com.test.registation.utils.CommonResponseUtil;
import com.test.registation.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.test.registation.constant.BusinessCode.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public CommonResponse<UserProfile> getUserByReferenceCode( String referenceCode){
        UserProfile userProfile = new UserProfile();
        try{
            Optional<User> dbUser = userRepository.findByReferenceCode(referenceCode);
            if(!dbUser.isPresent()){
                throw new BusinessException(RGTE1004);
            }else {
                    userProfile.setReferenceCode(dbUser.get().getReferenceCode());
                    userProfile.setName(dbUser.get().getName());
                    userProfile.setEmail(dbUser.get().getEmail());
                    userProfile.setPhoneNumber(dbUser.get().getPhoneNumber());
                    userProfile.setSalary(dbUser.get().getSalary());
                    userProfile.setMemberType(dbUser.get().getMemberType());
            }
        }catch (BusinessException be){
            throw be;
        } catch (Exception e){
            throw new BusinessException(USDB1000);
        }
        return CommonResponseUtil.createResponse(StatusCode.S0000,userProfile);
    }

    public CommonResponse<List<UserProfile>> getUserList(){
        List<UserProfile> userProfileList = new ArrayList<>();
        try{
            List<User> userList = userRepository.findAll();
            if(userList.isEmpty()){
                throw new BusinessException(RGTE1004);
            }else {
                for (User user: userList) {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setReferenceCode(user.getReferenceCode());
                    userProfile.setName(user.getName());
                    userProfile.setEmail(user.getEmail());
                    userProfile.setPhoneNumber(user.getPhoneNumber());
                    userProfile.setSalary(user.getSalary());
                    userProfile.setMemberType(user.getMemberType());
                    userProfileList.add(userProfile);
                }
            }
        }catch (BusinessException be){
            throw be;
        } catch (Exception e){
            throw new BusinessException(USDB1000);
        }
        return CommonResponseUtil.createResponse(StatusCode.S0000,userProfileList);
    }

    public CommonResponse<User> updateUserProfile (UserProfile param){
        Optional<User> dbUser;
        try{
            dbUser =  userRepository.findByReferenceCode(param.getReferenceCode());
            if(!dbUser.isPresent()){
                throw new BusinessException(RGTE1004);
            }else {
                User updatedUserProfile = dbUser.get();
                if(param.getName()!=null){
                    updatedUserProfile.setName(param.getName());
                }
                if(param.getPhoneNumber()!=null){
                    updatedUserProfile.setPhoneNumber(param.getPhoneNumber());
                }
                if(param.getSalary()!=null){
                    updatedUserProfile.setSalary(param.getSalary());
                    updatedUserProfile.setMemberType(CommonUtil.setMemberType(param.getSalary()));
                }
                userRepository.save(updatedUserProfile);
            }
        }catch (BusinessException be){
            throw be;
        } catch (Exception e){
            throw new BusinessException(USDB1000);
        }
        return CommonResponseUtil.createResponse(StatusCode.S0000,dbUser.get());
    }
}
