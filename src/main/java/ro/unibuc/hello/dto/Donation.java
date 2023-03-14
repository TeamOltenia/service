package ro.unibuc.hello.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Donation {
    private String id;
    private LocalDate dateOfDonation;
    private Double amount;
    private String message;
    private String userId;

//    private String campaignId;

}
