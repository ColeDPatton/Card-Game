package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler 
{
	private BufferedImage view;
	private int[] pixels;
	private int alpha = 0xFF00FE;
	public RenderHandler(int width, int height) 
	{
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}
	
	
	//*************************************
	//THINGS FOR RENDERING THE PLAYER HANDS
	//THINGS FOR RENDERING THE PLAYER HANDS
	//THINGS FOR RENDERING THE PLAYER HANDS
	//*************************************
	public void renderHands(Game g) {
		Player active = Game.player1;
		Player hide = Game.player2;

		if(g.getTurn() == Turn.even) {
			active = Game.player2;
			hide = Game.player1;
		}

		for (int i = 0; i < active.hand.getSize(); i++) {
			BufferedImage cardGraphic = active.hand.getCard(i).getGraphics();
			if(Game.player1 == active)
				renderImage(cardGraphic, (i*75)+144,600,2, 2);
			else
				renderImage(cardGraphic, (i*75)+144,25,2, 2);
		}
		for (int i = 0; i < hide.hand.getSize(); i++) {
			BufferedImage backGraphic = hide.hand.getCard(i).getBackGraphics();
			if(Game.player1 == hide)
				renderImage(backGraphic, (i*75)+144,600,2, 2);
			else
				renderImage(backGraphic, (i*75)+144,25,2, 2);
		}
		renderHandButtons(g);
	}
	public void renderHandButtons(Game g) {
		Player active = Game.player1;
		if(g.getTurn() == Turn.even) {
			active = Game.player2;
		}
		
		for (int i = 0; i < active.hand.getSize(); i++) {
			if(active.hand.getCard(i).isSelected() && active.hand.getCard(i).isMonster) {
				BufferedImage summonButton = Game.loadImage("/sprites/summon.jpg");
				renderImage(summonButton, (i*75)+138, active.hand.getTop(), 1, 1);
			}else if(active.hand.getCard(i).isSelected() && !active.hand.getCard(i).isMonster && ((SpellCard) active.hand.getCard(i)).using()) {
				BufferedImage summonButton = Game.loadImage("/sprites/activateSel.jpg");
				renderImage(summonButton, (i*75)+138, active.hand.getTop(), 1, 1);
			}else if(active.hand.getCard(i).isSelected() && !active.hand.getCard(i).isMonster) {
				BufferedImage summonButton = Game.loadImage("/sprites/activate.jpg");
				renderImage(summonButton, (i*75)+138, active.hand.getTop(), 1, 1);
			}
		}
	}

	public void renderHandTradeInButtons(Game game, Graphics g) {
		Player active = Game.player1;
		int y = 598;
		if (game.getTurn() == Turn.even) {
			active = Game.player2;
			y = 27;
		}
		g.setFont(new Font("Arial", 10, 16));
		if (!active.canDraw()) {
			if (active.hand.hasThree(PowerType.Earth)) {
				g.setColor(Color.GREEN);
				g.fillRect(63, y, 60, 25);
				g.setColor(Color.BLACK);
				g.drawString("Trade in", 64, y + 12);
				g.drawString("earth", 64, y + 23);
			}
			if (active.hand.hasThree(PowerType.Fire)) {
				g.setColor(Color.RED);
				g.fillRect(63, y + 40, 60, 25);
				g.setColor(Color.BLACK);
				g.drawString("Trade in", 64, y + 52);
				g.drawString("fire", 64, y + 63);
			}
			if (active.hand.hasThree(PowerType.Water)) {
				g.setColor(Color.BLUE);
				g.fillRect(63, y + 80, 60, 25);
				g.setColor(Color.BLACK);
				g.drawString("Trade in", 64, y + 92);
				g.drawString("water", 64, y + 103);

			}
		}

	}

	//*************************************
	//THINGS FOR RENDERING THE FIELD
	//THINGS FOR RENDERING THE FIELD
	//THINGS FOR RENDERING THE FIELD
	//*************************************
	public void renderField(Game g) {
		BufferedImage shield = Game.loadImage("/sprites/slddd.jpg");
		Card sel = new Card(PowerType.Fire);
		for (int i = 0; i < Game.player1.field.getSize(); i++) {
			Card currentCard = Game.player1.field.getCard(i);
			if(currentCard.isSelected())
				sel = currentCard;
			BufferedImage cardGraphic = currentCard.getGraphics();
			if(currentCard.getType() == PowerType.Purple)
				renderImage(cardGraphic, currentCard.getX(),currentCard.getY(),1, 1);
			else
				renderImage(cardGraphic, currentCard.getX(),currentCard.getY(),3, 3);
			if(currentCard.blocking) {
				renderImage(shield, currentCard.getX()+20,currentCard.getY()+50,1,1);
			}
		}
		for (int i = 0; i < Game.player2.field.getSize(); i++) {
			Card currentCard = Game.player2.field.getCard(i);
			if(currentCard.isSelected())
				sel = currentCard;
			BufferedImage cardGraphic = currentCard.getGraphics();
			if(currentCard.getType() == PowerType.Purple)
				renderImage(cardGraphic, currentCard.getX(),currentCard.getY(),1, 1);
			else
				renderImage(cardGraphic, currentCard.getX(),currentCard.getY(),3, 3);
			if(currentCard.blocking) {
				renderImage(shield, currentCard.getX()+20,currentCard.getY()+50,1,1);
			}
		}
		if(sel != null && sel.isSelected()) {
			renderFieldButtons(sel, g);
		}
	}

	public void renderFieldButtons(Card c, Game g) {
		BufferedImage Abutton = Game.loadImage("/sprites/attack.jpg");
		BufferedImage Bbutton = Game.loadImage("/sprites/block.jpg");
		BufferedImage AbuttonSel = Game.loadImage("/sprites/attackSel.jpg");
		BufferedImage BbuttonSel = Game.loadImage("/sprites/blockSel.jpg");
		BufferedImage Sbutton = Game.loadImage("/sprites/spellButton.jpg");
		BufferedImage SbuttonSel = Game.loadImage("/sprites/spellButtonUsed.jpg");

		if (g.getTurn() != Turn.First || g.getSta()==State.tutorial) {
			if (!c.used) {
				if (c.attacking) {
					renderImage(AbuttonSel, c.getX() + 10, c.getY(), 1, 1);
					renderImage(Bbutton, c.getX() + 10, c.getY() + 150, 1, 1);
				} else if (c.blocking) {
					renderImage(Abutton, c.getX() + 10, c.getY(), 1, 1);
					renderImage(BbuttonSel, c.getX() + 10, c.getY() + 150, 1, 1);
				} else {
					renderImage(Abutton, c.getX() + 10, c.getY(), 1, 1);
					renderImage(Bbutton, c.getX() + 10, c.getY() + 150, 1, 1);
				}
			}
			if (c.getType() == PowerType.Earth2 || (c.getType() == PowerType.Fire2 && !c.used)|| 
					c.getType() == PowerType.Water2 || (c.getType() == PowerType.Purple && !c.used)) {
				if(c.usedSkill)
					renderImage(SbuttonSel, c.getX() + 10, c.getY() + 75, 1, 1);
				else
					renderImage(Sbutton, c.getX() + 10, c.getY() + 75, 1, 1);
			}

		} else {
			if (c.blocking) {
				renderImage(BbuttonSel, c.getX() + 10, c.getY() + 150, 1, 1);
			} else {
				renderImage(Bbutton, c.getX() + 10, c.getY() + 150, 1, 1);
			}
		}
	}
	
	public void renderGems(Graphics g) {
		int width = 25;
		int height = 25;
		for (int i = 0; i < Game.player1.field.amountOfGems(); i++) {
			Gem temp = Game.player1.field.getGem(i);
			g.setColor(temp.getColor());
			g.fillRect(temp.getX(), temp.getY(), width, height);
			if(temp.getSpot() == Location.purpleGem) {
				g.fillRect(temp.getX()-7, temp.getY()+30, 39, 2);
				if(Game.player1.field.amountOfPowerGems() > 1)
					g.fillRect(temp.getX()+30, temp.getY()-7, 2, 39);
				if(Game.player1.field.amountOfPowerGems() > 2)
					g.fillRect(temp.getX()-7, temp.getY()-7, 39, 2);
				if(Game.player1.field.amountOfPowerGems() > 3)
					g.fillRect(temp.getX()-7, temp.getY()-7, 2, 39);
			}
			g.setColor(Color.BLACK);
			if (temp.getSpot() == Location.redGem)
				g.drawString(Integer.toString(Game.player1.field.amountOfFireGems()), temp.getX() + 8, temp.getY() + 20);
			else if (temp.getSpot() == Location.blueGem)
				g.drawString(Integer.toString(Game.player1.field.amountOfWaterGems()), temp.getX() + 8,
						temp.getY() + 20);
			else if (temp.getSpot() == Location.greenGem)
				g.drawString(Integer.toString(Game.player1.field.amountOfEarthGems()), temp.getX() + 8,
						temp.getY() + 20);
			else if (temp.getSpot() == Location.purpleGem)
				g.drawString(Integer.toString(Game.player1.field.amountOfPowerGems()), temp.getX() + 8,
						temp.getY() + 20);
		}
		for (int i = 0; i < Game.player2.field.amountOfGems(); i++) {
			Gem temp = Game.player2.field.getGem(i);
			g.setColor(temp.getColor());
			g.fillRect(temp.getX(), temp.getY(), width, height);
			if(temp.getSpot() == Location.purpleGemZ) {
				g.fillRect(temp.getX()-7, temp.getY()+30, 39, 2);
				if(Game.player2.field.amountOfPowerGems() > 1)
					g.fillRect(temp.getX()+30, temp.getY()-7, 2, 39);
				if(Game.player2.field.amountOfPowerGems() > 2)
					g.fillRect(temp.getX()-7, temp.getY()-7, 39, 2);
				if(Game.player2.field.amountOfPowerGems() > 3)
					g.fillRect(temp.getX()-7, temp.getY()-7, 2, 39);
			}
			g.setColor(Color.BLACK);
			if (temp.getSpot() == Location.redGemZ)
				g.drawString(Integer.toString(Game.player2.field.amountOfFireGems()), temp.getX() + 8, temp.getY() + 20);
			else if (temp.getSpot() == Location.blueGemZ)
				g.drawString(Integer.toString(Game.player2.field.amountOfWaterGems()), temp.getX() + 8,
						temp.getY() + 20);
			else if (temp.getSpot() == Location.greenGemZ)
				g.drawString(Integer.toString(Game.player2.field.amountOfEarthGems()), temp.getX() + 8,
						temp.getY() + 20);
			else if (temp.getSpot() == Location.purpleGemZ)
				g.drawString(Integer.toString(Game.player2.field.amountOfPowerGems()), temp.getX() + 8,
						temp.getY() + 20);
		}

	}

	public void renderCardStats(Game game, Graphics g) {
		g.setFont(new Font("Arial", 10, 18));
		for (int i = 0; i < Game.player1.field.getSize(); i++) {
			Card currentCard = Game.player1.field.getCard(i);
			g.setColor(Color.YELLOW);
			g.fillRect(currentCard.getX(), currentCard.getY() + 125, 30, 16);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(currentCard.attackPower) + ":" + Integer.toString(currentCard.health),
					currentCard.getX()+2, currentCard.getY() + 140);
		}
		for (int i = 0; i < Game.player2.field.getSize(); i++) {
			Card currentCard = Game.player2.field.getCard(i);
			g.setColor(Color.YELLOW);
			g.fillRect(currentCard.getX(), currentCard.getY() + 125, 30, 16);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(currentCard.attackPower) + ":" + Integer.toString(currentCard.health),
					currentCard.getX()+2, currentCard.getY() + 140);
		}

	}

	public void renderDecks() {
		BufferedImage backGraphic = new Card(PowerType.Earth).getBackGraphics();
		renderImage(backGraphic, 69,600,2, 2);
		renderImage(backGraphic, 67,598,2, 2);
		renderImage(backGraphic, 65,596,2, 2);
		renderImage(backGraphic, 63,594,2, 2);
		renderImage(backGraphic, 61,592,2, 2);
		
		renderImage(backGraphic, 69,27,2, 2);
		renderImage(backGraphic, 67,25,2, 2);
		renderImage(backGraphic, 65,23,2, 2);
		renderImage(backGraphic, 63,21,2, 2);
		renderImage(backGraphic, 61,19,2, 2);

	}
	public void renderInGameMenu(Graphics g) {
		g.setColor(new Color(105,182,175));
		//quit
		g.fillRect(900, 25, 100, 50);
		//next turn
		g.fillRect(800, 105, 200, 50);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 10, 32));
		g.drawString("QUIT", 910, 59);
		g.drawString("Next Turn", 830, 140);
	}
	
	public void renderScore(Game game, Graphics g) {
		g.setColor(new Color(105,182,175));
		g.fillRect(700, 25, 50, 50);
		g.fillRect(700, 700, 50, 50);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 10, 32));
		g.drawString(Integer.toString(Game.player2.lifePoints), 705, 61);
		g.drawString(Integer.toString(Game.player1.lifePoints), 705, 737);
	}
	
	//*************************************
	//THINGS FOR RENDERING THE MENU STATE
	//THINGS FOR RENDERING THE MENU STATE
	//THINGS FOR RENDERING THE MENU STATE
	//*************************************
	public void renderMenu(Graphics g) {
		g.setColor(new Color(105,182,175));
		g.fillRect(400, 50, 200, 100);
		g.fillRect(400, 250, 200, 100);
		g.fillRect(400, 450, 200, 100);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 10, 64));
		g.drawString("PLAY", 417, 120);
		g.drawString("DECK", 410, 320);
		g.drawString("QUIT", 418, 520);
	}
	//*************************************
	//THINGS FOR RENDERING THE DECK BUILDER STATE
	//THINGS FOR RENDERING THE DECK BUILDER STATE
	//THINGS FOR RENDERING THE DECK BUILDER STATE
	//*************************************
	public void renderDeckBuilder(Game game) {
		
		if (!game.changingDeck) {
			BufferedImage card = new Card(PowerType.Water).getGraphics();
			renderImage(card, 50, 15, 2, 2);
			card = new Card(PowerType.Fire).getGraphics();
			renderImage(card, 150, 15, 2, 2);
			card = new Card(PowerType.Earth).getGraphics();
			renderImage(card, 250, 15, 2, 2);
		}
		
		Deck active = Game.player1.deck;
		int count = 0;
		int y = 180;
		for (int i = 0; i < active.size(); i++) {
			int x = (count*84)+44;
			BufferedImage cardGraphic = active.getCard(i).getGraphics();
			renderImage(cardGraphic, x,y,2, 2);
			if(count<10) {
				count++;
			}else {
				count = 0;
				y += 145;
			}
		}
	}
	public void renderDeckBuilderLines(Game game, Graphics g) {
		g.fillRect(0, 160, 1000, 18);
		
		if (!game.changingDeck) {
			g.fillRect(360, 0, 20, 160);

			g.setColor(Color.CYAN);
			g.fillRect(400, 65, 150, 50);
			g.fillRect(590, 55, 170, 70);
			g.fillRect(800, 65, 150, 50);

			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("Arial", 10, 36));
			g.drawString("CLEAR", 415, 105);
			g.drawString("CHANGE", 600, 90);
			g.drawString("DECK", 623, 120);
			g.drawString("BACK", 825, 105);
		}else {
			g.fillRect(775, 0, 20, 160);

			g.setColor(Color.CYAN);
			g.fillRect(100, 65, 75, 50);
			g.fillRect(200, 65, 75, 50);
			g.fillRect(300, 65, 75, 50);
			g.fillRect(400, 65, 75, 50);
			g.fillRect(500, 65, 75, 50);
			g.fillRect(600, 65, 75, 50);
			g.fillRect(850, 65, 75, 50);
			
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("Arial", 10, 24));
			g.drawString("Deck 1", 101, 96);
			g.drawString("Deck 2", 200, 96);
			g.drawString("Deck 3", 300, 96);
			g.drawString("Deck 4", 400, 96);
			g.drawString("Deck 5", 500, 96);
			g.drawString("Deck 6", 600, 96);

			g.drawString("Back", 860, 96);

		}

	}
	//*************************************
	//THINGS FOR RENDERING THE GAME OVER STATE
	//THINGS FOR RENDERING THE GAME OVER STATE
	//THINGS FOR RENDERING THE GAME OVER STATE
	//*************************************
	public void renderGameOver(Graphics g) {
		String winner= "Player 1";
		if(Game.player2.lifePoints > Game.player1.lifePoints)
			winner = "Player 2";
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 10, 128));
		g.drawString("GAME OVER", 100, 250);
		g.drawString(winner + "Wins!", 100, 500);

	}
	
	//*************************************
	//THINGS FOR RENDERING THE PIXELS
	//THINGS FOR RENDERING THE PIXELS
	//THINGS FOR RENDERING THE PIXELS
	//*************************************
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
	
	public void renderImage(BufferedImage image,int xPos,int yPos,int xZoom, int yZoom) {
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels,image.getWidth(), image.getHeight(), xPos, yPos, xZoom, yZoom);
	}
	
	public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPos, int yPos, int xZoom, int yZoom){
		for (int y = 0; y < renderHeight; y++) 
			for (int x = 0; x < renderWidth; x++) 
				for (int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++) 
					for (int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++)
						setPixel(renderPixels[x + y * renderWidth], (x*xZoom)+xPos+xZoomPos, (y*yZoom)+yPos+yZoomPos);
	}
	
	private void setPixel(int pixel, int x, int y) {
		int pixelIndex = x + y * view.getWidth();
		if(pixelIndex < pixels.length && (pixel != alpha && pixel != 0xFF00FF))
			pixels[pixelIndex] = pixel; 
	}

}