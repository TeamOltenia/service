package ro.unibuc.hello.service;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.Donation;
import ro.unibuc.hello.exception.CustomErrorHandler;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private DonationService donationService;

    @Test
    public void getDonationById() {
        DonationEntity donationEntity = getDonationEntity();
        when(donationRepository.findById(anyString())).thenReturn(Optional.of(donationEntity));
        Donation donation = donationService.getDonationById("494");
        testEqualsDonationEntityDonation(donationEntity, donation);
    }

    @Test(expected = CustomErrorHandler.class)
    public void getDonationByIdEmptyFieldExceptionEmptyId(){
        donationService.getDonationById("");
    }

    @Test(expected = CustomErrorHandler.class)
    public void getDonationByIdEmptyFieldException(){
        DonationEntity donationEntity = getDonationEntity();
        when(donationRepository.findById(anyString())).thenReturn(Optional.empty());
        donationService.getDonationById("494");
    }

    @Test
    public void updateDonationById(){
        when(campaignRepository.findByDonationIdsContaining(any())).thenReturn(Optional.of(new CampaignEntity()));
        when(userRepository.findByDonationsContaining(any())).thenReturn(Optional.of(new UserEntity()));
        when(donationRepository.findById(anyString())).thenReturn(Optional.of(getDonationEntity()));
        when(donationRepository.save(any())).thenReturn(getDonationEntity());
        when(userRepository.findById(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(campaignRepository.findById(anyString())).thenReturn(Optional.of(new CampaignEntity()));
        String donationId = donationService.updateDonationById("494", getDonation());
        Assertions.assertEquals(donationId, getDonationEntity().getId());
    }

    @Test(expected = CustomErrorHandler.class)
    public void updateDonationByIdEntityNotFoundException(){
        donationService.updateDonationById("", getDonation());
    }

    @Test
    public void saveDonation(){
        when(donationRepository.save(any())).thenReturn(getDonationEntity());
        when(userRepository.findById(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(campaignRepository.findById(anyString())).thenReturn(Optional.of(new CampaignEntity()));
        when(donationRepository.save(any())).thenReturn(getDonationEntity());
        String campaignId = donationService.saveDonation(getDonation());
        Assertions.assertEquals(campaignId, getDonationEntity().getId());
    }

    @Test
    public void deleteDonationById(){

        when(campaignRepository.findByDonationIdsContaining(any())).thenReturn(Optional.of(new CampaignEntity()));
        when(userRepository.findByDonationsContaining(any())).thenReturn(Optional.of(new UserEntity()));
        when(donationRepository.findById(anyString())).thenReturn(Optional.of(getDonationEntity()));
        doNothing().when(donationRepository).deleteById(anyString());
        donationService.deleteDonationById("494");
    }

    @Test(expected = CustomErrorHandler.class)
    public void deleteDonationByIdCustomErrorHandler(){
        donationService.deleteDonationById("");
    }

    @Test
    public void deleteDonationByIdIllegalArgumentException(){
        when(campaignRepository.findByDonationIdsContaining(any())).thenReturn(Optional.of(new CampaignEntity()));
        when(userRepository.findByDonationsContaining(any())).thenReturn(Optional.of(new UserEntity()));
        when(donationRepository.findById(anyString())).thenReturn(Optional.of(getDonationEntity()));
        doThrow(new IllegalArgumentException()).when(donationRepository).deleteById(anyString());
        donationService.deleteDonationById("494");
    }

    @Test(expected = CustomErrorHandler.class)
    public void deleteDonationByIdEmptyFieldException(){
        DonationEntity donationEntity = getDonationEntity();
        when(donationRepository.findById(anyString())).thenReturn(Optional.empty());
        donationService.deleteDonationById("494");
    }



    private static void testEqualsDonationEntityDonation(DonationEntity donationEntity, Donation donation) {
        Assertions.assertEquals(donation.getAmount(), donationEntity.getAmount());
        Assertions.assertEquals(donation.getMessage(), donationEntity.getMessage());
        Assertions.assertEquals(donation.getId(), donationEntity.getId());
        Assertions.assertEquals(donation.getDateOfDonation(), donationEntity.getDateOfDonation());
    }

    private static DonationEntity getDonationEntity() {
        DonationEntity donationEntity = DonationEntity.builder()
                .id("494")
                .dateOfDonation(LocalDate.of(2023, 3, 29))
                .amount(153.65)
                .message("Message for a donation")
                .user(UserEntity.builder().id("10").build())
                .campaign(CampaignEntity.builder().id("100").build())
                .build();
        return donationEntity;
    }

    private static Donation getDonation() {
        return Donation.builder()
                .id("494")
                .dateOfDonation(LocalDate.of(2023, 3, 29))
                .amount(153.65)
                .message("Message for a donation")
                .userId("10")
                .campaignId("100")
                .build();
    }
}
