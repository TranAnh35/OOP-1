// package QuanLyThuVien;

// import java.sql.*;
// import java.time.LocalDate;
// import java.time.format.DateTimeParseException;
// import java.util.ArrayList;
// import java.util.Scanner;

// public class Library {
//     private ArrayList<Book> books;
//     private ArrayList<EBook> eBooks;
//     private Scanner scanner;
//     private ArrayList<BorrowReturnEventListener> listeners;
//     private ArrayList<BorrowEvent> borrowedBooks;

// public void addPrintBook(int id, String title, String author, int year, LocalDate borrowDate, LocalDate returnDate) {
//     // System.out.print("Nhap id sach in: ");
//     // int id = scanner.nextInt();
//     // scanner.nextLine();
//     // System.out.print("Nhap ten sach in: ");
//     // String title = scanner.nextLine();
//     // System.out.print("Nhap tac gia: ");
//     // String author = scanner.nextLine();
//     // System.out.print("Nhap nam xuat ban: ");
//     // int year = scanner.nextInt();
//     // scanner.nextLine();
//     // LocalDate borrowDate = inputDate("Nhap ngay muon sach in: ");
//     // LocalDate returnDate = inputDate("Nhap ngay tra sach in: ");
//     books.add(new Book(id, title, author, year, borrowDate, returnDate));
// }
//     public Library() {
//         books = new ArrayList<>();
//         eBooks = new ArrayList<>();
//         scanner = new Scanner(System.in);
//         listeners = new ArrayList<>();
//     }

//     public int choiceBooks(){
//         System.out.println("----Loai sach-----");
//         System.out.println("1. Sach in");
//         System.out.println("2. EBook");
//         System.out.println("3. Sach in va EBook");
//         System.out.print("Lua chon cua ban: ");
//         int choice = scanner.nextInt();
//         return choice;
//     }

//     private LocalDate inputDate(String message) {
//     while (true) {
//         System.out.print(message);
//         String dateStr = scanner.nextLine();
//         try {
//             return LocalDate.parse(dateStr);
//         } catch (DateTimeParseException e) {
//             System.out.println("Ngay nhap vao khong hop le. Vui long nhap lai!");
//         }
//     }
// }

// // 

// class AddPrintBookFrame {
//     private Library library;
//     //private Scanner scanner;

//     public AddPrintBookFrame(Library library) {
//         this.library = library;
//         scanner = new Scanner(System.in);
//     }

//     // public void addPrintBook() {
//     //     library.addPrintBook(id, title, author, year, borrowDate, returnDate);
//     // }
// }

// public void addEBook(int id, String title, String author, int year, LocalDate borrowDate, LocalDate returnDate, String fileFormat, int fileSize) {
//     // System.out.print("Nhap id sach dien tu: ");
//     // int id = scanner.nextInt();
//     // scanner.nextLine();
//     // System.out.print("Nhap ten sach dien tu: ");
//     // String title = scanner.nextLine();
//     // System.out.print("Nhap tac gia: ");
//     // String author = scanner.nextLine();
//     // System.out.print("Nhap nam xuat ban: ");
//     // int year = scanner.nextInt();
//     // scanner.nextLine();
//     // LocalDate borrowDate = inputDate("Nhap ngay muon sach dien tu: ");
//     // LocalDate returnDate = inputDate("Nhap ngay tra sach dien tu: ");
//     // System.out.print("Nhap dinh dang file: ");
//     // String fileFormat = scanner.nextLine();
//     // System.out.print("Nhap kich thuoc file: ");
//     // int fileSize = scanner.nextInt();

//     eBooks.add(new EBook(id, title, author, year, borrowDate, returnDate, fileFormat, fileSize));
// }
// class AddEBookFrame {
//     private Library library;
//     //private Scanner scanner;

//     public AddEBookFrame(Library library) {
//         this.library = library;
//         scanner = new Scanner(System.in);
//     }

