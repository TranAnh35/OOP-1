package demo.Book;

import javax.swing.*;
import javax.swing.border.*;

import demo.Functions.MySqlConnection;
import demo.Functions.SharedModel;
import demo.Manager.GuiManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.imageio.ImageIO;

public class ReturnBook extends JFrame {

    public ReturnBook() {
        setTitle("Ghi nhận trả sách");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Phần chứa thông tin ghi nhận trả sách
        JPanel borrowInfoPanel = new JPanel();
        borrowInfoPanel.setLayout(new BoxLayout(borrowInfoPanel, BoxLayout.Y_AXIS));
        borrowInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin ghi nhận trả sách"));
        borrowInfoPanel.setBackground(new Color(240, 255, 255));

        // Phần thông tin sách
        JPanel bookInfoPanel = new JPanel(new GridLayout(4, 2)); // Thêm một hàng cho ngày trả
        bookInfoPanel.setBackground(new Color(240, 255, 255));

        JLabel bookIdLabel = new JLabel("ID sách:");
        JTextField bookIdField = new JTextField();
        JLabel bookTitleLabel = new JLabel("Tiêu đề sách:");
        JTextField bookTitleField = new JTextField();
        JLabel borrowerNameLabel = new JLabel("Họ tên người mượn:");
        JTextField borrowerNameField = new JTextField();

        // Thêm nhãn và trường nhập cho ngày trả
        JLabel returnDateLabel = new JLabel("Ngày trả:");
        JTextField returnDateField = new JTextField(); // Có thể sử dụng JFormattedTextField để định dạng ngày tháng

        bookInfoPanel.add(bookIdLabel);
        bookInfoPanel.add(bookIdField);
        bookInfoPanel.add(bookTitleLabel);
        bookInfoPanel.add(bookTitleField);
        bookInfoPanel.add(borrowerNameLabel);
        bookInfoPanel.add(borrowerNameField);

        // Thêm nhãn và trường nhập cho ngày trả vào panel
        bookInfoPanel.add(returnDateLabel);
        bookInfoPanel.add(returnDateField);

        // Nút quay lại
        JButton backButton = new JButton("Trả sách");
        backButton.setBackground(new Color(0, 139, 139));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(e -> {
            String BookID = bookIdField.getText();
            String nameBook = bookTitleField.getText();
            String TenNguoiMuon = borrowerNameField.getText();
            String returnDate = returnDateField.getText();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false); // Không cho phép định dạng ngày không hợp lệ
            try {
                dateFormat.parse(returnDate);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Ngày mượn không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Dừng lại và không tiếp tục xử lý
            }
            String KhoanPhat = null;
            if(checkRequest(BookID, TenNguoiMuon)){
                long days = DaysDifference(new SharedModel().getBorrowReturnDate(BookID), returnDate);
                KhoanPhat = FineAmount(days);
            }

            if(!BookID.isEmpty() && !nameBook.isEmpty() && !TenNguoiMuon.isEmpty() && !returnDate.isEmpty()){
                try{
                    addReturnInfoToDatabase(BookID, TenNguoiMuon, returnDate, KhoanPhat);
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                deleteBorrowBook(BookID, new SharedModel().getIDByBorrowerName(TenNguoiMuon));
                checkBorrower(new SharedModel().getIDByBorrowerName(TenNguoiMuon));
                JOptionPane.showMessageDialog(this, "Ghi nhận trả sách thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);


            }else{
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
            
        });

        borrowInfoPanel.add(bookInfoPanel);
        borrowInfoPanel.add(Box.createVerticalStrut(20)); // Khoảng cách giữa các phần tử
        borrowInfoPanel.add(backButton);

        rightPanel.add(borrowInfoPanel, BorderLayout.CENTER);

        // Thêm phần bên trái và bên phải vào contentPane
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Phương thức để tạo nút chức năng
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addReturnInfoToDatabase(String BookID, String TenNguoiMuon, String NgayTra, String KhoanPhat) throws SQLException{
        try(Connection connection = MySqlConnection.getConnection()){
            String query = "INSERT INTO trasach (Return ID, IDNguoiMuon, BookID, NgayTra, KhoanPhat) VALUES (?, ? ,? ,? ,?);";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query);){
                preparedStatement.setString(1, generateUniqueReturnID());
                preparedStatement.setString(2, new SharedModel().getIDByBorrowerName(TenNguoiMuon));
                preparedStatement.setString(3, BookID);
                preparedStatement.setString(4, NgayTra);
                preparedStatement.setString(5, KhoanPhat);
                preparedStatement.executeQuery();

            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    private String generateUniqueReturnID(){
        String newID = "";
        try{
            Connection connection = MySqlConnection.getConnection();
            String query = "SELECT MAX(ReturnID) FROM trasach";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String maxID = resultSet.getString(1);
                if(maxID != null){
                    int maxIDInt = Integer.parseInt(maxID.split("-")[1]);
                    newID = String.format("NM-%03d", maxIDInt + 1);
                }else{
                    newID = "TS-001";
                }
            }else{
                newID = "TS-001";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newID;
    }

    private boolean checkRequest(String bookID, String BorrowerName){
        try(Connection connection = MySqlConnection.getConnection()){
            String query_1 = "SELECT IDNguoiMuon From NguoiMuon Where TenNguoiMuon = ?;";
            String query_2 = "SELECT IDNguoiMuon, BookID, Count(*) AS Total FROM muonsach"+
                            " Where IDNguoiMuon = ? and BookID = ?" +
                            " GROUP BY IDNguoiMuon, BookID";
            PreparedStatement preparedStatement = connection.prepareStatement(query_1);
            preparedStatement.setString(1, BorrowerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            String IDNguoiMuon = null;
            if(resultSet.next()){
                IDNguoiMuon = resultSet.getString(1);
            }

            preparedStatement = connection.prepareStatement(query_2);
            preparedStatement.setString(1, bookID);
            preparedStatement.setString(2, IDNguoiMuon);
            resultSet = preparedStatement.executeQuery();
            int total = 0;
            if(resultSet.next()){
                total = resultSet.getInt("total");
            }
            if(total == 0){
                return false;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public long DaysDifference(String startDateStr, String endDateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return (daysBetween > 0) ? daysBetween : 0;
    }

    public String FineAmount(long Days){
        long total = Days * 2000;
        return total + " vnd";
    }

    public void deleteBorrowBook(String BookID, String IDNguoiMuon){
        try(Connection connection = MySqlConnection.getConnection()){
            String query_1 = "SELECT borrowID From muonsach Where BookID = ? and IDNguoiMuon = ?;";
            String query_2 = "DELETE FROM muonsach Where borrowID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query_1);
            preparedStatement.setString(1, BookID);
            preparedStatement.setString(2, IDNguoiMuon);

            ResultSet resultSet = preparedStatement.executeQuery();
            String BorrowID = null;
            if(resultSet.next()){
                BorrowID = resultSet.getString("BorrowID");
            }

            preparedStatement = connection.prepareStatement(query_2);
            preparedStatement.setString(1, BorrowID);
            preparedStatement.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void checkBorrower(String IDNguoiMuon){
        try(Connection connection = MySqlConnection.getConnection()){
            String query = "SELECT IDNguoiMuon, Count(*) as total From muonsach Where IDNguoiMuon = ? Group By IDNguoiMuon;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, IDNguoiMuon);

            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            if(resultSet.next()){
                count = resultSet.getInt("total");
            }
            if(count == 0){
                String sql = "DELETE FROM nguoiMuon Where IDNguoiMuon = ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, IDNguoiMuon);
                preparedStatement.executeUpdate();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReturnBook::new);
    }
}
