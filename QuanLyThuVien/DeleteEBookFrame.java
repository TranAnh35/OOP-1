package QuanLyThuVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteEBookFrame extends JFrame {
    private JTextField idField;
    private Library library;

    public DeleteEBookFrame(Library library) {
        this.library = library;
        setTitle("Xoá sách điện tử");
        setSize(300, 100);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("ID sách điện tử cần xoá:");
        panel.add(idLabel);
        idField = new JTextField();
        panel.add(idField);

        JButton deleteButton = new JButton("Xoá sách điện tử");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện xoá sách điện tử khi nhấn nút
                String id = idField.getText();
                try {
                    int bookId = Integer.parseInt(id);
                    library.removeEBook(bookId); // Thêm bookId vào đây
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập ID sách điện tử là một số nguyên.");
                }
            }
        });
        panel.add(deleteButton);

        add(panel);
    }
}
