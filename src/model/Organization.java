package model;

public class Organization{
    private String organizationName;
    private String organizationType;

    private User user;

    public Organization(User user, String organizationName, String organizationType) {
        this.organizationName = organizationName;
        this.organizationType = organizationType;
        this.user = user;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
