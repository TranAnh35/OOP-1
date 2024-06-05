package demo.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import demo.Manager.Manage;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Quản lý thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 255, 255)); // Thiết lập màu nền cho JFrame

        // Tạo panel chứa tất cả các thành phần
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // Cho phép hiển thị màu nền của JFrame

        // Panel chứa hình ảnh
        ImageIcon libraryIcon = new ImageIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/AnhThuVien.jpg");
        JLabel imageLabel = new JLabel(libraryIcon);
        imageLabel.setPreferredSize(new Dimension(350, 200)); // Kích thước hình ảnh
        mainPanel.add(imageLabel, BorderLayout.WEST);

        // Panel chứa form đăng nhập
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginPanel.setOpaque(false); // Cho phép hiển thị màu nền của JFrame

        // Chữ đăng nhập size to, màu (0, 139, 139)
        JLabel loginLabel = new JLabel("Đăng nhập");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(new Color(0, 139, 139));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(loginLabel, gbc);

        // Tên đăng nhập, màu (0, 139, 139)
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setForeground(new Color(0, 139, 139));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(usernameField, gbc);

        // Dòng kẻ
        JSeparator separator1 = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        loginPanel.add(separator1, gbc);

        // Mật khẩu, màu (0, 139, 139)
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setForeground(new Color(0, 139, 139));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(passwordField, gbc);

        // Dòng kẻ
        JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        loginPanel.add(separator2, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Nút đăng nhập
        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(new Color(0, 139, 139)); // Chọn màu
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(loginButton, gbc);

        add(mainPanel);
        pack(); // Tự động điều chỉnh kích thước JFrame
        setLocationRelativeTo(null); // Hiển thị JFrame ở giữa màn hình
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra mật khẩu
                char[] inputPassword = passwordField.getPassword();
                String inputUsername = usernameField.getText();

                // Mật khẩu cố định
                String correctPassword = "1608a@"; // Thay yourFixedPassword bằng mật khẩu thực tế

                if (inputUsername.equals("Zin") && String.valueOf(inputPassword).equals(correctPassword)) {
                    // Nếu đăng nhập thành công, chuyển sang giao diện quản lý thư viện
                    dispose(); // Đóng cửa sổ đăng nhập
                    SwingUtilities.invokeLater(Manage::new); // Hiển thị giao diện quản lý thư viện
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Tên đăng nhập hoặc mật khẩu không chính xác!", "Đăng nhập không thành công", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(Login::new);
    // }
}
