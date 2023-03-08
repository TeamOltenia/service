package ro.unibuc.hello.dto;

public class Campaign {
    private String id;
    private String title;
    private String description;
    private Double campaignGoal;

    public Campaign() {}

    public Campaign(String id, String title, String description, Double campaignGoal) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.campaignGoal = campaignGoal;
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

    @Override
    public String toString() {
        return "Campaign{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", campaignGoal=" + campaignGoal +
                '}';
    }

}
