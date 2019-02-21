package game;

import java.awt.image.BufferedImage;

public class SpellCard extends Card{
	
	Spell spell;
	Card equiped;
	public boolean using = false;
	
	public SpellCard(Spell s) {
		super();
		spell = s;
	}
	public Spell getSpell() {
		return spell;
	}

	public Card getHost() {
		return equiped;
	}
	@Override
	public BufferedImage getGraphics() {
		if (spell == Spell.armor)
			return Game.loadImage("/sprites/armorCard.jpg");
		else if (spell == Spell.weapon)
			return Game.loadImage("/sprites/weaponCard.jpg");
		else if (spell == Spell.levelUp)
			return Game.loadImage("/sprites/levelUp.jpg");
		return null;
	}
	public void use() {
		using = true;
	}
	public boolean using() {
		return using;
	}
}
