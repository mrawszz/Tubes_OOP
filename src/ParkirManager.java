import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class ParkirManager {
    private List<Kendaraan> kendaraanTerdaftar = new ArrayList<>();
    private List<Kendaraan> kendaraanParkir = new ArrayList<>();
    private AreaParkir areaMahasiswa;
    private AreaParkir areaDosen;
    private AreaParkir areaTamu;
    private KendaraanDAO kendaraanDAO;

    // CONSTRUCTOR
    public ParkirManager() {
        areaMahasiswa = new AreaParkir("Area Mahasiswa", 50, 100, kendaraanTerdaftar);
        areaDosen = new AreaParkir("Area Dosen", 20, 50, kendaraanTerdaftar);
        areaTamu = new AreaParkir("Area Tamu", 10, 20, kendaraanTerdaftar);

        kendaraanDAO = new KendaraanDAO(); // Inisialisasi DAO
        kendaraanTerdaftar = kendaraanDAO.getAllKendaraan(); // memuat data dari DB
    }

    // METHOD UNTUK REGISTRASI KENDARAAN
    public void registrasiKendaraan(Kendaraan kendaraan) {
        kendaraanDAO.addKendaraan(kendaraan); // Menggunakan kendaraanDAO
        kendaraanTerdaftar.add(kendaraan);
        System.out.println("Kendaraan berhasil diregistrasi: " + kendaraan.getNomorKendaraan());
    }

    // METHOD UNTUK KENDARAAN MASUK KE AREA PARKIR
    public void kendaraanMasuk(Kendaraan kendaraan) {
        AreaParkir area = getAreaParkir(kendaraan);
        if (area != null && area.parkirMasuk(kendaraan)) {
            kendaraanParkir.add(kendaraan);
            System.out.println("Kendaraan berhasil masuk ke area " + area.getStatus());
        } else {
            System.out.println("Tidak ada slot parkir tersedia untuk kendaraan ini.");
        }
    }

    // METHOD UNTUK KENDARAAN KELUAR DARI AREA PARKIR
    public void kendaraanKeluar(Kendaraan kendaraan) {
        AreaParkir area = getAreaParkir(kendaraan);
        if (area != null && area.parkirKeluar(kendaraan)) {
            kendaraanParkir.remove(kendaraan);
            System.out.println("Kendaraan berhasil keluar dari area parkir.");
        } else {
            System.out.println("Kendaraan tidak ditemukan di area parkir.");
        }
    }

    // METHOD UNTUK MENAMPILKAN LAPORAN STATUS AREA PARKIR
    public void tampilkanLaporan() {
         String[] columnNames = {"Area", "Kapasitas Tersedia"};

    // Menyusun data untuk tabel
    Object[][] data = {
        {"Mahasiswa", areaMahasiswa.getStatus()},
        {"Dosen", areaDosen.getStatus()},
        {"Tamu", areaTamu.getStatus()}
    };

    // Membuat model tabel untuk menampilkan laporan
    DefaultTableModel model = new DefaultTableModel(data, columnNames);

    // Membuat JTable dengan model yang telah dibuat
    JTable table = new JTable(model);

    // Membungkus JTable dengan JScrollPane agar dapat discroll
    JScrollPane scrollPane = new JScrollPane(table);

    // Panel untuk menampilkan tabel
    JPanel panelLaporan = new JPanel(new BorderLayout());
    panelLaporan.add(scrollPane, BorderLayout.CENTER);

    // Frame untuk menampilkan laporan
    JFrame laporanFrame = new JFrame("Laporan Harian");
    laporanFrame.setSize(400, 300); // Sesuaikan ukuran frame
    laporanFrame.setLocationRelativeTo(null); // Posisikan di tengah
    laporanFrame.add(panelLaporan);
    laporanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Menutup frame tanpa keluar dari aplikasi
    laporanFrame.setVisible(true);
    }

    // METHOD UNTUK MENDAPATKAN AREA PARKIR BERDASARKAN KATEGORI KENDARAAN
    private AreaParkir getAreaParkir(Kendaraan kendaraan) {
        switch (kendaraan.getKategori()) {
            case "Mahasiswa":
                return areaMahasiswa;
            case "Dosen":
                return areaDosen;
            case "Tamu":
                return areaTamu;
            default:
                return null;
        }
    }
    // METHOD UNTUK MENGUBAH DATA KENDARAAN
    public void updateKendaraan(String namaPemilik, String nomorKendaraan, Kendaraan updatedKendaraan) { 
    Kendaraan kendaraan = findKendaraan(namaPemilik, nomorKendaraan); 
    if (kendaraan != null) { 
        kendaraanDAO.updateKendaraan(updatedKendaraan); 

        kendaraanTerdaftar.remove(kendaraan); 
        kendaraanTerdaftar.add(updatedKendaraan); 
        System.out.println("Data kendaraan berhasil diperbarui."); 
    } else { 
        System.out.println("Data tidak ditemukan"); 
    }
}
    // METHOD UNTUK MENGHAPUS DATA KENDARAAN
    public void deleteKendaraan(String namaPemilik, String nomorKendaraan) { 
        Kendaraan kendaraan = findKendaraan(namaPemilik, nomorKendaraan); 
        if (kendaraan != null) { 
            kendaraanDAO.deleteKendaraan(kendaraan.getIdKendaraan()); 
            kendaraanTerdaftar.remove(kendaraan); 
            System.out.println("Data kendaraan berhasil dihapus."); 
        } else { 
            System.out.println("Data tidak ditemukan"); 
        }
    }

    // METHOD UNTUK MENCARI KENDARAAN BERDASARKAN NAMA PEMILIK DAN NOMOR KENDARAAN
    public Kendaraan findKendaraan(String namaPemilik, String nomorKendaraan) { 
        for (Kendaraan k : kendaraanTerdaftar) { 
            if (k.getNamaPemilik().equalsIgnoreCase(namaPemilik) && k.getNomorKendaraan().equalsIgnoreCase(nomorKendaraan)) { 
                return k; 
            }
        }
        return null; 
    } 

    // METHOD UNTUK MENAMPILKAN DATA KENDARAAN KE DALAM GUI
    public void tampilkanDataGUI() { 
        List<Kendaraan> semuaKendaraan = kendaraanDAO.getAllKendaraan(); 
        if (semuaKendaraan.isEmpty()) { 
            JOptionPane.showMessageDialog(null, "Tidak ada data kendaraan yang terdaftar.", "Info", JOptionPane.INFORMATION_MESSAGE); 
        } else { 
            SwingUtilities.invokeLater(new Runnable() { 
                public void run() { 
                    DataTableFrame frame = new DataTableFrame(semuaKendaraan); 
                    frame.setVisible(true); 
                } 
            }); 
        } 
    } 
}