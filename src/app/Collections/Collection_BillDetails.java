package app.Collections;

import app.Object.Bill;
import app.Object.BillDetail;

import java.util.ArrayList;

public class Collection_BillDetails {
    ArrayList<BillDetail> bds;

    public Collection_BillDetails() {
        bds = new ArrayList<BillDetail>();
    }

    public ArrayList<BillDetail> getList() {
        return bds;
    }

    public Boolean addBillDetail(BillDetail newBillDetail) {
        if (!bds.contains(newBillDetail)) {
            bds.add(newBillDetail);
            System.out.println(bds);
            return true;
        }

        return false;
    }

    public Boolean updateBillDetail(BillDetail billDetail) {
        for (BillDetail bd : bds) {
            if (bd.getItemId().equals(billDetail.getItemId())) {
                bd.setQuantity(billDetail.getQuantity());
                bd.setTotal_price(bd.getQuantity(), bd.getTotal_price());
                System.out.println("updateBillDetail: " + bds);

                return true;
            }
        }

        return false;
    }

    public void updateBDOnOrder(int pos, int quantity, float price) {
        for (int i = 0 ; i < bds.size(); i++) {
            if (i == pos) {
                bds.get(i).setQuantity(quantity);
                bds.get(i).setTotal_price(bds.get(i).getQuantity(), price);
            }
        }

        System.out.println("updateBDOnOrder: " + bds);
    }

    public BillDetail getSelectedBillDetails(int pos) {
        for (int i = 0 ; i < bds.size(); i++) {
            if (i == pos) {
                return bds.get(i);
            }
        }

        return null;
    }

    public boolean deleteBillDetailById(String id) {
        for (int i = 0 ; i < bds.size() ; i++) {
             if (bds.get(i).getItemId().equals(id)) {
                 bds.remove(bds.get(i));
                 return true;
             }
        }

        return false;
    }

    public boolean removeAll() {
        return bds.removeAll(bds);
    }
}
