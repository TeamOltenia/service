package ro.unibuc.hello.data;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

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



}
