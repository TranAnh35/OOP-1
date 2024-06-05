package demo.Functions;

import demo.Book.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SharedModel {
    private static List<Book> books = new ArrayList<>();
    private static List<BorrowRecord> borrowRecords = new ArrayList<>();
    private static List<Borrower> borrowers = new ArrayList<>();

    public static List<BorrowRecord> getBorrowRecords() {
        List<BorrowRecord> listBorrowRecords = new ArrayList<>();
        String sql = "SELECT * FROM muonsach;";
        try(Connection conn = MySqlConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String borrowID = rs.getString("borrowID");
                String tenSach = rs.getString("TenSach");
                String bookID = rs.getString("BookID");
                String idNguoiMuon = rs.getString("IDNguoiMuon");
                String ngayMuon = rs.getString("NgayMuon");

                listBorrowRecords.add(new BorrowRecord(borrowID, tenSach, bookID, idNguoiMuon, borrowID, ngayMuon));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listBorrowRecords;
    }

    public static List<Borrower> getBorrowers() {
        List<Borrower> listBorrowers = new ArrayList<>();
        String sql = "SELECT * FROM NguoiMuon";
        try(Connection conn = MySqlConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String IDNguoiMuon = rs.getString("IDNguoiMuon");
                String TenNguoiMuon = rs.getString("TenNguoiMuon");
                String email = rs.getString("email");
                String soDienThoai = rs.getString("soDienThoai");

                listBorrowers.add(new Borrower(IDNguoiMuon, TenNguoiMuon, email, soDienThoai));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listBorrowers;
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
            String query = "SELECT TieuDeSach FROM book WHERE BookID = ?;";
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
        return "Unknown Title";
    }

    public static void saveBorrowRecordToDatabase(BorrowRecord record) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO MuonSach (BorrowID, TenSach, BookID, IDNguoiMuon, NgayMuon, NgayHenTra) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, record.getBorrowID());
                preparedStatement.setString(2, record.getBookId());
                preparedStatement.setString(3, record.getBookName());
                preparedStatement.setString(4, record.getBorrowerID());
                preparedStatement.setString(5, record.getBorrowDate());
                preparedStatement.setString(6, record.getReturnDate());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void saveBorrowerToDatabase(Borrower borrower) {
        try (Connection connection = MySqlConnection.getConnection()) {
            String query = "INSERT INTO NguoiMuon (IDNguoiMuon, TenNguoiMuon, email, SoDienThoai) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, borrower.getId());
                preparedStatement.setString(2, borrower.getName());
                preparedStatement.setString(3, borrower.getEmail());
                preparedStatement.setString(4, borrower.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String generateUniqueBorrowID(){
        String newID = "";
        try{
            Connection connection = MySqlConnection.getConnection();
            String query = "SELECT MAX(BorrowID) FROM MuonSach";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String maxID = resultSet.getString(1);
                if(maxID != null){
                    int maxIDInt = Integer.parseInt(maxID.split("-")[1]);
                    newID = String.format("MS-%03d", maxIDInt + 1);
                }else{
                    newID = "MS-001";
                }
            }else{
                newID = "MS-001";
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return newID;
    }
    
    public String generateUniqueBorrowerID(String borrowerName){
        String newID = "";
        if(getBorrowedBookCount(borrowerName) > 0){
            return getIDByBorrowerName(borrowerName);
        }
        try{
            Connection connection = MySqlConnection.getConnection();
            String query = "SELECT MAX(IDNguoiMuon) FROM NguoiMuon";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String maxID = resultSet.getString(1);
                if(maxID != null){
                    int maxIDInt = Integer.parseInt(maxID.split("-")[1]);
                    newID = String.format("NM-%03d", maxIDInt + 1);
                }else{
                    newID = "NM-001";
                }
            }else{
                newID = "NM-001";
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return newID;
    }

    public int getBorrowedBookCount(String BorrowerName){
        int count = 0;
        try{
            Connection connection = MySqlConnection.getConnection();
            String query = "SELECT COUNT(*) FROM NguoiMuon WHERE TenNguoiMuon = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, BorrowerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                count = resultSet.getInt(1);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return count;
    }

    public String getIDByBorrowerName(String BorrowerName){
        String ID = "";
        try{
            Connection connection = MySqlConnection.getConnection();
            String query = "SELECT IDNguoiMuon FROM NguoiMuon WHERE TenNguoiMuon = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, BorrowerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                ID = resultSet.getString(1);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return ID;
    }

    public String getBorrowReturnDate(String BookID){
        String returnDate = "";
        try{
            Connection connection = MySqlConnection.getConnection();
            String query = "SELECT NgayHenTra FROM MuonSach WHERE BookID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, BookID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                returnDate = resultSet.getString(1);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return returnDate;
    }
}