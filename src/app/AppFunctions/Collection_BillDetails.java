package app.AppFunctions;

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

    // public Boolean addBillDetail(BillDetail newBillDetail) {
    //     for (int i = 0 ; i < bds.size(); i++) {
    //         if (newBillDetail.getItem().getId().equals(bds.get(i).getItem().getId())
    //             && newBillDetail.getItem().getServeHot() == bds.get(i).getItem().getServeHot()) {
    //             int old_quantity = bds.get(i).getQuantity();
    //             double price = bds.get(i).getItem().getPrice();
    //             old_quantity++;

    //             bds.get(i).setQuantity(old_quantity);
    //             bds.get(i).setTotal_price(old_quantity, price);
    //             return false;
    //         }
    //     }

    //     return bds.add(newBillDetail);
    // }

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

    public Integer getSize() {
        return bds.size();
    }
}
