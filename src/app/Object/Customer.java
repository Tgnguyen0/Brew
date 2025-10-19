package app.Object;

import java.io.Serializable;
import java.time.LocalDate;

public class Customer implements Serializable {
    private String customerId;
    private String firtName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String sex;

    public Customer() {
    }

    public Customer(String customerId, String firtName, String lastName, String phoneNumber, String email, String sex) {
        this.customerId = customerId;
        this.firtName = firtName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
    }

    public String getId() {
        return this.customerId;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
