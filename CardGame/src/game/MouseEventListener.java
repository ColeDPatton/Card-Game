package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class MouseEventListener implements MouseListener, MouseMotionListener {

	private Game game;
	private Card selected;
	

	public MouseEventListener(Game game_PreAI) {
		this.game = game_PreAI;
	}


	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int y = e.getY();
			int x = e.getX();

			if(game.getSta() == State.GameOver) {
				game.setSta(State.Menu);
				game.restart();
			}else if (game.getSta() == State.Game) {
				Player active = Game.player1;
				Player opponent = Game.player2;

				if (game.getTurn() == Turn.even) {
					active = Game.player2;
					opponent = Game.player1;
				}
				
				if (y > 105 && y < 160 && x > 800 && x < 1000)
					game.nextTurn();
				else if(y > 25 && y < 80 && x > 900 && x < 1000) {
					game.setSta(State.Menu);
					game.restart();
				}
				
					
				if (y < active.hand.getBottom() && y > active.hand.getTop()) {
					if (x > 69 && x < 139 && active.canSummon()) {
						if (active.canDraw())
							active.draw();
						else if (y > active.hand.getTop() && y < active.hand.getTop()+25 && active.hand.hasThree(PowerType.Earth))
							active.hand.tradeIn(PowerType.Earth);
						else if (y > active.hand.getTop() +40 && y < active.hand.getTop()+65 && active.hand.hasThree(PowerType.Fire))
							active.hand.tradeIn(PowerType.Fire);
						else if (y > active.hand.getTop() +80 && y < active.hand.getTop()+105 && active.hand.hasThree(PowerType.Water))
							active.hand.tradeIn(PowerType.Water);

					}
					for (int i = 0; i < active.hand.getSize(); i++) {
						if (x > ((i * 75) + 150) && x < ((i * 75) + 150) + 65) {
							if (active.hand.getCard(i).isSelected() && active.hand.getCard(i).isMonster && active.canSummon()) {
								if (y < active.hand.getTop() + 40)
									active.playCard(i);
								if (i < active.hand.getSize())
									active.hand.getCard(i).setSelected(false);
							} else if(active.hand.getCard(i).isSelected() && active.canPlaySpell){
								if (y < active.hand.getTop() + 40) {
									((SpellCard) active.hand.getCard(i)).use();
									selected = active.hand.getCard(i);
								}else {
									((SpellCard) active.hand.getCard(i)).using = false;
								}
							} else if((active.canSummon() && active.hand.getCard(i).isMonster) || (active.canPlaySpell && !active.hand.getCard(i).isMonster)){
								active.hand.unselectAll();
								active.hand.getCard(i).setSelected(true);
							}
						}
					}
				} else if (y < active.field.getBottom() && y > active.field.getTop()) {
					for (int i = 0; i < active.field.getSize(); i++) {
						if (x > active.field.getCard(i).getX() && x < active.field.getCard(i).getX() + 100) {
							if(selected != null && !selected.isMonster && ((SpellCard) selected).using()) {
								active.playSpellCard(active.field.getCard(i), (SpellCard) selected);
							}
							if (active.field.getCard(i).isSelected()) {
								selected = active.field.getCard(i);
								if (y < active.field.getTop() + 35 && game.getTurn() != Turn.First && !selected.used) {
									active.field.getCard(i).attacking = true;
									active.field.getCard(i).blocking = false;
								} else if (y > active.field.getBottom() - 40 && !selected.used) {
									active.field.getCard(i).attacking = false;
									active.field.getCard(i).blocking = true;
								} else if (y < active.field.getTop() + 115 && y > active.field.getTop() + 75 && !selected.usedSkill && game.getTurn() != Turn.First) {
									game.useSpell(active, selected);
								} else {
									active.field.getCard(i).setSelected(false);
									active.field.getCard(i).attacking = false;
									active.field.getCard(i).blocking = false;
								}
							} else {
								active.field.unselectField();
								active.field.getCard(i).setSelected(true);
							}
						}
					}
					if(x>Location.blueGem.getX() && x<Location.blueGem.getX()+25) {
						if(y>active.field.getTop()+25 && y < active.field.getTop()+50) {
							active.summonSpecial(new Card(PowerType.Fire2));
						}else if(y>active.field.getTop()+75 && y < active.field.getTop()+100) {
							active.summonSpecial(new Card(PowerType.Water2));
						}else if(y>active.field.getTop()+125 && y < active.field.getTop()+150) {
							active.summonSpecial(new Card(PowerType.Earth2));
						}else if(y>active.field.getTop()+175 && y < active.field.getTop()+200) {
							active.summonSpecial(new Card(PowerType.Purple));
						}
						
					}

				} else if (y < opponent.field.getBottom() && y > opponent.field.getTop()) {
					if (selected != null && selected.attacking) {
						for (int i = 0; i < opponent.field.getSize(); i++) {
							if (x > opponent.field.getCard(i).getX() && x < opponent.field.getCard(i).getX() + 150) {
								Game.battle(selected, opponent.field.getCard(i));
							}
						}
					}
				} else if(x>700 && x<750 && selected!=null && selected.attacking && 
						(active.canAttackDirectly || opponent.field.getSize() == 0 || selected.canAttackDirectly)) {
					if(active == Game.player1 && (y>25 && y<75))
						game.directAttack(selected);
					else if(y>700 && y<750)
						game.directAttack(selected);
				}
			}else if(game.getSta() == State.Menu) {
				if(x<600 && x>400) {
					if(y<150 && y>50) {
						game.setSta(State.Game);
						Game.player1.deck.shuffle();
						Game.player2.deck.shuffle();

						Game.player1.draw(5);
						Game.player2.draw(5);
					} 
					else if (y < 350 && y > 250)
						game.setSta(State.deckBuilder);
//					else if (y < 350 && y > 250) {
//						game.setSta(State.tutorial);
//					}
					else if (y < 550 && y > 450)
						System.out.println("Quit");
				}
			} else if (game.getSta() == State.deckBuilder) {
				Deck custom = Game.player1.deck;
				Deck saveSpot = new Deck();
				// adding
				if (y > 15 && y < 145) {
					if (!game.changingDeck) {
						if (x > 50 && x < 120)
							custom.add(new Card(PowerType.Water));
						else if (x > 150 && x < 220)
							custom.add(new Card(PowerType.Fire));
						else if (x > 250 && x < 320)
							custom.add(new Card(PowerType.Earth));
						if (y > 65 && y < 115) {
							if (x > 400 && x < 550)
								custom.clear();
							else if (x > 800 && x < 950) {
								Game.player1.deck = custom.copy();
								Game.player1.deck.shuffle();
								game.setSta(State.Menu);
							}
						}
						if (y > 55 && y < 125 && x > 590 && x < 760) {
							game.changingDeck = true;
							if(saveSpot.equals(game.d1))
								game.d1 = custom;
							else if(saveSpot.equals(game.d2))
								game.d2 = custom;
							else if(saveSpot.equals(game.d3))
								game.d3 = custom;
							else if(saveSpot.equals(game.d4))
								game.d4 = custom;
							else if(saveSpot.equals(game.d5))
								game.d5 = custom;
							else if(saveSpot.equals(game.d6))
								game.d6 = custom;
						}
					} else {
						if (y > 65 && y < 115) {
							if (x > 100 && x < 175) {
								saveSpot = game.d1;
								custom = game.d1.copy();
								Game.player1.deck = game.d1;
							} else if (x > 200 && x < 275) {
								saveSpot = game.d2;
								custom = game.d2.copy();
								Game.player1.deck = game.d2;
							} else if (x > 300 && x < 375) {
								saveSpot = game.d3;
								custom = game.d3.copy();
								Game.player1.deck = game.d3;
							} else if (x > 400 && x < 475) {
								saveSpot = game.d4;
								custom = game.d4.copy();
								Game.player1.deck = game.d4;
							} else if (x > 500 && x < 575) {
								saveSpot = game.d5;
								custom = game.d5.copy();
								Game.player1.deck = game.d5;
							} else if (x > 600 && x < 675) {
								saveSpot = game.d6;
								custom = game.d6.copy();
								Game.player1.deck = game.d6;
							} else if (x > 850 && x < 925)
								game.changingDeck = false;
						}
					}
				}

				// removing
				Card[][] layout = new Card[4][11];
				int count = 0;
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 11; j++) {
						if (count < custom.size()) {
							layout[i][j] = custom.getCard(count);
							if ((x > (j * 84) + 44 && x < (j * 84) + 110)
									&& (y > (i * 145) + 180 && y < (i * 145) + 310))
								if (layout[i][j] != null) {
									custom.remove(custom.getCard(count));
									count--;
								}
							count++;
						}
					}
				}

			} else if (game.getSta() == State.tutorial) {
				if (game.stage == TutorialStages.stage1) {
					if (y < Game.player1.hand.getBottom() && y > Game.player1.hand.getTop())
						if (x > ((1 * 75) + 150) && x < ((1 * 75) + 150) + 65) {
							if (!Game.player1.hand.getCard(1).isSelected())
								Game.player1.hand.getCard(1).setSelected(true);
							else if (y < Game.player1.hand.getTop() + 40) {
								Game.player1.playCard(1);
								game.stage = TutorialStages.stage2;
							}
						}
				}else if(game.stage == TutorialStages.stage2) {
					if (y < Game.player1.field.getBottom() && y > Game.player1.field.getTop()) {
						if (x > Game.player1.field.getCard(0).getX() && x < Game.player1.field.getCard(0).getX() + 100) {
							if(!Game.player1.field.getCard(0).isSelected()) {
								Game.player1.field.getCard(0).setSelected(true);
							}
							else if(Game.player1.field.getCard(0).isSelected()) {// && y < Game.player1.field.getTop() + 35
								Game.player1.field.getCard(0).attacking=true;
							}
						}
					} else if (y < Game.player2.field.getBottom() && y > Game.player2.field.getTop() && 
							x > Game.player2.field.getCard(0).getX() && x < Game.player2.field.getCard(0).getX() + 150) {
						if(Game.player1.field.getCard(0).attacking)
							Game.battle(selected, Game.player2.field.getCard(0));
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
