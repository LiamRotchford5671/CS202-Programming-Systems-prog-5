//Liam Rotchford
// 11/28/18
package Main;
import java.util.Scanner;


public class Main {

    protected static Scanner input = null;

    public Main(){
        input = new Scanner(System.in);         //set scanner to the keyboard for input
    }

    public static void main(String[] args) {
        Main mainobject = new Main();               //create main object to call the non static main methods
        Manager Mobj = new Manager();               //create the manager class object to call the BST manager functions
        Mobj.build();                               //build the initial BST tree via file loading

        System.out.println("\to Welcome to the adventure game, please wait while we build the map.");
        System.out.println("\n\to Now that the map has been built you may modify the map to your pleasure using the options below if you so choose.");

        mainobject.usermenu(Mobj, mainobject);                  //call user menu to alter the current map that has been file loaded
    }

    /*this function present the options available to the user to manipulate the current tree we have loaded automatically, this can be add a new node space of a event type, chance, decision, end, or spell
        retrieve a node, retrieve all nodes of one type, remove one, remove all (destroy) display the tree.
     */
    public void usermenu(Manager Mobj, Main mainobject) {

        Player character = new Player();    //create the player object so we have our character with base stats
        int choice = 0, done = 0;
        boolean valid = false;

        do {    //user menu options to manipulate the tree
            System.out.print("\n\nO Map User menu: \n======================================================================================");
            System.out.print("\n\n\t1) Add new item to the map (Chance, Decision, End, Spell) ");
            System.out.print("\n\t2) Retrieve one item");
            System.out.print("\n\t3) Retrieve all items of a type");
            System.out.print("\n\t4) Remove one item from Map");
            System.out.print("\n\t5) Remove all items from Map");
            System.out.print("\n\t6) Display Map");
            System.out.print("\n\t7) Quit ");

            System .out.print("\n\n\t8) Begin game");
            System.out.print("\n\n\to Selection: ");        //take in user choice
            choice = input.nextInt();
            input.nextLine();

            if(choice < 1 || choice > 8){                   //input check
                System.out.print("\n\t+ Non-valid selection given, defaulting to 7) Quit");
                choice = 7;
            }

            if(choice == 8)
                valid = true;
            else if (!determine(choice, Mobj, character))                   //send choice to the determine function to perform the request action on the BST, if they chose 7 to quit it will return false and exit this menu
                valid = true;

        } while (!valid);                                   //loop until the user chooses to quit

        if(choice == 8) {                                      //choose to begin the game as a player after finalizing the tree map,
            System.out.print("\n\n o Game Start! \n\t+ Note: You may need to scroll over the run menu to be able to read the setting or event statements since they can be lengthy\n\n");
            character.player_stats();                           //print base player stats before starting
            done = character.game(character, Mobj, mainobject);     //send to the player game function to begin the game (traversing function)
        }else
            System.out.print("\n\t\t+ Thanks for working only with the Map.");

        //game has ended, give the option to print the path the user took through the tree
        System.out.print("\n\no Would you like to see the tale of your epic Journey that will be told around the globe?\n\t+ View Journey (1 - Yes / 2 - No) : ");
        done = input.nextInt();
        input.nextLine();

        if(done == 1)       //they want to see the journey
            character.history_disp();           //display the contents of the
        System.out.print("\n\n\t+ Thanks for playing the game!.");
    }


//======================================================================================================================================================================================================
    // GAME I/O ASSISTANCE FUNCTIONS

    //this function is called on each recursive call in the game function in the player class. allowing the user some options on each traverse through the map, those being display the player stats
    // since they get affected by the events they accept, or display the journey history which will display the LLL of arrays showing every node that was visited and what choice was made on the event.
    public void game_menu(Player character){
        int choice = 0;
                                //player options while traversing the map
            System.out.print("\n\nO Game User menu: \n======================================================================================");
            System.out.print("\n\n\t1) Display Player Stats ");         //display the play values
            System.out.print("\n\t2) Display Player History");             //display the LLL of arrays
            System.out.print("\n\t3) Move on to next space ");              //move onto the next node

            System.out.print("\n\n\to Selection: ");        //take in user choice
            choice = input.nextInt();
            input.nextLine();

            if(choice < 1 || choice > 3){                   //input check
                System.out.print("\n\t+ Non-valid selection given, defaulting to 3) Move on to next space");
                choice = 3;
            }

            if(choice == 1){
                character.player_stats();//call player display stats func with player obj
            }else if(choice == 2)
                character.history_disp();   //display the LLL of arrays
    }

