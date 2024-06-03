package demo.Manager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import demo.Functions.SharedModel;
import demo.Book.BorrowBook;
import demo.Functions.SharedModel.BorrowRecord;

public class ManageBorrow extends JFrame {

    private static DefaultTableModel tableModel;

    public ManageBorrow() {
        setTitle("Quản lý mượn sách");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(240, 255, 255));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 50)); // Large spacing between text and buttons

        // Title "Library Management"
        JLabel titleLabel = new JLabel("Quản lý thư viện");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel, BorderLayout.NORTH);

        // Functional buttons panel
        JPanel functionPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        functionPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Spacing between text and buttons
        functionPanel.setBackground(new Color(240, 255, 255));

        String[] options = {"Trang chủ", "Quản lý kho sách", "Quản lý mượn sách", "Quản lý trả sách", "Quản lý nhân viên", "Quản lý người mượn"};
        String[] iconPaths = {"home.png", "book.png", "calendarBorrow.png", "calendarReturn.png", "user.png", "manyUser.png"};

        for (int i = 0; i < options.length; i++) {
            JButton optionButton = createButton(options[i], iconPaths[i]);
            functionPanel.add(optionButton);
        }
        leftPanel.add(functionPanel, BorderLayout.CENTER);

        // Right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 255, 255));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý mượn sách");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 20)); // Adjust font size
        rightPanel.setBorder(titledBorder);

        // Panel for borrow button and search bar
        JPanel buttonSearchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonSearchPanel.setBackground(new Color(240, 255, 255));

        JButton borrowButton = new JButton("Ghi phiếu mượn");
        borrowButton.setBackground(new Color(0, 139, 139));
        borrowButton.setForeground(Color.WHITE);
        buttonSearchPanel.add(borrowButton);

        borrowButton.addActionListener(e -> new BorrowBook());

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(240, 255, 255));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchPanel.add(searchLabel);

        JTextField searchField = new JTextField(15);
        searchPanel.add(searchField);

        ImageIcon searchIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/loupe.png", 20, 20); // Adjust icon size
        JButton searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(30, 30));
        searchButton.setBackground(new Color(240, 255, 255));
        searchButton.setBorder(null);
        searchPanel.add(searchButton);

        // Panel for table of borrowed books
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 255, 255));

        // Initialize table of borrowed books with empty data
        String[] columnNames = {"ID sách", "Tên sách", "Họ tên người mượn", "Ngày mượn", "Ngày hẹn trả"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        rightPanel.add(buttonSearchPanel, BorderLayout.NORTH);
        rightPanel.add(searchPanel, BorderLayout.CENTER);
        rightPanel.add(tablePanel, BorderLayout.SOUTH);

        // Add left and right panels to the content pane
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Update the table with initial data
        updateTable();
    }

    // Method to create functional buttons
    private JButton createButton(String option, String iconPath) {
        JButton button = new JButton(option);
        button.setBackground(new Color(0, 139, 139));
        button.setForeground(Color.WHITE);
        button.setIcon(resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/" + iconPath, 30, 30)); // Adjust icon size
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        button.addActionListener(e -> {
            switch (option) {
                case "Trang chủ" -> GuiManager.manage();
                case "Quản lý kho sách" -> GuiManager.manageBooks();
                case "Quản lý mượn sách" -> GuiManager.manageBorrow();
                case "Quản lý trả sách" -> GuiManager.manageReturn();
                case "Quản lý nhân viên" -> GuiManager.manageStaff();
                case "Quản lý người mượn" -> GuiManager.manageBorrowers();
            }
        });

        return button;
    }

    // Method to resize icons
    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateTable() {
        tableModel.setRowCount(0);
        for (BorrowRecord record : SharedModel.getBorrowRecords()) {
            tableModel.addRow(new Object[]{
                record.getBookId(),
                SharedModel.getBookTitle(record.getBookId()),
                record.getBorrowerName(),
                record.getBorrowDate(),
                record.getReturnDate()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManageBorrow::new);
    }
}
