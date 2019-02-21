package game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AI extends Player{

	public AI(Deck d){
		super(d, false, true);
		deck = d;
		hand = new Hand();
		field = new Field();
		setLifePoints(30);
		if (!player1) {
			field.setTop(175);
			field.setBottom(365);
			hand.setTop(25);
			hand.setBottom(155);
		}
		this.player1 = false;
	}
	
	public void playCards() {
		int cardsPlayed=0;
		int i=0;
		ArrayList<SpellCard> ar = new ArrayList<SpellCard>();
		
		if(field.amountOfPowerGems() >= 4) {
			summonSpecial(new Card(PowerType.Purple));
			cardsPlayed++;
		}
		if(field.amountOfFireGems() >= 2) {
			summonSpecial(new Card(PowerType.Fire2));
			cardsPlayed++;
		}
		if(field.amountOfWaterGems() >= 2) {
			summonSpecial(new Card(PowerType.Water2));
			cardsPlayed++;
		}
		if(field.amountOfEarthGems() >= 2) {
			summonSpecial(new Card(PowerType.Earth2));
			cardsPlayed++;
		}

		if(hand.hasThree(PowerType.Fire))
			hand.tradeIn(PowerType.Fire);
		if(hand.hasThree(PowerType.Water))
			hand.tradeIn(PowerType.Water);
		if(hand.hasThree(PowerType.Earth))
			hand.tradeIn(PowerType.Earth);
		
		while(hand.getSize()>=1 && cardsPlayed<3) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(hand.getCard(i).isMonster) {
				playCard(i);
				cardsPlayed++;
				if(!ar.isEmpty()) {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!playSpellCard(field.getCard(0), ar.get(0)))
						playSpellCard(field.getCard(i), ar.get(0));
					ar.remove(0);
					i--;
				}
			} else {
				ar.add((SpellCard) hand.getCard(i));
				i++;
			}
		}
	}
	

	

}