//     public void addEBook() {
//         library.addEBook();
//     }
// }
//     public void saveBookToDatabase(Book book) {
//         try (Connection connection = ConnectMysql.getConnection()) {
//             String query = "INSERT INTO ThuVienBook (ID, TenSach, TacGia, NamXuatBan, NgayMuon, NgayTra) VALUES ( ?, ?, ?, ?, ?, ?)";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setInt(1, book.getId());
//                 preparedStatement.setString(2, book.getTitle());
//                 preparedStatement.setString(3, book.getAuthor());
//                 preparedStatement.setInt(4, book.getYear());
//                 preparedStatement.setDate(5, Date.valueOf(book.getBorrowDate()));
//                 preparedStatement.setDate(6, Date.valueOf(book.getReturnDate()));
//                 preparedStatement.executeUpdate();
//                 System.out.println("Sach da duoc luu thanh cong");
//             }
//         } catch (SQLException e) {
//             System.out.println("Xay ra loi khi luu sach vao database: " + e.getMessage());
//         }
//     }
    

//     public void saveBookToDatabase(EBook eBook) {
//         try (Connection connection = ConnectMysql.getConnection()) {
//             //dấu ? thay cho các giá trị cụ thể trong truy vấn là một phương pháp an toàn và hiệu quả khi làm việc với PreparedStatement trong Java
//             String query = "INSERT INTO ThuVienEBook (ID, TenSach, TacGia, NamXuatBan, NgayMuon, NgayTra, DinhDangFile, KichThuocFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setInt(1, eBook.getId());
//                 preparedStatement.setString(2, eBook.getTitle());
//                 preparedStatement.setString(3, eBook.getAuthor());
//                 preparedStatement.setInt(4, eBook.getYear());
//                 preparedStatement.setDate(5, Date.valueOf(eBook.getBorrowDate()));
//                 preparedStatement.setDate(6, Date.valueOf(eBook.getReturnDate()));
//                 preparedStatement.setString(7, eBook.getFileFormat());
//                 preparedStatement.setInt(8, eBook.getFileSize());
//                 preparedStatement.executeUpdate();
//                 System.out.println("Sach da duoc luu thanh cong");
//             }
//         } catch (SQLException e) {
//             System.out.println("Xay ra loi khi luu sach vao database: " + e.getMessage());
//         }
//     }
//     public void saveLibraryToFile(String fileName) {
//         FileHandler.saveBooksToFile(books, eBooks, fileName);
//     }    
//     public void addBook(){
//         /*int choice = choiceBooks();
//         if(choice == 1){
//             addPrintBook();
//             // Lấy sách vừa thêm vào cuối danh sách
//             Book book = books.get(books.size() - 1);
//             // Lưu thông tin sách vào cơ sở dữ liệu MySQL
//             saveBookToDatabase(book);
//         }
//         else if(choice == 2){
//             addEBook();
//             // Kiểm tra xem có eBook được thêm vào không
//             if (!eBooks.isEmpty()) {
//                 // Lấy eBook vừa thêm vào cuối danh sách
//                 EBook eBook = eBooks.get(eBooks.size() - 1);
//                 // Lưu thông tin eBook vào cơ sở dữ liệu MySQL
//                 saveBookToDatabase(eBook);
//             } else {
//                 System.out.println("Khong co eBook duoc them vao.");
//             }
//         } else if(choice == 3){
//             addPrintBook();
//             addEBook();
//             // Lấy sách vừa thêm vào cuối danh sách
//             Book book = books.get(books.size() - 1);
//             EBook eBook = eBooks.get(eBooks.size() - 1);
//             // Lưu thông tin sách vào cơ sở dữ liệu MySQL
//             saveBookToDatabase(book);
//             saveBookToDatabase(eBook);
//         } else {
//             System.out.println("Lua chon khong hop le");
//         }*/
//         // Code thêm sách vào ArrayList<Book> books và ArrayList<EBook> eBooks
//         // sau đó gọi phương thức để lưu vào cả file và cơ sở dữ liệu MySQL
//         FileHandler.saveBooksToFile(books, eBooks, "HongZin.txt");
//     }
    
