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
            if (bd.getMenuId().equals(billDetail.getMenuId())) {
                bd.setQuantity(billDetail.getQuantity());
                bd.setPrice(billDetail.getPrice());
                bd.Total_price();
                System.out.println("updateBillDetail: " + bds);

                return true;
            }
        }

        return false;
    }

    public boolean updateAllBillDetail(String billId) {
        for (BillDetail bd: bds) {
            bd.setBillId(billId);
        }

        return true;
    }

    public void updateBDOnOrder(int pos, int quantity, float price) {
        for (int i = 0 ; i < bds.size(); i++) {
            if (i == pos) {
                bds.get(i).setQuantity(quantity);
                bds.get(i).setPrice(price);
                bds.get(i).Total_price();
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
             if (bds.get(i).getMenuId().equals(id)) {
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
