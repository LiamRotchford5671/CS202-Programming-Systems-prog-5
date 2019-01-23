package Main;

public class node {
    protected Journey [] array;
    protected node next;

    public node(Journey object){        //when we construct the new node we send it the first new journey object so we can begin the new array in the new node with a store
        array = new Journey[5];            //array is of size 5 in every node
        this.array[0] = object;         //since when we create a new node that means either we currently have a empty array, or the current nodes array is full, but we still need to add this object
        next = null;
    }
}
