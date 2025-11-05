package app.Object;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer implements Serializable {
    private String customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean sex;
    private LocalDateTime createdDate;

    public Customer() {
    }

	public Customer(String customerId, String firstName, String lastName, String phoneNumber, String email, Boolean sex,
			LocalDateTime createdDate) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.sex = sex;
		this.createdDate = createdDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
    
	public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
	public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            this.firstName = "";
            this.lastName = "";
            return;
        }

        String[] parts = fullName.trim().split("\\s+");
        if (parts.length > 0) {
            this.firstName = parts[parts.length - 1];
            this.lastName = "";
            for (int i = 0; i < parts.length - 1; i++) {
                this.lastName += parts[i] + " ";
            }
            this.lastName = this.lastName.trim();
        } else {
             this.firstName = "";
             this.lastName = "";
        }
    }
}

    