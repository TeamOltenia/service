package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ro.unibuc.hello.data.DonationEntity;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Donation;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    public User getUserById(String id) {

        if(!StringUtils.hasText(id)) {
            throw new EntityNotFoundException(id);
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if(userEntityOptional.isEmpty()){
            throw new EntityNotFoundException(id);
        }

        return  userEntityToUser(userEntityOptional.get());
    }


    public String updateUserById(String id, User user) {
        if(!StringUtils.hasText(id)){
            throw new EntityNotFoundException(id);
        }

        UserEntity userEntity = userToUserEntity(user);

        return userRepository.save(userEntity).getId();
    }

    public String saveUser(User user) {
        UserEntity userEntity = userRepository.save(userToUserEntity(user));

        return userEntity.getId();
    }

    public void deleteUserById(String id) {
        if(!StringUtils.hasText(id)) {
            throw new EntityNotFoundException(id);
        }

        try{
            userRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public UserEntity userToUserEntity(User user) {
        List<DonationEntity> donations = new ArrayList<>();

        if(!user.getDonationIds().isEmpty()) {
            for (String donationId : user.getDonationIds()) {
                Optional<DonationEntity> donationEntityOptional = donationRepository.findById(donationId);
                if (!donationEntityOptional.isEmpty()) {
                    donations.add(donationEntityOptional.get());
                }
            }
        }

        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .donations(donations)
                .build();
    }

    public User userEntityToUser(UserEntity userEntity) {
        List<String> donationIds = new ArrayList<>();

        if(!CollectionUtils.isEmpty(userEntity.getDonations())) {
            for (DonationEntity donationEntity : userEntity.getDonations()) {
                donationIds.add(donationEntity.getId());
            }
        }

        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .donationIds(donationIds)
                .build();
    }
}
