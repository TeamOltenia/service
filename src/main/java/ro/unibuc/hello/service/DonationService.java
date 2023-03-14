package ro.unibuc.hello.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import ro.unibuc.hello.data.*;
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

    @Autowired
    private CampaignRepository campaignRepository;

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
        if(StringUtils.isEmpty(id) ||
                StringUtils.isEmpty(donation.getUserId()) ||
                StringUtils.isEmpty(donation.getCampaignId()) ||
                StringUtils.isEmpty(donation.getId()) ||
                !id.equals(donation.getId())) {
            throw new EntityNotFoundException(id);
        }

        Optional<DonationEntity> donationOptional = donationRepository.findById(id);
        if (donationOptional.isEmpty()) {
            throw new EntityNotFoundException("");
        }
        CampaignEntity campaign = campaignRepository.findByDonationIdsContaining(donationOptional.get()).get();
        UserEntity user = donationOptional.get().getUser();
        user.getDonations().remove(donationOptional.get());
        campaign.getDonationIds().remove(donationOptional.get());
        campaignRepository.save(campaign);
        userRepository.save(user);

        if(userRepository.findById(donation.getUserId()).isEmpty() ||
                campaignRepository.findById(donation.getCampaignId()).isEmpty()) {

            throw new EntityNotFoundException(donation.getUserId());
        }

        Optional<UserEntity> optionalUser = userRepository.findById(donation.getUserId());
        Optional<CampaignEntity> optionalCampaign = campaignRepository.findById(donation.getCampaignId());

        if (optionalUser.isEmpty() || optionalCampaign.isEmpty()) {
            throw new EntityNotFoundException("");
        }

        DonationEntity donationEntity = donationRepository.save(donationToDonationEntity(donation));
        user = optionalUser.get();
        campaign = optionalCampaign.get();
        campaign.getDonationIds().add(donationEntity);
        user.getDonations().add(donationEntity);
        userRepository.save(user);
        campaignRepository.save(campaign);



        return donationEntity.getId();
    }

    public String saveDonation(Donation donation) {

        if (StringUtils.isEmpty(donation.getUserId()) ||
                StringUtils.isEmpty(donation.getCampaignId())) {
            throw new EntityNotFoundException("");
        }

        Optional<UserEntity> optionalUser = userRepository.findById(donation.getUserId());
        Optional<CampaignEntity> optionalCampaign = campaignRepository.findById(donation.getCampaignId());

        if (optionalUser.isEmpty() || optionalCampaign.isEmpty()) {
            throw new EntityNotFoundException("");
        }

        DonationEntity donationEntity = donationRepository.save(donationToDonationEntity(donation));
        UserEntity user = optionalUser.get();
        CampaignEntity campaign = optionalCampaign.get();
        campaign.getDonationIds().add(donationEntity);
        user.getDonations().add(donationEntity);
        userRepository.save(user);
        campaignRepository.save(campaign);

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

        return Donation.builder()
                .id(donationEntity.getId())
                .dateOfDonation(donationEntity.getDateOfDonation())
                .amount(donationEntity.getAmount())
                .message(donationEntity.getMessage())
                .userId(donationEntity.getUser().getId())
                .campaignId(donationEntity.getCampaign().getId())
                .build();
    }

    public DonationEntity donationToDonationEntity(Donation donation) {

        return DonationEntity.builder()
                .id(donation.getId())
                .dateOfDonation(donation.getDateOfDonation())
                .amount(donation.getAmount())
                .message(donation.getMessage())
                .user(userRepository.findById(donation.getUserId()).get())
                .campaign((campaignRepository.findById(donation.getCampaignId()).get()))
                .build();

    }
}
