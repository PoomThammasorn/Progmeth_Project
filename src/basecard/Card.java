package basecard;

public class Card {
	
	private String name;
	private int effect;
	private int amount;
	
	
	public Card(String name,int effect,int amount) {
		// TODO Auto-generated constructor stub
		this.setName(name);
		this.setEffect(effect);
		this.setAmount(amount);
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEffect() {
		return effect;
	}
	public void setEffect(int effect) {
		this.effect = effect;
	}
	
	

}
