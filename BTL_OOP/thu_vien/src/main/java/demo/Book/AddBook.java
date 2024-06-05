package demo.Book;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.Functions.MySqlConnection;
import demo.Manager.GuiManager;

public class AddBook extends JFrame {

    public AddBook(DefaultTableModel physicalBookTableModel) {

        setTitle("Thêm sách mới");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Phần bên trái giao diện
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(240, 255, 255));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 50)); // Khoảng cách lớn giữa chữ và nút

        // Tiêu đề "Quản lý thư viện"
        JLabel titleLabel = new JLabel("Quản lý thư viện");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel, BorderLayout.NORTH);

        // Phần chứa các nút chức năng
        JPanel functionPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        functionPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Khoảng cách giữa chữ và nút
        functionPanel.setBackground(new Color(240, 255, 255));

        String[] options = {"Trang chủ", "Quản lý kho sách", "Quản lý mượn sách", "Quản lý trả sách", "Quản lý nhân viên", "Quản lý người mượn"};
        String[] iconPaths = {"home.png", "book.png", "calendarBorrow.png", "calendarReturn.png", "user.png", "manyUser.png"};

        for (int i = 0; i < options.length; i++) {
            JButton optionButton = createButton(options[i], iconPaths[i]); // Tạo nút chức năng
            functionPanel.add(optionButton);
        }
        leftPanel.add(functionPanel, BorderLayout.CENTER);

        // Phần bên phải giao diện
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 255, 255));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề "Thêm sách mới"
        JLabel addBookTitleLabel = new JLabel("Thêm sách mới");
        addBookTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addBookTitleLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Khoảng cách dưới của tiêu đề
        rightPanel.add(addBookTitleLabel, BorderLayout.NORTH);

        // Phần thông tin sách cần thêm
        JPanel bookInfoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        bookInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bookInfoPanel.setBackground(new Color(240, 255, 255));

        JLabel idLabel = new JLabel("ID sách:");
        JTextField idField = new JTextField();

        JLabel titleLabelAddBook = new JLabel("Tiêu đề sách:");
        JTextField titleField = new JTextField();

        JLabel authorLabel = new JLabel("Tác giả:");
        JTextField authorField = new JTextField();

        JLabel yearLabel = new JLabel("Năm xuất bản:");
        JTextField yearField = new JTextField();

        JLabel quantityLabel = new JLabel("Số lượng sách:");
        JTextField quantityField = new JTextField();

        bookInfoPanel.add(idLabel);
        bookInfoPanel.add(idField);
        bookInfoPanel.add(titleLabelAddBook);
        bookInfoPanel.add(titleField);
        bookInfoPanel.add(authorLabel);
        bookInfoPanel.add(authorField);
        bookInfoPanel.add(yearLabel);
        bookInfoPanel.add(yearField);
        bookInfoPanel.add(quantityLabel);
        bookInfoPanel.add(quantityField);

        // Button thêm sách
        JButton addButton = new JButton("Thêm sách");
        addButton.setBackground(new Color(0, 139, 139));
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);

        // Khi nhấn nút "Thêm sách"
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lấy thông tin từ các trường nhập liệu
                    String id = idField.getText();
                    String title = titleField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
        
                    // Kiểm tra tính duy nhất của ID sách
                    if (isIDUnique(id)) {
                        // Tạo một đối tượng sách mới
                        Book newBook = new Book(id, title, author, year, quantity);
        
                        // Thêm sách vào mô hình dữ liệu của bảng sách in
                        Object[] rowData = {newBook.getId(), newBook.getTitle(), newBook.getAuthor(), newBook.getYear(), newBook.getQuantity()};
                        physicalBookTableModel.addRow(rowData); // physicalBookTableModel là biến thành viên chứa mô hình dữ liệu của bảng sách in
        
                        // Cập nhật giao diện của bảng sách in
                        physicalBookTableModel.fireTableDataChanged();
        
                        // Thêm sách vào cơ sở dữ liệu
                        addBookToDatabase(newBook);
        
                        // Hiển thị thông báo thành công
                        JOptionPane.showMessageDialog(AddBook.this, "Đã thêm sách thành công");
        
                        // Đóng cửa sổ khi thêm sách thành công
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddBook.this, "ID sách đã tồn tại! Vui lòng nhập ID khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // Nếu nhập sai kiểu dữ liệu, hiển thị cảnh báo
                    JOptionPane.showMessageDialog(AddBook.this, "Dữ liệu nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        rightPanel.add(bookInfoPanel, BorderLayout.CENTER);
        rightPanel.add(addButton, BorderLayout.SOUTH);

        // Thêm phần bên trái và bên phải vào contentPane
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Phương thức để thêm sách vào cơ sở dữ liệu
    private void addBookToDatabase(Book book) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO Book (BookID, TieuDeSach, TacGia, NamXuatBan, SoLuong) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, book.getId());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setString(3, book.getAuthor());
                preparedStatement.setInt(4, book.getYear());
                preparedStatement.setInt(5, book.getQuantity());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sách vào cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    // Kiểm tra tính duy nhất của ID sách trong cơ sở dữ liệu
    private boolean isIDUnique(String id) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM Book WHERE BookID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) == 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Trong phương thức createButton của lớp AddBook
    private JButton createButton(String option, String iconPath) {
        JButton button = new JButton(option);
        button.setBackground(new Color(0, 139, 139));
        button.setForeground(Color.WHITE);
        button.setIcon(resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/" + iconPath, 30, 30)); // Điều chỉnh kích thước của icon
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        button.addActionListener(e -> {
            if (option.equals("Trang chủ")) {
                GuiManager.manage();
            } else if (option.equals("Quản lý kho sách")) {
                GuiManager.manageBooks();
            } else if (option.equals("Quản lý mượn sách")) {
                GuiManager.manageBorrow();
            } else if (option.equals("Quản lý trả sách")) {
                GuiManager.manageReturn();
            } else if (option.equals("Quản lý nhân viên")) {
                GuiManager.manageStaff();
            } else if (option.equals("Quản lý người mượn")) {
                GuiManager.manageBorrowers();
            }
        });

        return button;
    }

    // Phương thức để thay đổi kích thước của icon
    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng"}, 0);
        SwingUtilities.invokeLater(() -> {
            new AddBook(model);
        });
    }
}