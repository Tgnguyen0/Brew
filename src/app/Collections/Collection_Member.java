package app.Collections;

import app.Object.Customer;
import java.util.ArrayList;

public class Collection_Member {
    ArrayList<Customer> ml;

    public Collection_Member() {
        ml = new ArrayList<Customer>();
    }

    public ArrayList<Customer> getAllMember() {
        return ml;
    }

    public boolean addMember(Customer newMember) {

        if (ml.stream().anyMatch(c -> c.getCustomerId().equals(newMember.getCustomerId()))) {
            return false;
        }
        return ml.add(newMember);
    }
    
    // Thêm phương thức để cập nhật
    public boolean updateMember(Customer updatedMember) {
        for (int i = 0 ; i < ml.size() ; i++) {
            if (ml.get(i).getCustomerId().equals(updatedMember.getCustomerId())) {
                ml.set(i, updatedMember);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMember(String id) {
        return ml.removeIf(c -> c.getCustomerId().equals(id));
    }

    public Customer searchMember(String phone) {
        for (int i = 0 ; i < ml.size() ; i++) {
            if (ml.get(i).getPhoneNumber().equals(phone)) {
                return ml.get(i);
            }
        }
        return null;
    }
    
    // Thêm phương thức search theo ID
    public Customer searchMemberById(String id) {
        for (int i = 0 ; i < ml.size() ; i++) {
            if (ml.get(i).getCustomerId().equals(id)) {
                return ml.get(i);
            }
        }
        return null;
    }
}