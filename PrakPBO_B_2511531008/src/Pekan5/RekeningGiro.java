package Pekan5;

public class RekeningGiro extends Rekening {
	private double batasOverdraft;
	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		// memanggil inisialisasi dasar dari Superclass
		super(nomor,nama,saldoAwal,pinAwal);
		this.batasOverdraft = batasOverdraft;
	} 
	// implementasi plomorfisme overriding
	@Override
	public boolean TarikTunai(double nominal) {
		if(nominal <= 0) {
			System.out.println("Gagal: NOminal tarik Giro harus "
					+ "lebih dari 0");
			return false;
		}
		
		// logika khusus giro : boleh ditarik asalkan nominaml tidak melebihi saldo + batasOverdraft
		else if(nominal > (saldo + batasOverdraft)) {
			System.out.println("Transaksi Gagal : Melebih batas"
					+ " overdraft Maksimal penarikan: Rp" + 
					(saldo + batasOverdraft));
			return false;
		}
		else {
			saldo -= nominal; // saldo bisa bernilai negatif di sini (memakan overdraft)
			
			String idTrx = "TRX-G-" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx, "Debit Giro", nominal));
			
			System.out.println("Tarik tunai Giro Rp" + nominal + " berhasil. saldo saat ini : Rp " + saldo);
			return true;
		}
	}
	
	//getter khusus Giro
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	
	// catatan :penarikan hingga limit overdraft akan kita selesaikan di modul 5
}
