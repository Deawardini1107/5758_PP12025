package PrakPro1;

import java.sql.*;

public class GraphDB {

    // Tambah gudang baru jika belum ada
    public void tambahGudang(String namaGudang) {
        if (isEmpty(namaGudang)) {
            System.out.println("Nama gudang tidak boleh kosong!");
            return;
        }
        try (Connection conn = Database.connect()) {
            if (gudangSudahAda(namaGudang, conn)) {
                System.out.println("Gudang \"" + namaGudang + "\" sudah ada!");
                return;
            }
            String sql = "INSERT INTO gudang (nama) VALUES (?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, namaGudang);
                ps.executeUpdate();
                System.out.println("Gudang \"" + namaGudang + "\" berhasil ditambahkan.");
            }
        } catch (SQLException e) {
            System.out.println("Error tambahGudang: " + e.getMessage());
        }
    }

    // Tampilkan semua gudang
    public void tampilkanGudang() {
        try (Connection conn = Database.connect()) {
            String sql = "SELECT nama FROM gudang ORDER BY nama";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("Daftar Gudang:");
                int nomor = 1;
                while (rs.next()) {
                    System.out.println(nomor++ + ". " + rs.getString("nama"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error tampilkanGudang: " + e.getMessage());
        }
    }

    // Update nama gudang jika baru belum ada
    public void updateGudang(String namaLama, String namaBaru) {
        if (isEmpty(namaBaru)) {
            System.out.println("Nama gudang baru tidak boleh kosong!");
            return;
        }
        try (Connection conn = Database.connect()) {
            if (gudangSudahAda(namaBaru, conn)) {
                System.out.println("Gudang \"" + namaBaru + "\" sudah ada!");
                return;
            }
            String sql = "UPDATE gudang SET nama = ? WHERE nama = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, namaBaru);
                ps.setString(2, namaLama);
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    System.out.println("Gudang berhasil diupdate dari \"" + namaLama + "\" ke \"" + namaBaru + "\".");
                } else {
                    System.out.println("Gudang \"" + namaLama + "\" tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updateGudang: " + e.getMessage());
        }
    }

    // Hapus gudang
    public void hapusGudang(String namaGudang) {
        try (Connection conn = Database.connect()) {
            String sql = "DELETE FROM gudang WHERE nama = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, namaGudang);
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    System.out.println("Gudang \"" + namaGudang + "\" berhasil dihapus.");
                } else {
                    System.out.println("Gudang \"" + namaGudang + "\" tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error hapusGudang: " + e.getMessage());
        }
    }

    // Tambah penerimaan antar gudang
    public void tambahPenerimaan(String dari, String ke) {
        if (isEmpty(dari) || isEmpty(ke)) {
            System.out.println("Nama gudang asal dan tujuan tidak boleh kosong!");
            return;
        }
        try (Connection conn = Database.connect()) {
            if (!gudangSudahAda(dari, conn)) {
                System.out.println("Gudang asal \"" + dari + "\" tidak ditemukan.");
                return;
            }
            if (!gudangSudahAda(ke, conn)) {
                System.out.println("Gudang tujuan \"" + ke + "\" tidak ditemukan.");
                return;
            }
            String sql = "INSERT INTO penerimaan (dari, ke) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, dari);
                ps.setString(2, ke);
                ps.executeUpdate();
                System.out.println("Penerimaan dari \"" + dari + "\" ke \"" + ke + "\" berhasil dicatat.");
            }
        } catch (SQLException e) {
            System.out.println("Error tambahPenerimaan: " + e.getMessage());
        }
    }

    // Tampilkan semua data penerimaan
    public void tampilkanPenerimaan() {
        try (Connection conn = Database.connect()) {
            String sql = "SELECT dari, ke FROM penerimaan";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("Daftar Penerimaan Barang:");
                int nomor = 1;
                while (rs.next()) {
                    System.out.println(nomor++ + ". Dari: " + rs.getString("dari") + "  Ke: " + rs.getString("ke"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error tampilkanPenerimaan: " + e.getMessage());
        }
    }

    // Hapus penerimaan tertentu
    public void hapusPenerimaan(String dari, String ke) {
        try (Connection conn = Database.connect()) {
            String sql = "DELETE FROM penerimaan WHERE dari = ? AND ke = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, dari);
                ps.setString(2, ke);
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    System.out.println("Penerimaan barang berhasil dihapus.");
                } else {
                    System.out.println("Penerimaan barang tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error hapusPenerimaan: " + e.getMessage());
        }
    }

    // Cek apakah gudang sudah ada
    private boolean gudangSudahAda(String nama, Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM gudang WHERE nama = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Cek input kosong
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
