package ro.unibuc.hello.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ro.unibuc.hello.data.CampaignEntity;
import ro.unibuc.hello.data.CampaignRepository;
import ro.unibuc.hello.data.DonationEntity;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.dto.Campaign;
import ro.unibuc.hello.exception.CustomErrorHandler;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.ExceptionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CampaignService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign getCampaignById(String id){
        if(!StringUtils.hasText(id)){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        Optional<CampaignEntity> campaignEntityOptional= campaignRepository.findById(id);
        if(campaignEntityOptional.isEmpty()){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
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
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
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
             .description(campaignEntity.getDescription())
             .donationIds(CollectionUtils.isEmpty(campaignEntity.getDonationIds())?
                     new ArrayList<>():
                     campaignEntity.getDonationIds().stream().map(x->x.getId()).collect(Collectors.toList()))
             .build();
    }

    private CampaignEntity campaignToCampaignEntity(Campaign campaign) {

        return CampaignEntity.builder()
                .id(campaign.getId())
                .campaignGoal(campaign.getCampaignGoal())
                .title(campaign.getTitle())
                .description(campaign.getDescription())
                .donationIds(CollectionUtils.isEmpty(campaign.getDonationIds())?
                        new ArrayList<>():
                        donationRepository.findByIdIn(campaign.getDonationIds()))
                .build();
    }
}