    //===================================================================================================================================
    // PLAYER PROMPTS FOR THE DERIVED DISPLAYS WHEN TRAVERSING THE MAP

    //this function determines what path the user may want to take after the setting string displays. 1 for left and 2 for right, invalid input results in left path default
    public int path_choice(){
        int choice = 0;

            System.out.print("\n\t\t\t+ Please enter 1 to move to the left path, or enter 2 to move to the right. \n\t\t\t+ Path: ");   //prompt for path
            choice = input.nextInt();
            input.nextLine();

            if (choice < 1 || choice > 2) {         //check input
                System.out.print("\n\t\t\t\t+ Non- valid option given defaulting to the left path.");
                choice = 1;
            }
        return choice;      //return path
    }

    //this function determines if the user wishes to accept the event presented to them, this event can have either beneficial or detrimental options so its up to them. 1 to accept 2 to decline, invalid input defaults to decline
    public int eventchoice(){
        int choice = 0;

            System.out.print("\n\t\t\t+ Please enter 1 to accept the event, or enter 2 to decline the event. \n\t\t\t+ Choice: ");  //prompt
            choice = input.nextInt();
            input.nextLine();

            if (choice < 1 || choice > 2) {         //check input
                System.out.print("\n\t\t\t\t+ Non- valid option given defaulting to decline.");
                choice = 2;
            }
        return choice;
    }

    public void winner(){       //user has reached the end of the path and survived the journey
        System.out.print("\n\n\to You WIN!!!! You reached the end of the map without dying!!!");
    }

    public void death(){        //user has died via a end event space, or health is 0. state this fact
        System.out.print("\n\n\t+ You have unfortunately met you death along the journey, while you adventured boldly it seems to have been in vain.");
    }


//=============================================================================================================================================================================================
    //MAP MANUAL ADDING EVENT FUNCTIONS

