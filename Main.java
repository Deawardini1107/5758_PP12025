package PrakPro1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GraphDB db = new GraphDB();

        while (true) {
            tampilkanMenuTabel();
            System.out.print("Pilih menu: ");

            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    System.out.print("Masukkan nama gudang: ");
                    String namaGudang = input.nextLine();
                    db.tambahGudang(namaGudang);
                    break;
                case "2":
                    db.tampilkanGudang();
                    break;
                case "3":
                    System.out.print("Masukkan nama gudang lama: ");
                    String namaLama = input.nextLine();
                    System.out.print("Masukkan nama gudang baru: ");
                    String namaBaru = input.nextLine();
                    db.updateGudang(namaLama, namaBaru);
                    break;
                case "4":
                    System.out.print("Masukkan nama gudang yang ingin dihapus: ");
                    String namaHapus = input.nextLine();
                    db.hapusGudang(namaHapus);
                    break;
                case "5":
                    System.out.print("Masukkan nama gudang asal (dari): ");
                    String dari = input.nextLine();
                    System.out.print("Masukkan nama gudang tujuan (ke): ");
                    String ke = input.nextLine();
                    db.tambahPenerimaan(dari, ke);
                    break;
                case "6":
                    db.tampilkanPenerimaan();
                    break;
                case "7":
                    db.tampilkanPenerimaan();
                    System.out.print("Masukkan ID penerimaan yang ingin dihapus: ");
                    String idStr = input.nextLine();
                    try {
                        int id = Integer.parseInt(idStr);
                        db.hapusPenerimaanById(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Input ID tidak valid!");
                    }
                    break;
                case "8":
                    System.out.print("Masukkan kata kunci pencarian gudang: ");
                    String keyword = input.nextLine();
                    db.cariGudangDanPengiriman(keyword);
                    break;
                case "0":
                    System.out.println("Terima kasih. Program selesai.");
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void tampilkanMenuTabel() {
        System.out.println("+======+===============================+");
        System.out.println("| No   | Menu                          |");
        System.out.println("+======+===============================+");
        System.out.println("| 1    | Tambah Gudang                 |");
        System.out.println("| 2    | Tampilkan Gudang              |");
        System.out.println("| 3    | Update Gudang                 |");
        System.out.println("| 4    | Hapus Gudang                  |");
        System.out.println("| 5    | Tambah Penerimaan             |");
        System.out.println("| 6    | Tampilkan Penerimaan          |");
        System.out.println("| 7    | Hapus Penerimaan (by ID)      |");
        System.out.println("| 8    | Cari Gudang dan Pengiriman    |");
        System.out.println("| 0    | Keluar                        |");
        System.out.println("+======+===============================+");
    }
}


