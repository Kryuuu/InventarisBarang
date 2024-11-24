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
