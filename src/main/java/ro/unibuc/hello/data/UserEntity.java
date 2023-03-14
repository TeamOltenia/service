package ro.unibuc.hello.data;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.dto.Donation;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserEntity {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    @DBRef
    private List<DonationEntity> donations;

}
