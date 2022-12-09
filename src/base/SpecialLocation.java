package base;

public class SpecialLocation extends Location {

	public SpecialLocation(int amountOfPlayer) {
		super(amountOfPlayer);
	}
	public int sendReward(){
		int reward = super.sendReward();
		return 2*reward;
		// ยังไม่ได้ปรับจำนวนเงินให้ player
	}

}