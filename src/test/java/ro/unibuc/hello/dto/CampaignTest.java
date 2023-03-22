package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CampaignTest {

    private Campaign campaign = Campaign.builder()
            .id("1")
            .title("title")
            .description("description")
            .campaignGoal(1000.0)
            .build();

    @Test
    void testIdCorect() {
        assertEquals("1", campaign.getId());
    }

    @Test
    void testContent() {
        Assertions.assertAll(() -> assertEquals("title", campaign.getTitle()),
                () -> assertEquals("description", campaign.getDescription()),
                () -> assertEquals(Double.valueOf(1000.0), campaign.getCampaignGoal())
        );
    }

    @Test
    void testContentWrong() {
        Assertions.assertAll(() -> assertNotEquals("wrongTitle", campaign.getTitle()),
                () -> assertNotEquals("wrongDescription", campaign.getDescription()),
                () -> assertNotEquals(Double.valueOf(1.0), campaign.getCampaignGoal())
        );
    }
}
