/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controller;

import model.Buku;
import model.KategoriBuku;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import dao.BukuDAO;
import dao.KategoriBukuDAO;

public class MainController {

    @FXML private TextField kodeBukuField;
    @FXML private ComboBox<KategoriBuku> kategoriComboBox;
    @FXML private TextField judulField;
    @FXML private TextField pengarangField;
    @FXML private TextField penerbitField;
    @FXML private TextField tahunTerbitField;
    @FXML private TextField edisiField;
    @FXML private DatePicker tanggalPengadaanPicker;

    @FXML private TableView<Buku> bukuTable;
    @FXML private TableColumn<Buku, String> kodeBukuColumn;
    @FXML private TableColumn<Buku, String> kodeKategoriColumn;
    @FXML private TableColumn<Buku, String> namaKategoriColumn;
    @FXML private TableColumn<Buku, String> judulColumn;
    @FXML private TableColumn<Buku, String> pengarangColumn;
    @FXML private TableColumn<Buku, String> penerbitColumn;
    @FXML private TableColumn<Buku, Integer> tahunTerbitColumn;
    @FXML private TableColumn<Buku, Integer> edisiColumn;
    @FXML private TableColumn<Buku, LocalDate> tanggalPengadaanColumn;

    private ObservableList<Buku> bukuList = FXCollections.observableArrayList();
    private ObservableList<KategoriBuku> kategoriList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        kodeBukuColumn.setCellValueFactory(new PropertyValueFactory<>("kodeBuku"));
        kodeKategoriColumn.setCellValueFactory(new PropertyValueFactory<>("kodeKategori"));
        namaKategoriColumn.setCellValueFactory(new PropertyValueFactory<>("namaKategori"));
        judulColumn.setCellValueFactory(new PropertyValueFactory<>("judul"));
        pengarangColumn.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
        penerbitColumn.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        tahunTerbitColumn.setCellValueFactory(new PropertyValueFactory<>("tahunTerbit"));
        edisiColumn.setCellValueFactory(new PropertyValueFactory<>("edisi"));
        tanggalPengadaanColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPengadaan"));

        try {
            kategoriList.setAll(KategoriBukuDAO.getAllKategori());
            kategoriComboBox.setItems(kategoriList);
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data kategori: " + e.getMessage());
        }

        bukuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedBuku) -> {
            if (selectedBuku != null) {
                kodeBukuField.setText(selectedBuku.getKodeBuku());
                kodeBukuField.setDisable(true); // Disable pengeditan
                judulField.setText(selectedBuku.getJudul());
                pengarangField.setText(selectedBuku.getPengarang());
                penerbitField.setText(selectedBuku.getPenerbit());
                tahunTerbitField.setText(String.valueOf(selectedBuku.getTahunTerbit()));
                edisiField.setText(String.valueOf(selectedBuku.getEdisi()));
                tanggalPengadaanPicker.setValue(selectedBuku.getTanggalPengadaan());

                for (KategoriBuku kategori : kategoriList) {
                    if (kategori.getKodeKategori().equals(selectedBuku.getKodeKategori())) {
                        kategoriComboBox.setValue(kategori);
                        break;
                    }
                }
            }
        });

        kodeBukuField.setOnMouseClicked(e -> {
            if (kodeBukuField.isDisabled()) {
                showAlert("Tidak Bisa Diubah", "Kode Buku tidak bisa diubah.");
            }
        });

        kodeBukuField.setOnKeyTyped(e -> {
            if (kodeBukuField.isDisabled()) {
                showAlert("Tidak Bisa Diubah", "Kode Buku tidak bisa diubah.");
                e.consume();
            }
        });

        loadData();
    }

    private void loadData() {
        try {
            bukuList.setAll(BukuDAO.getAllBuku());
            bukuTable.setItems(bukuList);
        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleTambah() {
        if (isInputValid()) {
            if (!showConfirmation("Konfirmasi Tambah", "Apakah Anda akan tetap menambahkan data ini?")) return;

            Buku buku = getInputBuku();
            try {
                BukuDAO.insertBuku(buku);
                loadData();
                clearForm();
                showInfo("Sukses", "Data berhasil ditambahkan.");
            } catch (SQLException e) {
                showAlert("Insert Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleUbah() {
        Buku selected = bukuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilihan Kosong", "Pilih data terlebih dahulu untuk diubah.");
            return;
        }

        if (!selected.getKodeBuku().equals(kodeBukuField.getText())) {
            showAlert("Validasi", "Kode Buku tidak bisa diubah.");
            return;
        }

        if (isInputValid()) {
            if (!showConfirmation("Konfirmasi Ubah", "Apakah Anda tetap ingin memperbarui data ini?")) return;

            Buku buku = getInputBuku();
            try {
                BukuDAO.updateBuku(buku);
                loadData();
                clearForm();
                showInfo("Sukses", "Data berhasil diperbarui.");
            } catch (SQLException e) {
                showAlert("Update Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleHapus() {
        Buku selected = bukuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilihan Kosong", "Pilih data terlebih dahulu untuk dihapus.");
            return;
        }

        if (!showConfirmation("Konfirmasi Hapus", "Apakah Anda ingin tetap menghapus data ini?")) return;

        try {
            BukuDAO.deleteBuku(selected.getKodeBuku());
            loadData();
            clearForm();
            showInfo("Sukses", "Data berhasil dihapus.");
        } catch (SQLException e) {
            showAlert("Delete Error", e.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        if (showConfirmation("Konfirmasi Clear", "Apakah Anda ingin tetap menghapus semua datanya?")) {
            clearForm();
        }
    }

    private void clearForm() {
        kodeBukuField.clear();
        kodeBukuField.setDisable(false);
        kategoriComboBox.setValue(null);
        judulField.clear();
        pengarangField.clear();
        penerbitField.clear();
        tahunTerbitField.clear();
        edisiField.clear();
        tanggalPengadaanPicker.setValue(null);
    }

    private Buku getInputBuku() {
        KategoriBuku selectedKategori = kategoriComboBox.getValue();
        return new Buku(
            kodeBukuField.getText(),
            selectedKategori.getKodeKategori(),
            selectedKategori.getNamaKategori(), // Tambahan penting
            judulField.getText(),
            pengarangField.getText(),
            penerbitField.getText(),
            Integer.parseInt(tahunTerbitField.getText()),
            Integer.parseInt(edisiField.getText()),
            tanggalPengadaanPicker.getValue()
        );
    }

    private boolean isInputValid() {
    if (kodeBukuField.getText().isEmpty() || kategoriComboBox.getValue() == null ||
        judulField.getText().isEmpty() || pengarangField.getText().isEmpty() ||
        penerbitField.getText().isEmpty() || tahunTerbitField.getText().isEmpty() ||
        edisiField.getText().isEmpty() || tanggalPengadaanPicker.getValue() == null) {
        showAlert("Validasi Gagal", "Harus diisi semua tidak boleh kosong.");
        return false;
    }

    if (!isNumeric(kodeBukuField.getText())) {
        showAlert("Validasi Gagal", "Kode buku harus berupa angka.");
        return false;
    }

    if (!isNumeric(tahunTerbitField.getText())) {
        showAlert("Validasi Gagal", "Tahun terbit harus berupa angka.");
        return false;
    }

    if (tahunTerbitField.getText().length() < 4) {
        showAlert("Validasi Gagal", "Tahun terbit kurang dari 4 angka, periksa kembali.");
        return false;
    }

    if (!isNumeric(edisiField.getText())) {
        showAlert("Validasi Gagal", "Edisi harus berupa angka.");
        return false;
    }

    return true;
}


    private boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}
