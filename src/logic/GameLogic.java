package logic;

import java.util.ArrayList;
import java.util.Collections;

import base.Location;
import base.SpecialLocation;
import player.Player;


public class GameLogic {
	private ArrayList<Location> locationList;
	private ArrayList<String> locationNameList;
	private ArrayList<Player> playerList;
	private int roundCount;
	public GameLogic() {
		// TODO Auto-generated constructor stub
		//this.newgane();
	}
	//new game
	public void newGame(int amountOfPlayer) {
		this.setRoundCount(0);
		locationNameList.add(null);
		locationNameList.add(null);
		locationNameList.add(null);
		locationNameList.add(null);
		locationNameList.add(null);
		locationNameList.add(null);
		locationNameList.add(null);
		locationNameList.add(null);
		Collections.shuffle(locationNameList);
		for(int i=0;i<8;i++) {
			locationList.add(new Location(this.getLocationNameList().get(i),amountOfPlayer,i));
		}
	}
	//player แต่ละคนเบ่นเกม
	public void playGame(ArrayList<Player> playerList) {
		/*while(!allOutofDice()){
		  	int c = 0;
			if(playerList.get(i).getDiceInPlayer().size() != 0) {
				p.rollDice();
				->ให้กดเลือกสถานที่ if(..) Location l
				l.addDice(p,p.dropDice(หน้าลูกเต๋าประจำสถานที่))
			}
			c = (c+1)%playerList.size();
		}
		if(this.getRoundCount() != 4){
			this.endRound();
		}
		else{
			this.endgame();
		}
		*/	
		
		
	}
	//อัพเดตเงินในแต่ละสถานที่+แจกเงิน 
	public void endRound() {
		for(Location l:locationList) {
			while(l.fundValue(l.getFund()) < 50000) {
				l.updateFund();
			}
		}
		this.setRoundCount(this.getRoundCount()+1);
		
	}
	public boolean allOutOfDice() {
		for(Player p:playerList) {
			if(p.getDiceInPlayer().size() != 0) return false;
		}
		return true;
	}
	public ArrayList<String> getLocationNameList() {
		return locationNameList;
	}

	public void setLocationNameList(ArrayList<String> locationNameList) {
		this.locationNameList = locationNameList;
	}
	public int getRoundCount() {
		return roundCount;
	}
	public void setRoundCount(int roundCount) {
		this.roundCount = roundCount;
	}
	
}
