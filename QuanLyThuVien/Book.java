package QuanLyThuVien;

import java.time.LocalDate;
public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean borrowed; // Trạng thái mượn
    private String borrowerName;

    public Book(int id, String title, String author, int year, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowed = false;
        this.borrowerName = "";
    }
    //lấy giá trị của thuộc tính từ lớp bên ngoài
    public int getId(){
        return id;
    }
    //thiết lập giá trị cho một thuộc tính từ bên ngoài
    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public boolean isBorrowed() {
        return borrowed;
    }

    public void borrowBook(String borrowerName, LocalDate borrowDate, LocalDate returnDate) {
        // Cập nhật thông tin khi mượn sách
        this.borrowed = true;
        this.borrowerName = borrowerName;
    }
    public String getBorrowerName() {
        return borrowerName;
    }
    public void returnBook() {
        borrowed = false;
    }
    //Hiển thị các giá trị của Book
    public String toString() {
        return "ID: " + id + ", Sach: " + title + ", Tac gia: " + author + ", Nam xuat ban: " + year +
               ", Ngay muon: " + borrowDate + ", Ngay tra: " + returnDate;
    }
}
