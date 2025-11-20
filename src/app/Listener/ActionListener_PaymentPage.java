package app.Listener;

import app.Collections.Collection_Table;
import app.Components.ImagePanelButton;
import app.DAO.DAO_Bill;
import app.DAO.DAO_BillDetail;
import app.DAO.DAO_Customer;
import app.DAO.DAO_Table;
import app.GUI.BrewGUI;
import app.GUI.PaymentPage;
import app.GUI.SellPage;
import app.Object.Bill;
import app.Object.Customer;
import app.Object.Status;
import app.Object.Table;
import com.itextpdf.layout.element.Tab;

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

        if (o == paymentPage.isRegistedRadioButton) {
            boolean isSelected = paymentPage.isRegistedRadioButton.isSelected();
            paymentPage.phoneInput.setEnabled(isSelected);
            if (isSelected) {
                paymentPage.phoneInput.setBackground(Color.gray);
            } else {
                paymentPage.phoneInput.setBackground(new Color(241, 211, 178));
            }
        }

        if (o.equals(paymentPage.confirmedButton)) {
            if (paymentPage.phoneInput.getText().trim().isEmpty() && !paymentPage.isRegistedRadioButton.isSelected()) {
                paymentPage.showPhoneInputEmptyOptionPane();
                return;
            }

            if (paymentPage.custPaymentField.getText().trim().isEmpty()) {
                paymentPage.showCustPaymentEmptyOptionPane();
                return;
            }

            try {
                paymentPage.customerPayment = Float.parseFloat(paymentPage.custPaymentField.getText());
                paymentPage.change = paymentPage.customerPayment - paymentPage.totalBill;

                System.out.println("paymentPage.customerPayment: " + paymentPage.customerPayment);
                System.out.println("paymentPage.totalBill: " + paymentPage.totalBill);
                if (paymentPage.change < 0) {
                    paymentPage.showCustPaymentLowerOptionPane();
                    return;
                } else {
                    paymentPage.changeValueLabel.setText(String.valueOf(paymentPage.change));

                    DAO_Bill.createBill();
                    Bill bill = DAO_Bill.getLatestBill();

                    bill.setHourOut(Timestamp.valueOf(LocalDateTime.now()));
                    bill.setTotal(paymentPage.collectionBillDetails.total());
                    bill.setCustPayment(paymentPage.customerPayment);
                    bill.setStatus("Ðã thanh toán");

                    paymentPage.collectionBillDetails.updateAllBillDetail(bill.getBillId());
                    bill.setDetails(paymentPage.collectionBillDetails.getList());
                    DAO_BillDetail.saveAllBD(paymentPage.collectionBillDetails.getList());

                    Customer c = new Customer();
                    if (!paymentPage.isRegistedRadioButton.isSelected()) {
                        c = DAO_Customer.searchCustomerByPhoneNumber(paymentPage.phoneInput.getText());
                        bill.setPhoneNumber(c.getPhoneNumber());
                    } else {
                        bill.setPhoneNumber("");
                    }
                    bill.setCustomer(c);
                    bill.setEmployee(BrewGUI.acc.employee);

                    if (paymentPage.collectionTable.getAllTables().isEmpty()) {
                        Table t = new Table();
                        bill.setTable(t);
                    } else {
                        Table t = paymentPage.collectionTable.getAllTables().get(paymentPage.collectionTable.getAllTables().size() - 1);
                        bill.setTable(t);
                    }

                    DAO_Bill.updateBill(bill);
                    paymentPage.showPaySuccessfullyOptionPane(paymentPage.change);
//                    CardLayout cardLayout = (CardLayout) BrewGUI.pageContainer.getLayout();
//                    cardLayout.show(BrewGUI.pageContainer, "Receipt Page");

                    paymentPage.collectionBillDetails.removeAll();
                    paymentPage.collectionTable.removeAll();
                    for (int i = SellPage.productTableModel.getRowCount() - 1; i >= 0; i--) {
                        SellPage.productTableModel.removeRow(i);
                    }
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        if (paymentPage.exitButton == o) {
            paymentPage.dispose();
        }
    }
}
