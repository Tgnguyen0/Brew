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
            case "Trang chủ":
                // Khởi tạo trang Trang chủ
//                if (DevCafeGUI.homePage != null) DevCafeGUI.homePage = new HomePage();

                cardLayout.show(BrewGUI.pageContainer, "Home Page");
                break;
            case "Đồ uống":
                // Khởi tạo trang Bán hàng
//                if (DevCafeGUI.sellPage != null) DevCafeGUI.sellPage = new SellPage();

                cardLayout.show(BrewGUI.pageContainer, "Sell Page");
                break;
            case "Hóa đơn":
                cardLayout.show(BrewGUI.pageContainer, "Receipt Page");
                break;
            case "Sản phẩm":
                cardLayout.show(BrewGUI.pageContainer, "Product Page");
                break;
            case "Khách hàng":
                cardLayout.show(BrewGUI.pageContainer, "Promotion Page");
                break;
            case "Thống kê":
                cardLayout.show(BrewGUI.pageContainer, "Statistic Page");
                break;
            case "Nhân viên":
                cardLayout.show(BrewGUI.pageContainer, "Employee Page");
                break;
        }
    }

}
