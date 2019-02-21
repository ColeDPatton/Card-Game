package game;

public class Player {

	public Deck deck;
	public Hand hand;
	public Field field;
	public boolean player1;
	public boolean isAI;
	public int lifePoints;
	public int summonCount;
	private boolean canSummon = true;
	private boolean canDraw = true;
	public boolean canAttackDirectly = false;
	public boolean canPlaySpell = true;


	public Player(Deck d, boolean player1, boolean AI) {
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
		this.player1 = player1;
		this.isAI = AI;
	}

	public void draw() {
		if (canDraw) {
			Card grab = deck.removeTopCard();
			hand.add(grab);
			canDraw = false;
		}
	}

	public void draw(int num) {
		for (int i = 0; i < num; i++) {
			Card grab = deck.removeTopCard();
			hand.add(grab);
		}
	}

	public void playCard(int num) {
		if (field.getSize() < 5) {
			summonCount++;
			hand.unselectAll();
			Card temp = hand.removeCard(num);
			field.addCard(temp, player1, canSummon);
			if(summonCount>=3)
				canSummon = false;
		}
	}

	public boolean playSpellCard(Card c, SpellCard s) {
		if (s.getSpell() == Spell.levelUp)
			if(c.getType() == PowerType.Earth2 || c.getType() == PowerType.Water2 || c.getType() == PowerType.Fire2)
				return false;
		s.getSpell().effect(c, this);
		s.using = false;
		hand.removeCard(s);
		return true;
	}

	public void summonSpecial(Card c) {
		boolean acceptable = false;
		if (field.getSize() < 5 && canSummon) {
			if (c.getType() == PowerType.Earth2 && field.amountOfEarthGems() >= 2) {
				acceptable = true;
				field.minusEarthGems(2);
			}
			else if (c.getType() == PowerType.Fire2 && field.amountOfFireGems() >= 2) {
				acceptable = true;
				field.minusFireGems(2);
			}
			else if (c.getType() == PowerType.Water2 && field.amountOfWaterGems() >= 2) {
				acceptable = true;
				field.minusWaterGems(2);
			} else if(c.getType() == PowerType.Purple && field.amountOfPowerGems() >= 4) {
				acceptable = true;
				field.minusPowerGems(4);
			}

			if(acceptable) {
				summonCount++;
				hand.unselectAll();
				field.addCard(c, player1, canSummon);
				if (summonCount >= 3)
					canSummon = false;
			}
		}
	}
	
	public int getLifePoints() {
		return lifePoints;
	}
	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}
	public boolean canSummon() {
		return canSummon;
	}
	public void setCanSummon(boolean canSummon) {
		if(canSummon)
			summonCount = 0;
		this.canSummon = canSummon;
	}
	public void setCanDraw(boolean canDraw) {
		this.canDraw = canDraw;
	}
	public boolean canDraw() {
		return canDraw;
	}
}
