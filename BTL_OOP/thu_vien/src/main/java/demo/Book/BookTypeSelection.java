package demo.Book;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class BookTypeSelection extends JFrame {

    public BookTypeSelection(DefaultTableModel physicalBookTableModel, DefaultTableModel eBookTableModel) {

        setTitle("Chọn loại sách");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200)); // Increased size to fit larger buttons

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 255, 255));
        JLabel titleLabel = new JLabel("Chọn loại sách");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 255, 255));
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton physicalBookButton = new JButton("Sách in");
        physicalBookButton.setBackground(new Color(0, 139, 139));
        physicalBookButton.setForeground(Color.WHITE);
        physicalBookButton.setPreferredSize(new Dimension(300, 50)); // Adjusted button size


        JButton eBookButton = new JButton("Sách điện tử");
        eBookButton.setBackground(new Color(0, 139, 139));
        eBookButton.setForeground(Color.WHITE);
        eBookButton.setPreferredSize(new Dimension(300, 20)); // Adjusted button size

        // Action listener for physical book button
        physicalBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddBook addBook = new AddBook(physicalBookTableModel);
                addBook.setVisible(true);
                dispose();
            }
        });

        // Action listener for eBook button
        eBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddEBook addEBook = new AddEBook(eBookTableModel);
                addEBook.setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(physicalBookButton);
        buttonPanel.add(eBookButton);

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Test the BookTypeSelection class
        SwingUtilities.invokeLater(() -> {
            new BookTypeSelection(new DefaultTableModel(), new DefaultTableModel());
        });
    }
}
