package game;

import java.awt.image.BufferedImage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card {
	@Id
	private int card_id;
	public boolean used = false;
	public boolean usedSkill = false;
	public boolean canAttackDirectly = false;
	public boolean isMonster = true; 

	private PowerType type;

	private Location lo;
	private boolean selected = false;
	public boolean attacking;
	public boolean blocking;
	
	public int attackPower;
	public int health;

	
	private int x, y;
	
	public Card(PowerType p) {
		type = p;
		attackPower = type.getAttack();
		health = type.getHealth();
		card_id = type.getId();
	}
	public Card() {
		isMonster = false;
	}
	
	public void convertToGem(Field f) {
		boolean player1 = false;
		this.getLoc().setFilled(false);
		f.remove(this);
		if(lo == Location.one || lo == Location.two || lo == Location.three || lo == Location.four || lo == Location.five)
			player1 = true;
			
		if(type == PowerType.Purple){
			f.addGem(new Gem(PowerType.Earth, player1));
			f.addGem(new Gem(PowerType.Fire, player1));
			f.addGem(new Gem(PowerType.Water, player1));
		} else
			f.addGem(new Gem(this));

	}
	
	public BufferedImage getGraphics() {
		if (!used) {
			if (this.type == PowerType.Earth)
				return Game.loadImage("/sprites/card3.jpg");
			else if (this.type == PowerType.Fire)
				return Game.loadImage("/sprites/card1.jpg");
			else if (this.type == PowerType.Water)
				return Game.loadImage("/sprites/card2.jpg");
			else if (this.type == PowerType.Earth2)
				return Game.loadImage("/sprites/level2green.jpg");
			else if (this.type == PowerType.Fire2)
				return Game.loadImage("/sprites/level2r.jpg");
			else if (this.type == PowerType.Water2)
				return Game.loadImage("/sprites/level2b.jpg");
			else if (this.type == PowerType.Purple)
				return Game.loadImage("/sprites/purpleCardtest.jpg");
		}
		else {
			if (this.type == PowerType.Earth)
				return Game.loadImage("/sprites/card3used.jpg");
			else if (this.type == PowerType.Fire)
				return Game.loadImage("/sprites/card1used.jpg");
			else if (this.type == PowerType.Water)
				return Game.loadImage("/sprites/card2used.jpg");
			else if (this.type == PowerType.Earth2)
				return Game.loadImage("/sprites/level2greenused.jpg");
			else if (this.type == PowerType.Fire2)
				return Game.loadImage("/sprites/level2rUsed.jpg");
			else if (this.type == PowerType.Water2)
				return Game.loadImage("/sprites/level2bUsed.jpg");
			else if (this.type == PowerType.Purple)
				return Game.loadImage("/sprites/purpleCardtest.jpg");
		}
		return null;
	}
	public BufferedImage getBackGraphics() {
		return Game.loadImage("/sprites/cardBack.jpg");
	}
	
	public void setLoc(Location loca) {
		lo = loca;
	}
	public Location getLoc() {
		return lo;
	}
	public PowerType getType() {
		return type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected == false) {
			attacking = false;
		}
	}
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

}