    /*this function takes the user choice from the user menu function to perform the correct action requested to, each case dependant on the choice may ask for additional info from the user,
        or if the they choose to add a new node to the tree then the additional menu and information to be inputted it taken care of in additional functions
     */
    public boolean determine(int choice, Manager Mobj, Player character){

        switch(choice){
            case 1: //add
                add_type(Mobj);         //send to the add type menu method to determine what type of event they would like to add to the map
                break;

            case 2: {//retrieve one             //ask for the node ID of what node they want retrieved and if they want it to be individually displayed or removed
                System.out.print("\n\to Please enter the space ID number you wish to retrieve: ");
                int val = input.nextInt();
                System.out.print("\n\to Would you like to remove this space from the Map or Display it? (1 - display / 2 - remove) \n\t\t+ Selection: ");
                int option = input.nextInt();

                if(!Mobj.retrieve_one(val, option, character))
                    System.out.print("\n\t\tSpace not found in map.");
                else{
                    if(option == 2)
                        System.out.print("\n\t\t+ Space removed from the map");
                }
                break;
            }
            case 3: {//retrieve all                 //ask the user for the event type they want to retrieve from the map and if they want them only displayed or removed
                System.out.print("\n\to Please enter the space type you would like to retrieve from the map (ex: Chance, Decision, Spell, ,End): \n\t\t+ Selection:  ");
                String type = input.nextLine();

                type = type.toUpperCase();

                System.out.print("\n\to Would you like to remove these spaces from the Map or Display it? (1 - display / 2 - remove) \n\t\t+ Selection: ");
                int option = input.nextInt();

                if(!Mobj.count(type.charAt(0), option, character))
                    System.out.print("\n\t\t+ No spaces of that type found in the Map.");

                break;
            }
            case 4: {//remove one                   //ask for the node ID to be removed
                System.out.print("\n\to Please enter the space ID number of the space you wish to remove\n\t\t+ ID to find: " );
                int value = input.nextInt();

                if(!Mobj.remove_one(value))
                    System.out.print("\n\t\t+ ID not found in Map.");
                else
                    System.out.print("\n\t\t+ Space removed");
                break;
            }
            case 5: { //remove all                  //destroy the entire tree
                if (!Mobj.remove_all())
                    System.out.print("\n\t\t+ Error in removing all spaces from the map.");
                else
                    System.out.print("\n\t\t+ Map fully removed");

                break;
            }
            case 6: {//display all                  //display every nodes object in the tree in order
                if (!Mobj.display(character))
                    System.out.print("\n\t\t+ The Map is currently blank.");

                break;
            }
            case 7: //quit
                return false;
        }
        System.out.print("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        return true;
    }

    /* the user wants to add a new node to the tree, we need to determine what type of event they want so we can create the correct object, we then need the user to give the new node they
        want to create a unique node ID value
     */
    public void add_type(Manager Mobj){
        boolean valid = false;
        int choice = 0, num = 0;

        do{
            System.out.print("\n\no Map Addition Options.");
            System.out.print("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.print("\n\n\t1) Chance space\n\t\t+ This space will present you with a finding a potion or poison, a enemy, or a trap");
            System.out.print("\n\t2) Decision space\n\t\t+ You will be presented with choice based on some scenario that will add to the story of your journey in some way but is mainly focused around coins");
            System.out.print("\n\t3) Spell space\n\t\t+ You will uncover a new spell on your journey, but it will be in some unknown language you will have the option to cast it but it can be a blessing or a curse");
            System.out.print("\n\t4) End space\n\t\t+ You have come to your bitter end of the journey by some horrid event");
            System.out.print(("\n\n\t+Selection: "));
            choice = input.nextInt();
            input.nextLine();

            if(choice < 1 || choice > 4) {      //check choice by user
                System.out.print("\n\t\t+ Please only make your selection from the options given");
                System.out.print("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }else
                valid = true;

        }while(!valid);

        System.out.print("\n\to Please enter the new space ID number for the map. \n\t\t+ID Number: ");     //get new node ID value to be inserted into the new object for the node
        num = input.nextInt();
        input.nextLine();

        create(Mobj,choice, num);
    }

    /*  the create function takes in the choice of the user on the type and then sends to a switch statement to gather from the user the required data needed for that type.
        once the temp data variables have been filled, we send them into the derived class types constructor with args to load the new object as a base class journey object,
           once we have the new derived loaded object we send it to the insert function to place it into the tree
     */
    public void create(Manager Mobj, int choice, int num){

        String setting = new String();          //take in the user input description of the scene and the look of the right and left path of the directions available
        System.out.print("\n\to Please enter in the scene setting describing the setting of the left and right path:\n\t\t+ Setting: ");
        setting = input.nextLine();

        switch(choice){
            case 1:     //chance
            {
                String chance = "Chance Event: ";
                String chance_event = new String(); //enter the event scenario
                System.out.print("\n\to Please enter the type of chance event it is, finding a potion / poison, or a surprise enemy, or a trap. Ultimately you have to describe a event that will be a benefit to your journey (additional health, mana, stamina) or a consequence (reduced health, mana, stamina)\n\t\t+Event: ");
                chance_event = input.nextLine();
                chance_event += chance;

                System.out.print("\n\to Please now enter the benefit of this chance as a int value {if there isn't one enter a 0}\n\t\t+ Bonus: ");
                int bonus = input.nextInt();    //get the the benefit value
                input.nextLine();

                System.out.print("\n\t\t+ What type of stat does it affect? {Health, Mana, Stamina}\n\t\t+ Bonus Type: ");
                int potion = input.nextInt();       //get the type of stat it will affect
                if(bonus != 0) {
                    if(potion < 1 || potion > 3) {
                        System.out.print("\n\t+ Non-valid option given, defaulting to Health");
                        potion = 1;
                    }
                }

                System.out.print("\n\to Please now enter the consequence of this chance as a int value {if there isn't one enter a 0} \n\t\t+ Con: ");
                int penalty = input.nextInt();  //get the consequence value
                input.nextLine();

                System.out.print("\n\t\t+ What type of stat does it affect? {Health, Mana, Stamina}\n\t\t+ Con Type: ");
                int poison = input.nextInt();//get the type of stat it will affect
                if(penalty != 0) {
                    if (poison < 1 || poison > 3) {
                        System.out.print("\n\t+ Non-valid option given, defaulting to Health");
                        poison = 1;
                    }
                }

                Journey object = new Chance(setting, chance_event, num, bonus, potion, penalty, poison);//create the new derived object loaded with the user inputted values
                Mobj.insert(object);        //insert the new derived object into the tree

            }
            break;

            case 2:   //decision
            {
                String decision = "Decision Event: ";
                String decision_event = new String(); //enter the event scenario
                System.out.print("\n\to Please enter the type of chance event it is, finding a potion / poison, or a surprise enemy, or a trap. Ultimately you have to describe a event that will be a benefit to your journey (additional health, mana, stamina) or a consequence (reduced health, mana, stamina)\n\t\t+Event: ");
                System.out.print("\n\to Please describe a scenario in the form of a choice that must be made try to focus on the use of player coins, like selling or buying an item, or finding a chest filled with coins:\n\t\t+ Event: ");
                decision_event = input.nextLine();
                decision_event += decision;

                System.out.print("\n\to Please enter how much coin was gained {if there isn't one enter a 0}\n\t\t+ Gain: ");
                int gained = input.nextInt();       //enter the income value
                input.nextLine();

                System.out.print("\n\to Please enter loss of how much coin was lost {if there isn't one enter a 0}\n\t\t+ Loss: ");
                int lost = input.nextInt();         //enter the payment value
                input.nextLine();

                Journey object = new Decision(setting, decision_event, num, gained, lost);//create the new derived object loaded with the user inputted values
                Mobj.insert(object);//insert the new derived object into the tree
            }
            break;

            case 3:     //spell
            {
                String spell = "Spell Event: ";
                String spell_event = new String(); //enter the event scenario
                System.out.print("\n\to Please describe a scenario where you have a spell to use, it can be either a blessing or a curse spell, some spell can be in a unknown language so it could be either, but ultimately you choose to use it or not\n\t\t + Event: ");
                spell_event = input.nextLine();
                spell_event += spell;

                System.out.print("\n\to Please now enter the miracle value as a int {if there isn't one enter a 0}\n\t\t+ Miracle value: ");
                int miracle = input.nextInt();      //enter the benefit value
                input.nextLine();

                System.out.print("\n\t\t+ What type of miracle was it? {Health, Mana, Stamina}\n\t\t+ Miracle Type: ");
                int bless = input.nextInt();//get the type of stat it will affect
                if(miracle != 0) {
                    if(bless < 1 || bless > 3) {
                        System.out.print("\n\t+ Non-valid option given, defaulting to Health");
                        bless = 1;
                    }
                }

                System.out.print("\n\to Please now enter the curse value as a int {if there isn't one enter a 0}\n\t\t+ Con Value: ");
                int curse = input.nextInt();    //enter the curse value
                input.nextLine();

                System.out.print("\n\t\t+ What type of consequence does it affect? {Health, Mana, Stamina}\n\t\t+ Con Type: ");
                int bane = input.nextInt();//get the type of stat it will affect
                if(curse != 0) {
                    if (bane < 1 || bane > 3) {
                        System.out.print("\n\t+ Non-valid option given, defaulting to Health");
                        bane = 1;
                    }
                }

                Journey object = new Spell(setting, spell_event, num, miracle, bless, curse, bane);//create the new derived object loaded with the user inputted values
                Mobj.insert(object);//insert the new derived object into the tree

            }
            break;

            case 4:
            {
                String end = "End Event: ";
                String end_event = new String(); //enter the event scenario
                System.out.print("\n\to Please describe a ultimate demise that is unavoidable for your player:\n\t\t+ Event: ");
                end_event = input.nextLine();
                end_event += end;

                Journey object = new End(setting, end_event, num);  //create the new derived object loaded with the user inputted values
                Mobj.insert(object);//insert the new derived object into the tree
            }
            break;

        }
    }

}   //end main class