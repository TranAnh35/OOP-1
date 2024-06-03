// package QuanLyThuVien;

// import javax.swing.*;
// import java.util.Scanner;
// import java.io.File;
// import java.io.IOException;

// public class MainA {
//     public static void main(String[] args) {
//         // Tạo đối tượng Scanner để đọc dữ liệu từ terminal
//         Scanner scanner = new Scanner(System.in);

//         // Tạo đối tượng Library để quản lý sách
//         Library library = new Library();

//         // Tên file lưu danh sách sách
//         String fileName = "HongZin.txt";

//         // Kiểm tra và tạo file nếu cần
//         File file = new File(fileName);
//         try {
//             if (!file.exists()) {
//                 file.createNewFile();
//                 System.out.println("Đã tạo file '" + fileName + "'.");
//             }
//         } catch (IOException e) {
//             System.out.println("Lỗi khi tạo file: " + e.getMessage());
//         }

//         // Đọc danh sách sách từ file và lưu vào thư viện
//         FileHandler.readBooksFromFile(fileName, library.getBooks(), library.getEBooks());

//         // Hiển thị giao diện đăng nhập
//         /*GiaoDien giaoDien = new GiaoDien(library);
//         giaoDien.setAddBookListener(new GiaoDien.ActionListener() {
//             public void actionPerformed() {
//                 // Gọi phương thức để thêm sách
//                 addBook(library);
//             }
//         });

//         giaoDien.setRemoveBookListener(new GiaoDien.ActionListener() {
//             public void actionPerformed() {
//                 // Gọi phương thức để xoá sách
//                 removeBook(library);
//             }
//         });

//         giaoDien.setDisplayBooksListener(new GiaoDien.ActionListener() {
//             public void actionPerformed() {
//                 // Gọi phương thức để hiển thị sách
//                 displayBooks(library);
//             }
//         });

//         giaoDien.setBorrowBookListener(new GiaoDien.ActionListener() {
//             public void actionPerformed() {
//                 // Gọi phương thức để mượn sách
//                 borrowBook(library);
//             }
//         });

//         giaoDien.setReturnBookListener(new GiaoDien.ActionListener() {
//             public void actionPerformed() {
//                 // Gọi phương thức để trả sách
//                 returnBook(library);
//             }
//         });
//     }*/

//     // Phương thức để thêm sách
//     private static void addBook(Library library) {
//         // Đọc thông tin sách từ terminal và thêm vào thư viện
//         // Bạn có thể thêm mã logic ở đây để đọc từ giao diện người dùng thay vì từ terminal
//     }

//     // Phương thức để xoá sách
//     private static void removeBook(Library library) {
//         // Đọc ID sách từ terminal và xoá từ thư viện
//         // Bạn có thể thêm mã logic ở đây để đọc từ giao diện người dùng thay vì từ terminal
//     }

//     // Phương thức để hiển thị sách
//     private static void displayBooks(Library library) {
//         // Hiển thị danh sách sách trong thư viện
//     }
    
//     // Phương thức để mượn sách
//     private static void borrowBook(Library library) {
//         // Đọc thông tin sách từ terminal và thực hiện mượn từ thư viện
//         // Bạn có thể thêm mã logic ở đây để đọc từ giao diện người dùng thay vì từ terminal
//     }
    
//     // Phương thức để trả sách
//     private static void returnBook(Library library) {
//         // Đọc thông tin sách từ terminal và thực hiện trả vào thư viện
//         // Bạn có thể thêm mã logic ở đây để đọc từ giao diện người dùng thay vì từ terminal
//     }

//     // Phương thức kiểm tra thông tin đăng nhập
//     private static boolean isValidUser(String username, String password) {
//         // Thực hiện kiểm tra username và password, có thể thay thế bằng cơ chế xác thực thực tế
//         return username.equals("HongZin") && password.equals("1608a@");
//     }

// }
