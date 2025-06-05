package PrakPro1;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GraphDB graph = new GraphDB();

    public static void main(String[] args) {
        Database.init();
        while (true) {
            tampilkanMenu();
            int pilihan = bacaPilihan();

            switch (pilihan) {
                case 1 -> tambahPenerimaan();
                case 2 -> tambahGudang();
                case 3 -> graph.tampilkanGudang();
                case 4 -> updateGudang();
                case 5 -> hapusGudang();
                case 6 -> graph.tampilkanPenerimaan();
                case 7 -> hapusPenerimaan();
                case 8 -> {
                    System.out.println("Terima kasih!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== Menu Pencatatan Penerimaan Barang =====");
        System.out.println("1. Tambah Penerimaan Barang");
        System.out.println("2. Tambah Gudang");
        System.out.println("3. Tampilkan Semua Gudang");
        System.out.println("4. Edit Nama Gudang");
        System.out.println("5. Hapus Gudang");
        System.out.println("6. Tampilkan Semua Penerimaan Barang");
        System.out.println("7. Hapus Penerimaan Barang");
        System.out.println("8. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static int bacaPilihan() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void tambahPenerimaan() {
        System.out.print("Masukkan nama gudang asal: ");
        String dari = scanner.nextLine();
        System.out.print("Masukkan nama gudang tujuan: ");
        String ke = scanner.nextLine();
        graph.tambahPenerimaan(dari, ke);
    }

    private static void tambahGudang() {
        System.out.print("Masukkan nama gudang baru: ");
        String namaBaru = scanner.nextLine();
        graph.tambahGudang(namaBaru);
    }

    private static void updateGudang() {
        System.out.print("Masukkan nama gudang lama: ");
        String namaLama = scanner.nextLine();
        System.out.print("Masukkan nama gudang baru: ");
        String namaBaru = scanner.nextLine();
        graph.updateGudang(namaLama, namaBaru);
    }

    private static void hapusGudang() {
        System.out.print("Masukkan nama gudang yang akan dihapus: ");
        String nama = scanner.nextLine();
        graph.hapusGudang(nama);
    }

    private static void hapusPenerimaan() {
        System.out.print("Masukkan nama gudang asal: ");
        String dari = scanner.nextLine();
        System.out.print("Masukkan nama gudang tujuan: ");
        String ke = scanner.nextLine();
        graph.hapusPenerimaan(dari, ke);
    }
}
