import java.io.File;
import java.util.ArrayList;

/**
 * This subclass of Node represents a folder in a filesystem.
 * 
 * @author drewhalverson
 */
public class FolderNode extends Node {
	
	private ArrayList<Node> children;

	/**
	 * Constructor that simply calls super constructor and passes the strings.
	 * @param path String of the path of the file this node is representing.
	 * @param parent Node of the parent.
	 */
	public FolderNode(String path, Node parent) {
		super(path, parent);
		this.children = new ArrayList<Node>();
	}

	/**
	 * Constructor that simply calls super constructor and passes the Files.
	 * @param path the File this node is representing.
	 * @param parent Node of the parent.
	 */
	public FolderNode(File path, Node parent) {
		super(path, parent);
		this.children = new ArrayList<Node>();
	}
	
	/**
	 * Same as first constructor except it is passed an ArrayList for the children.
	 * @param path String of the path of the file this node is representing.
	 * @param parent Node of the parent.
	 * @param children a list of Children this folder has.
	 */
	public FolderNode(String path, Node parent, ArrayList<Node> children) {
		super(path, parent);
		this.children = children;
	}
	
	/**
	 * Same as second constructor except it is passed an ArrayList for the children.
	 * @param path File of the path of the file this node is representing.
	 * @param parent Node of the parent.
	 * @param children a list of Children this folder has.
	 */
	public FolderNode(File path, Node parent, ArrayList<Node> children) {
		super(path, parent);
		this.children = children;
	}
	
	/**
	 * Mutator method for the children instance variable. Adds a node to the list.
	 * @param node the object to be added to the list.
	 */
	public void addChild(Node node) {
		this.children.add(node);
		incrNumOfChild();
	}
	
	/**
	 * Increases the number of children instance field by one, used when adding a child.
	 */
	private void incrNumOfChild() {
		setNumOfChild(getNumOfChild()+1);
	}
	

}
