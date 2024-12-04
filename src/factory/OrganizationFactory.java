package factory;

import model.Organization;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationFactory{
    public Organization createOrganization(User user, String organizationName, String organizationType) {
        return new Organization(user, organizationName, organizationType);
    }

    public static Organization createOrganizationFromResultSet(User user, ResultSet rs) throws SQLException {
        String organizationName = rs.getString("organizationName");
        String organizationType = rs.getString("organizationType");
        return new Organization(user, organizationName, organizationType);
    }
}
