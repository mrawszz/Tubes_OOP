public abstract class Kendaraan {
    protected int idKendaraan;
    protected String nomorKendaraan;
    protected String jenisKendaraan;
    protected String namaPemilik; 

    public Kendaraan(String nomorKendaraan, String jenisKendaraan, String namaPemilik) {
        this.nomorKendaraan = nomorKendaraan;
        this.jenisKendaraan = jenisKendaraan;
        this.namaPemilik = namaPemilik;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public int getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(int idKendaraan) { 
        this.idKendaraan = idKendaraan;
    } 

    public String getNomorKendaraan() {
        return nomorKendaraan;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public abstract String getKategori();
}

class Mahasiswa extends Kendaraan {
    public Mahasiswa(String nomorKendaraan, String jenisKendaraan, String namaPemilik) {
        super(nomorKendaraan, jenisKendaraan, namaPemilik);
    }

    @Override
    public String getKategori() {
        return "Mahasiswa";
    }
}

class Dosen extends Kendaraan {
    public Dosen(String nomorKendaraan, String jenisKendaraan, String namaPemilik) {
        super(nomorKendaraan, jenisKendaraan, namaPemilik);
    }

    @Override
    public String getKategori() {
        return "Dosen";
    }
}

class Tamu extends Kendaraan {
    public Tamu(String nomorKendaraan, String jenisKendaraan, String namaPemilik) {
        super(nomorKendaraan, jenisKendaraan, namaPemilik);
    }

    @Override
    public String getKategori() {
        return "Tamu";
    }
}