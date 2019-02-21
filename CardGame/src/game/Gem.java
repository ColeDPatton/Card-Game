package game;

import java.awt.Color;

public class Gem {
	private Location spot;
	private Color color;
	
	public Gem(Card c) {
		boolean player1=false; 
		Location temp = c.getLoc();
		if(temp == Location.one || temp == Location.two || temp == Location.three || temp == Location.four || temp == Location.five)
			player1 = true;

		if (player1) {
			if (c.getType() == PowerType.Earth) {
				spot = Location.greenGem;
				color = Color.GREEN;
			} else if (c.getType() == PowerType.Fire) {
				spot = Location.redGem;
				color = Color.RED;
			} else if (c.getType() == PowerType.Water) {
				spot = Location.blueGem;
				color = Color.BLUE;
			} else if(c.getType() == PowerType.Earth2 || c.getType() == PowerType.Fire2 || c.getType() == PowerType.Water2) {
				spot = Location.purpleGem;
				color = new Color(0x7f007f);
			}
		}else {
			if (c.getType() == PowerType.Earth) {
				spot = Location.greenGemZ;
				color = Color.GREEN;
			} else if (c.getType() == PowerType.Fire) {
				spot = Location.redGemZ;
				color = Color.RED;
			} else if (c.getType() == PowerType.Water) {
				spot = Location.blueGemZ;
				color = Color.BLUE;
			} else if(c.getType() == PowerType.Earth2 || c.getType() == PowerType.Fire2 || c.getType() == PowerType.Water2) {
				spot = Location.purpleGemZ;
				color = new Color(0x7f007f);
			}
		}

	}
	public Gem(PowerType p, Boolean player1) {
		if (player1) {
			if (p == PowerType.Earth) {
				spot = Location.greenGem;
				color = Color.GREEN;
			} else if (p == PowerType.Fire) {
				spot = Location.redGem;
				color = Color.RED;
			} else if (p == PowerType.Water) {
				spot = Location.blueGem;
				color = Color.BLUE;
			} else if(p == PowerType.Earth2 || p == PowerType.Fire2 || p == PowerType.Water2) {
				spot = Location.purpleGem;
				color = new Color(0x7f007f);
			}
		}else {
			if (p == PowerType.Earth) {
				spot = Location.greenGemZ;
				color = Color.GREEN;
			} else if (p == PowerType.Fire) {
				spot = Location.redGemZ;
				color = Color.RED;
			} else if (p == PowerType.Water) {
				spot = Location.blueGemZ;
				color = Color.BLUE;
			} else if(p == PowerType.Earth2 || p == PowerType.Fire2 || p == PowerType.Water2) {
				spot = Location.purpleGemZ;
				color = new Color(0x7f007f);
			}
		}
	}

	public Color getColor() {
		return color;
	}
	public Location getSpot(){
		return spot;
	}
	public void setSpot(Location place) {
		spot = place;
	}
	
	public int getX() {
		return spot.getX();
	}
	public int getY() {
		return spot.getY();
	}
}
