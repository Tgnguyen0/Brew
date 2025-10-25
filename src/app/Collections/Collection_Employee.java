package app.Collections;

import app.Object.Employee;

import java.util.ArrayList;

public class Collection_Employee {
    private ArrayList<Employee> employeeList;

    public Collection_Employee() {
        this.employeeList = new ArrayList<>();
    }

    public ArrayList<Employee> getEmployeeList() {
        return this.employeeList;
    }

    public boolean addEmployee(Employee newEmployee) {
        for (Employee emp : employeeList) {
            if (emp.getId().equals(newEmployee.getId())) {
                return false;
            }
        }

        return employeeList.add(newEmployee);
    }

    public boolean addAllEmployee(ArrayList<Employee> newList) {
        return employeeList.addAll(newList);
    }

    public boolean deleteEmployee(String id) {
        for (Employee emp : employeeList) {
            if (emp.getId().equals(id)) {
                return employeeList.remove(id);
            }
        }

        return false;
    }
}
