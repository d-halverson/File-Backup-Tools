import java.io.File;

/**
 * Extends the Node class, creating an option for an object to be created that
 * is the most basic form of a "Node". Does not allow for children due to the
 * nature of a file in a file system, meaning only folders can have things in
 * them and not files.
 * 
 * @author drewhalverson
 */
public class FileNode extends Node {

	/**
	 * Basic constructor that calls the super constructor.
	 * 
	 * @param path   String of the path that leads to this file.
	 * @param parent Node of the parent.
	 */
	public FileNode(String path, Node parent) {
		super(path, parent);
	}

	/**
	 * Basic constructor that calls the super constructor.
	 * 
	 * @param path   File that this Node represents.
	 * @param parent Node of the parent.
	 */
	public FileNode(File path, Node parent) {
		super(path, parent);
	}

	/**
	 * Returns true if this Node equals the formal parameter according to these
	 * rules: the nodes must have the same file name, and must have the same file
	 * size. This check for equality has nothing to do with the pathname or location
	 * of the file.
	 * 
	 * @param node the Node that is being checked for equality.
	 * @return returns true if nodes are equal, false otherwise.
	 */
	public boolean equals(FileNode node) {
		if (!this.getPath().getName().equals(node.getPath().getName())) {
			return false;
		} else if (this.getPath().length() != node.getPath().length()) {
			return false;
		} else
			return true;
	}

	/**
	 * Another option for an equals method that accepts File object as input rather
	 * than FileNode. Simply calls the other version of the equals method and passes
	 * it a new temporary FileNode.
	 * 
	 * @param file the File that is being searched for.
	 * @return returns true if nodes are equal, false otherwise.
	 */
	public boolean equals(File file) {
		return this.equals(new FileNode(file, null));
	}
}
