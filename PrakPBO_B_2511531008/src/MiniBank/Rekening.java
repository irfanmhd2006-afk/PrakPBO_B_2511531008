package MiniBank;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	public Rekening(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		System.out.println("Rekening atas nama" + namaPemilik + 
				" berhasil dibuat dengan saldo Rp" + saldo);
	}
	
	public void setorTunai(double nominal) {
		if(nominal > 0) {
			saldo += nominal;
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
			saldo -= nominal;
			System.out.println("Tarik tunai Rp " + nominal 
					+" berhasil. saldo saat ini: Rp" + saldo);
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
