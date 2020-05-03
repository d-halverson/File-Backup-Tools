package filestructure;

import java.io.File;

/**
 * This abstract class represents a Node in general that will either contain a
 * file or folder.
 * 
 * @author drewhalverson
 */
public abstract class Node implements Comparable<Node> {

	private int numOfChild = 0;
	private File path;
	private Node parent;

	/**
	 * This constructor creates a new File object from the String it was passed and
	 * sends the File to the overloaded constructor
	 * 
	 * @param path   A String that represents the pathname of the File that this
	 *               Node will hold.
	 * @param parent A String that represents the pathname of the File of the parent
	 *               this Node will hold.
	 */
	public Node(String path, Node parent) {
		this(new File(path), parent);
	}

	/**
	 * This constructor assigns the File parameter to the instance variable.
	 * 
	 * @param path   A File that this Node will represent.
	 * @param parent A File that is the parent of this Node.
	 */
	public Node(File path, Node parent) {
		/*
		 * Commented this error check out, not sure if I want to have this done yet,
		 * will come back later.
		 * 
		 * if (!path.exists()) { throw new
		 * IllegalArgumentException("This File does not exist, and a node cannot be created."
		 * ); }
		 * 
		 * Need to add @throws to doc if uncomment this****
		 */

		this.path = path;
		this.parent = parent;
	}
	
	/**
	 * CompareTo method that implements Comparable interface by comparing the files' string names
	 * @param other the other Node that is being compared to this node.
	 * @return returns a negative number if this Node is earlier alphabetically than the other node
	 */
	@Override
	public int compareTo(Node other) {
		return this.getPath().getName().compareToIgnoreCase(other.getPath().getName());
	}

	/**
	 * Accessor method for the parent instance variable.
	 * 
	 * @return returns the parent Node.
	 */
	public Node getParent() {
		return this.parent;
	}

	/**
	 * Accessor method for the File stored in the parent Node instance field.
	 * 
	 * Precondition: only call when parent is not null.
	 * 
	 * @return returns the File object stored in parent.
	 */
	private File getParentPath() {
		return getParent().getPath();
	}
	
	/**
	 * Accessor method that returns a string of the name of this node's parent. Returns a zero length string
	 * if this node's parent is null.
	 * 
	 * @return returns the filename of this node's parent string.
	 */
	public String getParentPathString() {
		if(getParent()==null) {
			return "";
		}
		else {
			return getParentPath().getName();
		}
	}

	/**
	 * Accessor method for the path instance variable.
	 * 
	 * @return returns the path File.
	 */
	public File getPath() {
		return this.path;
	}

	/**
	 * Accessor method for the numOfChild instance variable.
	 * 
	 * @return returns the numOfChild int.
	 */
	public int getNumOfChild() {
		return this.numOfChild;
	}

	/**
	 * Tells whether or not this Node has a parent. This is useful because the root
	 * of a filesystem will not have a parent.
	 * 
	 * @return returns true if it has a parent, false otherwise.
	 */
	public boolean hasParent() {
		return getParent() != null;
	}

	/**
	 * Mutator method for the numOfChild instance field. Protected so that the
	 * FolderNode class can still access it.
	 * 
	 * @param num the new value of numOfChild.
	 */
	protected void setNumOfChild(int num) {
		this.numOfChild = num;
	}
}
