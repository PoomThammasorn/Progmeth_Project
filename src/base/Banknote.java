package base;

public class Banknote {
	private int BanknoteValue;
	private int amount;
	
	public Banknote(int BanknoteValue){
		this.setBanknoteValue(BanknoteValue);
		this.setAmount(1);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if(amount <0 ) {
			this.amount = 0;
			return;
		}
		this.amount = amount;
	}

	public int getBanknoteValue() {
		return BanknoteValue;
	}

	public void setBanknoteValue(int banknoteValue) {
		BanknoteValue = banknoteValue;
	}
	
	
	public boolean equals(Banknote obj) {
		Banknote other = (Banknote) obj;
		return this.getBanknoteValue() == other.getBanknoteValue();
	}
	
	
	
	
	
	
	
	//setColor
	//setPng
}
