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
