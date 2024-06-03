package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayEBooksFrame extends JFrame {
    private JTextArea textArea;

    public DisplayEBooksFrame() {
        setTitle("Danh sách sách điện tử");
        setSize(800, 300);

        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public void updateEBooks(ArrayList<EBook> eBooks) {
        displayEBooks(eBooks);
    }

    private void displayEBooks(ArrayList<EBook> eBooks) {
        textArea.setText("Danh sách sách điện tử:\n");
        if (eBooks.isEmpty()) {
            textArea.append("Không có sách điện tử nào được thêm vào.\n");
        } else {
            for (EBook eBook : eBooks) {
                textArea.append(eBook.toString() + "\n");
            }
        }
    }
}
