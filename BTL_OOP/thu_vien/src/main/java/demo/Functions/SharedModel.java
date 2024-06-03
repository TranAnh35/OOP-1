package demo.Functions;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SharedModel {
    private static List<Book> books = new ArrayList<>();
    private static List<BorrowRecord> borrowRecords = new ArrayList<>();
    private static List<Borrower> borrowers = new ArrayList<>();

    public static List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public static List<Borrower> getBorrowers() {
        return borrowers;
    }

    public static List<Book> getBooks() {
        return books;
    }

    public static void addBorrowRecord(BorrowRecord record) {
        borrowRecords.add(record);
        saveBorrowRecordToDatabase(record);
    }

    public static void addBorrower(Borrower borrower) {
        borrowers.add(borrower);
        saveBorrowerToDatabase(borrower);
    }

    public static void addBook(Book book) {
        books.add(book);
    }

    public static String getBookTitle(String bookId) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "SELECT TieuDeSach FROM library_oop1.thuvienbook WHERE id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, bookId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("TieuDeSach");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Unknown Title"; // Return a default value if the book is not found
    }

    public static class BorrowRecord {
        private int id;
        private String bookId;
        private String bookTitle;
        private String borrowerName;
        private Date borrowDate;
        private Date returnDate;

        // Constructor
        public BorrowRecord(int id, String bookId, String bookTitle, String borrowerName, Date borrowDate, Date returnDate) {
            this.id = id;
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.borrowerName = borrowerName;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getBookId() {
            return bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getBorrowerName() {
            return borrowerName;
        }

        public Date getBorrowDate() {
            return borrowDate;
        }

        public Date getReturnDate() {
            return returnDate;
        }
    }

    public static class Borrower {
        private int id;
        private String name;
        private String email;
        private String phoneNumber;

        // Constructor
        public Borrower(int id, String name, String email, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    private static void saveBorrowRecordToDatabase(BorrowRecord record) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO MuonSachBook (ID, BookId, TenSach, TenNguoiMuon, NgayMuon, NgayHenTra) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, record.getId());
                preparedStatement.setString(2, record.getBookId());
                preparedStatement.setString(3, record.getBookTitle());
                preparedStatement.setString(4, record.getBorrowerName());
                preparedStatement.setDate(5, new java.sql.Date(record.getBorrowDate().getTime()));
                preparedStatement.setDate(6, new java.sql.Date(record.getReturnDate().getTime()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void saveBorrowerToDatabase(Borrower borrower) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO NguoiMuonBook (ID, HoTen, email, SDT) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, borrower.getId());
                preparedStatement.setString(2, borrower.getName());
                preparedStatement.setString(3, borrower.getEmail());
                preparedStatement.setString(4, borrower.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}