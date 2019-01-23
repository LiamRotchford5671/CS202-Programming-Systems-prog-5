//Liam Rotchford
// 11/28/18
package Main;

/* This class handles chance event nodes, dependant on the event there will be a value assigned to the bonus and consequence variables, along with the adjoining potion and poison variables to determine
    what player stat they should apply that value to. This class has a focus on the altering the player stats in terms of health, mana, and stamina. In the chance class there is a constructor to
    initialize all values to 0, a constructor with args to take in values and set the chance class variables and the base class variables it inherits. A copy constructor, and a derived chance display
    that is dynamically bound via base class.
 */
public class Chance extends Journey{
    protected int bonus;
    protected int potion;
    protected int consequence;
    protected int poison;

    //CONSTRUCTOR
    public Chance(){
        bonus = 0;
        potion = 0;
        consequence = 0;
        poison = 0;
    }

    //COPY CONSTRUCTOR
    public Chance(final Chance c){
        this.bonus = c.bonus;
        this.potion = c.potion;
        this.consequence = c.consequence;
        this.poison = c.poison;
        super.copy(c);              //call the journey base class copy constructor
    }

    //CONSTRURTOR WITH ARGS
    public Chance(String set, String event, int num, int gain, int gain_type, int loss, int loss_type){
        setting = set;              //set the chance variables and the chance variables to the args
        type = event;
        ID = num;
        bonus = gain;
        potion = gain_type;
        consequence = loss;
        poison = loss_type;
    }

    //DERIVED CHANCE DISPLAY
    public int display(Player character){
        Main mainobj = new Main();                              //create main object to access main functions to be used for the users prompts and read in choices in run time without needing to exit function

        System.out.print("\n===========================================================");
        System.out.print("\n\to Space ID: " + ID);
        System.out.print("\n\to Setting: \n\t\t-" + setting);
        int path = mainobj.path_choice();                    //prompt user to choose a path via a i/o prompt and read in the main class
        System.out.print("\n\to Chance Event: \n\t\t-" + type);
        int event = mainobj.eventchoice();                      //prompt user for the event choice to accept or decline it via a main class function to prompt and read they're input

        if(event == 1) {  //accepted the event                  //if they accept then print the event affecting values
            accepted = 1;                                        //set accepting value to 1 so the history print knows to print it as well
            System.out.print("\n\to Bonus Value: " + bonus);
            System.out.print("\n\to Bonus Type: ");

            if (potion == 1) {                               //determine what stat the bonus applies to
                System.out.print("Health");
                character.health += bonus;
            } else if (potion == 2) {
                System.out.print("Mana");
                character.mana += bonus;
            } else if (potion == 3) {
                System.out.print("Stamina");
                character.stamina += bonus;
            }
            System.out.print("\n\to Consequence Value: " + consequence);
            System.out.print("\n\to Consequence Type: ");   //determine what stat the consequence value applies too
            if (poison == 1) {
                System.out.print("Health");
                character.health -= consequence;
            } else if (poison == 2) {
                System.out.print("Mana");
                character.mana -= consequence;
            } else if (poison == 3) {
                System.out.print("Stamina");
                character.stamina -= consequence;
            }
        }

        //update the player stat variables and then display them
        character.update_player(character.health, character.mana, character.stamina, character.coins);
        return path;
    }

    /*this is the dynamically bound function for all derived classes defined in the journey base class as a pure abstract, this function is used to print the objects stored in the
        linked list of journey objects array. it will print the object stored in each array index and match to the correct derived class. It will check if the user ever accepted the
        event to determine weather or not to print the event affect values as well.
     */

    public int historydisplay(){

        System.out.print("\n\to Space ID: " + ID);
        System.out.print("\n\to Setting: \n\t\t-" + setting);
        System.out.print("\n\to Chance Event: \n\t\t-" + type);

        if(accepted == 1){               //check if the user accepted the event or not and had the values applied to there stats, if so print what those values were and what they applied to, otherwise skip
            System.out.print("\n\to Bonus Value: " + bonus);
            System.out.print("\n\to Bonus Type: ");
            if (potion == 1)                             //determine what stat the bonus applies to
                System.out.print("Health");
            else if (potion == 2)
                System.out.print("Mana");
            else if (potion == 3)
                System.out.print("Stamina");

            System.out.print("\n\to Consequence Value: " + consequence);
            System.out.print("\n\to Consequence Type: ");   //determine what stat the consequence value applies too
            if (poison == 1)
                System.out.print("Health");
            else if (poison == 2)
                System.out.print("Mana");
            else if (poison == 3)
                System.out.print("Stamina");
        }else
            System.out.print("\n\t\t+ Event Declined");

        return 1;
    }



}
