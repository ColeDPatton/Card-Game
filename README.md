# Card-Game
"Card" Game coded in Java. Inspired by games like Yugioh and Hearthstone.
RULES:
The game is played by using cards to attack your opponent until their score reaches zero. Players take damage each time their monster dies and whenever they get attacked directly.

1. DRAW
  Each turn you may draw a card from your deck by clicking on the pile of face down (brown) cards in the bottom left corner   of the screen.
  
2. SUMMON
  After drawing you may play up to 3 monsters. A monster can be played in two ways. The first is by clicking on the picture of the card in your hand, and then selecting "Summon". Then second is by clicking on the gems along right side of the screen. Gems are obtained each time one of your cards die and you need two gems to summon a card. 
  
3. ASSIST CARDS
  Assist cards have a dull yellow background and are used to improve one of your monsters. To use, select an assist card, click activate, and then click a monster on your field.
Sword: increases attack by 1
Armor: increases health by 1
Arrows point up: evolve a basic monster to the upgraded(level 2) version

4. BATTLE
  Use a played monster to attack by clicking on it, selecting "attack", and then clicking on an opponents monster. If they don't have any monsters, you can click on their score (near the top right corner) to attack them directly. Each card has a type, either earth(green), fire(red), or water(blue), and each card has an "attack" and "health" stat that comes into play for battles. All the types have advantages and disadvantages over each other.
  Fire is strong against earth.
  Water is strong against fire.
  earth is strong against water.
When a card has an advantage over another card, their attack is doubled and the card getting attacked has it's attack halved (odd numbers get rounded down to the nearest whole number). After advantages are calculated, each monster deals takes damage equal to the opposing monsters attack. Monsters with a remaining health of zero or less is killed.

5. SPELLS
  Upgraded(level 2) monsters also have spells that can be used instead of an attack.
  Fire: increase the health of all friendly monsters by 1.
  Water: AFTER ATTACKING the spell can be used to allow a second attack.
  Earth: After selecting "spell" you can click "attack" and then attack directly, even if the opponent has monsters on the      field.

6. END TURN

