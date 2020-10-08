class Wall {

	protected boolean up, down, left, right;

	Wall()
	{
		//by default, we add the walls
		up = down = left = right = true;
	}

	public boolean isThere(char c){
		if(c == 'l') return left;
		else if(c == 'r') return right;
		else if(c == 'd') return down;
		else return up;
	}

	public void removeWall(char c)
	{
		if(c == 'l') left = false;
		else if (c == 'r') right = false;
		else if (c == 'u') up = false;
		else if (c == 'd') down = false;
		else
			return;
	}
	public void addWall(char c)
	{
		if(c == 'l') left = true;
		else if (c == 'r') right = true;
		else if (c == 'u') up = true;
		else if (c == 'd') down = true;
		else
			return;
	}

}


public class Cell {

	protected int i;
	protected int j;
	protected boolean emptyCell; // true -> cell is empty, false -> cell is occupied
	protected Robot bot;
	protected Wall cWall;
	protected boolean visited;
	protected int priority;

	public Cell(int i, int j){
		this.i = i;
		this.j = j;
		this.emptyCell = true; // by default we assume that the cell is empty
		this.bot = null; // since the cell is empty, no one is occupying it
		cWall = new Wall();
		this.visited = false;
		this.priority = 1;
	}

	public boolean putRobot(Robot r)
	{
		if (r.getType() != 'C' && r.getType() != 'P' && r.getType() != 'D' && r.getType() != 'S' && r.getType() != 'E' && r.getType() != 'F') { ///
			System.out.println("You are trying to put invalid/unknown object in a cell");
			return false; //operation unsuccessful
		}
		else {
			this.bot = r;
			this.emptyCell = false;
			return true; //operation successful
		}
	}


	public void replaceObject()
	{
		this.emptyCell = true;
		this.bot = null;
	}

	public void isVisited(){
		this.visited = true;
	}

	public boolean wasVisited(){
		return this.visited;
	}

	public char whoIsHere() {
		return this.bot.getType();
	}

	public boolean isEmpty()
	{
		return !emptyCell;
	} //returns false if the cell is empty


	


}
