package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Kiểm tra tên đăng nhập và mật khẩu
                if (username.equals("HongZin") && password.equals("1608a@")) {
                    dispose(); // Đóng cửa sổ đăng nhập nếu đăng nhập thành công
                    showLibraryFrame(); // Hiển thị cửa sổ quản lý sách
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private void showLibraryFrame() {
        LibraryFrame libraryFrame = new LibraryFrame(new Library()); // Truyền đối tượng Library vào constructor của LibraryFrame
        libraryFrame.setVisible(true);
    }
}
