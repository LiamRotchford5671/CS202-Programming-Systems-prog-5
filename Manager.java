//Liam Rotchford
// 11/28/18

package Main;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Manager {

    protected bnode root;

    //CONSTRUCTOR
//======================================================================================================================
    public Manager(){   //set root to null
        root = null;
    }

    //BUILDER
//======================================================================================================================
    public int build(){         //file loading function that takes in the values from the Map.txt file

        File file = new File("Map.txt");        //open the file
        try {                                               //try block to make sure the file was opened correctly
            Scanner scan = new Scanner(file);               //set the scanner to the file
            int number;                                     //number variable for the ID journey value, and set and type for the setting and type variable for journey
            String set, type;

            scan.useDelimiter("#");                         //use the # to separate the values in the file
            while (scan.hasNext()) {                        //as long as we still have characters in the file after the # keep loading
                if (scan.hasNextInt())                      //check for the int (ID) value to scan it on
                    number = scan.nextInt();
                else
                    number = 0;                             //if there is no int then auto set to 0

                set = scan.next();                          //scan in the 2 strings
                type = scan.next();

                switch (type.charAt(0)) {
                    case 'C': {              //chance event
                        int pro = scan.nextInt();               //scan in the bonus, potion, consequence, and poison values for chance object
                        int pro_type = scan.nextInt();
                        int con = scan.nextInt();
                        int con_type = scan.nextInt();

                        Journey object = new Chance(set, type, number, pro, pro_type, con, con_type);       //create a new derived chance object via the journey object, and load all the temp data into it
                        insert(object);         //call the insert function to place the new journey object into a node and into the tree
                        break;
                    }
                    case 'D': {         //decision event
                        int gain = scan.nextInt();                  //scan in the income and payment values
                        int loss = scan.nextInt();

                        Journey object = new Decision(set, type, number, gain, loss);  //create a new derived decision object via the journey object, and load all the temp data into it
                        insert(object);             //call the insert function to place the new journey object into a node and into the tree
                        break;
                    }
                    case 'S': {        //spell event
                        int pro = scan.nextInt();           //scan in the miracle, blessing, curse, bane values
                        int pro_type = scan.nextInt();
                        int con = scan.nextInt();
                        int con_type = scan.nextInt();

                        Journey object = new Spell(set, type, number, pro, pro_type, con, con_type);  //create a new derived spell object via the journey object, and load all the temp data into it
                        insert(object);                //call the insert function to place the new journey object into a node and into the tree
                        break;
                    }
                    case 'E':                                   //end event
                        Journey object = new End(set, type, number);  //create a new derived end object via the journey object, and load all the temp data into it
                        insert(object);         //call the insert function to place the new journey object into a node and into the tree
                        break;
                }
                System.out.print("\n\n");
            }
            scan.close();               //close the file
        } catch (FileNotFoundException e) {         //catch block if the file isn't opened correctly
            e.printStackTrace();
            e.getMessage();
        }
        return 1;
    }

    //INSERT INTO THE TREE
//======================================================================================================================
/* The insert function takes in the new journey object that needs to be places into a new node and placed into the tree */
    public int insert(Journey newobject){
        root = insert(root, newobject);         //send object into the recursive insert to find the correct place for the new node, catch the returned root placement so the tree actually updates
        return 1;
    }

    private bnode insert(bnode current, Journey newobject){

        if(current == null){                    //if current is null we are at a leaf and inset the new node
            current = new bnode(newobject);         //call the bnode constructor to create a new node and insert the new object into it
        }

        if(newobject.ID < current.object.ID)                    //based on the new objects ID values navigate the tree and determine where the new node should be placed in the tree
            current.left = insert(current.left, newobject);
        else if(newobject.ID > current.object.ID)
            current.right = insert(current.right, newobject);

        return current;                                         //return root
    }

    //DISPLAY TREE
//======================================================================================================================
/* the diplay function takes is used to print the contents of every journey object in every node in the tree, taking in the player object so we can print the player stats along with the
    events as it moves through and changes the values displays in-order (root, left subtree, right subtree)
 */
    public boolean display(Player character){
        if(root == null)                    //return false for empty list
            return false;
        return display(root, character);
    }

    private boolean display(bnode root, Player character){
        if(root == null)
            return false;

        root.object.display(character);     //call dynamic bound display on the derived object to display the contents of the node

        display(root.left, character);      //traverse tree in order
        display(root.right, character);
        return true;
    }

    //RETRIEVE ONE
//======================================================================================================================
/* The retrieve one function takes in the user imputed val variable that is the int value that should match to the node ID stored in the node object to be retrieved, the option value is used\
    to determine what the user wishes to do with the node, they can either individually display it or remove it from the tree
 */
    public boolean retrieve_one(int val, int option, Player character){
        boolean fix = false;        //bool that fixes odd error on the return to make sure the correct item it retrieved
        return retrieve_one(root, val, option, character, fix);
    }

    private boolean retrieve_one(bnode root, int val, int option, Player character, boolean fix){
        if(root == null)
            return false;

        if(val == root.object.ID) {         //val matched to a node
            if(option == 1)                         //the user wants to display this node
                root.object.display(character);
            else if(option == 2)                    //the user wants to remove this node
                remove_one(this.root, val);

            return true;
        }
                                                    //based on the val the user imputed traverse the tree to where the node should be placed in teh tree minimizing traversal
        if(val < root.object.ID)
            fix = retrieve_one(root.left, val, option, character, fix);
        else if(val > root.object.ID)
            fix =  retrieve_one(root.right, val, option, character, fix);

        return fix;
    }

    //RETRIEVE ALL OF ONE TYPE
//======================================================================================================================
/* the retrieve all of one type functions, takes in the first char of the derived object type the user wants (C, D, E, S) and searches through the tree, storing the matches into a bnode array
    once retrieved and stored dependant on the users choice it will either display them all or remove each moving through the array calling the remove one function on each stored bnode
 */
    public boolean count(char type, int option, Player character) {
        int counter = 0;
        counter = count(root, type, counter);       //determine how many matches currently exist in the tree

        if(counter == 0)            //no matches return false
            return false;

        bnode [] array = new bnode[counter];        //set the bnode array size based on how many matches we found in the tree
        array = retrieve_all(type, array);          //send to the recursive retriever to load the array with the bnode matches in the tree

        for (int i = 0; i < counter; ++i) {         //cycle through the array and perform the user choice
            if (option == 1)                    //display
                array[i].object.display(character);
            else if (option == 2)               //remove
                remove_one(array[i].object.ID);
        }
        return true;
    }

    private int count(bnode root, char type, int counter){      //determine how many matches of the type the user entered is currently in the tree to determine the array size
        if(root == null)
            return counter;

        if(type == root.object.type.charAt(0))                  //found match based on the first char of the type string which signifies the object event type
            ++counter;                                          //increment the counter variable

        counter = count(root.left, type, counter);              //traverse in order
        counter = count(root.right, type, counter);
        return counter;
    }

    private bnode[] place(bnode root, bnode [] array) {          //this function takes in wherever root currently is in the tree and inserts it into the next location that is empty in the array
        int i = 0;
        boolean done = false;

        while (!done){
            if (array[i] == null) {                             //determine the next null index in the array and insert the root node location into that index
                array[i] = root;
                done = true;
            }
            ++i;
        }

        return array;
    }

    private bnode[] retrieve_all(char type, bnode [] array){    //call the retrieve all recursive function
        return retrieve_all(root, array, type);
    }

    private bnode[] retrieve_all(bnode root, bnode [] array, char type){    //used to locate the type matches and store them into th bnode array ie retrieve the locations throughout the tree
        if(root == null)
            return array;

        if(type == root.object.type.charAt(0)){         //find a match via the first char of the type variable in the object
            array = place(root, array);
        }

        array = retrieve_all(root.left, array, type);   //traverse in order
        array = retrieve_all(root.right, array, type);
        return array;
    }

    //REMOVE ONE
//======================================================================================================================
/* take in the node ID value from the user for the node they wish to remove from the tree, move through the tree to the location of where the matched node should be to minimize traversal
    once located match to the four removal cases, leaf, one child left, one child right, two children. For the 2 children the IOS needs to be found in the right subtrees leftmost node to replace the
    the current node in the tree that is to be removed. we do this by resetting the nodes data (object) with the IOS and then recalling the recursive function to now beginning in the right subtree of the
    current location of root and the ID of the IOS to remove that leaf from the tree
 */
    public boolean remove_one(int ID_value){

        root = remove_one(root, ID_value);  //catch return with root to fully update the tree reference
        return true;
    }

    public Journey findsmall(bnode small){      //find the IOS of the node to be removed location starting in its right subtree (find the left most node in the right subtree)
        if(small.left == null){
            return small.object;    //find the smallest node in the tree wherever we start, it will be the left most node
        }
        return findsmall(small.left);
    }

    private bnode remove_one(bnode root, int ID_value) {        //take in the ID number the user inputted or is set by the retrieval functions to find the removal match
        if(root == null)
            return root;
        //traverse in order based on our removal value
        if(root.object.ID > ID_value)
            root.left = remove_one(root.left, ID_value);
        else if(root.object.ID < ID_value)
            root.right = remove_one(root.right, ID_value);
        else{   //we have reaches our location

            if(root.object.ID == ID_value) {
                //leaf node case
                if (root.left == null && root.right == null) {
                    return null;
                    //one child, left
                } else if (root.left != null && root.right == null) {
                    return root.left;       //set left child as the current root, i.e move it up to replace the remove node and inheirit the remaining tree

                    //one child, right
                } else if (root.left == null && root.right != null) {
                    return root.right;      //set right child as the current root location, i.e move up right child to be where the removal root node is
                    //2 children on remove node
                } else {
                    Journey obj = findsmall(root.right);  //find replacement node for the root to be brought up from the right child since it will need to be the smallest in the right subtree of where root is
                    root.object = obj;  //replace remove node with replacement node

                    root.right = remove_one(root.right, obj.ID);  //replace removal node with minimum node to get rid of the copy that is lower in the tree
                    return root;
                }
            }
        }
        return root;
    }

    //REMOVE ALL
//======================================================================================================================
//destroy the entire tree by setting the root to null, garbage collection handles the rest

    public boolean remove_all(){
        if(root == null)
            return false;

        root = null;
        return true;
    }

}
