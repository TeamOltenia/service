package ro.unibuc.hello.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.Campaign;
import ro.unibuc.hello.service.CampaignService;

@RestController
@RequestMapping(value = "/api/v1/campaign", produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<Campaign> getCamapignById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(campaignService.getCampaignById(id));
    }

    @PutMapping(value = "/updateById/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Campaign> updateCampaignById(@PathVariable("id") String id, @RequestBody Campaign campaign) {

        String objId = campaignService.updateCampaignById(id, campaign);

        return ResponseEntity.ok()
                .body(campaignService.getCampaignById(objId));
    }

}
