package app.Listener;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.GUI.DevCafeGUI;
import app.GUI.HomePage;
import app.GUI.SellPage;

public class ActionListener_NavbarPanel implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) DevCafeGUI.pageContainer.getLayout();
        String command = e.getActionCommand();

        switch (command) {
            case "Trang Chủ":
                // Khởi tạo trang Trang chủ
//                if (DevCafeGUI.homePage != null) DevCafeGUI.homePage = new HomePage();

                cardLayout.show(DevCafeGUI.pageContainer, "Home Page");
                break;
            case "Bán Hàng":
                // Khởi tạo trang Bán hàng
//                if (DevCafeGUI.sellPage != null) DevCafeGUI.sellPage = new SellPage();

                cardLayout.show(DevCafeGUI.pageContainer, "Sell Page");
                break;
            case "Hóa Đơn":
                cardLayout.show(DevCafeGUI.pageContainer, "Receipt Page");
                break;
            case "Sản Phẩm":
                cardLayout.show(DevCafeGUI.pageContainer, "Product Page");
                break;
            case "Khuyến Mại":
                cardLayout.show(DevCafeGUI.pageContainer, "Promotion Page");
                break;
            case "Thống Kê":
                cardLayout.show(DevCafeGUI.pageContainer, "Statistic Page");
                break;
            case "Nhân Viên":
                cardLayout.show(DevCafeGUI.pageContainer, "Employee Page");
                break;
        }
    }

}
