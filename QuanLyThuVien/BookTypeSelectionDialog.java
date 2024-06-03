package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookTypeSelectionDialog extends JDialog {
    private JButton confirmButton;
    private JButton backButton;
    private String selectedType;
    private Library library;

    public BookTypeSelectionDialog(JFrame parent, String action, Library library) {
        super(parent, "Chọn loại sách", true);
        this.library = library;
        setSize(300, 150);
        setLocationRelativeTo(parent);

        JRadioButton printBookRadioButton = new JRadioButton("Sách in");
        JRadioButton eBookRadioButton = new JRadioButton("Sách điện tử");
        ButtonGroup group = new ButtonGroup();
        group.add(printBookRadioButton);
        group.add(eBookRadioButton);

        confirmButton = new JButton("Xác nhận");
        backButton = new JButton("Quay lại");

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (printBookRadioButton.isSelected()) {
                    selectedType = "PrintBook";
                } else if (eBookRadioButton.isSelected()) {
                    selectedType = "EBook";
                }
                handleAction(action, selectedType);
                dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedType = null;
                dispose();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Chọn loại sách:"));
        panel.add(new JLabel());
        panel.add(printBookRadioButton);
        panel.add(eBookRadioButton);
        panel.add(backButton);
        panel.add(confirmButton);

        add(panel);
    }

    public String getSelectedType() {
        return selectedType;
    }

    private void handleAction(String action, String selectedType) {
        if ("Thêm sách".equals(action)) {
            if ("PrintBook".equals(selectedType)) {
                AddPrintBookFrame addPrintBookFrame = new AddPrintBookFrame(library);
                addPrintBookFrame.setVisible(true);
            } else if ("EBook".equals(selectedType)) {
                AddEBookFrame addEBookFrame = new AddEBookFrame(library);
                addEBookFrame.setVisible(true);
            }
        } else if ("Xoá sách".equals(action)) {
            if ("PrintBook".equals(selectedType)) {
                DeletePrintBookFrame deletePrintBookFrame = new DeletePrintBookFrame(library);
                deletePrintBookFrame.setVisible(true);
            } else if ("EBook".equals(selectedType)) {
                DeleteEBookFrame deleteEBookFrame = new DeleteEBookFrame(library);
                deleteEBookFrame.setVisible(true);
            }
        } else if ("Hiển thị sách".equals(action)) {
            if ("PrintBook".equals(selectedType)) {
                DisplayPrintBooksFrame displayPrintBooksFrame = new DisplayPrintBooksFrame();
                displayPrintBooksFrame.setVisible(true);
                displayPrintBooksFrame.updatePrintBooks(library.getPrintBooks()); // Cập nhật danh sách sách in
            } else if ("EBook".equals(selectedType)) {
                DisplayEBooksFrame displayEBooksFrame = new DisplayEBooksFrame();
                displayEBooksFrame.setVisible(true);
                displayEBooksFrame.updateEBooks(library.getEBooks()); // Cập nhật danh sách sách điện tử
            }
        }
    }
}
