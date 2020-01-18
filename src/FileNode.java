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
	 * @param path String of the path that leads to this file.
	 * @param parent Node of the parent.
	 */
	public FileNode(String path, Node parent) {
		super(path, parent);
	}

	/**
	 * Basic constructor that calls the super constructor.
	 * @param path File that this Node represents.
	 * @param parent Node of the parent.
	 */
	public FileNode(File path, Node parent) {
		super(path, parent);
	}

}
