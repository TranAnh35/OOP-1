package demo.Functions;

public class BorrowRecord {
    private String bookId;
    private String borrowerName;
    private String email;
    private String phoneNumber;
    private String borrowDate;
    private String returnDate;
    private String status;

    public BorrowRecord(String bookId, String borrowerName, String email, String phoneNumber, String borrowDate) {
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowDate = borrowDate;
        this.returnDate = ""; 
        this.status = "Borrowed"; // Default status
    }

    public String getBookId() {
        return bookId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
