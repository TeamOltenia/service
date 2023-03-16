package ro.unibuc.hello.data;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.dto.Donation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CampaignEntity {

    @Id
    private String id;

    private String title;
    private String description;
    private Double campaignGoal;

    @DBRef
    List<DonationEntity> donationIds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignEntity campaign = (CampaignEntity) o;
        return id.equals(campaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