//     public void removeBook() {
//         int choice = choiceBooks();
//         scanner.nextLine(); // consume newline character after nextInt()
    
//         if (choice == 1) {
//             removePrintBook();
//         } else if (choice == 2) {
//             removeEBook();
//         } else if (choice == 3) {
//             removePrintBook();
//             removeEBook();
//         } else {
//             System.out.println("Lua chon khong hop le");
//             return;
//         }
    
//         // Ghi lại danh sách sách vào file sau khi xoá
//         FileHandler.saveBooksToFile(books, eBooks, "HongZin.txt");
//     }
    
//     public void removePrintBook() {
//         System.out.print("Nhap id sach in can xoa: ");
//         int id = scanner.nextInt();
    
//         // Xoá sách in khỏi cơ sở dữ liệu MySQL
//         try (Connection connection = ConnectMysql.getConnection()) {
//             //String query = "DELETE FROM ThuVien WHERE TenSach = ? AND IsEBook = ?";
//             String query = "DELETE FROM ThuVienBook WHERE TenSach = ?";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setInt(1, id);
//                 //preparedStatement.setBoolean(2, true); // Xác định sách in
//                 int affectedRows = preparedStatement.executeUpdate();
//                 if (affectedRows > 0) {
//                     System.out.println("Sach in voi \"" + id + "\" da xoa!");
//                 } else {
//                     System.out.println("Sach in voi \"" + id + "\" khong tim thay hoac da duoc xoa truoc do!");
//                 }
//             }
//         } catch (SQLException e) {
//             System.out.println("Xay ra loi khi xoa sach in tu database: " + e.getMessage());
//         }
//     }
    
//     public void removeEBook() {
//         System.out.print("Nhap ten sach dien tu can xoa: ");
//         int id = scanner.nextInt();
    
//         // Xoá sách điện tử khỏi cơ sở dữ liệu MySQL
//         try (Connection connection = ConnectMysql.getConnection()) {
//             String query = "DELETE FROM ThuVienEBook WHERE TenSach = ?";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setInt(1, id);
//                 int affectedRows = preparedStatement.executeUpdate();
//                 if (affectedRows > 0) {
//                     System.out.println("EBook voi \"" + id + "\" da xoa!");
//                 } else {
//                     System.out.println("EBook \"" + id + "\" khong tim thay hoac da duoc xoa truoc do!");
//                 }
//             }
//         } catch (SQLException e) {
//             System.out.println("Xay ra loi khi xoa eBook tu database: " + e.getMessage());
//         }
//     }
    
    

//     public void displayPrintBooks(){
//         System.out.println("Book");
//         for(Book book: books){
//             System.out.println(book);
//         }
//     }

//     public void displayEBooks(){
//         System.out.println("EBook");
//         for(EBook ebook:eBooks){
//             System.out.println(ebook);
//         }
//     }
    
//     public void displayBooks(){
//         int choice2 = choiceBooks();
//         if(choice2 == 1){
//             displayPrintBooks();
//         }else if(choice2 == 2){
//             displayEBooks();
//         }else if(choice2 == 3){
//             displayPrintBooks();
//             displayEBooks();
//         }else{
//             System.out.println("Lua chon cua ban khong hop le");
//         }
//     }

//     public void addEventListener(BorrowReturnEventListener listener) {
//         listeners.add(listener);
//     }
// // Phương thức mượn, trả sách
//     public void borrowBook(Book book, String borrowerName) {
//         books.remove(book);
//         BorrowEvent event = new BorrowEvent(book, borrowerName);
//         fireBorrowEvent(event);
//     }
//     public void displayBorrowedBooks() {
//         if (borrowedBooks.isEmpty()) {
//             System.out.println("Khong co sach nao duoc muon.");
//         } else {
//             System.out.println("Danh sach sach da muon:");
//             for (BorrowEvent borrowEvent : borrowedBooks) {
//                 System.out.println(borrowEvent.getBook());
//             }
//         }
//     }

