package game;

public enum Location {
	// left to right
	one,
	two,
	three,
	four,
	five,
	oneZ,
	twoZ,
	threeZ,
	fourZ,
	fiveZ,
	
	redGem,
	blueGem,
	greenGem,
	purpleGem,
	redGemZ,
	blueGemZ,
	greenGemZ,
	purpleGemZ;
	
	private boolean oneOccupied = false;
	private boolean twoOccupied = false;
	private boolean threeOccupied = false;
	private boolean fourOccupied = false;
	private boolean fiveOccupied = false;
	private boolean oneZOccupied = false;
	private boolean twoZOccupied = false;
	private boolean threeZOccupied = false;
	private boolean fourZOccupied = false;
	private boolean fiveZOccupied = false;
	
	public int getY() {
		int start = 425;
		int startZ = 200;
		int add = 50;
		
		if(this == one || this == two || this == three || this == four || this == five)
			return 400;
		else if(this == oneZ || this == twoZ || this == threeZ || this == fourZ || this == fiveZ)
			return 175;
		else if(this == redGem)
			return start;
		else if(this == blueGem)
			return start+(add);
		else if(this == greenGem)
			return start+(add*2);
		else if(this == purpleGem)
			return start+(add*3);
		else if(this == redGemZ)
			return startZ;
		else if(this == blueGemZ)
			return startZ+(add);
		else if(this == greenGemZ)
			return startZ+(add*2);
		else
			return startZ+(add*3);
			
	}
	
	public int getX() {
		int start = 50;
		int add = 175;
		if(this==redGem || this==blueGem || this==greenGem || this==purpleGem || 
				this==redGemZ || this==blueGemZ || this==greenGemZ || this==purpleGemZ)
			return start+(add*5);		
		else if(this == one)
			return start+(add*2);
		else if(this == two)
			return start+add;
		else if(this == three)
			return start+(add*3);
		else if(this == four)
			return start;
		else if(this==five)
			return start+(add*4);
		else if(this == oneZ)
			return start+(add*2);
		else if(this == twoZ)
			return start+add;
		else if(this == threeZ)
			return start+(add*3);
		else if(this == fourZ)
			return start;
		else
			return start+(add*4);
	}
	public boolean getFilled(){
		if(this == one)
			return oneOccupied;
		else if(this == two)
			return twoOccupied;
		else if(this == three)
			return threeOccupied;
		else if(this == four)
			return fourOccupied;
		else if(this == five)
			return fiveOccupied;
		else if(this == oneZ)
			return oneZOccupied;
		else if(this == twoZ)
			return twoZOccupied;
		else if(this == threeZ)
			return threeZOccupied;
		else if(this == fourZ)
			return fourZOccupied;
		else
			return fiveZOccupied;
	}
	public void setFilled(Boolean b){
		if(this == one)
			oneOccupied = b;
		else if(this == two)
			twoOccupied = b;
		else if(this == three)
			threeOccupied = b;
		else if(this == four)
			fourOccupied = b;
		else if(this == five)
			fiveOccupied = b;
		else if(this == oneZ)
			oneZOccupied = b;
		else if(this == twoZ)
			twoZOccupied = b;
		else if(this == threeZ)
			threeZOccupied = b;
		else if(this == fourZ)
			fourZOccupied = b;
		else
			fiveZOccupied = b;
	}
}
