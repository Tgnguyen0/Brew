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
                bd.setTotal_price(bd.getQuantity(), bd.getTotal_price());
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
            // if (bds.get(i).getItem().getId().equals(id)) {
            //     bds.remove(i);
            //     return true;
            // }
        }

        return false;
    }
}
