package QuanLyThuVien;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandler {
    public static void saveBooksToFile(ArrayList<Book> books, ArrayList<EBook> eBooks, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Ghi thông tin sách in
            for (Book book : books) {
                writer.write("PrintBook: " + book.getId()+", " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getYear() + ", " + book.getBorrowDate() + ", " + book.getReturnDate());
                writer.newLine();
            }
            // Ghi thông tin sách điện tử
            for (EBook eBook : eBooks) {
                writer.write("EBook: " + eBook.getId()+", " + eBook.getTitle() + ", " + eBook.getAuthor() + ", " + eBook.getYear() + ", " + eBook.getBorrowDate() + ", " + eBook.getReturnDate() + ", " + eBook.getFileFormat() + ", " + eBook.getFileSize());
                writer.newLine();
            }
            System.out.println("Danh sach da duoc ghi vao file '" + fileName + "'.");
            //IOException xử lý ngoại lệ khi input/output của file
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi ghi file: " + e.getMessage());
        }
    }    
    

    // Đọc danh sách sách từ file
    public static void readBooksFromFile(String fileName, ArrayList<Book> books, ArrayList<EBook> eBooks) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", "); // Tách dòng thành các phần
                if (parts.length > 0) {
                    if (parts[0].equals("PrintBook")) {
                        // Xử lý thông tin sách in
                        int id = Integer.parseInt(parts[1]);
                        String title = parts[2];
                        String author = parts[3];
                        int year = Integer.parseInt(parts[4]);
                        // Xử lý ngày mượn và ngày trả (parts[5] và parts[6])
                        LocalDate borrowDate = LocalDate.parse(parts[5]);
                        LocalDate returnDate = LocalDate.parse(parts[6]);
                        books.add(new Book(id, title, author, year, borrowDate, returnDate));
                    } else if (parts[0].equals("EBook")) {
                        // Xử lý thông tin sách điện tử
                        int id = Integer.parseInt(parts[1]);
                        String title = parts[2];
                        String author = parts[3];
                        int year = Integer.parseInt(parts[4]);
                        // Xử lý ngày mượn và ngày trả (parts[4] và parts[5])
                        LocalDate borrowDate = LocalDate.parse(parts[5]);
                        LocalDate returnDate = LocalDate.parse(parts[6]);
                        String fileFormat = parts[7];
                        int fileSize = Integer.parseInt(parts[8]);
                        eBooks.add(new EBook(id, title, author, year, borrowDate, returnDate, fileFormat, fileSize));
                    }
                }
            }
            System.out.println("Danh sach da duoc doc tu file '" + fileName + "'.");
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi doc file: " + e.getMessage());
        }
    }
}
