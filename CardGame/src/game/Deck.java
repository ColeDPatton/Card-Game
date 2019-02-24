package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//@Entity
//@Table(name = "deck")
public class Deck {

	private List<Card> deck = new ArrayList<Card>();
	
//	@Id
	private int deck_id = 1;
	
	public Deck() {
		
	}
	
	public Deck(int numberOfCards) {
		while (deck.size() < numberOfCards)
			for (Spell s : Spell.values()) {
				if (deck.size() >= numberOfCards)
					break;
				add(new SpellCard(s));
				for (int i = 0; i < 2; i++) {
					for (PowerType p : PowerType.values()) {
						if (p.isMain()) {
							if (deck.size() >= numberOfCards)
								break;
							add(new Card(p));
						}
					}
				}
			}
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public void add(Card c) {
		deck.add(c);
	}
	public void add(SpellCard sc) {
		deck.add(sc);
	}
	public Card removeTopCard() {
		if(deck.isEmpty()) {
			System.out.println("Tried to draw from an empty deck");
			return null;
		}
		Card temp = deck.get(deck.size()-1);
		deck.remove(deck.size()-1);
		return temp;
	}
	public void remove(Card c) {
		if(deck.contains(c))
			deck.remove(c);
	}
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	public int size() {
		return deck.size();
	}
	public Card getCard(int i) {
		return deck.get(i);
	}
	public Deck copy() {
		Deck re = new Deck();
		for (int i = 0; i < deck.size(); i++) {
			re.add(deck.get(i));
		}
		return re;
	}
	public void clear() {
		for (int i = 0; i < deck.size(); i++) {
			deck.clear();
		}
	}

	public int getDeck_id() {
		return deck_id;
	}

	public void setDeck_id(int deck_id) {
		this.deck_id = deck_id;
	}
}
