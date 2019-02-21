package game;

public enum PowerType {
	Water, Fire, Earth,
	Water2, Fire2, Earth2,
	Purple;

	public int getAttack() {
		if(this == PowerType.Earth)
			return 1;
		else if(this == PowerType.Fire) {
			return 1;
		}else if(this == PowerType.Water)
			return 1;
		else if(this == PowerType.Earth2)
			return 2;
		else if(this == PowerType.Fire2) {
			return 2;
		}else if(this == PowerType.Water2)
			return 2;
		else if(this == PowerType.Purple)
			return 3;		
		else {
			System.out.println("problem");
			return 1;
		}
	}
	public int getHealth() {
		if(this == PowerType.Earth)
			return 1;
		else if(this == PowerType.Fire)
			return 1;
		else if(this == PowerType.Water)
			return 1;
		else if(this == PowerType.Earth2)
			return 2;
		else if(this == PowerType.Fire2) {
			return 2;
		}else if(this == PowerType.Water2)
			return 2;
		else if(this == PowerType.Purple)
			return 5;
		else return 1;
	}
	public boolean advantage(Card b) {
		if((this == PowerType.Fire || this == PowerType.Fire2) && (b.getType() == PowerType.Earth || b.getType() == PowerType.Earth2))
			return true;
		else if((this == PowerType.Water || this == PowerType.Water2) && (b.getType() == PowerType.Fire || b.getType() == PowerType.Fire2))
			return true;
		else if((this == PowerType.Earth || this == PowerType.Earth2) && (b.getType() == PowerType.Water || b.getType() == PowerType.Water2))
			return true;
		else
			return false;
	}
	public boolean disadvantage(Card b) {
		if((this == PowerType.Fire || this == PowerType.Fire2) && (b.getType() == PowerType.Water || b.getType() == PowerType.Water2))
			return true;
		else if((this == PowerType.Water || this == PowerType.Water2) && (b.getType() == PowerType.Earth || b.getType() == PowerType.Earth2))
			return true;
		else if((this == PowerType.Earth || this == PowerType.Earth2) && (b.getType() == PowerType.Fire || b.getType() == PowerType.Fire2))
			return true;
		else
			return false;
	}
	public boolean isMain() {
		if(this == PowerType.Earth || this == PowerType.Fire || this == PowerType.Water)
			return true;
		return false;
	}
	public void spell(Card c, Field good, Field bad) {
		if(this == PowerType.Earth2)
			c.canAttackDirectly = true;
		else if(this == PowerType.Water2) {
			c.used = false;
			c.attacking = true;
		}
		else if(this == PowerType.Fire2) {
			for (int i = 0; i < good.getSize(); i++) {
				good.getCard(i).health ++;
			}
		}else if(this==Purple) {
			for (int i = 0; i < bad.getSize(); i++) {
				bad.getCard(i).health--;
			}
		}
	}
}