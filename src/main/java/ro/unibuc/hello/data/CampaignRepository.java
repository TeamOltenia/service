package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Repository
public interface CampaignRepository extends MongoRepository<CampaignEntity, String> {

    Optional<CampaignEntity> findByDonationIdsContaining(DonationEntity donation);
}