//     public void returnBook() {
//         //books.add(book);
//         // Hiển thị danh sách sách đã mượn
//         displayBorrowedBooks();

//         // Nhập ID của sách muốn trả từ người dùng
//         System.out.print("Nhập ID của sách muốn trả: ");
//         int id = scanner.nextInt();

//         // Tìm sách theo ID trong danh sách sách đã mượn
//         Book borrowedBook = null;
//         for (BorrowEvent borrowEvent : borrowedBooks) {
//             if (borrowEvent.getBook().getId() == id) {
//                 borrowedBook = borrowEvent.getBook();
//                 break;
//             }
//         }

//         // Kiểm tra nếu sách được mượn tồn tại
//         if (borrowedBook != null) {
//             // Trả sách
//             borrowedBook.returnBook(); // Cập nhật trạng thái trả sách
//             System.out.println("Đã trả sách thành công!");

//             // Xoá thông tin mượn sách khỏi danh sách mượn
//             borrowedBooks.removeIf(borrowEvent -> borrowEvent.getBook().getId() == id);
//         } else {
//             System.out.println("Không tìm thấy sách được mượn.");
//         }
//     }
//     //Kích hoạt BorrowEvent, returnEvent
//     private void fireBorrowEvent(BorrowEvent event) {
//         for (BorrowReturnEventListener listener : listeners) {
//             listener.onBorrowBook(event);
//         }
//     }

//     private void fireReturnEvent(ReturnEvent event) {
//         for (BorrowReturnEventListener listener : listeners) {
//             listener.onReturnBook(event);
//         }
//     }
//     // Truy cập danh sách sách in
//     public ArrayList<Book> getBooks() {
//         return books;
//     }

//     // Truy cập danh sách sách điện tử
//     public ArrayList<EBook> getEBooks() {
//         return eBooks;
//     }
//     public void BorrowBook(){
        
//     }
//     // Truy cập danh sách sách in có trong thư viện
//     public ArrayList<Book> getPrintBooks() {
//         ArrayList<Book> printBooks = new ArrayList<>();
//         for (Book book : books) {
//             if (book instanceof Book) {
//                 printBooks.add(book);
//             }
//         }
//         return printBooks;
//     }
//     // Truy cập danh sách sách điện tử có trong thư viện
//     public ArrayList<EBook> getElectronicBooks() {
//         ArrayList<EBook> electronicBooks = new ArrayList<>();
//         for (EBook eBook : eBooks) {
//             electronicBooks.add(eBook);
//         }
//         return electronicBooks;
//     }
//     // Phương thức mượn sách
//     public void borrowBook() {
//         // Nhập tên người mượn từ bàn phím
//         System.out.print("Nhap ten nguoi muon: ");
//         String borrowerName = scanner.nextLine();

//         // Hiển thị danh sách sách in có sẵn để mượn
//         System.out.println("Danh sach sach in co san:");
//         displayPrintBooks();

//         // Nhập ID của sách in muốn mượn từ người dùng
//         System.out.print("Nhap ID cua sach in muon muon: ");
//         int id = scanner.nextInt();

//         // Tìm sách in theo ID
//         Book bookToBorrow = null;
//         for (Book book : books) {
//             if (book.getId() == id) {
//                 bookToBorrow = book;
//                 break;
//             }
//         }

//         // Kiểm tra nếu sách tồn tại và chưa được mượn
//         if (bookToBorrow != null && !bookToBorrow.isBorrowed()) {
//             // Mượn sách in
//             borrowBook(bookToBorrow, borrowerName);
//             System.out.println("Da muon sach in thanh cong!");

