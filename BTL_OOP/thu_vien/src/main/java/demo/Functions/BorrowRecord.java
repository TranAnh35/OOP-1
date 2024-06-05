package demo.Functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowRecord {
    private String BorrowID;
    private String TenSach;
    private String bookID;
    private String IDNguoiMuon;
    private String borrowDate;
    private String returnDate;

    public BorrowRecord(String BorrowID, String TenSach, String BookID, String IDNguoiMuon ,String borrowDate, String returnDate) {
        this.BorrowID = BorrowID;
        this.TenSach = TenSach;
        this.bookID = BookID;
        this.IDNguoiMuon = IDNguoiMuon;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate; 
    }

    public String getBookId() {
        return bookID;
    }

    public String getBorrowID() {
        return BorrowID;
    }

    public String getBookName() {
        return TenSach;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getBorrowerID() {
        return IDNguoiMuon;
    }

    public String getBorrowerName() throws SQLException{
        Connection connection = MySqlConnection.getConnection();
        String query = "SELECT TenNguoiMuon FROM nguoimuon WHERE IDNguoiMuon = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, IDNguoiMuon);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("TenNguoiMuon");
                }
            }
        }
        return "Unknown Borrower";
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
