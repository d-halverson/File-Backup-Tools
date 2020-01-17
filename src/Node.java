import java.io.File;

/**
 * This abstract class represents a Node in general that will either contain a
 * file or folder.
 * 
 * @author drewhalverson
 */
public abstract class Node {

	private int numOfChild = 0;
	private File path;
	private File parent;

	/**
	 * This constructor creates a new File object from the String it was passed and
	 * sends the File to the overloaded constructor
	 * 
	 * @param path   A String that represents the pathname of the File that this
	 *               Node will hold.
	 * @param parent A String that represents the pathname of the File of the parent
	 *               this Node will hold.
	 */
	public Node(String path, String parent) {
		this(new File(path), new File(parent));
	}

	/**
	 * This constructor assigns the File parameter to the instance variable.
	 * 
	 * @param path   A File that this Node will represent.
	 * @param parent A File that is the parent of this Node.
	 */
	public Node(File path, File parent) {
		/*
		 * Commented this error check out, not sure if I want to have this done yet,
		 * will come back later.
		 * 
		 * if (!path.exists()) { throw new
		 * IllegalArgumentException("This File does not exist, and a node cannot be created."
		 * ); }
		 * 
		 * if(!parent.exists()){ throw new
		 * IllegalArgumentException("The parent File does not exist, node cannot be created."
		 * ); }
		 * 
		 * 
		 * Need to add @throws to doc if uncomment this****
		 */

		this.path = path;
		this.parent = parent;
	}

	/**
	 * Accessor method for the parent instance variable.
	 * 
	 * @return returns the parent File.
	 */
	public File getParent() {
		return this.parent;
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

}
