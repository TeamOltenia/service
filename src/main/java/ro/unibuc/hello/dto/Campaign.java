package ro.unibuc.hello.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Campaign {
    private String id;
    private String title;
    private String description;
    private Double campaignGoal;


}
