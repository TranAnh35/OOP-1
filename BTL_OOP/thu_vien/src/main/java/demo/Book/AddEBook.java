package demo.Book;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

import demo.Functions.MySqlConnection;
import demo.Manager.GuiManager;

public class AddEBook extends JFrame {
    private DefaultTableModel eBookTableModel;

    public AddEBook(DefaultTableModel eBookTableModel) {
        this.eBookTableModel = eBookTableModel;

        setTitle("Thêm sách điện tử mới");
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

        // Tiêu đề "Thêm sách điện tử mới"
        JLabel addBookTitleLabel = new JLabel("Thêm sách điện tử mới");
        addBookTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addBookTitleLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Khoảng cách dưới của tiêu đề
        rightPanel.add(addBookTitleLabel, BorderLayout.NORTH);

        // Phần thông tin sách cần thêm
        JPanel bookInfoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        bookInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bookInfoPanel.setBackground(new Color(240, 255, 255));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Tên sách:");
        JTextField nameField = new JTextField();
        JLabel authorLabel = new JLabel("Tác giả:");
        JTextField authorField = new JTextField();
        JLabel yearLabel = new JLabel("Năm xuất bản:");
        JTextField yearField = new JTextField();
        JLabel formatLabel = new JLabel("Định dạng file:");
        JTextField formatField = new JTextField();
        JLabel sizeLabel = new JLabel("Kích thước file:");
        JTextField sizeField = new JTextField();

        bookInfoPanel.add(idLabel);
        bookInfoPanel.add(idField);
        bookInfoPanel.add(nameLabel);
        bookInfoPanel.add(nameField);
        bookInfoPanel.add(authorLabel);
        bookInfoPanel.add(authorField);
        bookInfoPanel.add(yearLabel);
        bookInfoPanel.add(yearField);
        bookInfoPanel.add(formatLabel);
        bookInfoPanel.add(formatField);
        bookInfoPanel.add(sizeLabel);
        bookInfoPanel.add(sizeField);

        // Button thêm sách
        JButton addButton = new JButton("Thêm");
        addButton.setBackground(new Color(0, 139, 139));
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);

        // Khi nhấn nút "Thêm"
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    String format = formatField.getText();
                    float size = Float.parseFloat(sizeField.getText());

                    // Kiểm tra xem có trường nào trống không
                    if (name.isEmpty() || author.isEmpty() || format.isEmpty()) {
                        JOptionPane.showMessageDialog(AddEBook.this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Kiểm tra tính duy nhất của ID sách
                        if (isIDUnique(id)) {
                            eBookTableModel.addRow(new Object[]{id, name, author, year, format, size});
                            dispose();
                            JOptionPane.showMessageDialog(AddEBook.this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            addEBookToDatabase(id, name, author, year, format, size);
                        } else {
                            JOptionPane.showMessageDialog(AddEBook.this, "ID sách đã tồn tại! Vui lòng nhập ID khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Nếu nhập sai định dạng số, hiển thị cảnh báo
                    JOptionPane.showMessageDialog(AddEBook.this, "Dữ liệu nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    // Phương thức thêm sách điện tử vào cơ sở dữ liệu
    private void addEBookToDatabase(String id, String name, String author, int year, String format, float size) {
        String query = "INSERT INTO EBook (eBookID, TenSach, TacGia, NamXuatBan, SoLuong, DinhDangFile, KichThuocFile) VALUES (?, ?, ?, ?, 1, ?, ?)";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, author);
            stmt.setInt(4, year);
            stmt.setString(5, format);
            stmt.setFloat(6, size);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sách vào cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Trong phương thức createButton của lớp AddEBook
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

    // Kiểm tra tính duy nhất của ID sách
    private boolean isIDUnique(String id) {
        for (int i = 0; i < eBookTableModel.getRowCount(); i++) {
            if (id == eBookTableModel.getValueAt(i, 0)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        DefaultTableModel eBookTableModel = new DefaultTableModel(new String[]{"ID", "Tên sách", "Tác giả", "Năm xuất bản", "Định dạng file", "Kích thước file"}, 0);
        SwingUtilities.invokeLater(() -> {
            new AddEBook(eBookTableModel);
        });
    }
}
