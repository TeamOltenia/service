package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DonationRepository extends MongoRepository<DonationEntity, String> {
    List<DonationEntity> findByIdIn(List<String> idList);
}
