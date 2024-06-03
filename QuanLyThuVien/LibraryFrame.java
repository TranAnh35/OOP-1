package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryFrame extends JFrame {
    private Library library;

    public LibraryFrame(Library library) {
        this.library = library;
        setTitle("Quản lý thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
    
        JButton addButton = new JButton("Thêm sách");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBookTypeSelectionDialog("Thêm sách", null);
                //dispose();
            }
        });
        panel.add(addButton);

        JButton deleteButton = new JButton("Xoá sách");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBookTypeSelectionDialog("Xoá sách", null);
                //dispose();
            }
        });
        panel.add(deleteButton);

        JButton displayPrintBooksButton = new JButton("Hiển thị sách in");
        displayPrintBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBookTypeSelectionDialog("Hiển thị sách", null);
                //dispose();
            }
        });
        panel.add(displayPrintBooksButton);

        JButton borrowPrintBookButton = new JButton("Mượn sách in");
        borrowPrintBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBorrowPrintBookFrame();
                //dispose();
            }
        });
        panel.add(borrowPrintBookButton);

        // Nút trả sách in
        JButton returnPrintBookButton = new JButton("Trả sách in");
        returnPrintBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayReturnPrintBookDialog();
                //dispose();
            }
        });
        panel.add(returnPrintBookButton);

        JButton exitButton = new JButton("Thoát chương trình");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        add(panel);
    }
    //Hiển thị dialog với chọn loại sách
    private void displayBookTypeSelectionDialog(String action, String selectedType) {
        BookTypeSelectionDialog dialog = new BookTypeSelectionDialog(this, action, library);
        dialog.setVisible(true);
    }
    //Hiển thị dialog mượn sách in
    private void displayBorrowPrintBookFrame() {
        BorrowPrintBookFrame borrowPrintBookFrame = new BorrowPrintBookFrame(library);
        borrowPrintBookFrame.setVisible(true);
    }

    // Hiển thị dialog để trả sách in
    private void displayReturnPrintBookDialog() {
        ReturnBookFrame returnBookFrame = new ReturnBookFrame(this, library);
        returnBookFrame.setVisible(true);
    }
}
