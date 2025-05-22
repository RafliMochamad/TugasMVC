/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Buku {
    private String kodeBuku;
    private String kodeKategori;
    private String namaKategori;
    private String judul;
    private String pengarang;
    private String penerbit;
    private int tahunTerbit;
    private int edisi;
    private LocalDate tanggalPengadaan;


    public Buku(String kodeBuku, String kodeKategori, String judul, String pengarang, String penerbit,
                int tahunTerbit, int edisi, LocalDate tanggalPengadaan) {
        this.kodeBuku = kodeBuku;
        this.kodeKategori = kodeKategori;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.edisi = edisi;
        this.tanggalPengadaan = tanggalPengadaan;
    }


    public Buku(String kodeBuku, String kodeKategori, String namaKategori, String judul, String pengarang,
                String penerbit, int tahunTerbit, int edisi, LocalDate tanggalPengadaan) {
        this(kodeBuku, kodeKategori, judul, pengarang, penerbit, tahunTerbit, edisi, tanggalPengadaan);
        this.namaKategori = namaKategori;
    }

    // Getter dan Setter
    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public String getKodeKategori() {
        return kodeKategori;
    }

    public void setKodeKategori(String kodeKategori) {
        this.kodeKategori = kodeKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public int getEdisi() {
        return edisi;
    }

    public void setEdisi(int edisi) {
        this.edisi = edisi;
    }

    public LocalDate getTanggalPengadaan() {
        return tanggalPengadaan;
    }

    public void setTanggalPengadaan(LocalDate tanggalPengadaan) {
        this.tanggalPengadaan = tanggalPengadaan;
    }
    
}
