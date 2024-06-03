package QuanLyThuVien;

import java.io.File;
import java.io.IOException;

public class MainB {
    public static void main(String[] args) {
        // Khởi tạo một đối tượng Library
        Library library = new Library();

        // Khởi tạo giao diện đăng nhập và hiển thị
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);

        // Tạo file txt nếu không tồn tại
        File file = new File("HongZin.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File HongZin.txt da duoc tao.");
            } else {
                System.out.println("File HongZin.txt da ton tai.");
            }
        } catch (IOException e) {
            System.out.println("Xay ra loi tao file: " + e.getMessage());
        }
    }
}
