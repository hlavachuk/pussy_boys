package Task3;

import java.util.Objects;

public class VolunteerEmployee extends Employee {
    private final String organizationName;

    public VolunteerEmployee(String employeeId, String fullName, String organizationName) {
        super(employeeId, fullName);
        this.organizationName = Objects.requireNonNull(organizationName, "organizationName не може бути null");
    }

    public String getOrganizationName() {
        return organizationName;
    }
}
