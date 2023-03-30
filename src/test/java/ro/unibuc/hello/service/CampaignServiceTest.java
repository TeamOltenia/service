package ro.unibuc.hello.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.CampaignEntity;
import ro.unibuc.hello.data.CampaignRepository;
import ro.unibuc.hello.dto.Campaign;
import ro.unibuc.hello.exception.CustomErrorHandler;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
    @RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    public void getCampaignById(){
        CampaignEntity campaignEntity = getCampaignEntity();
        when(campaignRepository.findById(Mockito.anyString())).thenReturn(Optional.of(campaignEntity));
        Campaign campaign = campaignService.getCampaignById("123");
        testEqualsCampaignEnityCampaign(campaignEntity, campaign);
    }
    @Test(expected = CustomErrorHandler.class)
    public void getCampaignByIdEmptyFieldExceptionEmptyId(){
        campaignService.getCampaignById("");
    }
    @Test(expected = CustomErrorHandler.class)
    public void getCampaignByIdEmptyFieldException(){
        CampaignEntity campaignEntity = getCampaignEntity();
        when(campaignRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
         campaignService.getCampaignById("123");
    }

    @Test
    public void updateCampaignById(){

        when(campaignRepository.save(any())).thenReturn(getCampaignEntity());
        String campaignId = campaignService.updateCampaignById("123",getCampaign());
        Assertions.assertEquals(campaignId,getCampaignEntity().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateCampaignByIdEntityNotFoundException(){
        campaignService.updateCampaignById("",getCampaign());
    }

    @Test
    public void saveCampaign(){
        when(campaignRepository.save(any())).thenReturn(getCampaignEntity());
        String campaignId = campaignService.saveCampaign(getCampaign());
        Assertions.assertEquals(campaignId,getCampaignEntity().getId());
    }

    @Test
    public void deleteCampaignById(){
        doNothing().when(campaignRepository).deleteById(anyString());
        campaignService.deleteCampaignById("123");
    }
    @Test(expected = CustomErrorHandler.class)
    public void deleteCampaignByIdCustomErrorHandler(){
        campaignService.deleteCampaignById("");
    }
    @Test
    public void deleteCampaignByIdIllegalArgumentException(){
        doThrow(new IllegalArgumentException()).when(campaignRepository).deleteById(anyString());
        campaignService.deleteCampaignById("123");
    }

    private static void testEqualsCampaignEnityCampaign(CampaignEntity campaignEntity, Campaign campaign) {
        Assertions.assertEquals(campaign.getCampaignGoal(), campaignEntity.getCampaignGoal());
        Assertions.assertEquals(campaign.getDescription(), campaignEntity.getDescription());
        Assertions.assertEquals(campaign.getId(), campaignEntity.getId());
        Assertions.assertEquals(campaign.getTitle(), campaignEntity.getTitle());
    }

    private static CampaignEntity getCampaignEntity() {
        CampaignEntity campaignEntity = CampaignEntity.builder()
                .id("123")
                .campaignGoal(123131.0)
                .title("test")
                .description("test")
                .build();
        return campaignEntity;
    }

    private static Campaign getCampaign() {
        return Campaign.builder()
                .id("123")
                .campaignGoal(123131.0)
                .title("test")
                .description("test")
                .build();    }
}
