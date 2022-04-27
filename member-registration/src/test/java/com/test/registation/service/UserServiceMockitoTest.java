package com.test.registation.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.test.registation.ApplicationMockitoTest;
import com.test.registation.entity.User;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.UserProfile;
import com.test.registation.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.test.registation.constant.StatusCode.S0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockitoTest extends ApplicationMockitoTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Before
    public void setUp(){

    }

    @Test
    public void getUserList() throws IOException {
        Gson gson = new GsonBuilder().create();
        String userDb = readFile("mock-data/user-db-list.json");
        List<User> userList = gson.fromJson(userDb,new TypeToken<List<User>>(){}.getType());
        when(userRepository.findAll()).thenReturn(userList);
        CommonResponse<List<UserProfile>> actual = userService.getUserList();
        assertEquals(S0000.getCode(),actual.getCode());
        assertEquals(S0000.getMessage(),actual.getMessage());
        assertEquals("202204275697",actual.getData().get(0).getReferenceCode());
        assertEquals("test2@gmail.com",actual.getData().get(0).getEmail());
        assertEquals("supreme",actual.getData().get(0).getName());
        assertEquals("0999999999",actual.getData().get(0).getPhoneNumber());
        assertEquals(Optional.of(30000).get(),actual.getData().get(0).getSalary());
    }

    @Test(expected = Exception.class)
    public void getUserListWithRGTE1004() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        CommonResponse<List<UserProfile>> actual = userService.getUserList();

    }

    @Test(expected = Exception.class)
    public void getUserListWithUSDB1000() {
        when(userRepository.findAll()).thenThrow(RuntimeException.class);
        CommonResponse<List<UserProfile>> actual = userService.getUserList();
    }

    @Test
    public void getUserByReferenceCode() throws IOException {
        Gson gson = new GsonBuilder().create();
        String userDb = readFile("mock-data/user-db.json");
        User user = gson.fromJson(userDb, User.class);
        when(userRepository.findByReferenceCode(any())).thenReturn(Optional.of(user));
        CommonResponse<UserProfile> actual = userService.getUserByReferenceCode(referenceCode);
        assertEquals(S0000.getCode(),actual.getCode());
        assertEquals(S0000.getMessage(),actual.getMessage());
        assertEquals("202204275697",actual.getData().getReferenceCode());
        assertEquals("test2@gmail.com",actual.getData().getEmail());
        assertEquals("supreme",actual.getData().getName());
        assertEquals("0999999999",actual.getData().getPhoneNumber());
        assertEquals(Optional.of(30000).get(),actual.getData().getSalary());
    }

    @Test(expected = Exception.class)
    public void getUserByReferenceCodeWithRGTE1004() {
        when(userRepository.findByReferenceCode(any())).thenReturn(Optional.empty());
        CommonResponse<UserProfile> actual = userService.getUserByReferenceCode(referenceCode);
    }

    @Test(expected = Exception.class)
    public void getUserByReferenceCodeWithUSDB1000() {
        when(userRepository.findByReferenceCode(any())).thenThrow(RuntimeException.class);
        CommonResponse<UserProfile> actual = userService.getUserByReferenceCode(referenceCode);
    }

    @Test
    public void updateUserProfile() throws IOException {
        Gson gson = new GsonBuilder().create();
        String userDb = readFile("mock-data/user-db.json");
        User user = gson.fromJson(userDb, User.class);
        when(userRepository.findByReferenceCode(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        UserProfile userProfile = new UserProfile();
        userProfile.setName("supreme");
        CommonResponse<User> actual = userService.updateUserProfile(userProfile);
        assertEquals(S0000.getCode(),actual.getCode());
        assertEquals(S0000.getMessage(),actual.getMessage());
        assertEquals("202204275697",actual.getData().getReferenceCode());
        assertEquals("test2@gmail.com",actual.getData().getEmail());
        assertEquals("supreme",actual.getData().getName());
        assertEquals("0999999999",actual.getData().getPhoneNumber());
        assertEquals(Optional.of(30000).get(),actual.getData().getSalary());
    }
    @Test(expected = Exception.class)
    public void updateUserProfileWithRGTE1004(){
        when(userRepository.findByReferenceCode(any())).thenReturn(Optional.empty());
        UserProfile userProfile = new UserProfile();
        userProfile.setName("supreme");
        CommonResponse<User> actual = userService.updateUserProfile(userProfile);
    }

    @Test(expected = Exception.class)
    public void updateUserProfileWithUSDB1000(){
        when(userRepository.findByReferenceCode(any())).thenThrow(RuntimeException.class);
        UserProfile userProfile = new UserProfile();
        userProfile.setName("supreme");
        CommonResponse<User> actual = userService.updateUserProfile(userProfile);
    }
}
