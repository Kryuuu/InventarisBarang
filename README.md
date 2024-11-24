
# Aplikasi Inventaris Barang



# Deskripsi
Aplikasi Inventaris Barang adalah sebuah sistem berbasis Java yang dirancang untuk membantu pengelolaan data inventaris barang secara efisien. Aplikasi ini memungkinkan pengguna untuk melakukan berbagai operasi CRUD (Create, Read, Update, Delete) pada data barang. Dengan menggunakan antarmuka yang sederhana, aplikasi ini dapat digunakan untuk mencatat informasi penting seperti nama barang, kategori, jumlah, dan lokasi penyimpanan barang.
# Features

- Manajemen Data Barang:
Tambah, ubah, hapus, dan cari data barang secara mudah.
- Filter Data:
Fitur filter untuk menyaring data berdasarkan kategori atau lokasi.
- Validasi Input:
Memastikan data yang dimasukkan valid untuk mengurangi kesalahan pengguna.
- Ekspor Laporan:
Ekspor data barang ke format PDF atau lainnya untuk keperluan laporan.
- Login Sistem:
Sistem login sederhana untuk melindungi akses data.

# Teknologi yang digunakan
- Backend:
    Java dengan konsep OOP.
- Database:
    MySQL menggunakan XAMPP.
- Antarmuka:
    GUI berbasis Swing di NetBeans.


# Komponen GUI

Aplikasi ini menggunakan beberapa komponen GUI utama, di antaranya:

- JFrame
- JPanel
- JLabel
- JTextField
- JButton
- JComboBox
- JTable
- JScrollPane


# Dokumentasi Code

## FormUtama.java

