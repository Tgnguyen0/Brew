package app.Listener;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.GUI.BrewGUI;

public class ActionListener_NavbarPanel implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) BrewGUI.pageContainer.getLayout();
        String command = e.getActionCommand();

        switch (command) {
            case "Trang Chủ":
                // Khởi tạo trang Trang chủ
//                if (DevCafeGUI.homePage != null) DevCafeGUI.homePage = new HomePage();

                cardLayout.show(BrewGUI.pageContainer, "Home Page");
                break;
            case "Bán Hàng":
                // Khởi tạo trang Bán hàng
//                if (DevCafeGUI.sellPage != null) DevCafeGUI.sellPage = new SellPage();

                cardLayout.show(BrewGUI.pageContainer, "Sell Page");
                break;
            case "Hóa Đơn":
                cardLayout.show(BrewGUI.pageContainer, "Receipt Page");
                break;
            case "Sản Phẩm":
                cardLayout.show(BrewGUI.pageContainer, "Product Page");
                break;
            case "Khách Hàng":
                cardLayout.show(BrewGUI.pageContainer, "Customer Page");
                break;
            case "Thống Kê":
                cardLayout.show(BrewGUI.pageContainer, "Statistic Page");
                break;
            case "Nhân Viên":
                cardLayout.show(BrewGUI.pageContainer, "Employee Page");
                break;
        }
    }

}
