package app.Object;

import java.util.Objects;

public class Account {
    public String accountId;
    public String username;
    public String password;
    public String role;
    public Employee employee;

    public Account() {
        this.accountId = "";
        this.username = "";
        this.password = "";
        this.role = "";
        this.employee = new Employee();
    }

    public Account(String username, String password, String role, Employee employee) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.employee = employee;
    }

    public Account(String accountId, String username, String password, String role, Employee employee) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.employee = employee;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", employee=" + employee +
                '}';
    }
}
