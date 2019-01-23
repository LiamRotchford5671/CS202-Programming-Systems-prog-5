//Liam Rotchford
// 11/28/18
package Main;

/* This class handles decision event nodes, the decision class holds a focus on the coins stat on the player. the income value will increase the coin stat and the payment variable will decrease it .
    In the decision class there is a constructor to initialize all values to 0, a constructor with args to take in values and set the decision class variables and the base class variables it inherits.
    A copy constructor, and a derived decision display that is dynamically bound via base class.
 */

public class Decision extends Journey {
    protected int income;           //variable who's value will increase the player coin stat
    protected int payment;          //variable who's value will decrease the player coin stat

    //CONSTRUCTOR
    public Decision(){
        income = 0;         //initialize decision values to 0
        payment = 0;
    }

    //COPY CONSTRUCTOR
    public Decision(final Decision d){
        this.income = d.income;
        this.payment = d.payment;
        super.copy(d);          //call the journey base class copy constructor
    }

    //CONSTRUCTOR WITH ARGS
    public Decision(String set, String event, int num, int gain, int loss){
        setting = set;              //set the decision values and the journey values that it inherits as a derived class
        type = event;
        ID = num;
        income = gain;
        payment = loss;
    }

    //DERIVED DECISION DISPLAY
    public int display(Player character){       //take in the player object that contains the player stat variables
        Main mainobj = new Main();                  //create main object to access main functions to be used for the users prompts and read in choices in run time without needing to exit function

        System.out.print("\n===========================================================");
        System.out.print("\n\to Space ID: " + ID);
        System.out.print("\n\to Setting: \n\t\t-" + setting);
        int path = mainobj.path_choice();                           //prompt user to choose a path via a i/o prompt and read in the main class
        System.out.print("\n\to Decision Event: \n\t\t-" + type);
        int event = mainobj.eventchoice();                          //prompt user for the event choice to accept or decline it via a main class function to prompt and read they're input

        if(event == 1) {  //accepted the event              //if they accept then print the event affecting values
            accepted = 1;                                        //set accepting value to 1 so the history print knows to print it as well
            System.out.print("\n\to Income: " + income);
            System.out.print("\n\to Payment: " + payment);

            if (income != 0)                         //alter the coin stat if the income and payment values are anything besides 0
                character.coins += income;
            if (payment != 0)
                character.coins -= payment;
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
        System.out.print("\n\to Decision Event: \n\t\t-" + type);

        if(accepted == 1){           //check if the user accepted the event or not and had the values applied to there stats, if so print what those values were and what they applied to, otherwise skip
            System.out.print("\n\to Income: " + income);
            System.out.print("\n\to Payment: " + payment);
        }else
            System.out.print("\n\t\t+ Event Declined");

        return 1;
    }



}
