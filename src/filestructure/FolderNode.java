package filestructure;

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
	 * 
	 * @param path   String of the path of the file this node is representing.
	 * @param parent Node of the parent.
	 */
	public FolderNode(String path, Node parent) {
		super(path, parent);
		this.children = new ArrayList<Node>();
	}

	/**
	 * Constructor that simply calls super constructor and passes the Files.
	 * 
	 * @param path   the File this node is representing.
	 * @param parent Node of the parent.
	 */
	public FolderNode(File path, Node parent) {
		super(path, parent);
		this.children = new ArrayList<Node>();
	}

	/**
	 * Same as first constructor except it is passed an ArrayList for the children.
	 * 
	 * @param path     String of the path of the file this node is representing.
	 * @param parent   Node of the parent.
	 * @param children a list of Children this folder has.
	 */
	public FolderNode(String path, Node parent, ArrayList<Node> children) {
		super(path, parent);
		this.children = children;
	}

	/**
	 * Same as second constructor except it is passed an ArrayList for the children.
	 * 
	 * @param path     File of the path of the file this node is representing.
	 * @param parent   Node of the parent.
	 * @param children a list of Children this folder has.
	 */
	public FolderNode(File path, Node parent, ArrayList<Node> children) {
		super(path, parent);
		this.children = children;
	}

	/**
	 * Mutator method for the children instance variable. Adds a node to the list.
	 * 
	 * @param node the object to be added to the list.
	 */
	protected void addChild(Node node) {
		this.children.add(node);
		incrNumOfChild();
	}

	/**
	 * Accessor for the children instance field.
	 * 
	 * @return returns the children list.
	 */
	public ArrayList<Node> getChildren() {
		return this.children;
	}

	/**
	 * True if file is in the immediate children of this FolderNode.
	 * 
	 * @param file the FileNode being searched for.
	 * @return returns true if file is in the immediate children of this FolderNode,
	 *         false otherwise.
	 */
	public boolean contains(FileNode file) {
		return this.getChildren().contains(file);
	}
	
	/**
	 * Checks to see if if folder parameter is somewhere in the immediate contents
	 * of this folder. Only checks to see if a folder with the same name is in this
	 * folder, and nothing else (See equalsName()).
	 * 
	 * @param folder the other folder being searched for.
	 * @return returns true if this folder has a folder in its children with the same
	 * name as folder parameter, false otherwise.
	 */
	public boolean contains(FolderNode folder) {
		Node curNode; 
		
		for(int i=0; i<this.getChildren().size(); i++) {
			curNode = this.getChildren().get(i);
			
			if(FolderNode.isFolderNode(curNode)) {
				if(((FolderNode)curNode).equalsName(folder)) {
					return true;
				}
			}
		}
		
		return false; //if true wasn't already returned, folder wasn't found.
	}

	/**
	 * Increases the number of children instance field by one, used when adding a
	 * child.
	 */
	private void incrNumOfChild() {
		setNumOfChild(getNumOfChild() + 1);
	}

	/**
	 * Helper method that returns true if the node parameter is a FolderNode type,
	 * not a FileNode.
	 * 
	 * @param node the Node to be checked.
	 * @return returns true if node is a FolderNode object.
	 */
	public static boolean isFolderNode(Node node) {
		FolderNode temp = new FolderNode("Hi", null);
		if (temp.getClass() == node.getClass())
			return true;
		else
			return false;
	}

	/**
	 * Equals method for comparing folders. Two FolderNodes are equal if: 
	 * 
	 * -They have the same parent String 
	 * 
	 * -They have the same name String 
	 * 
	 * -All of the children are the same
	 * 
	 * @param other the other FolderNode being compared to this one.
	 * @return boolean true if the two FolderNodes are equal, false otherwise
	 */
	public boolean equals(FolderNode other) {
		boolean parents = this.getParentPathString().equals(other.getParentPathString());
		boolean name = this.getPath().getName().equals(other.getPath().getName());

		boolean children = true;
		// iterate through this node's children, make sure each of them is somewhere in
		// other's children
		for (int i = 0; i < this.getChildren().size(); i++) {
			if (!other.getChildren().contains(this.getChildren().get(i))) {
				children = false;
			}
		}

		if (children) { // if children is false already, there is no reason to go through this loop
			// iterate through other node's children, make sure this node has them all
			for (int i = 0; i < other.getChildren().size(); i++) {
				if (!this.getChildren().contains(other.getChildren().get(i))) {
					children = false;
				}
			}
		}

		return parents && name && children;
	}
	
	/**
	 * Equals method that only checks to see if both folders have the same name,
	 * and NOTHING else.
	 * 
	 * @param other the other folder being checked.
	 * @return true if this folder has the same String name as the other folder.
	 */
	public boolean equalsName(FolderNode other) {
		return this.getPath().getName().equals(other.getPath().getName());
	}

}
