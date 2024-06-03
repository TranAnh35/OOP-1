package demo.Manager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import demo.Book.BookData;
import demo.Book.BookTypeSelection;

public class ManageBooks extends JFrame {
    private DefaultTableModel physicalBookTableModel;
    private DefaultTableModel eBookTableModel;
    private JTable physicalBookTable;
    private JTable eBookTable;

    public ManageBooks() {
        setTitle("Quản lý kho sách");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Phần bên trái giao diện
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(240, 255, 255));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 50)); // Khoảng cách lớn giữa chữ và nút

        // Tiêu đề "Quản lý thư viện"
        JLabel titleLabel = new JLabel("Quản lý thư viện");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel, BorderLayout.NORTH);

        // Phần chứa các nút chức năng
        JPanel functionPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        functionPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Khoảng cách giữa chữ và nút
        functionPanel.setBackground(new Color(240, 255, 255));

        String[] options = {"Trang chủ", "Quản lý kho sách", "Quản lý mượn sách", "Quản lý trả sách", "Quản lý nhân viên", "Quản lý người mượn"};
        String[] iconPaths = {"home.png", "book.png", "calendarBorrow.png", "calendarReturn.png", "user.png", "manyUser.png"};

        for (int i = 0; i < options.length; i++) {
            JButton optionButton = createButton(options[i], iconPaths[i]); // Tạo nút chức năng
            functionPanel.add(optionButton);
        }
        leftPanel.add(functionPanel, BorderLayout.CENTER);

        // Phần bên phải giao diện
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 255, 255));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý kho sách");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 20)); // Điều chỉnh kích thước font
        rightPanel.setBorder(titledBorder);

        // Phần chứa nút thêm sách mới và thanh tìm kiếm
        JPanel buttonSearchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonSearchPanel.setBackground(new Color(240, 255, 255));

        // Button thêm sách mới
        JButton addButton = new JButton("Thêm sách mới");
        addButton.setBackground(new Color(0, 139, 139));
        addButton.setForeground(Color.WHITE);

        // Xử lý sự kiện khi nhấn vào nút "Thêm sách mới"
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hiển thị giao diện chọn loại sách
                BookTypeSelection bookTypeSelection = new BookTypeSelection(physicalBookTableModel, eBookTableModel);
                bookTypeSelection.setVisible(true); // Hiển thị cửa sổ chọn loại sách
            }
        });

        buttonSearchPanel.add(addButton); // Thêm nút vào panel

        // Phần tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(240, 255, 255));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchPanel.add(searchLabel);

        JTextField searchField = new JTextField(15);
        searchPanel.add(searchField);

        ImageIcon searchIcon = resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/loupe.png", 20, 20); // Điều chỉnh kích thước của icon
        JButton searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(30, 30));
        searchButton.setBackground(new Color(240, 255, 255));
        searchButton.setBorder(null);
        searchPanel.add(searchButton);

        // Thêm sự kiện cho nút tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchBooks(searchField.getText());
            }
        });

        // Phần chứa bảng danh sách sách
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 255, 255));

        // Lấy mô hình dữ liệu từ BookData
        physicalBookTableModel = BookData.getInstance().getPhysicalBookTableModel();
        physicalBookTable = new JTable(physicalBookTableModel);
        addButtonToTable(physicalBookTable, true);
        JScrollPane physicalBookTableScrollPane = new JScrollPane(physicalBookTable);

        eBookTableModel = BookData.getInstance().getEBookTableModel();
        eBookTable = new JTable(eBookTableModel);
        addButtonToTable(eBookTable, false);
        JScrollPane eBookTableScrollPane = new JScrollPane(eBookTable);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sách in", physicalBookTableScrollPane);
        tabbedPane.addTab("Sách điện tử", eBookTableScrollPane);

        tablePanel.add(tabbedPane, BorderLayout.CENTER);

        rightPanel.add(buttonSearchPanel, BorderLayout.NORTH);
        rightPanel.add(searchPanel, BorderLayout.CENTER);
        rightPanel.add(tablePanel, BorderLayout.SOUTH);

        // Thêm phần bên trái và bên phải vào contentPane
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Phương thức để tạo nút chức năng
    private JButton createButton(String option, String iconPath) {
        JButton button = new JButton(option);
        button.setBackground(new Color(0, 139, 139));
        button.setForeground(Color.WHITE);
        button.setIcon(resizeIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/" + iconPath, 30, 30)); // Điều chỉnh kích thước của icon
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        button.addActionListener(e -> {
            if (option.equals("Trang chủ")) {
                GuiManager.manage();
            } else if (option.equals("Quản lý kho sách")) {
                GuiManager.manageBooks();
            } else if (option.equals("Quản lý mượn sách")) {
                GuiManager.manageBorrow();
            } else if (option.equals("Quản lý trả sách")) {
                GuiManager.manageReturn();
            } else if (option.equals("Quản lý nhân viên")) {
                GuiManager.manageStaff();
            } else if (option.equals("Quản lý người mượn")) {
                GuiManager.manageBorrowers();
            }
        });

        return button;
    }

    // Phương thức để thay đổi kích thước của icon
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

    // Phương thức thêm nút vào bảng
    private void addButtonToTable(JTable table, boolean isPhysicalBook) {
        table.getColumn("Hành động").setCellRenderer(new ButtonRenderer());
        table.getColumn("Hành động").setCellEditor(new ButtonEditor(new JCheckBox(), table, isPhysicalBook));
    }

    // Phương thức tìm kiếm sách
    private void searchBooks(String searchText) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(physicalBookTableModel);
        TableRowSorter<TableModel> eBookSorter = new TableRowSorter<>(eBookTableModel);

        physicalBookTable.setRowSorter(sorter);
        eBookTable.setRowSorter(eBookSorter);

        RowFilter<Object, Object> filter = RowFilter.regexFilter("(?i)" + searchText);

        sorter.setRowFilter(filter);
        eBookSorter.setRowFilter(filter);
    }

    // Button renderer class
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            // Đọc biểu tượng từ file ảnh và điều chỉnh kích thước
            ImageIcon icon = new ImageIcon("BTL_OOP/thu_vien/src/main/java/demo/resourse/image/delete.png");
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(resizedImg)); // Đặt biểu tượng đã điều chỉnh kích thước cho nút
            setBackground(new Color(240, 255, 255)); // Đặt màu nền cho nút
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Button editor class
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        private boolean isPhysicalBook;

        public ButtonEditor(JCheckBox checkBox, JTable table, boolean isPhysicalBook) {
            super(checkBox);
            this.table = table;
            this.isPhysicalBook = isPhysicalBook;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Delete" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Perform action here
                int row = table.convertRowIndexToModel(table.getEditingRow());
                if (isPhysicalBook) {
                    BookData.getInstance().removePhysicalBook(row);
                } else {
                    BookData.getInstance().removeEBook(row);
                }
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageBooks());
    }
}
