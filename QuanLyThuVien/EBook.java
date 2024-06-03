package QuanLyThuVien;

import java.time.LocalDate;

public class EBook extends Book {
    private String fileFormat;
    private int fileSize;

    public EBook(int id, String title, String author, int year, LocalDate borrowDate, LocalDate returnDate, String fileFormat, int fileSize) {
        //kế thừa các thuộc tính của lớp Book
        super(id, title, author, year, borrowDate, returnDate);
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String toString() {
        return super.toString() + ", Dinh dang file: " + fileFormat + ", Kich thuoc: " + fileSize;
    }
}
