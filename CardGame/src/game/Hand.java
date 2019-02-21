package game;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	
	private int top = 600;
	private int bottom = 730;
	
	private List<Card> heldCards = new ArrayList<Card>();
	
	public Hand() {
		
	}
	public boolean hasThree(PowerType p) {
		int count=0;
		for (int i = 0; i < heldCards.size(); i++) {
			if(heldCards.get(i).getType() == p)
				count++;
		}
		return count>=3;
	}
	public void tradeIn(PowerType p) {
		PowerType newP;
		if(p == PowerType.Earth)
			newP = PowerType.Earth2;
		else if(p == PowerType.Fire)
			newP = PowerType.Fire2;
		else
			newP = PowerType.Water2;
	
		int count=0;
		for (int i = 0; i < heldCards.size(); i++) {
			if(heldCards.get(i).getType() == p) {
				heldCards.remove(i);
				count++;
				i--;
			}
			if(count>=3)
				break;
		}
		heldCards.add(new Card(newP));
	}

	public void add(Card c) {
		heldCards.add(c);
	}
	public int getSize() {
		return heldCards.size();
	}
	public Card getCard(int index) {
		return heldCards.get(index);
	}
	public Card removeCard(int index) {
		return heldCards.remove(index);
	}
	public Card removeCard(Card c) {
		if(heldCards.contains(c))
			heldCards.remove(c);
		return c;
	}
	public void unselectAll() {
		for (int i = 0; i < heldCards.size(); i++) {
			heldCards.get(i).setSelected(false);
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
