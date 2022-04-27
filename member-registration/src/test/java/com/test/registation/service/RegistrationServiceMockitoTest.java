package com.test.registation.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.test.registation.ApplicationMockitoTest;
import com.test.registation.entity.User;
import com.test.registation.entity.Wallet;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.RegisterForm;
import com.test.registation.repository.UserRepository;
import com.test.registation.repository.WalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.test.registation.constant.StatusCode.S0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceMockitoTest extends ApplicationMockitoTest {
    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Spy
    private PasswordEncoder encoder;

    @Test
    public void saveRegistrationData() throws IOException {
        when(userRepository.findAllByUsernameOrEmailOrPhoneNumber(any(),any(),any())).thenReturn(new ArrayList<>());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(walletRepository.findByReferenceCode(any())).thenReturn(Optional.empty());

        RegisterForm registerForm = new RegisterForm();
        registerForm.setUserName(username);
        registerForm.setPassword("123456789");
        registerForm.setPhoneNumber("0918035691");
        registerForm.setEmail("test@gmail.com");
        registerForm.setName("peam");
        registerForm.setSalary(50000);

        CommonResponse<String> actual = registrationService.saveRegistrationData(registerForm);
        assertEquals(S0000.getCode(),actual.getCode());
        assertEquals(S0000.getMessage(),actual.getMessage());
        assertEquals("Success",actual.getData());

    }

    @Test(expected = Exception.class)
    public void saveRegistrationDataUserAlreadyHave() throws IOException {
        Gson gson = new GsonBuilder().create();
        String userDb = readFile("mock-data/user-db-list.json");
        List<User> userList = gson.fromJson(userDb,new TypeToken<List<User>>(){}.getType());
        when(userRepository.findAllByUsernameOrEmailOrPhoneNumber(any(),any(),any())).thenReturn(userList);
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUserName(username);
        registerForm.setPassword("123456789");
        registerForm.setPhoneNumber("0918035691");
        registerForm.setEmail("test@gmail.com");
        registerForm.setName("peam");
        registerForm.setSalary(50000);
        CommonResponse<String> actual = registrationService.saveRegistrationData(registerForm);

    }
    @Test(expected = Exception.class)
    public void createWalletWithRGTE1003() throws IOException {;
        when(walletRepository.findByReferenceCode(any())).thenReturn(Optional.of(new Wallet()));
        registrationService.createWallet(referenceCode);

    }
}
