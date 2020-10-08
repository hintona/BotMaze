import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author fahmidahamid
 *
 */


/* Some good resources
 * https://rosettacode.org/wiki/Maze_generation#Java
 * I have taken some idea for implementing the printMazeBoard(...)
 */

class MazeBoard
{
	private int d;
	private Cell[][] board;
	protected Random rg;

//Creates a new MazeBoard
	public MazeBoard(int d){
		this.rg = new Random();
		this.d = d;
		board = new Cell[d][d];

		/*
		 * Initially all the cells have walls all around them
		 */
		for(int i = 0; i < d; i++)
			for(int j = 0; j < d; j++)
				board[i][j] = new Cell(i,j);
	}

	public Cell cellAt(int i, int j){
		return board[i][j];
	}

	//Adds robots to the maze with random numbers of bots
	public void populate(){
		int pNum = 0;
		int cNum = 0;
		int sNum = 0;
		int dNum = 0;
		//Decides how many bots to add based on maze size
		int botNum = (int)(d*d*0.05);
		while(botNum > 0){
			if(pNum == dNum) pNum++;
			else if(pNum > cNum) cNum++;
			else if(cNum > sNum) sNum++;
			else if(sNum > dNum) dNum++;
			botNum--;
		}

		this.populate(pNum,cNum,sNum,dNum);
	}

	//Adds robots to the maze given a specified number of each type
	public void populate(int pNum, int cNum, int sNum, int dNum){
		//Add the rest of the bot types
		for(int i = 0;i<=pNum-1;i++){
			this.updateCell(rg.nextInt(d),rg.nextInt(d), new PoliceBot("p"+i));
		}

		for(int i = 0;i<=cNum-1;i++){
			this.updateCell(rg.nextInt(d),rg.nextInt(d), new ScientistBot("c"+i));
		}

		for(int i = 0;i<=sNum-1;i++){
			this.updateCell(rg.nextInt(d),rg.nextInt(d), new StudentBot("s"+i));
		}

		for(int i = 0;i<=dNum-1;i++){
			this.updateCell(rg.nextInt(d),rg.nextInt(d), new DoctorBot("d"+i));
		}

		//Alice is added last, since she'll always be at the same spot in the maze
		//Bob is added separately, since he is the one who starts the game. He's added in the play method
		this.updateCell(d-1,d-1, new AliceBot());
	}

	//Updates the cells with their new contents
	public boolean updateCell(int i, int j, Robot r)
	{
		if((i < this.d && i >= 0) && (j<this.d && j >= 0)) {
			board[i][j].putRobot(r);
			if(r.getName() == "Ender") board[i][j].isVisited(); ///here
			return true;
		}
		else
			return false;
	}

	public void printMazeBoard()
	{
		for(int j = 0; j < this.d;j++){
			//Makes the top of each cell
			for(int i = 0; i < this.d;i++){
				if(board[i][j].cWall.up){
					System.out.print("+---");
				}
				else System.out.print("+   ");
			}
			System.out.print("+\n");

			//Makes the sides of each cell
			System.out.print("|");
			for(int i = 0 ; i <this.d;i++){
				if(board[i][j].cWall.right){
					if(!board[i][j].isEmpty()) System.out.print("   |");
					else{
						System.out.print(" "+board[i][j].whoIsHere()+" |");
					}
				}
				else{
					if(!board[i][j].isEmpty()) System.out.print("    ");
					else{
						System.out.print(" "+board[i][j].whoIsHere()+"  ");
					}
				}
			}
			System.out.print("\n");
		}
		for(int j = 0;j < this.d;j++) System.out.print("+---");
		System.out.print("+\n");

		/* This is the code given to us, I couldn't make it work with the primsAlgorithm so I
		* just redid it. I have left it in here just in case something happens and I need to
		* look at it again. The actual working code for this method is written above.
		for(int i = 0; i < this.d; i++) {
				// draw the north edge
				for (int j = 0; j < this.d; j++) {
					System.out.print("+---");
				}
				System.out.println("+");
				// draw the west edge
				for (int j = 0; j < this.d; j++) {
					if(this.board[i][j].isEmpty()) {
						System.out.print("| " + board[i][j].whoIsHere() + " ");
					}
					else {
						 System.out.print("|   ");
					}
				}
				System.out.println("|");
		 }
		// draw the bottom line
		for (int j = 0; j < this.d; j++) {
			System.out.print("+---");
		}
		System.out.println("+");

		 */
	}


	public void primsAlgorithm(int rootx, int rooty) {
		int i = rootx;
		int j = rooty;

		while(i != d-1 || j != d-1){
			List<Character> choices = new ArrayList<>();
			choices.add('u');
			choices.add('d');
			choices.add('l');
			choices.add('r');

			//examine for board edges
			if(j == 0) choices.remove((Character)'u');
			if(i == d-1) choices.remove((Character)'r');
			if(i == 0) choices.remove((Character)'l');
			if(j == d-1) choices.remove((Character)'d');

			//select remaining wall from list at random and remove it
			char w = choices.get(rg.nextInt(choices.size()));
			board[i][j].cWall.removeWall(w);

			//go to neighbouring cell and remove complementary wall, then set neighbouring cell to new one
			if(w == 'u'){
				board[i][j-1].cWall.removeWall('d');
				j--;
			}
			if(w == 'd'){
				board[i][j+1].cWall.removeWall('u');
				j++;
			}
			if(w == 'r'){
				board[i+1][j].cWall.removeWall('l');
				i++;
			}
			if(w == 'l'){
				board[i-1][j].cWall.removeWall('r');
				i--;
			}
		}
	}
	public void play(MazeBoard mzb){
		BobBot bob = new BobBot(mzb,d);
		this.updateCell(0,0,bob);
		bob.move();

	}
}


