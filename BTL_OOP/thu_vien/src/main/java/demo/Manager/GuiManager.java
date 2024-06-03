package demo.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GuiManager {
    // Biến static để theo dõi giao diện hiện tại
    private static JFrame currentFrame;

    public static void manage() {
        // Hiển thị giao diện đăng nhập
        new Manage();
    }

    public static void manageBooks() {
        // Đóng giao diện hiện tại nếu có
        disposeCurrentFrame();
        // Hiển thị giao diện quản lý kho sách
        new ManageBooks();

    }

    public static void manageBorrow() {
        // Đóng giao diện hiện tại nếu có
        disposeCurrentFrame();
        // Hiển thị giao diện quản lý mượn sách
        new ManageBorrow();
    }

    public static void manageReturn() {
        // Đóng giao diện hiện tại nếu có
        disposeCurrentFrame();
        // Hiển thị giao diện quản lý trả sách
        new ManageReturn();
    }

    public static void manageStaff() {
        // Hiển thị giao diện quản lý nhân viên
        // Gọi hàm hiển thị thông báo cần quyền truy cập
        showAccessDeniedMessage("quản lý nhân viên");
    }

    public static void manageBorrowers() {
        // Đóng giao diện hiện tại nếu có
        disposeCurrentFrame();
        // Hiển thị giao diện quản lý người mượn
        new ManageBorrower();
    }

    // Phương thức đóng giao diện hiện tại nếu có
    private static void disposeCurrentFrame() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    public static void updateBookTable(JTable table, Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }

    // Phương thức hiển thị thông báo cần quyền truy cập
    private static void showAccessDeniedMessage(String feature) {
        JOptionPane.showMessageDialog(null, "Bạn cần có quyền truy cập để sử dụng tính năng " + feature, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
}
