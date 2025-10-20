package app.AppFunctions;

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
        return ml.add(newMember);
    }

    public boolean addAllMember(ArrayList<Customer> newList) {
        return ml.addAll(newList);
    }

    public boolean deleteMember(String id) {
        for (int i = 0 ; i < ml.size() ; i++) {
            if (ml.get(i).getId().equals(id)) {
                ml.remove(i);
                return true;
            }
        }

        return false;
    }

    public Customer searchMember(String phone) {
        for (int i = 0 ; i < ml.size() ; i++) {
            if (ml.get(i).getPhoneNumber().equals(phone)) {
                return ml.get(i);
            }
        }

        return null;
    }
}
