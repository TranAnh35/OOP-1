package demo.Book;

import javax.swing.*;
import javax.swing.border.*;

import demo.Manager.GuiManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        JButton backButton = new JButton("Quay lại");
        backButton.setBackground(new Color(0, 139, 139));
        backButton.setForeground(Color.WHITE);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReturnBook::new);
    }
}
