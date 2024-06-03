package QuanLyThuVien;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddPrintBookFrame extends JFrame {
    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTextField borrowDateField;
    private JTextField returnDateField;

    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel yearLabel;
    private JLabel borrowDateLabel;
    private JLabel returnDateLabel;

    private Library library;

    public AddPrintBookFrame(Library library) {
        this.library = library;
        setTitle("Thêm sách in");
        setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

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

        // Buttons
        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePrintBook();
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

    private void savePrintBook() {
    try {
        int id = Integer.parseInt(idField.getText());
        String title = titleField.getText();
        String author = authorField.getText();
        int year = Integer.parseInt(yearField.getText());
        LocalDate borrowDate = LocalDate.parse(borrowDateField.getText());
        LocalDate returnDate = LocalDate.parse(returnDateField.getText());

        // // Tạo một đối tượng Book từ thông tin nhập vào
        Book newBook = new Book(id, title, author, year, borrowDate, returnDate);
        
        // Thêm sách vào danh sách sách in trong Library
        library.addPrintBook(id, title, author, year, borrowDate, returnDate);
        // Lưu sách vào cơ sở dữ liệu MySQL
        library.saveBookToDatabase(newBook);
        
        JOptionPane.showMessageDialog(this, "Sách in đã được thêm vào thư viện");
        clearFields(); // Xóa trống các trường nhập liệu sau khi thêm sách
        // Lưu sách vào file HongZin
        // FileHandler.saveBooksToFile(library.getBooks(), library.getEBooks(), "HongZin.txt");
        library.saveLibraryToFile("HongZin.txt");
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
    }
}
