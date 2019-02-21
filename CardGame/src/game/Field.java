package game;

import java.util.ArrayList;
import java.util.List;

public class Field {

	private int top = 400;
	private int bottom = 590;
	
	private List<Card> playedCards = new ArrayList<Card>();
	private List<Gem> gems = new ArrayList<Gem>();
	
	public Field() {
		
	}
	
	public void addCard(Card c, boolean player1, boolean allowed) {
		if (allowed) {
			for (Location loca : Location.values()) {
				if (player1 && (loca == Location.one || loca == Location.two || loca == Location.three
						|| loca == Location.four || loca == Location.five)) {
					if (!loca.getFilled()) {
						loca.setFilled(true);
						c.setLoc(loca);
						c.setX(loca.getX());
						c.setY(loca.getY());
						playedCards.add(c);
						break;
					}
				} else if ((!player1) && (loca == Location.oneZ || loca == Location.twoZ || loca == Location.threeZ
						|| loca == Location.fourZ || loca == Location.fiveZ)) {
					if (!loca.getFilled()) {
						loca.setFilled(true);
						c.setLoc(loca);
						c.setX(loca.getX());
						c.setY(loca.getY());
						playedCards.add(c);
						break;
					}
				}
			}
		}
	}
	
	public boolean allDefense() {
		boolean defense = true;
		for (int i = 0; i < playedCards.size(); i++) {
			if(!playedCards.get(i).blocking)
				defense = false;
		}
		return defense;
	}
	public void addGem(Gem g) {
		gems.add(g);
	}
	public Gem getGem(int i) {
		return gems.get(i);
	}
	public int amountOfGems() {
		return gems.size();
	}

	public int amountOfFireGems() {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.redGem || gems.get(i).getSpot() == Location.redGemZ)
				count++;
		}
		return count;
	}

	public int amountOfWaterGems() {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.blueGem || gems.get(i).getSpot() == Location.blueGemZ)
				count++;
		}
		return count;
	}

	public int amountOfEarthGems() {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.greenGem || gems.get(i).getSpot() == Location.greenGemZ)
				count++;
		}
		return count;
	}
	public int amountOfPowerGems() {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.purpleGem || gems.get(i).getSpot() == Location.purpleGemZ)
				count++;
		}
		return count;
	}
	public void minusEarthGems(int num) {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.greenGem || gems.get(i).getSpot() == Location.greenGemZ) {
				count++;
				gems.remove(i);
				i--;
				if(count >= num)
					break;
			}
		}
	}
	public void minusFireGems(int num) {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.redGem || gems.get(i).getSpot() == Location.redGemZ) {
				count++;
				gems.remove(i);
				i--;
				if(count >= num)
					break;
			}
		}
	}
	public void minusWaterGems(int num) {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.blueGem || gems.get(i).getSpot() == Location.blueGemZ) {
				count++;
				gems.remove(i);
				i--;
				if(count >= num)
					break;
			}
		}
	}
	public void minusPowerGems(int num) {
		int count = 0;
		for (int i = 0; i < gems.size(); i++) {
			if (gems.get(i).getSpot() == Location.purpleGem || gems.get(i).getSpot() == Location.purpleGemZ) {
				count++;
				gems.remove(i);
				i--;
				if(count >= num)
					break;
			}
		}
	}

	public void remove(Card c) {
		c.getLoc().setFilled(false);
		playedCards.remove(c);
	}
	public Card getCard(int i) {
		return playedCards.get(i);
	}
	
	public int getSize() {
		return playedCards.size();
	}
	public void unselectField() {
		for (int i = 0; i < playedCards.size(); i++) {
			if(playedCards.get(i).isSelected())
				playedCards.get(i).setSelected(false);
		}
	}
	public void unblockField() {
		for (int i = 0; i < playedCards.size(); i++) {
			if(playedCards.get(i).blocking)
				playedCards.get(i).blocking = false;
		}
	}
	public void unuse() {
		for (int i = 0; i < playedCards.size(); i++) {
			playedCards.get(i).used = false;
			playedCards.get(i).usedSkill = false;
		}
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
	
	
	
}
