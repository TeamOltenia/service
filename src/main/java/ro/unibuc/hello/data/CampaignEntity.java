package ro.unibuc.hello.data;

import nonapi.io.github.classgraph.json.Id;

public class CampaignEntity {

    @Id
    public String id;

    private String title;
    private String description;
    private Double campaignGoal;

    public CampaignEntity(String id, String title, String description, Double campaignGoal) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.campaignGoal = campaignGoal;
    }

    @Override
    public String toString() {
        return "CampaignEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", campaignGoal=" + campaignGoal +
                '}';
    }
}
