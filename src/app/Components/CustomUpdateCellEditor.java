package app.Components;

import app.Collections.Collection_BillDetails;
import app.GUI.SellPage;
import app.Object.BillDetail;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class CustomUpdateCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new GridBagLayout());
    private Collection_BillDetails collectionBillDetails;
    private final JButton increaseButton;
    private final JButton decreaseButton;
    private final JTextField quantityField;

    public CustomUpdateCellEditor(Collection_BillDetails collectionBillDetails) {
        this.collectionBillDetails = collectionBillDetails;

        increaseButton = new JButton("+");
        increaseButton.setBackground(Color.white);
        increaseButton.setPreferredSize(new Dimension(25, 25));

        decreaseButton = new JButton("-");
        decreaseButton.setBackground(Color.white);
        decreaseButton.setPreferredSize(new Dimension(25, 25));

        quantityField = new JTextField("1", 3);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        quantityField.setBackground(Color.white);
        quantityField.setPreferredSize(new Dimension(30, 25));

        // Tạo 1 panel con để chứa 3 thành phần theo hàng ngang
        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        innerPanel.add(decreaseButton);
        innerPanel.add(quantityField);
        innerPanel.add(increaseButton);
        innerPanel.setBackground(Color.white);
        innerPanel.setOpaque(false);

        // Dùng GridBagLayout để căn giữa innerPanel theo cả 2 chiều
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(innerPanel, gbc);

        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);

        // Xử lý sự kiện
        increaseButton.addActionListener(e -> changeValue(1));
        decreaseButton.addActionListener(e -> changeValue(-1));
    }

    private void changeValue(int delta) {
        try {
            int value = Integer.parseInt(quantityField.getText());
            value = Math.max(0, value + delta);

            int selectedRow = SellPage.productTable.getSelectedRow();
            if (selectedRow == -1) return;

            if (value == 0) {
                BillDetail bd = collectionBillDetails.getSelectedBillDetails(selectedRow);
                bd.setQuantity(value);

                if (SellPage.productTable.isEditing()) {
                    SellPage.productTable.getCellEditor().stopCellEditing();
                }

                SellPage.productTableModel.removeRow(selectedRow);
                collectionBillDetails.deleteBillDetailById(bd.getItemId());
                return;
            }

            quantityField.setText(String.valueOf(value));

            double unitPrice = Double.parseDouble(SellPage.productTableModel.getValueAt(selectedRow, 2).toString()) /
                    Integer.parseInt(SellPage.productTableModel.getValueAt(selectedRow, 1).toString());
            SellPage.productTableModel.setValueAt(value * unitPrice, selectedRow, 2);

        } catch (NumberFormatException ex) {
            quantityField.setText("1");
        }
    }

    @Override
    public Object getCellEditorValue() {
        return quantityField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (value != null) {
            quantityField.setText(value.toString());
        }

        SellPage.productTable.setRowSelectionInterval(row, row);

        return panel;
    }
}
