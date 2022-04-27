package com.test.registation.utils;

import com.test.registation.exception.BusinessException;
import static com.test.registation.constant.BusinessCode.RGTE1002;

public class CommonUtil {
    public static String setMemberType(Integer salary){
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
}
