package app.Listener;

import app.Collections.Collection_Table;
import app.Components.ImagePanelButton;
import app.DAO.DAO_Bill;
import app.DAO.DAO_BillDetail;
import app.DAO.DAO_Customer;
import app.GUI.BrewGUI;
import app.GUI.PaymentPage;
import app.Object.Bill;
import app.Object.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ActionListener_PaymentPage implements ActionListener {
    private PaymentPage paymentPage;

    public ActionListener_PaymentPage(PaymentPage paymentPage) {
        this.paymentPage = paymentPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (paymentPage.isRegistedRadioButton.isSelected()) {
            paymentPage.phoneInput.setEnabled(false);
            paymentPage.phoneInput.setBackground(Color.gray);
        } else {
            paymentPage.phoneInput.setEnabled(true);
            paymentPage.phoneInput.setBackground(new Color(241, 211, 178));
        }

        if (o.equals(paymentPage.confirmedButton)) {
            try {
                paymentPage.customerPayment = Float.parseFloat(paymentPage.custPaymentField.getText());
                paymentPage.change = paymentPage.customerPayment - paymentPage.totalBill;

                System.out.println("paymentPage.customerPayment: " + paymentPage.customerPayment);
                System.out.println("paymentPage.totalBill: " + paymentPage.totalBill);
                if (paymentPage.change < 0) {
                    JOptionPane.showMessageDialog(paymentPage, "Thanh toán thất bại !", "Thanh toán thất bại", JOptionPane.ERROR_MESSAGE);
                } else {
                    paymentPage.changeValueLabel.setText(String.valueOf(paymentPage.change));

                    DAO_Bill.createBill();
                    Bill bill = DAO_Bill.getLatestBill();

                    bill.setHourOut(Timestamp.valueOf(LocalDateTime.now()));
                    bill.setPhoneNumber(BrewGUI.BREW_HOTLINE);
                    bill.setTotal(paymentPage.collectionBillDetails.total());
                    bill.setCustPayment(paymentPage.customerPayment);
                    bill.setStatus("Ðã thanh toán");

                    paymentPage.collectionBillDetails.updateAllBillDetail(bill.getBillId());
                    bill.setDetails(paymentPage.collectionBillDetails.getList());
                    DAO_BillDetail.saveAllBD(paymentPage.collectionBillDetails.getList());

                    Customer c = new Customer();
                    if (paymentPage.isRegistedRadioButton.isSelected()) {
                        c = DAO_Customer.searchCustomerByPhoneNumber(paymentPage.phoneInput.getText());
                    }
                    bill.setCustomer(c);
                    bill.setEmployee(BrewGUI.acc.employee);
                    bill.setTable(paymentPage.collectionTable.getAllTables().get(paymentPage.collectionTable.getAllTables().size() - 1));

                    DAO_Bill.updateBill(bill);
//                    CardLayout cardLayout = (CardLayout) BrewGUI.pageContainer.getLayout();
//                    cardLayout.show(BrewGUI.pageContainer, "Receipt Page");
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }
}
