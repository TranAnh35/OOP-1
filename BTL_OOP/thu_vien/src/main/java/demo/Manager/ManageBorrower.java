package demo.Manager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import demo.Functions.SharedModel;
import demo.Functions.Borrower;

public class ManageBorrower extends JFrame {
    private static DefaultTableModel borrowerTableModel;
    private JTable borrowerTable;
    private JTextField searchField;

    public ManageBorrower() {
        setTitle("Quản lý người mượn");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Left panel for navigation
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
            JButton optionButton = createButton(options[i], iconPaths[i]); // Create function buttons
            functionPanel.add(optionButton);
        }
        leftPanel.add(functionPanel, BorderLayout.CENTER);

        // Right panel for borrower management
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 255, 255));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Borrower management information panel
        JPanel borrowerInfoPanel = new JPanel(new BorderLayout());
        borrowerInfoPanel.setBorder(BorderFactory.createTitledBorder("Quản lý người mượn"));
        borrowerInfoPanel.setBackground(new Color(240, 255, 255));

        // Title "Manage Borrowers"
        JLabel manageBorrowerLabel = new JLabel("Quản lý người mượn");
        manageBorrowerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        borrowerInfoPanel.add(manageBorrowerLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(240, 255, 255));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchPanel.add(searchLabel);

        searchField = new JTextField(15);
        searchPanel.add(searchField);

        ImageIcon searchIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/loupe.png", 20, 20);
        JButton searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(30, 30));
        searchButton.setBackground(new Color(240, 255, 255));
        searchButton.setBorder(null);
        searchButton.addActionListener(e -> searchBorrower(searchField.getText().toLowerCase()));
        searchPanel.add(searchButton);

        
        // Borrower information table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 255, 255));
        
        borrowerTableModel = new DefaultTableModel(new Object[]{"ID", "Họ tên", "Email", "Số điện thoại"}, 0);
        borrowerTable = new JTable(borrowerTableModel);
        JScrollPane scrollPane = new JScrollPane(borrowerTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        borrowerInfoPanel.add(searchPanel, BorderLayout.NORTH);
        borrowerInfoPanel.add(tablePanel, BorderLayout.CENTER);

        rightPanel.add(borrowerInfoPanel, BorderLayout.CENTER);

        // Add left and right panels to the content pane
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        updateTable();
    }

    // Method to create function buttons
    private JButton createButton(String option, String iconPath) {
        JButton button = new JButton(option);
        button.setBackground(new Color(0, 139, 139));
        button.setForeground(Color.WHITE);
        button.setIcon(resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/" + iconPath, 30, 30)); // Resize the icon
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

    // Method to update borrower table
    public static void updateTable() {
        borrowerTableModel.setRowCount(0);
        for (Borrower borrower : SharedModel.getBorrowers()) {
            borrowerTableModel.addRow(new Object[]{borrower.getId(), borrower.getName(), borrower.getEmail(), borrower.getPhoneNumber()});
        }
    }

    private void searchBorrower(String searchText) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(borrowerTableModel);
        borrowerTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManageBorrower::new);
    }
}
