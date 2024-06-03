package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class BorrowPrintBookFrame extends JFrame {
    private JTextField borrowerNameField;
    private JTextField bookIdField;
    private Library library;

    public BorrowPrintBookFrame(Library library) {
        this.library = library;
        setTitle("Mượn sách in");
        setSize(400, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel borrowerNameLabel = new JLabel("Tên người mượn:");
        panel.add(borrowerNameLabel);
        borrowerNameField = new JTextField();
        panel.add(borrowerNameField);

        JLabel bookIdLabel = new JLabel("ID sách in cần mượn:");
        panel.add(bookIdLabel);
        bookIdField = new JTextField();
        panel.add(bookIdField);

        JButton borrowButton = new JButton("Mượn sách in");
        borrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowPrintBook();
            }
        });
        panel.add(borrowButton);

        JButton cancelButton = new JButton("Huỷ bỏ");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ nếu huỷ bỏ
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void borrowPrintBook() {
        String borrowerName = borrowerNameField.getText();
        String bookIdStr = bookIdField.getText();

        try {
            int bookId = Integer.parseInt(bookIdStr);
            Book printBook = library.getPrintBookById(bookId);
            if (printBook != null && !printBook.isBorrowed()) {
                LocalDate borrowDate = LocalDate.now();
                LocalDate returnDate = borrowDate.plusDays(30); // Hạn trả sau 30 ngày mượn
                library.borrowPrintBook(printBook, borrowerName, borrowDate, returnDate);
                JOptionPane.showMessageDialog(null, "Mượn sách thành công! Hạn trả: " + returnDate);
                
                // Xoá sách khỏi file HongZin.txt
                library.removePrintBook(bookId);
                dispose(); // Đóng cửa sổ sau khi mượn sách
            } else {
                JOptionPane.showMessageDialog(null, "Sách không tồn tại hoặc đã được mượn.");
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ID sách in là một số nguyên.");
        }
    }
}
