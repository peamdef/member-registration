package com.test.registation.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.registation.ApplicationMockitoTest;
import com.test.registation.entity.User;
import com.test.registation.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplMockitoTest extends ApplicationMockitoTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void loadUserByUsername() throws IOException {
        Gson gson = new GsonBuilder().create();
        String userDb = readFile("mock-data/user-db.json");
        User user = gson.fromJson(userDb, User.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        userDetailsService.loadUserByUsername(username);
    }

    @Test(expected = Exception.class)
    public void loadUserByUsernameUserNotFound() throws IOException {
        Gson gson = new GsonBuilder().create();
        String userDb = readFile("mock-data/user-db.json");
        User user = gson.fromJson(userDb, User.class);
        when(userRepository.findByUsername(anyString())).thenThrow(RuntimeException.class);
        userDetailsService.loadUserByUsername(username);
    }
}
