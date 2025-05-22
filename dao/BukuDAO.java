/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ASUS
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Buku;

public class BukuDAO {

    public static List<Buku> getAllBuku() throws SQLException {
        List<Buku> list = new ArrayList<>();

        String query = """
                SELECT b.kode_buku, b.kode_kategori, b.nama_kategori,
                       b.judul, b.pengarang, b.penerbit,
                       b.tahun_terbit, b.edisi, b.tanggal_pengadaan
                FROM buku b
                """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Buku buku = new Buku(
                        rs.getString("kode_buku"),
                        rs.getString("kode_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getString("judul"),
                        rs.getString("pengarang"),
                        rs.getString("penerbit"),
                        rs.getInt("tahun_terbit"),
                        rs.getInt("edisi"),
                        rs.getDate("tanggal_pengadaan").toLocalDate()
                );
                list.add(buku);
            }
        }

        return list;
    }

    public static void insertBuku(Buku buku) throws SQLException {
        // Ambil nama_kategori berdasarkan kode_kategori
        String namaKategori = getNamaKategoriByKode(buku.getKodeKategori());
        if (namaKategori == null) {
            throw new SQLException("Nama kategori tidak ditemukan untuk kode: " + buku.getKodeKategori());
        }

        String query = """
                INSERT INTO buku 
                (kode_buku, kode_kategori, nama_kategori, judul, pengarang, penerbit, tahun_terbit, edisi, tanggal_pengadaan) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, buku.getKodeBuku());
            stmt.setString(2, buku.getKodeKategori());
            stmt.setString(3, namaKategori); // disisipkan di sini
            stmt.setString(4, buku.getJudul());
            stmt.setString(5, buku.getPengarang());
            stmt.setString(6, buku.getPenerbit());
            stmt.setInt(7, buku.getTahunTerbit());
            stmt.setInt(8, buku.getEdisi());
            stmt.setDate(9, Date.valueOf(buku.getTanggalPengadaan()));
            stmt.executeUpdate();
        }
    }

    public static void updateBuku(Buku buku) throws SQLException {
        // Ambil nama_kategori berdasarkan kode_kategori
        String namaKategori = getNamaKategoriByKode(buku.getKodeKategori());
        if (namaKategori == null) {
            throw new SQLException("Nama kategori tidak ditemukan untuk kode: " + buku.getKodeKategori());
        }

        String query = """
                UPDATE buku SET 
                kode_kategori=?, nama_kategori=?, judul=?, pengarang=?, penerbit=?, 
                tahun_terbit=?, edisi=?, tanggal_pengadaan=? 
                WHERE kode_buku=?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, buku.getKodeKategori());
            stmt.setString(2, namaKategori); // disisipkan di sini juga
            stmt.setString(3, buku.getJudul());
            stmt.setString(4, buku.getPengarang());
            stmt.setString(5, buku.getPenerbit());
            stmt.setInt(6, buku.getTahunTerbit());
            stmt.setInt(7, buku.getEdisi());
            stmt.setDate(8, Date.valueOf(buku.getTanggalPengadaan()));
            stmt.setString(9, buku.getKodeBuku());
            stmt.executeUpdate();
        }
    }

    public static void deleteBuku(String kodeBuku) throws SQLException {
        String query = "DELETE FROM buku WHERE kode_buku=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, kodeBuku);
            stmt.executeUpdate();
        }
    }

    private static String getNamaKategoriByKode(String kodeKategori) throws SQLException {
        String query = "SELECT nama_kategori FROM kategori_buku WHERE kode_kategori = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, kodeKategori);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nama_kategori");
                }
            }
        }
        return null;
    }
}
