package demo.Manager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import demo.Book.ReturnBook;
import demo.Functions.MySqlConnection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;

public class ManageReturn extends JFrame {

    private DefaultTableModel returnTableModel;
    private JTable returnTable;
    private JTextField searchField;

    public ManageReturn() {
        setTitle("Quản lý trả sách");
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
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý trả sách");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 20)); // Điều chỉnh kích thước font
        rightPanel.setBorder(titledBorder);

        // Phần chứa nút ghi nhận trả sách và thanh tìm kiếm
        JPanel buttonSearchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonSearchPanel.setBackground(new Color(240, 255, 255));

        JButton returnButton = new JButton("Ghi nhận trả sách");
        returnButton.setBackground(new Color(0, 139, 139));
        returnButton.setForeground(Color.WHITE);
        buttonSearchPanel.add(returnButton);

        returnButton.addActionListener(e -> {
            new ReturnBook();
        });

        // Phần tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(240, 255, 255));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchPanel.add(searchLabel);

        searchField = new JTextField(15);
        searchPanel.add(searchField);

        ImageIcon searchIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/loupe.png", 20, 20); // Điều chỉnh kích thước của icon
        JButton searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(30, 30));
        searchButton.setBackground(new Color(240, 255, 255));
        searchButton.setBorder(null);
        searchPanel.add(searchButton);

        // Phần chứa bảng danh sách trả sách
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 255, 255));

        // Khởi tạo bảng danh sách trả sách với dữ liệu rỗng
        String[] columnNames = {"ID sách", "Tên người mượn", "Ngày trả sách", "Khoản phạt"};
        Object[][] data = {};
        returnTableModel = new DefaultTableModel(data, columnNames);
        returnTable = new JTable(returnTableModel);
        JScrollPane tableScrollPane = new JScrollPane(returnTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        rightPanel.add(buttonSearchPanel, BorderLayout.NORTH);
        rightPanel.add(searchPanel, BorderLayout.CENTER);
        rightPanel.add(tablePanel, BorderLayout.SOUTH);

        // Thêm phần bên trái và bên phải vào contentPane
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        updateTable();
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

    public void updateTable() {
        returnTableModel.setRowCount(0);
        try(Connection conn = MySqlConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM trasach;");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String bookID = rs.getString("BookID");
                String tenNguoiMuon = rs.getString("TenNguoiMuon");
                String ngayTra = rs.getString("NgayTra");
                String khoanPhat = rs.getString("KhoanPhat");

                returnTableModel.addRow(new Object[]{bookID, tenNguoiMuon, ngayTra, khoanPhat});
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManageReturn::new);
    }
}
