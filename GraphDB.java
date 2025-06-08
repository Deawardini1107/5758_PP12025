package PrakPro1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GraphDB {

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

    public void tampilkanGudang() {
        try (Connection conn = Database.connect()) {
            String sql = "SELECT nama FROM gudang ORDER BY nama";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("+====+========================+");
                System.out.println("| No | Nama Gudang            |");
                System.out.println("+====+========================+");
                int nomor = 1;
                while (rs.next()) {
                    System.out.printf("| %-2d | %-22s |\n", nomor++, rs.getString("nama"));
                }
                System.out.println("+====+========================+");
            }
        } catch (SQLException e) {
            System.out.println("Error tampilkanGudang: " + e.getMessage());
        }
    }

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

    // Modifikasi tampilkan penerimaan agar ada kolom ID
    public void tampilkanPenerimaan() {
        try (Connection conn = Database.connect()) {
            String sql = "SELECT id, dari, ke FROM penerimaan";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("+====+========================+========================+");
                System.out.println("| ID | Dari                   | Ke                     |");
                System.out.println("+====+========================+========================+");
                while (rs.next()) {
                    System.out.printf("| %-2d | %-22s | %-22s |\n",
                            rs.getInt("id"),
                            rs.getString("dari"),
                            rs.getString("ke"));
                }
                System.out.println("+====+========================+========================+");
            }
        } catch (SQLException e) {
            System.out.println("Error tampilkanPenerimaan: " + e.getMessage());
        }
    }

    // Fungsi hapus penerimaan berdasarkan ID
    public void hapusPenerimaanById(int id) {
        try (Connection conn = Database.connect()) {
            String sql = "DELETE FROM penerimaan WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    System.out.println("Penerimaan dengan ID " + id + " berhasil dihapus.");
                } else {
                    System.out.println("Penerimaan dengan ID " + id + " tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error hapusPenerimaanById: " + e.getMessage());
        }
    }

    // (Optional) tetap bisa pakai ini kalau kamu mau, tapi sebaiknya hapus agar tidak rancu
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

    public void cariGudangDanPengiriman(String keyword) {
        if (isEmpty(keyword)) {
            System.out.println("Kata kunci tidak boleh kosong.");
            return;
        }

        try (Connection conn = Database.connect()) {
            String sql = "SELECT nama FROM gudang WHERE nama LIKE ? ORDER BY nama";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + keyword + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    List<String[]> dataTabel = new ArrayList<>();
                    int nomor = 1;

                    while (rs.next()) {
                        String namaGudang = rs.getString("nama");
                        String kirimKe = ambilDaftarTujuan(namaGudang, conn);
                        String terimaDari = ambilDaftarAsal(namaGudang, conn);
                        dataTabel.add(new String[]{String.valueOf(nomor++), namaGudang, kirimKe, terimaDari});
                    }

                    if (dataTabel.isEmpty()) {
                        System.out.println("Tidak ditemukan gudang yang cocok dengan kata kunci \"" + keyword + "\".");
                        return;
                    }

                    System.out.println("+======+==========================+==========================+==========================+");
                    System.out.println("| No   | Nama Gudang              | Mengirim ke              | Menerima dari            |");
                    System.out.println("+======+==========================+==========================+==========================+");

                    for (String[] row : dataTabel) {
                        System.out.printf("| %-4s | %-24s | %-24s | %-24s |\n",
                                row[0], row[1], row[2], row[3]);
                    }

                    System.out.println("+======+==========================+==========================+==========================+");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error cariGudangDanPengiriman: " + e.getMessage());
        }
    }

    private String ambilDaftarTujuan(String dari, Connection conn) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT ke FROM penerimaan WHERE dari = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dari);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(rs.getString("ke"));
                }
            }
        }
        return sb.length() == 0 ? "-" : sb.toString();
    }

    private String ambilDaftarAsal(String ke, Connection conn) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT dari FROM penerimaan WHERE ke = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ke);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(rs.getString("dari"));
                }
            }
        }
        return sb.length() == 0 ? "-" : sb.toString();
    }

    private boolean gudangSudahAda(String nama, Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM gudang WHERE nama = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
