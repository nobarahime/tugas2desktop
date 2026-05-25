import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static Menu[] daftarMenu = new Menu[100];
    static int jumlahMenu = 8;

    static Menu[] pesanan = new Menu[100];
    static int[] qty = new int[100];
    static int jumlahPesanan = 0;

    public static void main(String[] args) {

        // MENU AWAL
        daftarMenu[0] = new Menu("Nasi Goreng", 25000, "Makanan");
        daftarMenu[1] = new Menu("Mie Ayam", 20000, "Makanan");
        daftarMenu[2] = new Menu("Ayam Geprek", 30000, "Makanan");
        daftarMenu[3] = new Menu("Sate Ayam", 35000, "Makanan");

        daftarMenu[4] = new Menu("Es Teh", 10000, "Minuman");
        daftarMenu[5] = new Menu("Jus Jeruk", 15000, "Minuman");
        daftarMenu[6] = new Menu("Kopi", 18000, "Minuman");
        daftarMenu[7] = new Menu("Milkshake", 20000, "Minuman");

        menuUtama();
    }

    static void menuUtama() {
        int pilih;

        do {
            System.out.println("\n===== SELAMAT DATANG DI RESTORAN =====");
            System.out.println("1. Pesan Makanan/Minuman");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Ketik angka pilihan :");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    prosesPemesanan();
                    break;
                case 2:
                    manajemenMenu();
                    break;
                case 3:
                    System.out.println("Terima kasih telah berkunjung!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 3);
    }

    static void tampilkanMenu() {

        System.out.println("\n===== MENU MAKANAN =====");

        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equalsIgnoreCase("Makanan")) {
                System.out.println(daftarMenu[i].nama + " - Rp " + daftarMenu[i].harga);
            }
        }

        System.out.println("\n===== MENU MINUMAN =====");

        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equalsIgnoreCase("Minuman")) {
                System.out.println(daftarMenu[i].nama + " - Rp " + daftarMenu[i].harga);
            }
        }
    }

    // PROSES PEMESANAN
    static void prosesPemesanan() {

        jumlahPesanan = 0;

        tampilkanMenu();

        while (true) {
            if (jumlahPesanan >= 4) {
                break;
            }

            System.out.println("\nAnda dapat memesan maksimal 4 item.");
            System.out.print("\nMasukkan nama menu atau ketik 'selesai' untuk mengakhiri pesanan/): ");
            String namaMenu = input.nextLine();

            if (namaMenu.equalsIgnoreCase("selesai")) {
                break;
            }

            int index = cariMenu(namaMenu);

            if (index == -1) {
                System.out.println("Menu tidak ditemukan!");
                continue;
            }

            System.out.print("Jumlah pesanan: ");
            int jumlah = input.nextInt();
            input.nextLine();

            pesanan[jumlahPesanan] = daftarMenu[index];
            qty[jumlahPesanan] = jumlah;

            jumlahPesanan++;

            System.out.println("Pesanan berhasil ditambahkan.");
        }

        cetakStruk();
    }

    // CARI MENU
    static int cariMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].nama.equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }

    // CETAK STRUK
    static void cetakStruk() {

        System.out.println("\n========== STRUK ==========");

        int totalAwal = 0;
        for (int i = 0; i < jumlahPesanan; i++) {
            int subtotal = pesanan[i].harga * qty[i];
            System.out.println(pesanan[i].nama + " x" + qty[i] + " = Rp " + subtotal);
            totalAwal += subtotal;
        }

        // DISKON
        double diskon = 0;

        if (totalAwal > 100000) {
            diskon = totalAwal * 0.10;
            System.out.println("Diskon 10% = Rp " + (int) diskon);
        }

        // B1G1 MINUMAN
        if (totalAwal > 50000) {
            System.out.println("Anda mendapatkan Promo Buy 1 Get 1!");
            for (int i = 0; i < jumlahPesanan; i++) {
                if (pesanan[i].kategori.equalsIgnoreCase("Minuman")) {
                    int bonus = qty[i];

                    System.out.println(pesanan[i].nama + " x" + bonus + " (Gratis)");
                }
            }
        }

        double totalSetelahDiskon = totalAwal - diskon;
        double pajak = totalSetelahDiskon * 0.10;
        int pelayanan = 20000;
        double totalAkhir = totalSetelahDiskon + pajak + pelayanan;

        System.out.println("----------------------------");
        System.out.println("Total Awal      : Rp " + totalAwal);
        System.out.println("Pajak 10%       : Rp " + (int) pajak);
        System.out.println("Biaya Pelayanan : Rp " + pelayanan);
        System.out.println("----------------------------");
        System.out.println("TOTAL BAYAR     : Rp " + (int) totalAkhir);
        System.out.println("============================");
    }

    // MENU MANAJEMEN
    static void manajemenMenu() {
        int pilih;

        do {
            System.out.println("\n===== MANAJEMEN MENU =====");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHarga();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    System.out.println("Kembali...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 4);
    }

    // TAMBAH MENU
    static void tambahMenu() {
        char lagi;

        do {
            System.out.print("Nama menu: ");
            String nama = input.nextLine();

            System.out.print("Harga: ");
            int harga = input.nextInt();
            input.nextLine();

            System.out.print("Kategori (Makanan/Minuman): ");
            String kategori = input.nextLine();

            daftarMenu[jumlahMenu] = new Menu(nama, harga, kategori);

            jumlahMenu++;

            System.out.print("Tambah menu lagi? (y/t): ");
            lagi = input.next().charAt(0);
            input.nextLine();

        } while (lagi == 'y' || lagi == 'Y');
    }

    // UBAH HARGA
    static void ubahHarga() {
        tampilkanSemuaMenu();

        System.out.print("Pilih nomor menu: ");
        int nomor = input.nextInt();
        input.nextLine();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ingin mengubah? (Y/T): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Y")) { 
            System.out.print("Harga baru: ");
            int hargaBaru = input.nextInt();
            input.nextLine();

            daftarMenu[nomor - 1].harga = hargaBaru;

            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    // HAPUS MENU
    static void hapusMenu() {
        tampilkanSemuaMenu();

        System.out.print("Pilih nomor menu yang dihapus: ");
        int nomor = input.nextInt();
        input.nextLine();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ingin menghapus? (Y/T): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Y")) {
            for (int i = nomor - 1; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            jumlahMenu--;

            System.out.println("Menu berhasil dihapus.");
        } else if (konfirmasi.equalsIgnoreCase("T")) {
            System.out.println("Penghapusan dibatalkan.");
        } else {
            System.out.println("Tidak Valid.");
        }
    }

    // TAMPIL SEMUA MENU
    static void tampilkanSemuaMenu() {

        System.out.println("\n===== DAFTAR MENU =====");

        for (int i = 0; i < jumlahMenu; i++) {
            System.out.println((i + 1) + ". " + daftarMenu[i].nama + " | " +
                            daftarMenu[i].kategori + " | Rp " + daftarMenu[i].harga);
        }
    }
}