package ro.unibuc.hello.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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



}
