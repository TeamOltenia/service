package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.unibuc.hello.dto.Donation;
import ro.unibuc.hello.service.DonationService;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/donation", produces = MediaType.APPLICATION_JSON_VALUE)

public class DonationController {
    
    @Autowired
    private DonationService donationService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(donationService.getDonationById(id));
    }

    @PutMapping(value = "/updateById/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Donation> updateDonationById(@PathVariable("id") String id, @RequestBody Donation donation) {

        String objId = donationService.updateDonationById(id, donation);

        return ResponseEntity.ok()
                .body(donationService.getDonationById(objId));
    }

    @PostMapping("")
    public ResponseEntity<Donation> createDonation(@RequestBody Donation
                                                         donation) throws Exception{
        String id= donationService.saveDonation(donation);
        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id).toUriString();
        return ResponseEntity.created(new URI(uri))
                .body(donationService.getDonationById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteDonationById(@PathVariable("id") String id) {

        donationService.deleteDonationById(id);

        return ResponseEntity.ok().build();
    }
    

}
