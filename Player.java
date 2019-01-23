//Liam Rotchford
// 11/28/18
package Main;

/*  The player class handles the player stat variables that change as the player moves through the tree encountering events that affect these stats. I only declare the player object once so it stays
    consistent as the user moves through the tree. The constructor begins the character with base stat values, the update player takes in the args from the function call of the derived class
    displays that chance the values of the stats. The player stats display after they have been updated is called to show the player after each movement where they currently stand stat-wise
 */

public class Player {

    protected int health;   //player stats
    protected int mana;
    protected int stamina;
    protected int coins;

    protected node head;

    //CONSTRUCTOR
    public Player(){        //set the player with base stats
        head = null;

        health = 100;
        mana = 100;
        stamina = 100;
        coins = 50;
    }

    //UPDATE PLAYER STATS
    public int update_player(int life, int magic, int energy, int cash){
        this.health = life;             //this function is called after the node object event has been displayed and the effects of the event have altered the values, this is called
        this.mana = magic;                  //to fully update the objects variables so they don't get reset after each event
        this.stamina = energy;
        this.coins = cash;
        return 1;
    }

    //PLAYER STAT DISPLAY
    public int player_stats(){             //display the current values of the player stats
        System.out.print("\n\n\t\to Player Stats: \n\t\t++++++++++++++++");
        System.out.print("\n\t\t+ Health: " + health);
        System.out.print("\n\t\t+ Mana: " + mana);
        System.out.print("\n\t\t+ Stamina: " + stamina);
        System.out.print("\n\t\t+ Coins: " + coins);
        return 1;
    }

    //=============================================================================================================================================================================
    // MAP TRAVERSING FUNCTIONS / INSERTED FOR LINKED LIST OF ARRAYS

    public int game(Player character, Manager Mobj, Main mainobj){      //function callable from main class functions
        return game(character, Mobj.root, mainobj);
    }

    /*  this is the function that will move through the decision tree, it recurse through based on the users ath choice. it will call the objects display, which will prompt them to choose
        a path and accept or decline the event effects via i/p functions from the main class. From there it will return the path choice the user made and once everything else has been taken care of
        use that value to choose how to traverse to the next node. on each recurse the node root is currently on will be stored in the LLL of arrays via the place function. we also have a check case
        in the event that we hit a end event or lose all health in which case the game needs to end since the player had died. After that we display the user with some player options being to display
        their player stats, the journey history, or to move on to the next space.

     */
    private int game(Player character, bnode root, Main mainobj){
        if(root == null){                               //if our root is null then we reached the end of the path and won the game
            mainobj.winner();                           //winning prompt
            return 0;
        }

        int path = root.object.display(character);      //display the space event and get the path the user wants to traverse next

        if(head == null) {                      //we currently don't have a list, this is the first node
            head = new node(root.object);           //set first object to be array [0]
            head.next = null;
        }else
            place(head, root.object);           //we have a list we can add to so determine where it should be placed in the current heads array or if we need to make a new node


        if(root.object.type.charAt(0) == 'E' || character.health == 0){     //check case for a end event space or our players health is 0 in which case end the game
            mainobj.death();                              //print death prompt
            character.player_stats();                       //print ending player stats (health = 0)
            return 0;

        }else{                                         //if we havent died yet then continue traversing the tree

            mainobj.game_menu(character);           //give player options to display player stats and player history

            if (path == 1)                           //based on the path option the user gave us in the derived object display traverse the tree (1 is left, 2 is right)
                return game(character, root.left, mainobj);
            else
                return game(character, root.right, mainobj);
        }
    }

    /* this function determines first if the current node array is full by checking the last index and making sure we are also at the end of the list. if we are full then we create a new node
        sending the new node the newest object to be set as the new nodes array [0]. we then attach the list at the end. if we aren't full we determine where the next index is where we have a null
        space in the array and place the new object at that index
     */
    private int place(node head, Journey obj){
        if(head == null)
            return 0;

        if(head.array[4] != null && head.next == null) {                     //if we have a full array in the current node and we are at the last existing current node
            node temp = new node(obj);                      //create new node and set new object as its array[0]
            temp.next = null;                               //make sure its next is set to null
            head.next = temp;                               //attach the new node as heads next since head should be at the end of the list
            return 0;

        }else{                                  //our current nodes array isn't yet full

            int placement = 0, found = 0;
            for(int i = 0; i < 5; ++i){             //cycle through the length of the array
                if(head.array[i] == null && found == 0){        //determine the index of the next null space and make sure it doesn't get reset if we have say index 1 and 2 null, (wont set 1 then change to 2)
                    placement = i;
                    found = 1;
                }
            }

            head.array[placement] = obj;            //use that marked index to set the object into the nodes array
        }

        return place(head.next, obj);               //recurse
    }

//=================================================================================================================================================================
    //LLL OF ARRAYS DISPLAY
    /* main function game_menu uses this function to display the journey history contents of the LLL of arrays */

    public int history_disp(){

        System.out.print("\n\to Player Journey History: \n");
        System.out.print("\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int catcher = history_disp(head);       //call recursive print
        System.out.print("\n\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\no Map: \n");

        return catcher;
    }

    private int history_disp(node head){
        if(head == null) return 1;

        for(int i = 0; i < 5; ++i){             //cycle through the nodes array of journey objects
            if(head.array[i] != null) {             //as long as it isn't null call the dynamically bound historydisplay function on the object to print the contents
                head.array[i].historydisplay();
                System.out.print("\n\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        return history_disp(head.next);             //recurse to the next node
    }




}