```java


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import config.DatabaseConnection;
import dao.BarangDAO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Barang;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;

/**
 *
 * @author muham
 */
public class FormUtama extends javax.swing.JFrame {

    private List<Integer> listId = new ArrayList<>();

    public FormUtama() {
        initComponents();
        loadTable(); // Memuat semua data
        loadKategoriToComboBox(); // Memuat kategori ke JComboBox
        loadLokasiToJList();
    }

    private void loadTable() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);
            List<Barang> barangList = barangDAO.getSemuaBarang();

            DefaultTableModel model = (DefaultTableModel) tableBarang.getModel();
            model.setRowCount(0); // Kosongkan tabel
            listId.clear(); // Kosongkan listId sebelum mengisinya kembali

            for (Barang barang : barangList) {
                listId.add(barang.getId()); // Simpan ID barang ke listId
                model.addRow(new Object[]{
                    barang.getNamaBarang(),
                    barang.getKategori(),
                    barang.getJumlah(),
                    barang.getLokasi()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data: " + e.getMessage());
        }
    }

    private void loadTableCari(String keyword) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);
            List<Barang> barangList = barangDAO.cariBarang(keyword);

            DefaultTableModel model = (DefaultTableModel) tableBarang.getModel();
            model.setRowCount(0); // Kosongkan tabel sebelum menambahkan data

            for (Barang barang : barangList) {
                model.addRow(new Object[]{
                    barang.getNamaBarang(),
                    barang.getKategori(),
                    barang.getJumlah(),
                    barang.getLokasi()
                });
            }

            if (barangList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data yang sesuai dengan pencarian.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencari data: " + e.getMessage());
        }
    }

    private void loadTableByKategori(String kategori) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);
            List<Barang> barangList = barangDAO.filterByKategori(kategori);

            DefaultTableModel model = (DefaultTableModel) tableBarang.getModel();
            model.setRowCount(0); // Kosongkan tabel sebelum menambahkan data

            for (Barang barang : barangList) {
                model.addRow(new Object[]{
                    barang.getNamaBarang(),
                    barang.getKategori(),
                    barang.getJumlah(),
                    barang.getLokasi()
                });
            }

            if (barangList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data dalam kategori: " + kategori);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memfilter data: " + e.getMessage());
        }
    }

    private void loadKategoriToComboBox() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);
            List<String> kategoriList = barangDAO.getKategoriList();

            cmbKategori.removeAllItems(); // Hapus item lama
            for (String kategori : kategoriList) {
                cmbKategori.addItem(kategori); // Tambahkan kategori ke JComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat kategori: " + e.getMessage());
        }
    }

    private void loadLokasiToJList() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);
            List<String> lokasiList = barangDAO.getLokasiList();

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String lokasi : lokasiList) {
                listModel.addElement(lokasi); // Tambahkan lokasi ke model
            }
            listLokasi.setModel(listModel); // Pasang model ke JList
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat lokasi: " + e.getMessage());
        }
    }

    private void loadTableByLokasi(String lokasi) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);
            List<Barang> barangList = barangDAO.filterByLokasi(lokasi);

            DefaultTableModel model = (DefaultTableModel) tableBarang.getModel();
            model.setRowCount(0); // Kosongkan tabel sebelum menambahkan data

            for (Barang barang : barangList) {
                model.addRow(new Object[]{
                    barang.getNamaBarang(),
                    barang.getKategori(),
                    barang.getJumlah(),
                    barang.getLokasi()
                });
            }

            if (barangList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data untuk lokasi: " + lokasi);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtNamaBarang.setText("");
        txtKategori.setText("");
        txtJumlah.setText("");
        txtLokasi.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        txtNamaBarang = new javax.swing.JTextField();
        txtKategori = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        txtLokasi = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        cmbKategori = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listLokasi = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        btnExportPDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Inventaris Barang");

        jLabel2.setText("Nama Barang");

        jLabel3.setText("Kategori");

        jLabel4.setText("Jumlah");

        jLabel5.setText("Lokasi");

        tableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Barang", "Kategori", "Jumlah", "Lokasi"
            }
        ));
        tableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBarang);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setText("Ubah");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnRefresh.setText("Muat Ulang");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnCari.setText("Cari Barang");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnFilter.setText("Filter");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        listLokasi.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listLokasiValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listLokasi);

        jLabel6.setText("Filter berdasarkan Lokasi");

        btnExportPDF.setText("Export Data");
        btnExportPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtLokasi)
                                .addComponent(txtKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtJumlah)))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCari)
                            .addComponent(cmbKategori, 0, 136, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExportPDF)
                        .addGap(11, 11, 11))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCari))
                            .addGap(29, 29, 29)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFilter))
                            .addGap(103, 103, 103))
                        .addComponent(txtLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(89, 89, 89)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus)
                    .addComponent(btnRefresh)
                    .addComponent(btnExportPDF))
                .addGap(121, 121, 121))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String namaBarang = txtNamaBarang.getText();
        String kategori = txtKategori.getText();
        int jumlah = Integer.parseInt(txtJumlah.getText());
        String lokasi = txtLokasi.getText();

        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);

            // Buat objek Barang tanpa atribut foto
            Barang barang = new Barang(0, namaBarang, kategori, jumlah, lokasi);
            barangDAO.tambahBarang(barang);

            JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan!");

            // Muat ulang tabel dan kosongkan form
            loadTable();
            loadKategoriToComboBox();
            loadLokasiToJList();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }
    }                                         

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {                                        
        int selectedRow = tableBarang.getSelectedRow(); // Baris yang dipilih di JTable
        if (selectedRow == -1) { // Jika tidak ada baris yang dipilih
            JOptionPane.showMessageDialog(this, "Pilih barang yang ingin diedit!");
            return;
        }

        // Ambil ID dari listId
        int id = listId.get(selectedRow);

        // Ambil data dari input field
        String namaBarang = txtNamaBarang.getText();
        String kategori = txtKategori.getText();
        int jumlah = Integer.parseInt(txtJumlah.getText());
        String lokasi = txtLokasi.getText();

        try {
            // Koneksi ke database dan inisialisasi DAO
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);

            // Buat objek Barang tanpa atribut foto
            Barang barang = new Barang(id, namaBarang, kategori, jumlah, lokasi);

            // Update barang di database
            barangDAO.updateBarang(barang);

            // Tampilkan pesan sukses
            JOptionPane.showMessageDialog(this, "Barang berhasil diperbarui!");

            // Muat ulang tabel dan kosongkan form
            loadTable();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }
    }                                       

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int selectedRow = tableBarang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih barang yang ingin dihapus!");
            return;
        }

        int id = listId.get(selectedRow); // Ambil ID dari listId

        try {
            Connection conn = DatabaseConnection.getConnection();
            BarangDAO barangDAO = new BarangDAO(conn);

            barangDAO.hapusBarang(id);

            JOptionPane.showMessageDialog(this, "Barang berhasil dihapus!");
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }
    }                                        

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {                                           
        loadTable();
        clearForm();
        txtCari.setText(""); // Kosongkan text field pencarian
        listLokasi.clearSelection(); // Hapus seleksi di JList
    }                                          

    private void tableBarangMouseClicked(java.awt.event.MouseEvent evt) {                                         
        int selectedRow = tableBarang.getSelectedRow(); // Baris yang dipilih
        if (selectedRow == -1) { // Tidak ada baris yang dipilih
            JOptionPane.showMessageDialog(this, "Pilih baris di tabel terlebih dahulu!");
            return;
        }

        // Pastikan listId memiliki data
        if (selectedRow >= listId.size()) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan untuk baris yang dipilih!");
            return;
        }

        try {
            // Ambil ID dari listId
            int id = listId.get(selectedRow);

            // Ambil data dari JTable
            String namaBarang = (String) tableBarang.getValueAt(selectedRow, 0);
            String kategori = (String) tableBarang.getValueAt(selectedRow, 1);
            int jumlah = Integer.parseInt(tableBarang.getValueAt(selectedRow, 2).toString());
            String lokasi = (String) tableBarang.getValueAt(selectedRow, 3);

            // Isi field dengan data dari baris yang dipilih
            txtNamaBarang.setText(namaBarang);
            txtKategori.setText(kategori);
            txtJumlah.setText(String.valueOf(jumlah));
            txtLokasi.setText(lokasi);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengambil data: " + e.getMessage());
        }
    }                                        

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        String keyword = txtCari.getText(); // Ambil kata kunci dari JTextField
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan kata kunci untuk pencarian.");
            return;
        }
        loadTableCari(keyword); // Panggil method untuk mencari data
    }                                       

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String selectedKategori = (String) cmbKategori.getSelectedItem(); // Ambil kategori yang dipilih
        loadTableByKategori(selectedKategori); // Panggil method untuk memfilter data
    }                                         

    private void listLokasiValueChanged(javax.swing.event.ListSelectionEvent evt) {                                        
        if (!evt.getValueIsAdjusting()) { // Hanya tangani event final, bukan event sementara
            String selectedLokasi = listLokasi.getSelectedValue(); // Ambil lokasi yang dipilih
            if (selectedLokasi != null) {
                loadTableByLokasi(selectedLokasi); // Filter data di JTable berdasarkan lokasi
            }
        }
    }                                       

    private void btnExportPDFActionPerformed(java.awt.event.ActionEvent evt) {                                             
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan File PDF");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf"; // Tambahkan ekstensi .pdf jika belum ada
            }

            try {
                // Buat dokumen PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Tambahkan judul dokumen
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph title = new Paragraph("Laporan Inventaris Barang", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Tambahkan spasi
                document.add(Chunk.NEWLINE);

                // Buat tabel PDF
                PdfPTable table = new PdfPTable(4); // 4 kolom: Nama Barang, Kategori, Jumlah, Lokasi
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                // Tambahkan header tabel
                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                table.addCell(new PdfPCell(new Phrase("Nama Barang", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Kategori", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Jumlah", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Lokasi", headerFont)));

                // Tambahkan data dari JTable
                DefaultTableModel model = (DefaultTableModel) tableBarang.getModel();
                Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        String cellData = model.getValueAt(row, col).toString();
                        table.addCell(new PdfPCell(new Phrase(cellData, cellFont)));
                    }
                }

                // Tambahkan tabel ke dokumen
                document.add(table);

                // Tutup dokumen
                document.close();

                JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke PDF: " + filePath);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            }
        }
    }                                            

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExportPDF;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listLokasi;
    private javax.swing.JTable tableBarang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKategori;
    private javax.swing.JTextField txtLokasi;
    private javax.swing.JTextField txtNamaBarang;
    // End of variables declaration                   
}



```

