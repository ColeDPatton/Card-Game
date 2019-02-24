package game;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class Game extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage testBackground;
	private MouseEventListener mouseListener = new MouseEventListener(this);
	
	private Turn turn = Turn.First;
	public static Player player1;
	public static Player player2;
	
	public boolean changingDeck = false;
	public Deck d1 = new Deck(40);
	public Deck d2 = new Deck(40);
	public Deck d3 = new Deck(40);
	public Deck d4 = new Deck(40);
	public Deck d5 = new Deck(40);
	public Deck d6 = new Deck(40);

	private State state = State.Menu;
	public TutorialStages stage;

	public Game() 
	{
		//Make our program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		setBounds(0,0, 1000, 800);

		//Put our frame in the center of the screen.
		setLocationRelativeTo(null);

		//Add our graphics component
		add(canvas);

		//Make our frame visible.
		setVisible(true);

		//Create our object for buffer strategy.
		canvas.createBufferStrategy(3);

		renderer = new RenderHandler(getWidth(), getHeight());
		
		//load images
		testBackground = loadImage("/sprites/game-background.jpg");
	
		//add listeners
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);

		//Create Player1
		player1 = new Player(d1.copy(), true, false);
		
		//Create Player2
		player2 = new AI(d2.copy());

	}
	
	//*************************************
	//BATTLE
	//BATTLE
	//BATTLE
	//*************************************
	public static void battle(Card a, Card b) {
		Player attacking = player1;
		Player gettingAttacked = player2;
		int originalAttackA = a.attackPower;
		int originalAttackB = b.attackPower;

		if (a.getLoc() == Location.oneZ || a.getLoc() == Location.twoZ || a.getLoc() == Location.threeZ
				|| a.getLoc() == Location.fourZ || a.getLoc() == Location.fiveZ) {
			attacking = player2;
			gettingAttacked = player1;
		}

		attacking.setCanSummon(false);

		if (a.getType().advantage(b)) {
			a.attackPower *= 2;
			if (b.attackPower >= 2)
				b.attackPower = (b.attackPower) / 2;
			else
				b.attackPower = 0;
		} else if (a.getType().disadvantage(b)) {
			b.attackPower *= 2;
			if (a.attackPower >= 2)
				a.attackPower = (a.attackPower) / 2;
			else
				a.attackPower = 0;
		}

		a.health -= b.attackPower;
		if(b.blocking)
			a.attackPower--;
		b.health -= a.attackPower;

		if (b.blocking) {
			b.blocking = false;
			if (a.health <= 0 && b.health <= 0) { // both die
				a.convertToGem(attacking.field);
			} else if (b.health <= 0) { // b dies
				b.convertToGem(gettingAttacked.field);
			} else if (a.health <= 0) { // a dies
				a.convertToGem(attacking.field);
				attacking.field.remove(a);
				attacking.lifePoints--;
				if (!gettingAttacked.deck.isEmpty())
					gettingAttacked.draw(1);
			}
		} else {
			if (a.health <= 0 && b.health <= 0) { // both die
				a.convertToGem(attacking.field);
				b.convertToGem(gettingAttacked.field);
				if (!attacking.deck.isEmpty())
					attacking.draw(1);
				attacking.lifePoints--;
				gettingAttacked.lifePoints--;
			} else if (b.health <= 0) { // b dies
				b.convertToGem(gettingAttacked.field);
				gettingAttacked.lifePoints--;
				if (!attacking.deck.isEmpty())
					attacking.draw(1);
			} else if (a.health <= 0) { // a dies
				a.convertToGem(attacking.field);
				attacking.lifePoints--;
				if (!gettingAttacked.deck.isEmpty())
					gettingAttacked.draw(1);
			}
		}

		a.attacking = false;
		attacking.field.unselectField();
		gettingAttacked.field.unselectField();
		attacking.hand.unselectAll();
		gettingAttacked.hand.unselectAll();

		a.attackPower = originalAttackA;
		b.attackPower = originalAttackB;

		a.used = true;
		attacking.canPlaySpell = false;

	}
	//*************************************
	//DIRECT ATTACK
	//DIRECT ATTACK
	//DIRECT ATTACK
	//*************************************
	public void directAttack(Card a) {
		Player attacking = player1;
		Player gettingAttacked = player2;

		if (a.getLoc() == Location.oneZ || a.getLoc() == Location.twoZ || a.getLoc() == Location.threeZ
				|| a.getLoc() == Location.fourZ || a.getLoc() == Location.fiveZ) {
			attacking = player2;
			gettingAttacked = player1;
		}

		attacking.setCanSummon(false);

		a.attacking = false;
		attacking.field.unselectField();
		gettingAttacked.field.unselectField();
		attacking.hand.unselectAll();
		gettingAttacked.hand.unselectAll();
		
		a.used = true;
		
		gettingAttacked.lifePoints -= a.attackPower;
	}
	//*************************************
	//MONSTER SPELLS
	//MONSTER SPELLS
	//MONSTER SPELLS
	//*************************************
	public void useSpell(Player using, Card a) {
		Player attacking = player1;
		Player gettingAttacked = player2;

		if (a.getLoc() == Location.oneZ || a.getLoc() == Location.twoZ || a.getLoc() == Location.threeZ
				|| a.getLoc() == Location.fourZ || a.getLoc() == Location.fiveZ) {
			attacking = player2;
			gettingAttacked = player1;
		}
		if(a.getType() == PowerType.Water2 && !a.used)
			return;
		if((a.getType() == PowerType.Fire2 || a.getType() == PowerType.Purple) && a.used)
			return;
		
		a.getType().spell(a, attacking.field, gettingAttacked.field);
		a.usedSkill = true;
		if(a.getType() == PowerType.Fire2 || a.getType() == PowerType.Purple)
			a.used = true;
		for (int i = 0; i < gettingAttacked.field.getSize(); i++) {
			if(gettingAttacked.field.getCard(i).health <=0) {
				gettingAttacked.field.getCard(i).convertToGem(gettingAttacked.field);
				i--;
			}
		}
	}
	
	public void update() {

	}
	
	public static BufferedImage loadImage(String path) {
		try {
			BufferedImage loadImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage Formatted = new BufferedImage(loadImage.getWidth(), loadImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			Formatted.getGraphics().drawImage(loadImage, 0, 0, null);
			return Formatted;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

		renderer.renderImage(testBackground, 0, 0, 1, 1);

		if(state == State.Game || state==State.tutorial) {
			renderer.renderHands(this);
			renderer.renderField(this);
			renderer.renderDecks();
		} else if(state==State.deckBuilder){
			renderer.renderDeckBuilder(this);
		}
		
		
		renderer.render(graphics);
		
		
		if(state == State.Menu) {
			renderer.renderMenu(graphics);
		} else if (state==State.Game) {
			renderer.renderInGameMenu(graphics);
			renderer.renderScore(this, graphics);
			renderer.renderCardStats(this, graphics);
			renderer.renderGems(graphics);
			renderer.renderHandTradeInButtons(this, graphics);
		} else if (state == State.GameOver) {
			renderer.renderGameOver(graphics);
		} else if (state == State.deckBuilder) {
			renderer.renderDeckBuilderLines(this, graphics);
		}
		
		if(player1.lifePoints <= 0 || player2.lifePoints <=0) {
			state = State.GameOver;
		}
		graphics.dispose();
		bufferStrategy.show();

	}

	@SuppressWarnings("unused")
	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}

	}

	public static void main(String[] args) 
	{
		
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

	public MouseEventListener getMouseListener() {
		return mouseListener;
	}
	public Turn getTurn() {
		return turn;
	}
	public void nextTurn() {
		player1.field.unselectField();
		player1.hand.unselectAll();
		player2.field.unselectField();
		player2.hand.unselectAll();
		player1.setCanSummon(true);
		player2.setCanSummon(true);
		player1.setCanDraw(true);
		player2.setCanDraw(true);
		player1.canAttackDirectly = false;
		player2.canAttackDirectly = false;
		if(turn == Turn.First || turn == Turn.odd) {
			turn = Turn.even;
			if(player1.field.allDefense())
				player2.canAttackDirectly = true;
			player2.field.unblockField();
			player2.field.unuse();
			player1.field.unuse();
			player2.canPlaySpell=true;
			
			if (player2.isAI) {
				takeTurn((AI) player2);
			}

		} else {
			turn = Turn.odd;
			if (player2.field.allDefense())
				player1.canAttackDirectly = true;
			player1.field.unblockField();
			player1.field.unuse();
			player2.field.unuse();
			player1.canPlaySpell = true;
		}

	}

	public State getSta() {
		return state;
	}

	public void setSta(State s) {
		state = s;
		if (s == State.tutorial) {
			stage = TutorialStages.stage1;
			stage.setUp(this);
		}

	}

	public void restart() {
		player1 = new Player(d1.copy(), true, false);
		player1.hand.unselectAll();
		player2 = new AI(d2.copy());

		turn = Turn.First;

		for (Location loca : Location.values()) {
			loca.setFilled(false);
		}
	}

	public void takeTurn(AI ai) {
		ai.draw(1);
		if (ai.canSummon())
			ai.playCards();

		for (int i = 0; i < ai.field.getSize(); i++) {
			for (int j = 0; j < player1.field.getSize(); j++) {
				if (ai.field.getCard(i).getType().advantage(player1.field.getCard(j))) {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					battle(ai.field.getCard(i), player1.field.getCard(j));
					break;
				}
			}
		}
		for (int i = 0; i < ai.field.getSize(); i++) {
			for (int j = 0; j < player1.field.getSize(); j++) {
				if (!ai.field.getCard(i).getType().disadvantage(player1.field.getCard(j))
						&& !ai.field.getCard(i).used) {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					battle(ai.field.getCard(i), player1.field.getCard(j));
					break;
				}
			}

		}
		if (player1.field.getSize() == 0) {
			for (int i = 0; i < ai.field.getSize(); i++) {
				if (!ai.field.getCard(i).used) {
					directAttack(ai.field.getCard(i));
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			for (int i = 0; i < ai.field.getSize(); i++) {
				if (!ai.field.getCard(i).used)
					ai.field.getCard(i).blocking = true;
			}
		}
		nextTurn();
	}
	
	
	
}