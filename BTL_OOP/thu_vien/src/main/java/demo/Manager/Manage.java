package demo.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Manage extends JFrame {
    public Manage() {
        setTitle("Quản lý thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ màu nền cho contentPane
                g.setColor(new Color(240, 255, 255));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(contentPane);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // Cho phép hiển thị màu nền của JFrame

        JLabel titleLabel = new JLabel("Quản lý thư viện");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(0, 139, 139));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new BorderLayout()); // Sử dụng BorderLayout để căn chỉnh panel chứa nút vào giữa
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel buttonGridPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 hàng 2 cột, khoảng cách 10 giữa các ô
        buttonGridPanel.setOpaque(false);

        ImageIcon bookIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/book.png", 50, 50);
        ImageIcon userIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/user.png", 50, 50);
        ImageIcon calendarBorrowIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/calendarBorrow.png", 50, 50);
        ImageIcon manyUserIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/manyUser.png", 50, 50);
        ImageIcon calendarReturnIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/calendarReturn.png", 50, 50);
        ImageIcon exitIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/exit.png", 50, 50);

        JButton manageBooksButton = new JButton("Quản lý kho sách", bookIcon);
        manageBooksButton.setPreferredSize(new Dimension(250, 100));
        manageBooksButton.setBackground(new Color(0, 139, 139));
        manageBooksButton.setForeground(Color.WHITE);
        manageBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiManager.manageBooks();
                dispose();
            }
        });
        buttonGridPanel.add(manageBooksButton);

        JButton manageEmployeesButton = new JButton("Quản lý nhân viên", userIcon);
        manageEmployeesButton.setPreferredSize(new Dimension(250, 100));
        manageEmployeesButton.setBackground(new Color(0, 139, 139));
        manageEmployeesButton.setForeground(Color.WHITE);
        manageEmployeesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Phần này cần kiểm tra quyền truy cập của người dùng trước khi mở giao diện quản lý nhân viên
                // Nếu người dùng có quyền truy cập, mới mở giao diện quản lý nhân viên
                // Nếu không, hiển thị thông báo hoặc thực hiện hành động khác
                JOptionPane.showMessageDialog(Manage.this, "Bạn không có quyền truy cập chức năng này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonGridPanel.add(manageEmployeesButton);

        JButton manageBorrowingButton = new JButton("Quản lý mượn sách", calendarBorrowIcon);
        manageBorrowingButton.setPreferredSize(new Dimension(250, 100));
        manageBorrowingButton.setBackground(new Color(0, 139, 139));
        manageBorrowingButton.setForeground(Color.WHITE);
        manageBorrowingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiManager.manageBorrow();
                dispose();
            }
        });
        buttonGridPanel.add(manageBorrowingButton);

        JButton manageBorrowersButton = new JButton("Quản lý người mượn", manyUserIcon);
        manageBorrowersButton.setPreferredSize(new Dimension(250, 100));
        manageBorrowersButton.setBackground(new Color(0, 139, 139));
        manageBorrowersButton.setForeground(Color.WHITE);
        manageBorrowersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiManager.manageBorrowers();
                dispose();
            }
        });
        buttonGridPanel.add(manageBorrowersButton);

        JButton manageReturnButton = new JButton("Quản lý trả sách", calendarReturnIcon);
        manageReturnButton.setPreferredSize(new Dimension(250, 100));
        manageReturnButton.setBackground(new Color(0, 139, 139));
        manageReturnButton.setForeground(Color.WHITE);
        manageReturnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiManager.manageReturn();
                dispose();
            }
        });
        buttonGridPanel.add(manageReturnButton);

        JButton logoutButton = new JButton("Đăng xuất", exitIcon);
        logoutButton.setPreferredSize(new Dimension(250, 100));
        logoutButton.setBackground(new Color(0, 139, 139));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Thêm code để đăng xuất và đóng cửa sổ hiện tại
                dispose();
                // Đưa người dùng về màn hình đăng nhập hoặc làm thêm bước xác thực
            }
        });
        buttonGridPanel.add(logoutButton);

        buttonPanel.add(buttonGridPanel, BorderLayout.CENTER); // Thêm panel chứa nút vào giữa của buttonPanel

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPane.add(mainPanel); // Thêm mainPanel vào contentPane thay vì trực tiếp vào JFrame

        pack(); // Tự động điều chỉnh kích thước JFrame
        setLocationRelativeTo(null); // Hiển thị JFrame ở giữa màn hình
        setVisible(true);
    }

    // Phương thức để thay đổi kích thước của icon
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Manage::new);
    }
}
