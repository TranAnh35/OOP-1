package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePrintBookFrame extends JFrame {
    private JTextField idField;
    private Library library;

    public DeletePrintBookFrame(Library library) {
        this.library = library;
        setTitle("Xoá sách in");
        setSize(300, 100);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("ID sách in cần xoá:");
        panel.add(idLabel);
        idField = new JTextField();
        panel.add(idField);

        JButton deleteButton = new JButton("Xoá sách in");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện xoá sách in khi nhấn nút
                String id = idField.getText();
                try {
                    int bookId = Integer.parseInt(id);
                    library.removePrintBook(bookId); // Thêm bookId vào đây
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập ID sách in là một số nguyên.");
                }
            }
        });
        panel.add(deleteButton);

        add(panel);
    }
}

