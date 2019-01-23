//Liam Rotchford
// 11/28/18
package Main;

/* This class handles spell event nodes, dependant on the event there will be a value assigned to the miracle and curse variables, along with the adjoining blessing and bane variables to determine
    what player stat they should apply that value to. This class has a focus on the altering the player stats in terms of health, mana, and stamina. In the spell class there is a constructor to
    initialize all values to 0, a constructor with args to take in values and set the spell class variables and the base class variables it inherits. A copy constructor, and a derived spell display
    that is dynamically bound via base class.
 */
public class Spell extends Journey{
    protected int miracle;          //spell variables, miracle, beneficial value
    protected int blessing;             //determines which stat miracle value is applied (health, mana, stamina)
    protected int curse;                //curse, provides a value that will decrement from a stat, and bane determines what stat that will be
    protected int bane;

    //CONSTRUTOR
    public Spell(){
        miracle = 0;
        blessing = 0;
        curse = 0;
        bane = 0;
    }

    //COPY CONSTRUCTOR
    public Spell(final Spell s){
        this.miracle = s.miracle;
        this.blessing = s.blessing;
        this.curse = s.curse;
        this.bane = s.bane;
        super.copy(s);          //call journey base class copy constructor
    }

    //CONSTRUCTOR WITH ARGS
    public Spell(String set, String event, int num, int mira, int bless, int cur , int ban){
        setting = set;          //initialize Spell values and journey values for a spell object to then be stored in the bnode to be inserted into the BST tree
        type = event;
        ID = num;
        miracle = mira;
        blessing = bless;
        curse = cur;
        bane = ban;
    }

    //SPELL DISPLAY
    public int display(Player character){       //dynamically bound display to journey
        Main mainobj = new Main();                  //create main object to access main functions to be used for the users prompts and read in choices in run time without needing to exit function

        System.out.print("\n===========================================================");
        System.out.print("\n\to Space ID: " + ID);
        System.out.print("\n\to Setting: \n\t\t-" + setting);
        int path = mainobj.path_choice();                       //prompt user to choose a path via a i/o prompt and read in the main class

        System.out.print("\n\to Spell Event: \n\t\t-" + type);
        int event = mainobj.eventchoice();                      //prompt user for the event choice to accept or decline it via a main class function to prompt and read they're input

        if(event == 1) {                                        //if they accept then print the event affecting values
            accepted = 1;                                           //set accepting value to 1 so the history print knows to print it as well
            System.out.print("\n\to Miracle Value: " + miracle);
            System.out.print("\n\to Miracle Type: ");
            if (blessing == 1) {                                 //determine what the miracle value will apply too on character stats which are stored as player object values
                System.out.print("Health");
                character.health += miracle;
            } else if (blessing == 2) {
                System.out.print("Mana");
                character.mana += miracle;
            } else if (blessing == 3) {
                System.out.print("Stamina");
                character.stamina += miracle;
            }

            System.out.print("\n\to Curse Value: " + curse);
            System.out.print("\n\to Curse Type: ");             //determine what the curse value will apply too on character stats which are stored as player object values
            if (bane == 1) {
                System.out.print("Health");
                character.health -= curse;
            } else if (bane == 2) {
                System.out.print("Mana");
                character.mana -= curse;
            } else if (bane == 3) {
                System.out.print("Stamina");
                character.stamina -= curse;
            }
        }
                                                            //update the character stat values now that they have been altered by the miracle and curse values
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
        System.out.print("\n\to Spell Event: \n\t\t-" + type);

        if(accepted == 1){                                          //check if the user accepted the event or not and had the values applied to there stats, if so print what those values were and what they applied to, otherwise skip
            System.out.print("\n\to Miracle Value: " + miracle);
            System.out.print("\n\to Miracle Type: ");
            if (blessing == 1)                           //determine what the miracle value will apply too on character stats which are stored as player object values
                System.out.print("Health");
            else if (blessing == 2)
                System.out.print("Mana");
            else if (blessing == 3)
                System.out.print("Stamina");

            System.out.print("\n\to Curse Value: " + curse);
            System.out.print("\n\to Curse Type: ");             //determine what the curse value will apply too on character stats which are stored as player object values
            if (bane == 1)
                System.out.print("Health");
            else if (bane == 2)
                System.out.print("Mana");
            else if (bane == 3)
                System.out.print("Stamina");
        }else
            System.out.print("\n\t\t+ Event Declined");

        return 1;
    }


}
