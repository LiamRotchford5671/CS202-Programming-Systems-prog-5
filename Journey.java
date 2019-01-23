//Liam Rotchford
// 11/28/18
package Main;

/* Journey is the abstract base class that holds together the four derived class, chance, decision, end, spell. These each handle different ways of interacting with the player, It contains the
    2 string variables called setting (which describes the current setting and what the left and right paths look like so the user can make a choice on there path) and the type sting
    which is the event type (derived class types) that will present a choice except in end that the player will have tp choose from. The ID variable is how i label the tree nodes to make the
    retrieve one, and remove one a bit easier, along with making insert more clear on where a new node would and should be placed
 */

abstract public class Journey {
    protected String setting;           //present the setting and the two paths the user can choose
    protected String type;              //describe the event type and the choice the player must make for it
    protected int ID;                   //node label to allow for easier node tracing
    protected int accepted;

    //CONSTRUCTOR
    public Journey(){
        setting = null;
        type = null;
        ID = 0;
        accepted = 0;
    }

    //COPY CONSTRUCTOR {that can be called by the derived classes}
    public void copy(Journey j) {
        this.setting = j.setting;
        this.type = j.type;
        this.ID = j.ID;
        this.accepted = j.accepted;
    }

    //JOURNEY COPY CONSTRUTOR
    public Journey(Journey source){
        copy(source);
    }

    //PURE ABSTRACT DISPLAY FUNCTION THAT IS IMPLEMENTED IN EVERY DERIVED CLASS
    abstract int display(Player character); //abstract pure

    abstract int historydisplay();          //abstract function that is a generalized derived display that gives a object display without the choice prompts, and will only print the event data if the user accepted the event

}
