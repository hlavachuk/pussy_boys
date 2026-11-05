package Task3;

import java.util.Objects;

public abstract class Employee {
    private final String employeeId;
    private final String fullName;

    protected Employee(String employeeId, String fullName) {
        this.employeeId = Objects.requireNonNull(employeeId, "employeeId не може бути null");
        this.fullName = Objects.requireNonNull(fullName, "fullName не може бути null");
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }
}
