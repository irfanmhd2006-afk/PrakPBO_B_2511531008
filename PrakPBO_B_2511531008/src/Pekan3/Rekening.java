package Pekan3;

import java.util.*;
public class Rekening {
	// 1. kunci atribut dengan private
	
	private String nomorRekening;
	private String namaPemilik;
	private double saldo;
	private String pin;
	
	
	// chalengee
	private int gagal = 0;
	private boolean isBlokir = false;
	//implementasi Asosiasi (1 to many)
	ArrayList<Transaksi> riwayatTransaksi;
	
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		// Validasi PIN di dalam Constructor
		if (pinAwal.length() == 6) {
			this.pin = pinAwal;
		}
		else {
			System.out.println("Peringatan : PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin = "123456";
		}
		// wajib menginisialisasi ArrayList di dalam construktor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<>();
		System.out.println("Rekening atas nama" + namaPemilik + 
				" berhasil dibuat dengan saldo Rp" + saldo);
	}
	
	//3.getter untuk atribut yang diizinkan di baca publik
	public String getNomorRekening() {
		return nomorRekening;
	}
	public String getNamaPemilik() {
		return namaPemilik;
	}
	
	
	public boolean otentifikasi (String inputPin) {
		if(isBlokir) {
			System.out.println("rekening ini sudah diblokir");
			return false;
		}
		
		if(this.pin.equals(inputPin)) {
			gagal = 0;
			return true;
		}
		else {
			gagal++;
			int sisacoba = 3 - gagal;
			
			if(gagal >= 3) {
				isBlokir = true;
				System.out.println("akun sudah di blokir");
			}else {
				System.out.println("PIN salah  sisa percobaan" + sisacoba);
			}
		}
		return this.pin.equals(inputPin);
	}
	public void setorTunai(double nominal) {
		if(nominal > 0) {
			saldo += nominal;
			
			//merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx,"Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			System.out.println("Setor tunai Rp" + nominal 
					+" berhasil. saldo saat ini: Rp" + saldo);
		}else {
			System.out.println("Gagal : Nominal setor harus lebih dari 0!");
		}
	}
	
	public void TarikTunai(double nominal) {
		if(nominal < 10000) {
			System.out.println("Gagal : Nominal tarik setor harus lebih dari 10000!");
			
		}else if (saldo <= nominal) {
			System.out.println("Gagal : Saldo lebih kecil dari nominal tarikan");
		}
		else {
			//case berhasil di sini yakk
			//rekam riwayat 
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx,"Dedit", nominal);
			riwayatTransaksi.add(trxBaru);
			saldo -= nominal;
			
			System.out.println("Tarik tunai Rp " + nominal 
					+" berhasil. saldo saat ini: Rp" + saldo);
		}
	}
	public void cetakMutasi() {
		System.out.println("Riwayat Transaksi");
	
		int totalriwayat = 0;
		if(riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada Transaksi pada rekening ini");
			return;
		}
		for(Transaksi tr : riwayatTransaksi) {
				tr.cetakDetail();
				totalriwayat++;
			}
		System.out.println("Total Transaksi sebanyak :" + totalriwayat);
	}
	
	
	
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Salod Akhir  : Rp" + saldo);
		System.out.println("---------------------");
	}
	
	
	
}
