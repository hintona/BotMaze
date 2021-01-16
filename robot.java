/* This Robot interface will be used to create the robots used
in the maze
 */
package BotMaze;
import java.util.ArrayList;

interface Robot {
  String getName();
  char getType();
}

/* This will create a Bob robot with the necessary abilities to look for
* an Alice robot. Since Bob is a special robot who gets extra knowledge, we pass
* his constructor the mazeboard and its size
 */
class BobBot implements Robot{
  protected String name;
  protected char type;
  protected int stepsTaken;
  protected MazeBoard mzb;
  protected int d;

  public BobBot(MazeBoard board,int d){
    this.name = "Ender"; ///
    this.type = 'E';
    this.stepsTaken = 0;
    this.mzb = board;
    this.d = d;
  }

  public String getName() {
    return name;
  }

  public char getType(){
    return type;
  }


  public void move(){
    //Bob will always start at the beginning of the maze, so we can tell him his starting position
    int i = 0;
    int j = 0;
    this.mzb.updateCell(i,j,this);
    while(true){
      //This will be Bob's list of best choices for moving
      ArrayList<Character> choices = new ArrayList<>();
      choices.add('u');
      choices.add('d');
      choices.add('l');
      choices.add('r');

      //This will be Bob's back up choices if he can't find any new cells
      ArrayList<Character> backtrack = new ArrayList<>();

      //Bob examines his surroundings for open walls. If the wall is there, he removes it from his
      //choices. If it is open, he looks for Alice. Any cells he's already been to get removed from
      //choices and added to backtrack.
      if(this.mzb.cellAt(i,j).cWall.isThere('u')){
        choices.remove((Character) 'u');
      }
      else{
        if(this.mzb.cellAt(i,j-1).isEmpty()){
          checkForAlice(this.mzb.cellAt(i,j-1));
          choices.remove((Character) 'u');
        }
        else if(this.mzb.cellAt(i,j-1).wasVisited()){
          backtrack.add('u');
          choices.remove((Character) 'u');
        }
      }

      if(this.mzb.cellAt(i,j).cWall.isThere('d')){
        choices.remove((Character) 'd');
      }
      else{
          if(this.mzb.cellAt(i,j+1).isEmpty()){
            checkForAlice(this.mzb.cellAt(i,j+1));
            choices.remove((Character) 'd');
          }
          else if(this.mzb.cellAt(i,j+1).wasVisited()){
            backtrack.add('d');
            choices.remove((Character) 'd');
          }
      }

      if(this.mzb.cellAt(i,j).cWall.isThere('l')){
        choices.remove((Character) 'l');
      }
      else{
        if(this.mzb.cellAt(i-1,j).isEmpty()){
          checkForAlice(this.mzb.cellAt(i-1,j));
          choices.remove((Character) 'l');
        }
        else if(this.mzb.cellAt(i-1,j).wasVisited()){
          backtrack.add('l');
          choices.remove((Character) 'l');
        }
      }

      if(this.mzb.cellAt(i,j).cWall.isThere('r')){
        choices.remove((Character) 'r');
      }
      else{
        if(this.mzb.cellAt(i+1,j).isEmpty()){
          checkForAlice(this.mzb.cellAt(i+1,j));
          choices.remove((Character) 'r');
        }
        else if(this.mzb.cellAt(i+1,j).wasVisited()){
          backtrack.add('r');
          choices.remove((Character) 'r');
        }
      }

      //With Alice unfound, Bob now selects from his list of options to move to. If he sees new cells, he
      // goes to them. If not, he picks an old one to backtrack to. He marks his cell his
      //cell as empty and prepares to go to a new one
      char c = ' ';
      if(choices.size() != 0){
        c = choices.get(this.mzb.rg.nextInt(choices.size()));
      }
      else{
        c = backtrack.get(this.mzb.rg.nextInt(backtrack.size()));
      }

      this.mzb.cellAt(i,j).replaceObject();
      if(c == 'l'){
        i--;
      }
      if(c == 'r'){
        i++;
      }
      if(c == 'u'){
        j--;
      }
      if(c == 'd'){
        j++;
      }

      //Bob goes to a new cell, marks it, and checks if he'll give up
      this.mzb.updateCell(i,j,this);
      stepsTaken++;
      if(stepsTaken >= 0.9*d*d){
        System.out.print("Ender: I've searched so much of this maze for Ferris and I can't find him. I've taken "+stepsTaken+" steps. ;w;");
        System.exit(0);
      }

      this.mzb.printMazeBoard();
    }
  }

  public void checkForAlice(Cell c){
    if(c.whoIsHere()=='F'){
      System.out.println("Ender: Ferris! I've finally found you, after "+stepsTaken+" steps!"); ///
      System.out.print("Ferris: Ender! Wow, that sure sounds like a ~long~ journey uwu"); ///
      System.exit(0);
    }
    else return;
  }
}

/*
This will create an Alice Robot. Since we will only have one, we only
need a basic constructor
 */
class AliceBot implements Robot{
  String name;
  char type;

  public AliceBot(){
    this.name = "Ferris";///
    this.type = 'F';///
  }

  public String getName() {
    return name;
  }

  public char getType(){
    return type;
  }

}

/*
This will create a policeman type Robot. Since we could have multiple of
these bots, the constructor will require a name for each bot.
 */
class PoliceBot implements Robot{
  String name;
  char type;

  public PoliceBot(String name){
    this.name = name;
    this.type = 'P';
  }

  public String getName() {
    return name;
  }

  public char getType(){
    return type;
  }
}

/*
This will create a Student type Robot. Since we could have multiple of
these bots, the constructor will require a name for each bot.
 */
class StudentBot implements Robot{
  String name;
  char type;

  public StudentBot(String name){
    this.name = name;
    this.type = 'S';
  }

  public String getName() {
    return name;
  }

  public char getType(){
    return type;
  }
}

/*
This will create a Scientist type Robot. Since we could have multiple of
these bots, the constructor will require a name for each bot.
 */
class ScientistBot implements Robot{
  String name;
  char type;

  public ScientistBot(String name){
    this.name = name;
    this.type = 'C';
  }

  public String getName() {
    return name;
  }

  public char getType(){
    return type;
  }
}

/*
This will create a policeman type Robot. Since we could have multiple of
these bots, the constructor will require a name for each bot.
 */
class DoctorBot implements Robot{
  String name;
  char type;

  public DoctorBot(String name){
    this.name = name;
    this.type = 'D';
  }

  public String getName() {
    return name;
  }

  public char getType(){
    return type;
  }
}
