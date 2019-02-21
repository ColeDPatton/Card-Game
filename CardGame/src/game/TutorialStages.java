package game;

public enum TutorialStages {
	stage1,
	stage2,
	stage3,
	stage4,
	stage5,
	stage6,
	stage7;

	public void setUp(Game g) {
		if(this == stage1) {
			Game.player1.hand.add(new Card(PowerType.Fire));
			Game.player1.hand.add(new Card(PowerType.Water));
			Game.player1.hand.add(new Card(PowerType.Earth));

			Game.player2.field.addCard(new Card(PowerType.Fire), false, true);
		} else if(this == stage2) {
			Game.player1.hand.add(new Card(PowerType.Fire));
			Game.player1.hand.add(new Card(PowerType.Earth));
			Game.player1.field.addCard(new Card(PowerType.Water), false, true);

			Game.player2.field.addCard(new Card(PowerType.Fire), false, true);
		} else if(this == stage3) {
			
		} else if(this == stage4) {
			
		} else if(this == stage5) {
			
		} else if(this == stage6) {
			
		} else if(this == stage7) {
			
		}
	}
}
