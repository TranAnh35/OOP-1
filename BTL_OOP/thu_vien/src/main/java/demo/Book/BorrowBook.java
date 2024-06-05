package demo.Book;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.Calendar;
import javax.imageio.ImageIO;

import demo.Functions.SharedModel;
import demo.Manager.GuiManager;
import demo.Manager.ManageBorrow;
import demo.Manager.ManageBorrower;
import demo.Functions.BorrowRecord;
import demo.Functions.Borrower;
import demo.Functions.MySqlConnection;

public class BorrowBook extends JFrame {
    private JTextField bookIdField;
    private JLabel bookTitleLabel; // Label for displaying the book title
    private JTextField borrowerNameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField borrowDateField;

    public BorrowBook() {
        setTitle("Ghi nhận mượn sách");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(240, 255, 255));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 50)); // Large spacing between text and buttons

        // Title "Library Management"
        JLabel titleLabel = new JLabel("Quản lý thư viện");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel, BorderLayout.NORTH);

        // Functional buttons panel
        JPanel functionPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        functionPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Spacing between text and buttons
        functionPanel.setBackground(new Color(240, 255, 255));

        String[] options = {"Trang chủ", "Quản lý kho sách", "Quản lý mượn sách", "Quản lý trả sách", "Quản lý nhân viên", "Quản lý người mượn"};
        String[] iconPaths = {"home.png", "book.png", "calendarBorrow.png", "calendarReturn.png", "manyUser.png", "user.png"};

        for (int i = 0; i < options.length; i++) {
            JButton optionButton = createButton(options[i], iconPaths[i]);
            functionPanel.add(optionButton);
        }
        leftPanel.add(functionPanel, BorderLayout.CENTER);

        // Right panel for borrowing information
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 255, 255));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Borrowing information panel
        JPanel borrowInfoPanel = new JPanel();
        borrowInfoPanel.setLayout(new BoxLayout(borrowInfoPanel, BoxLayout.Y_AXIS));
        borrowInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin ghi nhận mượn sách"));
        borrowInfoPanel.setBackground(new Color(240, 255, 255));

        // Book information panel
        JPanel bookInfoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        bookInfoPanel.setBackground(new Color(240, 255, 255));

        JLabel bookIdLabel = new JLabel("ID sách:");
        bookIdField = new JTextField();
        JLabel bookTitleStaticLabel = new JLabel("Tên sách:"); // Static label
        bookTitleLabel = new JLabel(); // Dynamic label

        JLabel borrowerNameLabel = new JLabel("Họ tên người mượn:");
        borrowerNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel phoneNumberLabel = new JLabel("Số điện thoại:");
        phoneNumberField = new JTextField();
        JLabel borrowDateLabel = new JLabel("Ngày mượn:");
        borrowDateField = new JTextField();

        bookInfoPanel.add(bookIdLabel);
        bookInfoPanel.add(bookIdField);
        bookInfoPanel.add(bookTitleStaticLabel);
        bookInfoPanel.add(bookTitleLabel);
        bookInfoPanel.add(borrowerNameLabel);
        bookInfoPanel.add(borrowerNameField);
        bookInfoPanel.add(emailLabel);
        bookInfoPanel.add(emailField);
        bookInfoPanel.add(phoneNumberLabel);
        bookInfoPanel.add(phoneNumberField);
        bookInfoPanel.add(borrowDateLabel);
        bookInfoPanel.add(borrowDateField);
        // Confirm button
        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setBackground(new Color(0, 139, 139));
        confirmButton.setForeground(Color.WHITE);

        confirmButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            String borrowerName = borrowerNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String borrowDate = borrowDateField.getText();

            // Kiểm tra định dạng email
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng lại và không tiếp tục xử lý
            }

            // Kiểm tra định dạng số điện thoại
            if (!phoneNumber.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng lại và không tiếp tục xử lý
            }

            // Kiểm tra định dạng ngày mượn
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false); // Không cho phép định dạng ngày không hợp lệ
            try {
                dateFormat.parse(borrowDate);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Ngày mượn không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng lại và không tiếp tục xử lý
            }

            if (!bookId.isEmpty() && !borrowerName.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() && !borrowDate.isEmpty()) {
                // Kiểm tra số lượng sách
                int currentQuantity = checkBookQuantity(bookId);
                if (currentQuantity > 0) {
                    // Tính ngày hẹn trả (15 ngày sau ngày mượn)
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(dateFormat.parse(borrowDate));
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(this, "Ngày mượn không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return; // Dừng lại và không tiếp tục xử lý
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, 15);
                    String returnDate = dateFormat.format(calendar.getTime());

                    SharedModel sm = new SharedModel();
                    String borrowID = sm.generateUniqueBorrowID();
                    String borrowerID = sm.generateUniqueBorrowerID(borrowerName);

                    if(!isBorrowUnique(bookId, borrowerID)) {
                        JOptionPane.showMessageDialog(this, "Sách đã được mượn bởi người này", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    BorrowRecord record = new BorrowRecord(borrowID, fetchBookTitle(bookId), bookId, borrowerID, borrowDate, returnDate);
                    Borrower borrower = new Borrower(borrowerID, borrowerName, email, phoneNumber);

                    addBorrowerToDatabase(borrower);
                    addBorrowRecordToDatabase(record);

                    // Cập nhật số lượng sách
                    updateBookQuantity(bookId, currentQuantity - 1);

                    ManageBorrow.updateTable();
                    ManageBorrower.updateTable();

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    // Quay lại giao diện Quản lý mượn sách
                    GuiManager.manageBorrow();

                    dispose(); // Đóng cửa sổ sau khi hiển thị thông báo
                } else {
                    JOptionPane.showMessageDialog(this, "Sách đã hết", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        borrowInfoPanel.add(bookInfoPanel);
        borrowInfoPanel.add(Box.createVerticalStrut(20));
        borrowInfoPanel.add(confirmButton);

        rightPanel.add(borrowInfoPanel, BorderLayout.CENTER);

        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        bookIdField.addFocusListener(new FocusAdapter() {

            public void focusLost(FocusEvent e) {
                String bookId = bookIdField.getText();
                if (!bookId.isEmpty()) {
                    String bookTitle = fetchBookTitle(bookId);
                    if (bookTitle != null) {
                        bookTitleLabel.setText(bookTitle);
                    } else {
                        bookTitleLabel.setText("Không tìm thấy sách");
                    }
                } else {
                    bookTitleLabel.setText("");
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to create functional buttons
    private JButton createButton(String option, String iconPath) {
        JButton button = new JButton(option);
        button.setBackground(new Color(0, 139, 139));
        button.setForeground(Color.WHITE);
        button.setIcon(resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/" + iconPath, 30, 30));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        button.addActionListener(e -> {
            switch (option) {
                case "Trang chủ" -> GuiManager.manage();
                case "Quản lý kho sách" -> GuiManager.manageBooks();
                case "Quản lý mượn sách" -> GuiManager.manageBorrow();
                case "Quản lý trả sách" -> GuiManager.manageReturn();
                case "Quản lý nhân viên" -> GuiManager.manageStaff();
                case "Quản lý người mượn" -> GuiManager.manageBorrowers();
            }
        });

        return button;
    }

    // Method to resize icons
    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Lấy tên sách từ cơ sở dữ liệu dựa trên ID sách
        private String fetchBookTitle(String bookId) {
            String bookTitle = null;
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
        try {
            // Kết nối đến cơ sở dữ liệu
            connection = MySqlConnection.getConnection();

            // Truy vấn để lấy tên sách dựa trên ID sách
            String sql = "SELECT TieuDeSach FROM Book WHERE BookID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, bookId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                bookTitle = resultSet.getString("TieuDeSach");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bookTitle;
    }

    // Kiểm tra số lượng sách hiện tại
    private int checkBookQuantity(String bookId) {
        int quantity = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = MySqlConnection.getConnection();

            // Truy vấn để lấy số lượng sách hiện tại dựa trên ID sách
            String sql = "SELECT SoLuong FROM Book WHERE BookID = ?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, bookId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quantity = resultSet.getInt("SoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return quantity;
    }

    // Cập nhật số lượng sách
    private void updateBookQuantity(String bookId, int newQuantity) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = MySqlConnection.getConnection();

            // Cập nhật số lượng sách mới dựa trên ID sách
            String sql = "UPDATE Book SET SoLuong = ? WHERE BookID = ?;";
            statement = connection.prepareStatement(sql);
            statement.setInt(5, newQuantity);
            statement.setString(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Thêm thông tin mượn sách vào cơ sở dữ liệu
    private boolean addBorrowRecordToDatabase(BorrowRecord record) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO muonsach (borrowID, TenSach, BookID, IDNguoiMuon, NgayMuon, NgayHenTra) VALUES (?, ?, ?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, record.getBorrowID());
                preparedStatement.setString(2, record.getBookName());
                preparedStatement.setString(3, record.getBookId());
                preparedStatement.setString(4, record.getBorrowerID());
                preparedStatement.setString(5, record.getBorrowDate());
                preparedStatement.setString(6, record.getReturnDate());
                preparedStatement.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    // Thêm thông tin người mượn vào cơ sở dữ liệu
    private boolean addBorrowerToDatabase(Borrower borrower) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO NguoiMuon (IDNguoiMuon, TenNguoiMuon, email, SoDienThoai) VALUES (?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, borrower.getId());
                preparedStatement.setString(2, borrower.getName());
                preparedStatement.setString(3, borrower.getEmail());
                preparedStatement.setString(4, borrower.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    private boolean isBorrowUnique(String bookId, String borrowerName) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = MySqlConnection.getConnection();
            String sql = "SELECT IDNguoiMuon, BookID, COUNT(*) AS Total" +
                        " FROM MuonSach" +
                        " WHERE BookID = ? AND IDNguoiMuon = ? " +
                        "GROUP BY IDNguoiMuon, BookID;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, bookId);
            statement.setString(2, borrowerName);
            ResultSet resultSet = statement.executeQuery();
            int total = 0;
            if (resultSet.next()) {
                total = resultSet.getInt("Total");
            }
            if (total > 0) {
                return false;
            }     
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public int getRows() throws SQLException{
        int rows = 0;
        Connection connection = MySqlConnection.getConnection();
        String query = "select Count(*) AS Total From muonsach;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            rows = resultSet.getInt("Total");
        }
        return rows;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BorrowBook::new);
    }
}
