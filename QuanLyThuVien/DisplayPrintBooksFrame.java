package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayPrintBooksFrame extends JFrame {
    private JTextArea textArea;

    public DisplayPrintBooksFrame() {
        setTitle("Danh sách sách in");
        setSize(800, 300);

        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public void updatePrintBooks(ArrayList<Book> printBooks) {
        displayPrintBooks(printBooks);
    }

    private void displayPrintBooks(ArrayList<Book> printBooks) {
        textArea.setText("Danh sách sách in:\n");
        if (printBooks.isEmpty()) {
            textArea.append("Không có sách in nào được thêm vào.\n");
        } else {
            for (Book book : printBooks) {
                textArea.append(book.toString() + "\n");
            }
        }
    }
}
