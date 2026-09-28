package Pekan4;
import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Rekening akunAktif = null;
		boolean isRunning = true;
		ArrayList<Rekening> DaftarRekening = new ArrayList<>();
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek infromasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6.riwayat transaksi");
			// nomor 7 challenge
			System.out.println("7. Simulasi akhir bulan");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine();
			
			switch (pilihan) {
			case 1:
				System.out.println("Masukkan No Rekening : ");
				String no = input.nextLine();
				System.out.println("Masukkan Nama Pemilik: ");
				String nama = input.nextLine();
				System.out.println("Masukkan saldo Awal  : ");
				double saldo = input.nextDouble();
				input.nextLine();
				System.out.println("inputkan String 6 digit");
				String pinn = input.nextLine();
				Rekening rekeningBaru = null;
				
				while(rekeningBaru == null) {
					System.out.println("Pilih produk : 1 Tabungan Umum | 2.Giro Bisnis");
					int input2= input.nextInt();
					if(input2 == 1) {
						System.out.println("inputkan suku bunga dalam nominal angka");
						int sukubunga = input.nextInt();
						rekeningBaru = new RekeningTabungan(no,nama,saldo,pinn, sukubunga);
					}else if (input2 == 2) {
						System.out.println("inputkan batasOvedraft dalam nominal angka");
						int batasOverdraft = input.nextInt();
						rekeningBaru = new RekeningGiro(no,nama,saldo,pinn, batasOverdraft);
					}else {
						System.out.println("pilihan tak sesuai, silahkan di ulang lagi");
					}
					
				}
				
				DaftarRekening.add(rekeningBaru);
				akunAktif = rekeningBaru;
				break;
				
			case 2:
				if(akunAktif == null) {
					System.out.println("Error : Mohon maaf, Anda belum memiliki nomor rekening!");
				} else {
					System.out.print("Masukkan nominal setor :");
					double setor = input.nextDouble();
					input.nextLine();
					System.out.println("Masukkan PIN");
					String pinsetor = input.nextLine();
					// cek PIN dari akunAktif Rekening
					
					if(akunAktif.otentifikasi(pinsetor)) {
						akunAktif.setorTunai(setor);
					}else {
						System.out.print("Akses ditolak : PIN yang Anda masukkan salah!");
					}
					
				}
				break;
			case 3:
				System.out.println("Masukkan nominal yang harus ditarik(minimal 10 ribu): ");
				double tarik =  input.nextDouble();
				input.nextLine();
				System.out.println("Masukkan PIN");
				String pintarik = input.nextLine();
				// cek PIN dari akunAktif Rekening
				
				if(akunAktif.otentifikasi(pintarik)) {
					akunAktif.TarikTunai(tarik);
				}else {
					System.out.print("Akses ditolak : PIN yang Anda masukkan salah!");
				}
				
				break;
			
			case 4:
				if(akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening");
				} else {
					akunAktif.cekInformasi();
				}
				break;
			
			case 5:
				//soal bonus dari modul
				if(DaftarRekening.isEmpty()) {
					System.out.println("belum ada rekening");
					return;
				}
				System.out.print("Masukkan nomor rekening yang ingin di aktifkan");
				String noRekening = input.nextLine();
				Rekening ditemukan = null;
				for(Rekening r : DaftarRekening) {
					if(r.getNomorRekening().equals(noRekening)) {
						ditemukan = r;
						break;
					}
				}
				if(ditemukan != null) {
					akunAktif = ditemukan;
					System.out.println("Akun aktif berhasil diganti ke:" +akunAktif.getNomorRekening());
				} else {
					System.out.println("Rekning dengan nomor di inputkan tidak ditemukan");
				}
				break;
				
			case 6:
				System.out.println("Masukkan kode PIN 6 digit :");
				String pinmutasi = input.nextLine();
				// cek PIN dari akunAktif Rekening
				if(akunAktif.otentifikasi(pinmutasi)) {
					akunAktif.cetakMutasi();
				}else {
					System.out.print("Akses ditolak : PIN yang Anda masukkan salah!");
				}
				
				break;
			
			case 7:
				if(akunAktif instanceof RekeningTabungan) {
					System.out.println("simulasi akhir bulan");
					// downcasting dari tipe rekening ke rekeningTabungan
					RekeningTabungan tabungan =  (RekeningTabungan) akunAktif;
					tabungan.tambahBungaAkhirBulan();
					
				}else {
					System.out.println("gagal dimuat, akun aktif bukan tabungan");
				}
				break;
			case 0:
				isRunning = false;
				System.out.println("Sistem ditutup. Terima kasih!");
				break;
				
			default:
				System.out.println("Pilihan tidak valid");
		}
		
	}
		input.close();
	}
	
	
}