//             // Xoá sách in khỏi danh sách sau khi mượn
//             books.remove(bookToBorrow);
//             System.out.println("Sach in da bi loai khoi thu vien sau khi muon.");
//         } else {
//             System.out.println("Sach khong ton tai hoac da bi muon.");
//         }
//     }
// }
package QuanLyThuVien;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<EBook> eBooks;
    private Scanner scanner;
    // Khai báo một ArrayList để lưu các sách đã mượn
    private ArrayList<Book> borrowedBooks;
    private DisplayPrintBooksFrame displayPrintBooksFrame;
    public void addPrintBook(int id, String title, String author, int year, LocalDate borrowDate, LocalDate returnDate) {
        books.add(new Book(id, title, author, year, borrowDate, returnDate));
    }

    public Library() {
        books = new ArrayList<>();
        eBooks = new ArrayList<>();
        scanner = new Scanner(System.in);
        borrowedBooks = new ArrayList<>();
    }

    private LocalDate inputDate(String message) {
        while (true) {
            System.out.print(message);
            String dateStr = scanner.nextLine();
            try {
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Ngay nhap vao khong hop le. Vui long nhap lai!");
            }
        }
    }

    public void addEBook(int id, String title, String author, int year, LocalDate borrowDate, LocalDate returnDate, String fileFormat, int fileSize) {
        eBooks.add(new EBook(id, title, author, year, borrowDate, returnDate, fileFormat, fileSize));
    }

    public void saveBookToDatabase(Book book) {
        try (Connection connection = ConnectMysql.getConnection()) {
            String query = "INSERT INTO ThuVienBook (ID, TenSach, TacGia, NamXuatBan, NgayMuon, NgayTra) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, book.getId());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setString(3, book.getAuthor());
                preparedStatement.setInt(4, book.getYear());
                preparedStatement.setDate(5, Date.valueOf(book.getBorrowDate()));
                preparedStatement.setDate(6, Date.valueOf(book.getReturnDate()));
                preparedStatement.executeUpdate();
                System.out.println("Sach da duoc luu thanh cong");
            }
        } catch (SQLException e) {
            System.out.println("Xay ra loi khi luu sach vao database: " + e.getMessage());
        }
    }

    public void saveBookToDatabase(EBook eBook) {
        try (Connection connection = ConnectMysql.getConnection()) {
            String query = "INSERT INTO ThuVienEBook (ID, TenSach, TacGia, NamXuatBan, NgayMuon, NgayTra, DinhDangFile, KichThuocFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, eBook.getId());
                preparedStatement.setString(2, eBook.getTitle());
                preparedStatement.setString(3, eBook.getAuthor());
                preparedStatement.setInt(4, eBook.getYear());
                preparedStatement.setDate(5, Date.valueOf(eBook.getBorrowDate()));
                preparedStatement.setDate(6, Date.valueOf(eBook.getReturnDate()));
                preparedStatement.setString(7, eBook.getFileFormat());
                preparedStatement.setInt(8, eBook.getFileSize());
                preparedStatement.executeUpdate();
                System.out.println("Sach da duoc luu thanh cong");
            }
        } catch (SQLException e) {
            System.out.println("Xay ra loi khi luu sach vao database: " + e.getMessage());
        }
    }

    public void saveLibraryToFile(String fileName) {
        FileHandler.saveBooksToFile(books, eBooks, fileName);
    }

    public void removePrintBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                break;
            }
        }
        // Xoá sách khỏi tệp "HongZin.txt"
        FileHandler.saveBooksToFile(books, eBooks, "HongZin.txt");

        try (Connection connection = ConnectMysql.getConnection()) {
            String query = "DELETE FROM ThuVienBook WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {

                    
                    System.out.println("Sach in voi \"" + id + "\" da xoa!");
                } else {
                    System.out.println("Sach in voi \"" + id + "\" khong tim thay hoac da duoc xoa truoc do!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Xay ra loi khi xoa sach in tu database: " + e.getMessage());
        }
    }

    public void removeEBook(int id) {
        for (EBook eBook : eBooks) {
            if (eBook.getId() == id) {
                eBooks.remove(eBook);
                break;
            }
        }
        // Xoá sách khỏi tệp "HongZin.txt"
        FileHandler.saveBooksToFile(books, eBooks, "HongZin.txt");
        try (Connection connection = ConnectMysql.getConnection()) {
            String query = "DELETE FROM ThuVienEBook WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("EBook voi \"" + id + "\" da xoa!");
                } else {
                    System.out.println("EBook \"" + id + "\" khong tim thay hoac da duoc xoa truoc do!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Xay ra loi khi xoa eBook tu database: " + e.getMessage());
        }
    }

    public void displayPrintBooks() {
        System.out.println("Book");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayEBooks() {
        System.out.println("EBook");
        for (EBook ebook : eBooks) {
            System.out.println(ebook);
        }
    }

    public void displayBooks() {
        System.out.println("Print Books:");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("\nEBooks:");
        for (EBook eBook : eBooks) {
            System.out.println(eBook);
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<EBook> getEBooks() {
        return eBooks;
    }

    public ArrayList<Book> getPrintBooks() {
        ArrayList<Book> printBooks = new ArrayList<>();
        for (Book book : books) {
            if (book instanceof Book) {
                printBooks.add(book);
            }
        }
        return printBooks;
    }

    public ArrayList<EBook> getElectronicBooks() {
        ArrayList<EBook> electronicBooks = new ArrayList<>();
        for (EBook eBook : eBooks) {
            electronicBooks.add(eBook);
        }
        return electronicBooks;
    }

    public void borrowPrintBook(Book book, String borrowerName, LocalDate borrowDate, LocalDate returnDate) {
        book.borrowBook(borrowerName, borrowDate, returnDate); // Cập nhật thông tin sách
        borrowedBooks.add(book); // Thêm sách vào danh sách đã mượn
        saveLibraryToFile("HongZin.txt"); // Lưu thông tin thư viện sau khi mượn sách
    }
    public Book getPrintBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId && book instanceof Book) {
                return (Book) book; // Ép kiểu về PrintBook
            }
        }
        return null;
    }
    // Phương thức để lấy danh sách sách đã mượn
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    // Phương thức để trả về một sách đã mượn dựa trên ID sách
    public Book getBorrowedBookById(int bookId) {
        for (Book book : borrowedBooks) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null; // Trả về null nếu không tìm thấy sách đã mượn
    }
    public void setDisplayPrintBooksFrame(DisplayPrintBooksFrame displayPrintBooksFrame) {
        this.displayPrintBooksFrame = displayPrintBooksFrame;
    }
    // Phương thức để trả sách đã mượn
    public void returnPrintBook(Book book) {
        book.returnBook(); // Đánh dấu sách đã trả
        borrowedBooks.remove(book); // Xóa sách khỏi danh sách sách đã mượn
        // Thêm sách đã trả vào danh sách sách in
        books.add(book);
        // Cập nhật lại file và cơ sở dữ liệu nếu cần
        FileHandler.saveBooksToFile(books, eBooks, "HongZin.txt");
        saveBookToDatabase(book);
        // Cập nhật lại giao diện hiển thị sách sau khi trả
        updateBookDisplay();
    }
    private void updatePrintBookInDatabase(Book book) {
        try (Connection connection = ConnectMysql.getConnection()) {
            String query = "UPDATE ThuVienBook WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(book.getReturnDate()));
                preparedStatement.setInt(2, book.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Sách đã được cập nhật trong cơ sở dữ liệu.");
                } else {
                    System.out.println("Không có sách nào được cập nhật.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Xảy ra lỗi khi cập nhật sách trong cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private void updateBookDisplay() {
        if (displayPrintBooksFrame != null) {
            displayPrintBooksFrame.updatePrintBooks(getPrintBooks());
        }
    }
    
    public ArrayList<Book> getBorrowedPrintBooks() {
        ArrayList<Book> borrowedPrintBooks = new ArrayList<>();
        for (Book book : books) {
            if (book instanceof Book && book.isBorrowed()) {
                borrowedPrintBooks.add(book);
            }
        }
        return borrowedPrintBooks;
    }
    
}
