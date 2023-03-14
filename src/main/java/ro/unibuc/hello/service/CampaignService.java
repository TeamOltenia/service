package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ro.unibuc.hello.data.CampaignEntity;
import ro.unibuc.hello.data.CampaignRepository;
import ro.unibuc.hello.dto.Campaign;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Optional;

@Component
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign getCampaignById(String id){
        if(!StringUtils.hasText(id)){
            throw new EntityNotFoundException(id);
        }
        Optional<CampaignEntity> campaignEntityOptional= campaignRepository.findById(id);
        if(campaignEntityOptional.isEmpty()){
            throw new EntityNotFoundException(id);
        }
        Campaign campaign = campaignEntityToCampaign(campaignEntityOptional.get());
        return campaign;

    }

    public String updateCampaignById(String id, Campaign campaign) {

        if(!StringUtils.hasText(id)){
            throw new EntityNotFoundException(id);
        }

        CampaignEntity campaignEntity = campaignToCampaignEntity(campaign);

        return campaignRepository.save(campaignEntity).getId();
    }

    public String saveCampaign(Campaign campaign){
        CampaignEntity campaignEntity=
                campaignRepository.save(campaignToCampaignEntity(campaign));
        return campaignEntity.getId();
    }

    public void deleteCampaignById(String id) {

        if(!StringUtils.hasText(id)) {
            throw new EntityNotFoundException(id);
        }

        try {
            campaignRepository.deleteById(id);
        }  catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    private Campaign campaignEntityToCampaign(CampaignEntity campaignEntity){
     return Campaign.builder()
             .id(campaignEntity.getId())
             .campaignGoal(campaignEntity.getCampaignGoal())
             .title(campaignEntity.getTitle())
             .description(campaignEntity.getDescription()).build();
    }

    private CampaignEntity campaignToCampaignEntity(Campaign campaign) {
        return CampaignEntity.builder()
                .id(campaign.getId())
                .campaignGoal(campaign.getCampaignGoal())
                .title(campaign.getTitle())
                .description(campaign.getDescription()).build();
    }
}
