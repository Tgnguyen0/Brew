package app.Collections;

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
                System.out.println(bds);
                return true;
            }
        }

        return false;
    }

    public Boolean addListBillDetail(ArrayList<BillDetail> bdl) {
        return bds.addAll(bdl);
    }

    public boolean deleteBillDetail(String id) {
        for (int i = 0 ; i < bds.size() ; i++) {
            // SỬA LỖI: Dùng getItemId() đã khôi phục/ánh xạ
            if (bds.get(i).getItemId().equals(id)) { 
                bds.remove(i);
                return true;
            }
        }
        return false;
    }
}