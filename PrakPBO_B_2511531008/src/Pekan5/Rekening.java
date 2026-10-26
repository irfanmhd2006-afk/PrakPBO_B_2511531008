package Pekan5;

import java.util.*;
public class Rekening {
	
	private String nomorRekening;
	private String namaPemilik;
	private String pin;
	//gunakan protected agar subclass bisa mengaksesnya langsung
	protected double saldo;
	protected ArrayList<Transaksi> riwayatTransaksi;
	
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
	
	public boolean TarikTunai(double nominal) {
		if(nominal < 10000) {
			System.out.println("Gagal : Nominal tarik setor harus lebih dari 10000!");
			return false;
			
		}else if (saldo <= nominal) {
			System.out.println("Gagal : Saldo lebih kecil dari nominal tarikan");
			return false;
		}
		else {
			//case berhasil di sini yakk
			//rekam riwayat 
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx,"Dedit", nominal));
			saldo -= nominal;
			
			System.out.println("Tarik tunai Rp " + nominal 
				 	+" berhasil. saldo saat ini: Rp" + saldo);
			return true;
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
	
	public void transfer(Rekening tujuan, double nominal, String pinInput) {
//		1. otentikasi PIN
		if(!this.otentifikasi(pinInput)) {
			System.out.println("Transfer dibatalkan : PIN salah");
			return;
		}
		// 2. tarik tunai (polimorfik: Tabungan atau Giro otomasti pakai aturan masing masing)
		// 3. jika berhasil, setor ke tujuan
		
		if(this.TarikTunai(nominal)) {
			tujuan.setorTunai(nominal);
			System.out.println("Transfer Rp" + 
			nominal + " ke " + tujuan.getNomorRekening() + " berhasil");
		}
		
	}
	
	
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Salod Akhir  : Rp" + saldo);
		System.out.println("---------------------");
	}
	
	
	
}
