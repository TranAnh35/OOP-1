package demo.Book;

// Lớp EBook kế thừa từ lớp Book
public class EBook extends Book {
    private String format;
    private float fileSize;

    // Constructor
    public EBook(String id, String title, String author, int year, int quantity, String format, float fileSize) {
        super(id, title, author, year, quantity);
        this.format = format;
        this.fileSize = fileSize;
    }

    // Getter và Setter cho thuộc tính mới
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public float getFileSize() {
        return fileSize;
    }

    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }

    // Override phương thức toString để hiển thị thông tin của EBook

    public String toString() {
        return "EBook{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", year=" + getYear() +
                ", quantity=" + getQuantity() +
                ", format='" + format + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
