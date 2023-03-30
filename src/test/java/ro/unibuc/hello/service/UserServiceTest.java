package ro.unibuc.hello.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.DonationEntity;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.CustomErrorHandler;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void getUserById(){
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userEntity));
        User user = userService.getUserById("10");
        testEqualsUserEntityUser(userEntity, user);
    }

    @Test(expected = CustomErrorHandler.class)
    public void getUserByIdEmptyFieldExceptionEmptyId(){
        userService.getUserById("");
    }
    @Test(expected = CustomErrorHandler.class)
    public void getUserByIdEmptyFieldException(){
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        userService.getUserById("10");
    }

    @Test
    public void updateUserById() {
        when(userRepository.save(any())).thenReturn(getUserEntity());
        when(donationRepository.findById(anyString())).thenReturn(Optional.of(new DonationEntity()));
        String userId = userService.updateUserById("10", getUser());
        Assertions.assertEquals(userId, getUserEntity().getId());
    }
    @Test(expected = CustomErrorHandler.class)
    public void updateUserByIdEntityNotFoundException() {
        userService.updateUserById("", getUser());
    }

    @Test
    public void saveUser() {
        when(userRepository.save(any())).thenReturn(getUserEntity());
        String userId = userService.saveUser(getUser());
        Assertions.assertEquals(userId, getUserEntity().getId());
    }

    @Test
    public void deleteUserById() {
        doNothing().when(userRepository).deleteById(anyString());
        userService.deleteUserById("10");
    }

    @Test(expected = CustomErrorHandler.class)
    public void deleteUserByIdCustomErrorHandler() {
        userService.deleteUserById("");
    }
    @Test
    public void deleteUserByIdIllegalArgumentException() {
        doThrow(new IllegalArgumentException()).when(userRepository).deleteById(anyString());
        userService.deleteUserById("10");
    }

    private static void testEqualsUserEntityUser(UserEntity userEntity, User user) {
        Assertions.assertEquals(user.getId(), userEntity.getId());
        Assertions.assertEquals(user.getFirstName(), userEntity.getFirstName());
        Assertions.assertEquals(user.getLastName(), userEntity.getLastName());
        Assertions.assertEquals(user.getEmail(), userEntity.getEmail());
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = UserEntity.builder()
                .id("10")
                .firstName("test")
                .lastName("test")
                .email("email@deproba.com")
                .build();
        return userEntity;
    }

    private static User getUser() {
        return User.builder()
                .id("10")
                .firstName("test")
                .lastName("test")
                .email("email@deproba.com")
                .donationIds(List.of("123"))
                .build();
    }
}
