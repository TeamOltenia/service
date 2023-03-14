package ro.unibuc.hello.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ro.unibuc.hello.data.DonationEntity;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Donation;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    public Donation getDonationById(String id) {

        if(!StringUtils.hasText(id)) {
            throw new EntityNotFoundException(id);
        }

        Optional<DonationEntity> donationEntityOptional = donationRepository.findById(id);

        if(donationEntityOptional.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        Donation donation = donationEntityToDonation(donationEntityOptional.get());

        return donation;
    }

    public String updateDonationById(String id, Donation donation) {
        if(!StringUtils.hasText(id)){
            throw new EntityNotFoundException(id);
        }

        DonationEntity donationEntity = donationToDonationEntity(donation);

        return donationRepository.save(donationEntity).getId();
    }

    public String saveDonation(Donation donation) {
        DonationEntity donationEntity = donationRepository.save(donationToDonationEntity(donation));

        return donationEntity.getId();
    }

    public void deleteDonationById(String id){
        if(!StringUtils.hasText(id)){
            throw new EntityNotFoundException(id);
        }

        try{
            donationRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Donation donationEntityToDonation(DonationEntity donationEntity) {

        Donation donation = new Donation();

        donation.setId(donationEntity.getId());
        donation.setDateOfDonation(donationEntity.getDateOfDonation());
        donation.setAmount(donationEntity.getAmount());
        donation.setMessage(donationEntity.getMessage());
        donation.setUserId(donationEntity.getUser().getId());

        return donation;
    }

    public DonationEntity donationToDonationEntity(Donation donation) {

        DonationEntity donationEntity = new DonationEntity();

        donationEntity.setId(donation.getId());
        donationEntity.setDateOfDonation(donation.getDateOfDonation());
        donationEntity.setAmount(donation.getAmount());
        donationEntity.setMessage(donation.getMessage());
        donationEntity.setUser(userRepository.findById(donation.getUserId()).get());

        return donationEntity;
    }
}
