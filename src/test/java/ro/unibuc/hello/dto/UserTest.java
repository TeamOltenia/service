package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {

    private User user = User.builder()
            .id("1")
            .firstName("firstName")
            .lastName("lastName")
            .email("userTest@email.com")
            .build();

    @Test
    void testIdCorrect() {
        assertEquals("1", user.getId());
    }

    @Test
    void testContent() {
        Assertions.assertAll(() -> assertEquals("firstName", user.getFirstName()),
                () -> assertEquals("lastName2", user.getLastName()),
                () -> assertEquals("userTest2@email.com", user.getEmail())
        );
    }

    @Test
    void testContentWrong() {
        Assertions.assertAll(() -> assertNotEquals("firstNameWrong", user.getFirstName()),
                () -> assertNotEquals("lastNameWrong", user.getLastName()),
                () -> assertNotEquals("userTestWrong@email.com", user.getEmail())
        );
    }

}
