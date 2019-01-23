//Liam Rotchford
// 11/28/18
package Main;

/* The end class has only one variable called death, since the end object when encountered in the tree means nothing other than player death and the end of the game, the death varible is used to update
    the health character variable, once the player options have been introduced we can use this value to trigger an end. In the decision class there is a constructor to initialize all values to 0,
    a constructor with args to take in values and set the end class variables and the base class variables it inherits. A copy constructor, and a derived end display that is dynamically bound via
    base class.
 */
public class End extends Journey {
    protected int death;

    //CONSTRUCTOR
    public End(){ death = 0;}

    //COPY CONSTRUCTOR
    public End(final End e){
        this.death = e.death;
        super.copy(e);          //call the journey base class copy constructor
    }

    //CONSTRUCTOR WITH ARGS
    public End(String set, String event, int num){
        setting = set;          //set the journey variables
        type = event;
        ID = num;
    }

    //DERIVED End DISPLAY
    public int display(Player character){   //take in the player object that contains the player stat variables
        System.out.print("\n===========================================================");
        System.out.print("\n\to Space ID: " + ID);
        System.out.print("\n\to Setting: \n\t\t-" + setting);
        System.out.print("\n\to End Event: \n\t\t-" + type);
                                //update the player stat variables and then display them
        character.update_player(death, character.mana, character.stamina, character.coins);
        character.player_stats();
        return 1;
    }

    /*this is the dynamically bound function for all derived classes defined in the journey base class as a pure abstract, this function is used to print the objects stored in the
        linked list of journey objects array. it will print the object stored in each array index and match to the correct derived class. It will check if the user ever accepted the
        event to determine weather or not to print the event affect values as well.
     */

    public int historydisplay(){

        System.out.print("\n\to Space ID: " + ID);
        System.out.print("\n\to Setting: \n\t\t-" + setting);
        System.out.print("\n\to End Event: \n\t\t-" + type);

        return 1;
    }

}
