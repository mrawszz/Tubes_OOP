import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KendaraanDAO {
    private static final String URL = "jdbc:mysql://localhost:3308/data_kendaraan";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    // Membuat koneksi ke database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE
    public void addKendaraan(Kendaraan kendaraan) {
        String sql = "INSERT INTO data_kendaraan (nomor_kendaraan, jenis_kendaraan, nama_pemilik, kategori_pemilik) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            stmt.setString(1, kendaraan.getNomorKendaraan());
            stmt.setString(2, kendaraan.getJenisKendaraan());
            stmt.setString(3, kendaraan.getNamaPemilik());
            stmt.setString(4, kendaraan.getKategori());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) { 
                if (generatedKeys.next()) { 
                    kendaraan.setIdKendaraan(generatedKeys.getInt(1)); 
                }
            }
            System.out.println("Kendaraan berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Kendaraan> getAllKendaraan() {
        List<Kendaraan> list = new ArrayList<>();
        String sql = "SELECT * FROM data_kendaraan";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                int id = rs.getInt("id_kendaraan"); 
                String nomor = rs.getString("nomor_kendaraan");
                String jenis = rs.getString("jenis_kendaraan");
                String nama = rs.getString("nama_pemilik");
                String kategori = rs.getString("kategori_pemilik");

                Kendaraan kendaraan = null;
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
                        System.out.println("Kategori tidak dikenali: " + kategori);
                }

                if (kendaraan != null) {
                    kendaraan.setIdKendaraan(id); 
                    list.add(kendaraan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void updateKendaraan(Kendaraan kendaraan) {
        String sql = "UPDATE data_kendaraan SET nomor_kendaraan = ?, jenis_kendaraan = ?, nama_pemilik = ?, kategori_pemilik = ? WHERE id_kendaraan = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, kendaraan.getNomorKendaraan());
            stmt.setString(2, kendaraan.getJenisKendaraan());
            stmt.setString(3, kendaraan.getNamaPemilik());
            stmt.setString(4, kendaraan.getKategori());
            stmt.setInt(5, kendaraan.getIdKendaraan());
            stmt.executeUpdate();
            System.out.println("Kendaraan berhasil diperbarui di database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteKendaraan(int idKendaraan) {
        String sql = "DELETE FROM data_kendaraan WHERE id_kendaraan = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, idKendaraan);
            stmt.executeUpdate();
            System.out.println("Kendaraan berhasil dihapus dari database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ BY ID
    public Kendaraan getKendaraanById(int idKendaraan) {
        String sql = "SELECT * FROM data_kendaraan WHERE id_kendaraan = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, idKendaraan);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_kendaraan"); 
                    String nomor = rs.getString("nomor_kendaraan");
                    String jenis = rs.getString("jenis_kendaraan");
                    String nama = rs.getString("nama_pemilik");
                    String kategori = rs.getString("kategori_pemilik");

                    Kendaraan kendaraan = null;
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
                            System.out.println("Kategori tidak dikenali: " + kategori);
                    }

                    if (kendaraan != null) {
                        kendaraan.setIdKendaraan(id); 
                        return kendaraan;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tambahkan metode untuk mencari kendaraan berdasarkan nama pemilik dan nomor kendaraan
    public Kendaraan findKendaraan(String namaPemilik, String nomorKendaraan) { 
        String sql = "SELECT * FROM data_kendaraan WHERE nama_pemilik = ? AND nomor_kendaraan = ?"; 
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
             
            stmt.setString(1, namaPemilik);
            stmt.setString(2, nomorKendaraan); 
            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) { 
                    int id = rs.getInt("id_kendaraan"); 
                    String nomor = rs.getString("nomor_kendaraan");
                    String jenis = rs.getString("jenis_kendaraan");
                    String nama = rs.getString("nama_pemilik");
                    String kategori = rs.getString("kategori_pemilik");

                    Kendaraan kendaraan = null;
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
                            System.out.println("Kategori tidak dikenali: " + kategori); 
                    }

                    if (kendaraan != null) { 
                        kendaraan.setIdKendaraan(id); 
                        return kendaraan; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    } 
}