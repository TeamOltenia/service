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

    private Campaign campaignEntityToCampaign(CampaignEntity campaignEntity){
     return new Campaign(campaignEntity.getId(),
                campaignEntity.getTitle(),
                campaignEntity.getDescription(),
                campaignEntity.getCampaignGoal());
    }

    private CampaignEntity campaignToCampaignEntity(Campaign campaign) {
        return new CampaignEntity(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getDescription(),
                campaign.getCampaignGoal());
    }
}
