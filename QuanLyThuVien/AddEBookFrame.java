package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddEBookFrame extends JFrame {
    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTextField borrowDateField;
    private JTextField returnDateField;
    private JTextField fileFormatField;
    private JTextField fileSizeField;

    private JLabel fileFormatLabel;
    private JLabel fileSizeLabel;

    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel yearLabel;
    private JLabel borrowDateLabel;
    private JLabel returnDateLabel;

    private Library library;

    public AddEBookFrame(Library library) {
        this.library = library;
        setTitle("Thêm sách điện tử");
        setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        // Labels
        idLabel = new JLabel("ID:");
        panel.add(idLabel);
        idField = new JTextField();
        panel.add(idField);

        titleLabel = new JLabel("Tên sách:");
        panel.add(titleLabel);
        titleField = new JTextField();
        panel.add(titleField);

        authorLabel = new JLabel("Tác giả:");
        panel.add(authorLabel);
        authorField = new JTextField();
        panel.add(authorField);

        yearLabel = new JLabel("Năm xuất bản:");
        panel.add(yearLabel);
        yearField = new JTextField();
        panel.add(yearField);

        borrowDateLabel = new JLabel("Ngày mượn:");
        panel.add(borrowDateLabel);
        borrowDateField = new JTextField();
        panel.add(borrowDateField);

        returnDateLabel = new JLabel("Ngày trả:");
        panel.add(returnDateLabel);
        returnDateField = new JTextField();
        panel.add(returnDateField);

        fileFormatLabel = new JLabel("Định dạng file:");
        panel.add(fileFormatLabel);
        fileFormatField = new JTextField();
        panel.add(fileFormatField);

        fileSizeLabel = new JLabel("Kích thước file (KB):");
        panel.add(fileSizeLabel);
        fileSizeField = new JTextField();
        panel.add(fileSizeField);

        // Buttons
        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEBook();
            }
        });
        panel.add(saveButton);

        JButton backButton = new JButton("Trở lại");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ hiện tại
            }
        });
        panel.add(backButton);

        add(panel);
    }

    private void saveEBook() {
    try {
        int id = Integer.parseInt(idField.getText());
        String title = titleField.getText();
        String author = authorField.getText();
        int year = Integer.parseInt(yearField.getText());
        LocalDate borrowDate = LocalDate.parse(borrowDateField.getText());
        LocalDate returnDate = LocalDate.parse(returnDateField.getText());
        String fileFormat = fileFormatField.getText();
        int fileSize = Integer.parseInt(fileSizeField.getText());
        
        // Tạo một đối tượng EBook từ thông tin nhập vào
         EBook newEBook = new EBook(id, title, author, year, borrowDate, returnDate, fileFormat, fileSize);

        // Thêm sách vào danh sách eBook trong Library
        library.addEBook(id, title, author, year, borrowDate, returnDate, fileFormat, fileSize);
        // Lưu sách vào cơ sở dữ liệu MySQL
        library.saveBookToDatabase(newEBook);
        // Lưu danh sách sách vào file HongZin
        library.saveLibraryToFile("HongZin.txt");

        JOptionPane.showMessageDialog(this, "eBook đã được thêm vào thư viện");
        clearFields(); // Xóa trống các trường nhập liệu sau khi thêm sách
    } catch (NumberFormatException | DateTimeParseException ex) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng dữ liệu.");
    }
}


    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        borrowDateField.setText("");
        returnDateField.setText("");
        fileFormatField.setText("");
        fileSizeField.setText("");
    }
}
