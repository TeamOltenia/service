package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DonationTest {

    private final Donation donation = Donation.builder()
            .id("1")
            .dateOfDonation(LocalDate.of(2023, 3, 22))
            .amount(123.55)
            .message("This is a powerful donation")
            .userId("9")
            .campaignId("12")
            .build();

    @Test
    void testDonationId() {
        assertEquals("1", donation.getId());
    }

    @Test
    void testDateDonation() {
        assertThat(donation.getDateOfDonation()).isEqualTo(LocalDate.of(2023, 3, 22));
    }

    @Test
    void testAmount() {
        assertThat(donation.getAmount()).isEqualTo(123.55);
    }

    @Test
    void testUserId() {
        assertThat(donation.getUserId()).isEqualTo("9");
    }

    @Test
    void testCampaignId() {
        assertThat(donation.getCampaignId()).isEqualTo("12");
    }

    @Test
    void testAll() {
        Assertions.assertAll(() -> assertNotEquals("Wrong Message", donation.getMessage()),
                () -> assertNotEquals(102.99, donation.getAmount()),
                () -> assertNotEquals(LocalDate.of(2023, 3, 20), donation.getDateOfDonation()),
                () -> assertNotEquals("10", donation.getUserId())
        );
    }
}
