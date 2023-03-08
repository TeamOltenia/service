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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCampaignGoal() {
        return campaignGoal;
    }

    public void setCampaignGoal(Double campaignGoal) {
        this.campaignGoal = campaignGoal;
    }
}
