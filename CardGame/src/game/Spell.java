package game;

public enum Spell {
weapon,
armor,
levelUp;
//colorChange;
	
	public void effect(Card c, Player p) {
		if(this==weapon) {
			c.attackPower++;
		} else if(this==armor) {
			c.health++;
		} else if(this==levelUp) {
			if(c.getType() == PowerType.Earth) {
				p.field.remove(c);
				p.field.addCard(new Card(PowerType.Earth2), p.player1, true);
			} else if(c.getType() == PowerType.Fire) {
				p.field.remove(c);
				p.field.addCard(new Card(PowerType.Fire2), p.player1, true);
			} else if(c.getType() == PowerType.Water) {
				p.field.remove(c);
				p.field.addCard(new Card(PowerType.Water2), p.player1, true);
			}	
		}// else if(this==colorChange) {
//			System.out.println("color change");
//		}
	}
}
