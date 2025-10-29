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
            case "Home":
                // Khởi tạo trang Trang chủ
//                if (DevCafeGUI.homePage != null) DevCafeGUI.homePage = new HomePage();

                cardLayout.show(BrewGUI.pageContainer, "Home Page");
                break;
            case "Menu":
                // Khởi tạo trang Bán hàng
//                if (DevCafeGUI.sellPage != null) DevCafeGUI.sellPage = new SellPage();

                cardLayout.show(BrewGUI.pageContainer, "Sell Page");
                break;
            case "Bill":
                cardLayout.show(BrewGUI.pageContainer, "Receipt Page");
                break;
            case "Product":
                cardLayout.show(BrewGUI.pageContainer, "Product Page");
                break;
            case "Customer":
                cardLayout.show(BrewGUI.pageContainer, "Promotion Page");
                break;
            case "Statistics":
                cardLayout.show(BrewGUI.pageContainer, "Statistic Page");
                break;
            case "Employee":
                cardLayout.show(BrewGUI.pageContainer, "Employee Page");
                break;
        }
    }

}
