package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ro.unibuc.hello.data.DonationEntity;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
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

        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());

        if(user.getDonationIds().isEmpty()) {
            userEntity.setDonations(new ArrayList<>());
        }

         else {

            List<DonationEntity> donations = new ArrayList<>();

            for (String donationId : user.getDonationIds()) {

                Optional<DonationEntity> donationEntityOptional = donationRepository.findById(donationId);

                if (!donationEntityOptional.isEmpty()) {
                    donations.add(new DonationEntity(donationEntityOptional.get().getId()
                            , donationEntityOptional.get().getDateOfDonation()
                            , donationEntityOptional.get().getAmount()
                            , donationEntityOptional.get().getMessage()
                            , donationEntityOptional.get().getUser()));
                }
            }

            userEntity.setDonations(donations);
        }
        return userEntity;
    }

    public User userEntityToUser(UserEntity userEntity) {

        User user = new User();

        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        List<String> donationIds = new ArrayList<>();

        if(userEntity.getDonations().size() != 0) {
            for (DonationEntity donationEntity : userEntity.getDonations()) {
                donationIds.add(donationEntity.getId());
            }

        }

        user.setDonationIds(donationIds);
        return user;
    }
}
