package ro.unibuc.hello.data;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class DonationEntity {

    @Id
    private String id;
    private LocalDate dateOfDonation;
    private Double amount;
    private String message;

    @DBRef
    private UserEntity user;

    @DBRef
    private CampaignEntity campaign;
}
