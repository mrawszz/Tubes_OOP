import java.util.List;

public class AreaParkir {
    private String namaArea;
    private int kapasitasMobil;
    private int kapasitasMotor;
    private int mobilTerisi = 0;
    private int motorTerisi = 0;
    private List<Kendaraan> kendaraanList;  // Menyimpan daftar kendaraan yang terdaftar (dari database)

    // CONSTRUCTOR
    public AreaParkir(String namaArea, int kapasitasMobil, int kapasitasMotor, List<Kendaraan> kendaraanList) {
        this.namaArea = namaArea;
        this.kapasitasMobil = kapasitasMobil;
        this.kapasitasMotor = kapasitasMotor;
        this.kendaraanList = kendaraanList;  
    }

    // METHOD UNTUK MASUK PARKIR
    public boolean parkirMasuk(Kendaraan kendaraan) {
        if (!kendaraanList.contains(kendaraan)) {
            System.out.println("Kendaraan tidak ditemukan.");
            return false; 
        }

        // Memeriksa jenis kendaraan dan kapasitas
        if (kendaraan.getJenisKendaraan().equalsIgnoreCase("Mobil")) {
            if (mobilTerisi < kapasitasMobil) {
                mobilTerisi++;
                return true;  // Kendaraan berhasil masuk
            }
        } else if (kendaraan.getJenisKendaraan().equalsIgnoreCase("Motor")) {
            if (motorTerisi < kapasitasMotor) {
                motorTerisi++;
                return true;  // Kendaraan berhasil masuk
            }
        }
        return false;  
    }

    // METHOD UNTUK KELUAR PARKIR
    public boolean parkirKeluar(Kendaraan kendaraan) {
        if (!kendaraanList.contains(kendaraan)) {
            System.out.println("Kendaraan tidak ditemukan.");
            return false;  
        }

        if (kendaraan.getJenisKendaraan().equalsIgnoreCase("Mobil") && mobilTerisi > 0) {
            mobilTerisi--;
            return true;  
        } else if (kendaraan.getJenisKendaraan().equalsIgnoreCase("Motor") && motorTerisi > 0) {
            motorTerisi--;
            return true;  
        }

        System.out.println("Kendaraan tidak ditemukan di area parkir.");
        return false;  
    }

    // METHOD UNTUK MENAMPILKAN STATUS AREA PARKIR
    public String getStatus() {
        return "Kapasitas Mobil : " + mobilTerisi + " dari " + kapasitasMobil + " || Kapasitas Motor: " + motorTerisi + " dari " + kapasitasMotor;
    }
}