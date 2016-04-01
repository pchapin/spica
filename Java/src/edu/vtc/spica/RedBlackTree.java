/* *************************************************************************
FILE          : RedBlackTree.java
PROGRAMMER    : Peter C. Chapin
LAST REVISION : 2003-09-26

(C) Copyright 2003 by Peter C. Chapin

This file contains the definition of a class that implements a Red/Black
tree. This implementation follows the pseudo-code in the book "Introduction
to Algorithms" by Cormen, Leiserson, Rivest, and Stein. It even uses the
rather poor (my opinion) variable names used in the book in order to make
comparing this code to the book's code easier and more direct.
************************************************************************* */

package edu.vtc.spica;

//
// A red/black tree is a type of sorted set. Extend the helper class (AbstractSet) instead of trying to implement all of
// the Set interface methods.
//
public class RedBlackTree extends java.util.AbstractSet {

  // These colors are needed. The values are arbitrary.
  private final static int RED   = 1;
  private final static int BLACK = 2;

  // A node in the red/black tree contains these items. I make all of the fields public to simplify access in the
  // RedBlackTree methods. This is not a major problem because this class is only accessible by RedBlackTree.
  //
  static private class Node {
    public Node   parent = null;
    public Node   left   = null;
    public Node   right  = null;
    public int    color;
    public Object key;

    // Constructor to initialize the final two fields.
    public Node(Object o, int c)
    {
      key = o; color = c;
    }
  }

  // This is the sentinel. There is one for each tree. Only the color is used.
  private Node nil = new Node(null, BLACK);

  // A reference to the root node (could be nil if the tree is empty).
  private Node root = nil;

  // The number of items in this tree (initially zero).
  private int  count = 0;


  // ====================================
  //           Private Methods
  // ====================================

  // Helper method to do a left rotation around a given node, x. This method assumes that x.right is not nil.
  //
  private void leftRotate(Node x)
  {
    Node y = x.right;

    // Attach y.left to x.right (setting parent of y.left appropriately).
    x.right = y.left;
    y.left.parent = x;

    // Set the new parent properly. Deal with three cases (x was root, x was left child of its parent, x was right child
    // of its parent).
    //
    y.parent = x.parent;
    if (x.parent == nil) {
      root = y;
    }
    else if (x == x.parent.left) {
      x.parent.left = y;
    }
    else {
      x.parent.right = y;
    }

    // Make the new connection between x and y (now in the reverse order).
    y.left = x;
    x.parent = y;
  }


  // Helper method to do a right rotation around a given node, y. This method assumes that y.left is not nil.
  //
  private void rightRotate(Node y)
  {
    Node x = y.left;

    // Attach x.right to y.left (setting parent of x.right appropriately).
    y.left = x.right;
    x.right.parent = y;

    // Set the new parent properly. Deal with three cases (y was root, y was left child of its parent, y was right child
    // of its parent).
    //
    x.parent = y.parent;
    if (y.parent == nil) {
      root = x;
    }
    else if (y == y.parent.left) {
      y.parent.left = x;
    }
    else {
      y.parent.right = x;
    }

    // Make the new connection between x and y (now in the reverse order).
    x.right = y;
    y.parent = x;
  }


  // The following method returns the minimum node in the subtree rooted at x. This method assumes that there is at
  // least one node in the subtree.
  // 
  private Node minimumNode(Node x)
  {
    while (x.left != nil) {
      x = x.left;
    }

    return x;
  }


  // The following method returns the maximum node in the subtree rooted at x. This method assumes that there is at
  // least one node in the subtree.
  // 
  private Node maximumNode(Node x)
  {
    while (x.right != nil) {
      x = x.right;
    }

    return x;
  }


  // The following method returns the next node in the tree after the given node. It returns nil if the parameter is
  // already referring to the maximum node in the tree. This method assumes that x is not nil.
  // 
  private Node successorNode(Node x)
  {
    if (x.right != nil) {
      return minimumNode(x.right);
    }

    Node y = x.parent;
    while (y != nil && x == y.right) {
      x = y;
      y = x.parent;
    }

    return y;
  }


  // The following method returns the previous node in the tree before the given node. It returns nil if the parameter
  // is already referring to the minimum node in the tree. This method assumes that x is not nil.
  // 
  private Node predecessorNode(Node x)
  {
    if (x.left != nil) {
      return maximumNode(x.left);
    }

    Node y = x.parent;
    while (y != nil && x == y.left) {
      x = y;
      y = x.parent;
    }

    return y;
  }


