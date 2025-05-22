/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ASUS
 */
import dao.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.KategoriBuku;

public class KategoriBukuDAO {
    public static List<KategoriBuku> getAllKategori() throws SQLException {
        List<KategoriBuku> list = new ArrayList<>();
        String query = "SELECT * FROM kategori_buku";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                KategoriBuku kategori = new KategoriBuku(
                    rs.getString("kode_kategori"),
                    rs.getString("nama_kategori")
                );
                list.add(kategori);
            }
        }
        return list;
    }
}