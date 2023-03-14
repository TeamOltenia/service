package ro.unibuc.hello.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class User {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> donationIds;
}
