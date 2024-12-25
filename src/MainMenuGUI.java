import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {
    private ParkirManager manager;

    public MainMenuGUI() {
        manager = new ParkirManager();
        setTitle("Sistem Manajemen Parkir Kampus");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Panel utama
        JPanel panelText = new JPanel();
        panelText.setLayout(new BorderLayout());

        // Panel untuk tombol menu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10)); // 8 baris, 1 kolom dengan jarak 10px

        // Judul aplikasi
        JLabel label = new JLabel("Aplikasi Managemen Parkir Kampus", JLabel.CENTER); 
        label.setFont(new Font("Arial", Font.BOLD, 20)); 
        panelText.add(label, BorderLayout.NORTH);

        // Tombol-tombol menu
        JButton btnRegistrasi = new JButton("1. Registrasi Kendaraan");
        JButton btnUpdate = new JButton("2. Update Data Kendaraan");
        JButton btnDelete = new JButton("3. Delete Data Kendaraan");
        JButton btnTampilkan = new JButton("4. Tampilkan Data Kendaraan");
        JButton btnMasuk = new JButton("5. Simulasi Masuk Parkir");
        JButton btnKeluar = new JButton("6. Simulasi Keluar Parkir");
        JButton btnLaporan = new JButton("7. Laporan Harian");
        JButton btnKeluarApp = new JButton("8. Keluar");

        // Menambahkan tombol ke panel
        panel.add(btnRegistrasi);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnTampilkan);
        panel.add(btnMasuk);
        panel.add(btnKeluar);
        panel.add(btnLaporan);
        panel.add(btnKeluarApp);

        // Menambahkan panel ke frame
        add(panelText, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // ActionListener pada setiap tombol
        btnRegistrasi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrasiKendaraanGUI();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateKendaraanGUI();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteKendaraanGUI();
            }
        });

        btnTampilkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.tampilkanDataGUI();
            }
        });

        btnMasuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulasiMasukParkirGUI();
            }
        });

        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulasiKeluarParkirGUI();
            }
        });

        btnLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.tampilkanLaporan();
            }
        });

        btnKeluarApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void registrasiKendaraanGUI() {
        JTextField nomorField = new JTextField();
        JTextField jenisField = new JTextField();
        JTextField kategoriField = new JTextField();
        JTextField namaField = new JTextField();
        Object[] message = {
            "Nomor Kendaraan:", nomorField,
            "Jenis Kendaraan (Mobil/Motor):", jenisField,
            "Kategori Pemilik (Mahasiswa/Dosen/Tamu):", kategoriField,
            "Nama Pemilik Kendaraan:", namaField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Registrasi Kendaraan", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nomor = nomorField.getText().trim();
            String jenis = jenisField.getText().trim();
            String kategori = kategoriField.getText().trim();
            String nama = namaField.getText().trim();

            if (nomor.isEmpty() || jenis.isEmpty() || kategori.isEmpty() || nama.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Kendaraan kendaraan;
            switch (kategori.toLowerCase()) {
                case "mahasiswa":
                    kendaraan = new Mahasiswa(nomor, jenis, nama);
                    break;
                case "dosen":
                    kendaraan = new Dosen(nomor, jenis, nama);
                    break;
                case "tamu":
                    kendaraan = new Tamu(nomor, jenis, nama);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Kategori tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            manager.registrasiKendaraan(kendaraan);
            JOptionPane.showMessageDialog(null, "Kendaraan berhasil diregistrasi: " + kendaraan.getNomorKendaraan(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateKendaraanGUI() {
        JTextField namaField = new JTextField();
        JTextField nomorField = new JTextField();
        Object[] searchMessage = {
            "Nama Pemilik:", namaField,
            "Nomor Kendaraan:", nomorField
        };

        int searchOption = JOptionPane.showConfirmDialog(null, searchMessage, "Cari Kendaraan untuk Update", JOptionPane.OK_CANCEL_OPTION);
        if (searchOption == JOptionPane.OK_OPTION) {
            String namaPemilik = namaField.getText().trim();
            String nomorKendaraan = nomorField.getText().trim();

            if (namaPemilik.isEmpty() || nomorKendaraan.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama pemilik dan nomor kendaraan harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Kendaraan kendaraan = manager.findKendaraan(namaPemilik, nomorKendaraan);
            if (kendaraan == null) {
                JOptionPane.showMessageDialog(null, "Data kendaraan tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField newNomorField = new JTextField(kendaraan.getNomorKendaraan());
            JTextField newJenisField = new JTextField(kendaraan.getJenisKendaraan());
            JTextField newKategoriField = new JTextField(kendaraan.getKategori());
            JTextField newNamaField = new JTextField(kendaraan.getNamaPemilik());
            Object[] updateMessage = {
                "Update Nomor Kendaraan:", newNomorField,
                "Update Jenis Kendaraan (Mobil/Motor):", newJenisField,
                "Update Kategori Pemilik (Mahasiswa/Dosen/Tamu):", newKategoriField,
                "Update Nama Pemilik:", newNamaField
            };

            int updateOption = JOptionPane.showConfirmDialog(null, updateMessage, "Update Kendaraan", JOptionPane.OK_CANCEL_OPTION);
            if (updateOption == JOptionPane.OK_OPTION) {
                String newNomor = newNomorField.getText().trim();
                String newJenis = newJenisField.getText().trim();
                String newKategori = newKategoriField.getText().trim();
                String newNama = newNamaField.getText().trim();

                if (newNomor.isEmpty() || newJenis.isEmpty() || newKategori.isEmpty() || newNama.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Kendaraan updatedKendaraan;
                switch (newKategori.toLowerCase()) {
                    case "mahasiswa":
                        updatedKendaraan = new Mahasiswa(newNomor, newJenis, newNama);
                        break;
                    case "dosen":
                        updatedKendaraan = new Dosen(newNomor, newJenis, newNama);
                        break;
                    case "tamu":
                        updatedKendaraan = new Tamu(newNomor, newJenis, newNama);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Kategori tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                updatedKendaraan.setIdKendaraan(kendaraan.getIdKendaraan());
                manager.updateKendaraan(namaPemilik, nomorKendaraan, updatedKendaraan);
                JOptionPane.showMessageDialog(null, "Data kendaraan berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void deleteKendaraanGUI() {
        JTextField namaField = new JTextField();
        JTextField nomorField = new JTextField();
        Object[] deleteMessage = {
            "Nama Pemilik:", namaField,
            "Nomor Kendaraan:", nomorField
        };

        int deleteOption = JOptionPane.showConfirmDialog(null, deleteMessage, "Hapus Kendaraan", JOptionPane.OK_CANCEL_OPTION);
        if (deleteOption == JOptionPane.OK_OPTION) {
            String namaPemilik = namaField.getText().trim();
            String nomorKendaraan = nomorField.getText().trim();

            if (namaPemilik.isEmpty() || nomorKendaraan.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama pemilik dan nomor kendaraan harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Kendaraan kendaraan = manager.findKendaraan(namaPemilik, nomorKendaraan);
            if (kendaraan == null) {
                JOptionPane.showMessageDialog(null, "Data kendaraan tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus kendaraan ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteKendaraan(namaPemilik, nomorKendaraan);
                JOptionPane.showMessageDialog(null, "Data kendaraan berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void simulasiMasukParkirGUI() {
        JTextField nomorField = new JTextField();
        JTextField namaField = new JTextField();
        JTextField jenisField = new JTextField("Mobil"); // Asumsi jenis kendaraan
        Object[] masukMessage = {
            "Nomor Kendaraan:", nomorField,
            "Nama Pemilik:", namaField,
            "Jenis Kendaraan (Mobil/Motor):", jenisField
        };

        int option = JOptionPane.showConfirmDialog(null, masukMessage, "Simulasi Masuk Parkir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nomor = nomorField.getText().trim();
            String namaPemilik = namaField.getText().trim();
            String jenis = jenisField.getText().trim();

            if (nomor.isEmpty() || namaPemilik.isEmpty() || jenis.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Asumsi kategori berdasarkan database atau logika lain
            // Di sini kita harus mencari kategori dari kendaraan terdaftar
            Kendaraan kendaraan = manager.findKendaraan(namaPemilik, nomor);
            if (kendaraan == null) {
                JOptionPane.showMessageDialog(null, "Kendaraan belum diregistrasi.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            manager.kendaraanMasuk(kendaraan);
            JOptionPane.showMessageDialog(null, "Kendaraan berhasil masuk ke parkir.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void simulasiKeluarParkirGUI() {
        JTextField nomorField = new JTextField();
        JTextField namaField = new JTextField();
        Object[] keluarMessage = {
            "Nomor Kendaraan:", nomorField,
            "Nama Pemilik:", namaField
        };

        int option = JOptionPane.showConfirmDialog(null, keluarMessage, "Simulasi Keluar Parkir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nomor = nomorField.getText().trim();
            String namaPemilik = namaField.getText().trim();

            if (nomor.isEmpty() || namaPemilik.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Kendaraan kendaraan = manager.findKendaraan(namaPemilik, nomor);
            if (kendaraan == null) {
                JOptionPane.showMessageDialog(null, "Kendaraan tidak ditemukan di parkir.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            manager.kendaraanKeluar(kendaraan);
            JOptionPane.showMessageDialog(null, "Kendaraan berhasil keluar dari parkir.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuGUI gui = new MainMenuGUI();
            gui.setVisible(true);
        });
    }
}