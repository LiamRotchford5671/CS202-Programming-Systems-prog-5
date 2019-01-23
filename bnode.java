//Liam Rotchford
// 11/28/18
package Main;

/* This class is the BST node class that contains the Journey abstract base class object and left and right node pointers, its only functionality is the constructor that take in a journey
    object that is set into the node. This class is used as the containers for the binary tree
 */

public class bnode {
    //BST NODE CLASS
    // ======================================================================================================================
    protected Journey object;       //journey object
    protected bnode left;           //node left and right pointers
    protected bnode right;

    //CONSTRUCTOR
    public bnode(Journey obj){      //takes in a journey object, however it is actually being sent derived objects that have been loaded with data
        this.object = obj;          //set this node object to the journey object we sent in
        left = null;
        right = null;
    }
}
