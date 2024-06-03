package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnBookFrame extends JDialog {
    private Library library;
    private JComboBox<String> borrowedBooksComboBox;
    private JTextField borrowerNameField;
    private JTextField bookIdField;
    private JButton returnButton;

    public ReturnBookFrame(JFrame parentFrame, Library library) {
        super(parentFrame, "Trả sách in", true);
        this.library = library;
        setSize(400, 250);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel borrowedBooksLabel = new JLabel("Danh sách sách đã mượn:");
        panel.add(borrowedBooksLabel);
        borrowedBooksComboBox = new JComboBox<>();
        populateBorrowedBooksComboBox();
        panel.add(borrowedBooksComboBox);

        JLabel borrowerNameLabel = new JLabel("Tên người mượn:");
        panel.add(borrowerNameLabel);
        borrowerNameField = new JTextField();
        panel.add(borrowerNameField);

        JLabel bookIdLabel = new JLabel("ID sách in:");
        panel.add(bookIdLabel);
        bookIdField = new JTextField();
        panel.add(bookIdField);

        returnButton = new JButton("Trả sách");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnPrintBook();
            }
        });
        panel.add(returnButton);

        JButton cancelButton = new JButton("Hủy bỏ");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void populateBorrowedBooksComboBox() {
        borrowedBooksComboBox.removeAllItems(); // Xóa tất cả các mục trong combobox trước khi thêm mới
        for (Book book : library.getBorrowedPrintBooks()) {
            borrowedBooksComboBox.addItem(book.getTitle() + " - ID: " + book.getId());
        }
    }

    private void returnPrintBook() {
        String borrowerName = borrowerNameField.getText();
        String bookIdStr = bookIdField.getText();

        if (borrowerName.isEmpty() || bookIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên người mượn và ID sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        Book borrowedBook = library.getBorrowedBookById(bookId);
        if (borrowedBook == null) {
            JOptionPane.showMessageDialog(this, "Sách không tồn tại hoặc không được mượn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        library.returnPrintBook(borrowedBook);
        JOptionPane.showMessageDialog(this, "Trả sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        dispose();

        // Cập nhật lại giao diện hiển thị sách sau khi trả
        updateBookDisplay();
    }

    private void updateBookDisplay() {
        // Xóa tất cả các mục trong combobox và sau đó điền lại danh sách sách đã mượn mới
        populateBorrowedBooksComboBox();
    }
}
