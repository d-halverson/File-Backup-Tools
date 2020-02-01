import java.io.File;
import java.util.ArrayList;

/**
 * A tree of Nodes that represents a file system in a computer.
 * 
 * @author drewhalverson
 */
public class FileTree {

	private FolderNode root;

	/**
	 * Constructs a FolderNode object for the root after checking the provided file
	 * and verifing its validity. Then calls constructTree() to finish constructing
	 * the tree.
	 * 
	 * @param root File object that points to the root of this FileTree.
	 */
	public FileTree(File root) {
		checkFile(root);
		this.root = new FolderNode(root, null);
		constructTree();
	}

	/**
	 * Constructs a FolderNode object from a String pointing to its path and sets
	 * this Node as the root. Then calls constructTree() to finish constructing the
	 * tree.
	 * 
	 * @param root String pathname of the root of this FileTree.
	 */
	public FileTree(String root) {
		if (root == null)
			throw new IllegalArgumentException("String root cannot be null.");
		checkFile(new File(root));
		this.root = new FolderNode(root, null);
		constructTree();
	}

	/**
	 * Private helper file that checks the passed File object to see if it is valid
	 * to be used as the root in this FileTree. Simply Throws errors if it is not.
	 * 
	 * @param root the File object to be checked.
	 * @throws IllegalArgumentException when root is either null or points to a file
	 *                                  and not a directory.
	 */
	private void checkFile(File root) {
		if (root == null) {
			throw new IllegalArgumentException("Root cannot be null.");
		} else if (root.isFile()) {
			throw new IllegalArgumentException(
					"Cannot create a FileTree with a root that points to a file and not a directory.");
		}
	}

	/**
	 * Helper method that is called after the root is initialized that adds the
	 * root's children according the the actual file system on the computer, and
	 * continues running until no more files are found.
	 */
	private void constructTree() {
		FolderNode cur;
		File[] curFiles;
		ArrayList<FolderNode> nodesToCheck = new ArrayList<FolderNode>();
		FolderNode tempNode;

		while (!nodesToCheck.isEmpty()) {

			cur = nodesToCheck.get(0);
			curFiles = cur.getPath().listFiles();

			for (int i = 0; i < curFiles.length; i++) {
				if (curFiles[i].isFile()) {
					cur.addChild(new FileNode(curFiles[i], cur));
				} else {
					tempNode = new FolderNode(curFiles[i], cur);
					cur.addChild(tempNode);
					nodesToCheck.add(tempNode);
				}
			}

			nodesToCheck.remove(0);
		}
	}

	/**
	 * Returns true if this FileTree contains the file passed as a parameter. This
	 * is based on if the file's name can be found in the tree with the same file
	 * size, not if the specific file with the same pathname can be found.
	 * 
	 * @param file the FileNode object that will be searched for.
	 * @return Returns true if the file was found, false otherwise.
	 */
	public boolean contains(FileNode file) {
		ArrayList<Node> nodesToTraverse = this.root.getChildren();
		FileNode temp2;
		
		while(!nodesToTraverse.isEmpty() && nodesToTraverse!=null) {
			if(!FolderNode.isFolderNode(nodesToTraverse.get(0))) {
				temp2 = (FileNode)nodesToTraverse.get(0);
				if(temp2.equals(file)) {
					return true;
				}
			}
		}
		
		return false;
	}

}

//check to see if nodes have parents, throw error if they don't and are not the root.