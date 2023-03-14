package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonationRepository extends MongoRepository<DonationEntity, String> {
}