  // The following helper method does the raw insertion into the tree. It knows nothing about colors. This method
  // assumes that the key field of the incoming node has already been filled in.
  //
  private void treeInsert(Node z)
  {
    Node y = nil;
    Node x = root;

    // Search down the tree until we come to a leaf.
    while (x != nil) {

      // Remember the potential leaf.
      y = x;

      // Do we follow the left or right branch?
      if (((Comparable)z.key).compareTo(x.key) < 0) {
        x = x.left;
      }
      else {
        x = x.right;
      }
    }

    // The parent of the new node is the leaf found above.
    // 
    z.parent = y;

    // Attach the new node to the tree at the right spot.
    if (y == nil) {
      root = z;
    }
    else if (((Comparable)z.key).compareTo(y.key) < 0) {
      y.left = z;
    }
    else {
      y.right = z;
    }

    // Fill in the other fields of the incoming node.
    z.left = nil;
    z.right = nil;

    // Don't forget to update this!
    count++;
  }


  // The following helper method does the raw deletion from the tree. It knows nothing about colors. The given node, z,
  // must refer to an existing node in the tree.
  //
  private void treeDelete(Node z)
  {
    Node y;
    Node x;

    // Locate the node that actually needs to be removed.
    if (z.left == nil || z.right == nil)
      y = z;
    else
      y = successorNode(z);

    // Locate the subtree that needs to be joined back to the main tree. Note that if that y will always have only one
    // child. If z had two children, y is z's successor which, in that case, would only have one child.
    // 
    if (y.left != nil)
      x = y.left;
    else
      x = y.right;

    // Attach rest to the main tree (Part I).
    x.parent = y.parent;

    // Attach rest to the main tree (Part II).
    if (y.parent == nil)
      root = x;
    else if (y == y.parent.left)
      y.parent.left = x;
    else
      y.parent.right = x;

    // Copy key if necessary.
    if (y != z)
      z.key = y.key;

    // Update the count!
    count--;
  }


  // ===================================
  //           Public Methods
  // ===================================

  // The following method adds an item to the tree. I'm supposed to throw an exception if I don't like the item for some
  // other reason. I don't like null items.
  //
  public boolean add(Object item)
  {
    // I don't support null items.
    if (item == null)
      throw new IllegalArgumentException();

    // If the item already exists, return false to indicate that I didn't actively add it this time. This is rather
    // inefficient. I should detect already existing items while I am working to insert a new item.
    // 
    if (contains(item)) return false;

    // Put this item into a Node and then into the tree.
    Node z = new Node(item, RED);
    treeInsert(z);

    // Keep going until we come to the root or until we no longer have two reds in a row. Notice that inside this loop,
    // z's parent is red. Since the root is always black, it follows that z.parent.parent is not nil.
    //
    while (z.parent.color == RED) {
      Node y;

      // If z's parent is a left child...
      if (z.parent == z.parent.parent.left) {

        // y is the sibling of x's parent.
        y = z.parent.parent.right;
        if (y.color == RED) {
          z.parent.color        = BLACK;
          y.color               = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        }
        else {
          if (z == z.parent.right) {
            z = z.parent;
            leftRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          rightRotate(z.parent.parent);
        }
      }

      // Otherwise z's parent must be a right child...
      else {
        y = z.parent.parent.left;
        if (y.color == RED) {
          z.parent.color        = BLACK;
          y.color               = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        }
        else {
          if (z == z.parent.left) {
            z = z.parent;
            rightRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          leftRotate(z.parent.parent);
        }
      }
    }
    root.color = BLACK;

    return true;
  }


  // The following method returns the number of items in the collection.
  // 
  public int size()
  {
    return count;
  }


  // The following method returns a suitable iterator that allows the items stored in the tree to be visited in a
  // sequence.
  // 
  public java.util.Iterator iterator()
  {
    return new RedBlackIterator(this, minimumNode(root));
  }


  // The following method returns true if the tree contains the given object (in the sense of equals()).
  // 
  public boolean contains(Object item)
  {
    // I'm not supporting null items. Thus I don't contain any nulls.
    if (item == null) return false;

    Node x = root;
    while (x != nil && !x.key.equals(item)) {
      if (((Comparable)item).compareTo((Comparable)x.key) < 0) {
        x = x.left;
      }
      else {
        x = x.right;
      }
    }

    // If I found it, return true; otherwise return false.
    return x != nil;
  }

  // ===================================
  //           Iterator Class
  // ===================================

  static public class RedBlackIterator implements java.util.Iterator {

    private RedBlackTree      myTree;
    private RedBlackTree.Node currentNode;

    //
    // The constructor allows the private fields to be set.
    public RedBlackIterator(RedBlackTree t, RedBlackTree.Node n)
    {
      myTree = t;
      currentNode = n;
    }

    // This method is part of the Iterator interface.
    public boolean hasNext()
    {
      return currentNode != myTree.nil;
    }

    // This method is part of the iterator interface.
    public Object next()
    {
      if (currentNode == myTree.nil)
        throw new java.util.NoSuchElementException();

      Object returnValue = currentNode.key;
      currentNode = myTree.successorNode(currentNode);
      return returnValue;
    }

    // Don't support removing tree items via the Iterator interface.
    public void remove()
    {
      throw new UnsupportedOperationException();
    }

  }

}
