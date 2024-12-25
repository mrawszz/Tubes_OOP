import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DataTableFrame extends JFrame { 
    private JTable table; 
    private DefaultTableModel tableModel; 

    public DataTableFrame(List<Kendaraan> kendaraanList) { 
        setTitle("Data Kendaraan Terdaftar"); 
        setSize(800, 400);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Membuat kolom untuk tabel 
        String[] columnNames = {"ID", "Nomor Kendaraan", "Jenis Kendaraan", "Nama Pemilik", "Kategori Pemilik"}; 

        // Inisialisasi model tabel 
        tableModel = new DefaultTableModel(columnNames, 0); 

        // Mengisi data ke model tabel 
        for (Kendaraan k : kendaraanList) { 
            Object[] rowData = { 
                k.getIdKendaraan(), 
                k.getNomorKendaraan(), 
                k.getJenisKendaraan(), 
                k.getNamaPemilik(), 
                k.getKategori() 
            }; 
            tableModel.addRow(rowData); 
        }
        // Membuat JTable dengan model 
        table = new JTable(tableModel); 

        // Menambahkan JTable ke JScrollPane
        JScrollPane scrollPane = new JScrollPane(table); 
        add(scrollPane, BorderLayout.CENTER); 
    } 
}