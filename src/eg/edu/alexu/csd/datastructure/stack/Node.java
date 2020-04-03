package eg.edu.alexu.csd.datastructure.stack;
/**
 * Node class which is the used data type 
 * in the implementation of the stacks
 *
 */
public class Node {
  private Object data;
  private Node next;
 
  public Node() {
    this (null,null); 
  }
  
  public Node(Object data,Node next) {
    this.data = data;
    this.next = next;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Object getData() {
    return this.data;
  }

  public void setNext(Node next) {
    this.next = next;
  }
 
  public Node getNext() {
    return this.next;
  }

}
