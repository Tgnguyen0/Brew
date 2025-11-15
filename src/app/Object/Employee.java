package app.Object;

import java.io.Serializable;

public class Employee implements Serializable {
    private String empId;
    private String firstName;
    private String lastName;
    private boolean gender; // true = Nam, false = Nữ
    private String phoneNumber;
    private String email;
    private String role;
    private String address;

    public Employee() {}

    public Employee(String empId, String firstName, String lastName, boolean gender,
                    String phone, String email, String role, String address) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phone;
        this.email = email;
        this.role = role;
        this.address = address; 
    }

    // ===== Canonical getters/setters =====
    public String getId() { return this.empId; }
    public void setId(String id) { this.empId = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public boolean isGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }

    public String getPhone() { return this.phoneNumber; }
    public void setPhone(String phone) { this.phoneNumber = phone; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return this.role; }
    public void setRole(String role) { this.role = role; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmployeeId() { return getId(); }
    public void setEmployeeId(String id) { setId(id); }

    public boolean isSex() { return isGender(); }        // alias cho giới tính
    public void setSex(boolean sex) { setGender(sex); }

    public String getPhoneNumber() { return getPhone(); }
    public void setPhoneNumber(String phoneNumber) { setPhone(phoneNumber); }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", phone='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
