package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignRepository extends MongoRepository<CampaignEntity, String> {

}