## DatabaseConnection.java

```java

package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/db_inventaris";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi ke database berhasil!");
        } catch (SQLException e) {
            System.err.println("Koneksi ke database gagal!");
            e.printStackTrace();
        }
        return connection;
    }
}



```

## UserDAO.java

```java


package dao;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Return true jika ada hasil
            }
        }
    }
}



```

## User.java

```java

package model;

public class User {

    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


```

## Barang.java

```java

package model;

public class Barang {

    private int id;
    private String namaBarang;
    private String kategori;
    private int jumlah;
    private String lokasi;

    // Konstruktor
    public Barang(int id, String namaBarang, String kategori, int jumlah, String lokasi) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.lokasi = lokasi;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}


```

## BarangDAO.java

```java

package dao;

import model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    private Connection conn;

    public BarangDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void tambahBarang(Barang barang) throws SQLException {
        String sql = "INSERT INTO barang (nama_barang, kategori, jumlah, lokasi) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, barang.getNamaBarang());
            stmt.setString(2, barang.getKategori());
            stmt.setInt(3, barang.getJumlah());
            stmt.setString(4, barang.getLokasi());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Barang> getSemuaBarang() throws SQLException {
        String sql = "SELECT * FROM barang";
        List<Barang> barangList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                barangList.add(new Barang(
                        rs.getInt("id"),
                        rs.getString("nama_barang"),
                        rs.getString("kategori"),
                        rs.getInt("jumlah"),
                        rs.getString("lokasi")
                ));
            }
        }
        return barangList;
    }

    // UPDATE
    public void updateBarang(Barang barang) throws SQLException {
        String sql = "UPDATE barang SET nama_barang = ?, kategori = ?, jumlah = ?, lokasi = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, barang.getNamaBarang());
            stmt.setString(2, barang.getKategori());
            stmt.setInt(3, barang.getJumlah());
            stmt.setString(4, barang.getLokasi());
            stmt.setInt(5, barang.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void hapusBarang(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Barang> cariBarang(String keyword) throws SQLException {
        String sql = "SELECT * FROM barang WHERE nama_barang LIKE ? OR kategori LIKE ?";
        List<Barang> barangList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%"); // Filter nama barang
            stmt.setString(2, "%" + keyword + "%"); // Filter kategori barang
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    barangList.add(new Barang(
                            rs.getInt("id"),
                            rs.getString("nama_barang"),
                            rs.getString("kategori"),
                            rs.getInt("jumlah"),
                            rs.getString("lokasi")
                    ));
                }
            }
        }
        return barangList;
    }

    public List<Barang> filterByKategori(String kategori) throws SQLException {
        String sql = "SELECT * FROM barang";
        if (!kategori.equalsIgnoreCase("Semua")) { // Jika bukan "Semua", tambahkan WHERE
            sql += " WHERE kategori = ?";
        }

        List<Barang> barangList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (!kategori.equalsIgnoreCase("Semua")) {
                stmt.setString(1, kategori);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    barangList.add(new Barang(
                            rs.getInt("id"),
                            rs.getString("nama_barang"),
                            rs.getString("kategori"),
                            rs.getInt("jumlah"),
                            rs.getString("lokasi")
                    ));
                }
            }
        }
        return barangList;
    }

    public List<String> getKategoriList() throws SQLException {
        String sql = "SELECT DISTINCT kategori FROM barang";
        List<String> kategoriList = new ArrayList<>();
        kategoriList.add("Semua"); // Tambahkan "Semua" sebagai pilihan default

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                kategoriList.add(rs.getString("kategori"));
            }
        }
        return kategoriList;
    }

    public List<String> getLokasiList() throws SQLException {
        String sql = "SELECT DISTINCT lokasi FROM barang";
        List<String> lokasiList = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lokasiList.add(rs.getString("lokasi"));
            }
        }
        return lokasiList;
    }

    public List<Barang> filterByLokasi(String lokasi) throws SQLException {
        String sql = "SELECT * FROM barang WHERE lokasi = ?";
        List<Barang> barangList = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lokasi); // Filter berdasarkan lokasi
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    barangList.add(new Barang(
                            rs.getInt("id"),
                            rs.getString("nama_barang"),
                            rs.getString("kategori"),
                            rs.getInt("jumlah"),
                            rs.getString("lokasi")
                    ));
                }
            }
        }
        return barangList;
    }

}


```

## LoginPage.java

```java

package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/db_inventaris";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi ke database berhasil!");
        } catch (SQLException e) {
            System.err.println("Koneksi ke database gagal!");
            e.printStackTrace();
        }
        return connection;
    }
}


```



## Authors
Ahmad Shawity

2210010023 5B Reg Pagi Banjarmasin



