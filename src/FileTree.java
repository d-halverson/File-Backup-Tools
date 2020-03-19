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
		} else if (!root.exists()) {
			throw new IllegalArgumentException("File given doesn't exist.");
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

		cur = this.root;
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
	 * @throws IllegalArgumentException when file is null
	 */
	public boolean contains(FileNode file) {
		if (file == null)
			throw new IllegalArgumentException("file is null");

		@SuppressWarnings("unchecked")
		ArrayList<Node> nodesToTraverse = (ArrayList<Node>) this.root.getChildren().clone();
		FolderNode temp;
		FileNode temp2;

		while (!nodesToTraverse.isEmpty() && nodesToTraverse != null) {
			if (FolderNode.isFolderNode(nodesToTraverse.get(0))) {
				//computing score and index and using it to add Folder's children:
				int index;
				temp = (FolderNode) nodesToTraverse.get(0);
				if(compareStrings(temp.getParentPathString(), temp.getPath().getName(), 
						file.getParentPathString(), file.getPath().getName()) >= 3) {
					index = 0;
				}
				else {
					index = nodesToTraverse.size()-1;
					if(index < 0)
						index = 0;
				}
				nodesToTraverse.remove(0);
				addChildrenList(nodesToTraverse, temp.getChildren(), index);
				
			} else { // FileNode was found
				temp2 = (FileNode) nodesToTraverse.get(0);
				if (temp2.equals(file)) {
					return true;
				}
				nodesToTraverse.remove(0);
			}
			
		}

		return false;
	}

	/**
	 * Private helper method that adds all of the Nodes in newChildren list to the
	 * nodesToTraverse list. This method was created to replace the addAll method of
	 * the ArrayList class in order to use the compareStrings() method in order to
	 * traverse "smartly". Adds all of the items beginning at the index provided.
	 * 
	 * Precondition: index must be >= 0 and < nodesToTraverse.size()
	 * 
	 * @param nodesToTraverse the list of nodes that are being traversed.
	 * @param newChildren the list of nodes being added to nodesToTraverse
	 * @param index the index of nodesToTraverse at which the newChildren items will be added.
	 */
	private static void addChildrenList(ArrayList<Node> nodesToTraverse, ArrayList<Node> newChildren, int index) {
		for(int i = 0; i<newChildren.size(); i++) {
			nodesToTraverse.add(index, newChildren.get(i));
		}
	}

	/**
	 * Finds extra files that are in tree2 and not in this tree.
	 * 
	 * @param tree2 the tree being compared to this tree
	 * @return returns an array list of File objects that are the extra files that
	 *         were found.
	 */
	public ArrayList<File> findExtraFiles(FileTree tree2) {
		@SuppressWarnings("unchecked")
		ArrayList<Node> nodesToTraverse = (ArrayList<Node>) tree2.root.getChildren().clone();
		FileNode tempFile;
		ArrayList<File> extraFiles = new ArrayList<File>();

		while (!nodesToTraverse.isEmpty()) {
			if (FolderNode.isFolderNode(nodesToTraverse.get(0))) {
				nodesToTraverse.addAll(((FolderNode) nodesToTraverse.get(0)).getChildren());
			} else { // FileNode was found
				tempFile = (FileNode) nodesToTraverse.get(0);
				if (!this.contains(tempFile)) { // if the file found in tree2 was not found in this tree, it is extra
					extraFiles.add(tempFile.getPath());
				}
			}
			nodesToTraverse.remove(0);
		}
		return extraFiles;
	}

	/**
	 * Accessor method for the root instance field.
	 * 
	 * @return the root of this tree.
	 */
	protected FolderNode getRoot() {
		return this.root;
	}

	/**
	 * Computes the similarity of the two String parameters and returns it as an
	 * integer.
	 * 
	 * This currently just returns a very simple computation of whether or not the
	 * characters in each string are the same in the same place (not case sensitive)
	 * and adds a point if the strings are close in length
	 * 
	 * @param first  the first string being compared
	 * @param second the second string being compared
	 * @return a score of similarity, 0 being the lowest possible score.
	 */
	public static int compareStrings(String first, String second) {
		int result = 0;

		if (Math.abs(first.length() - second.length()) <= 2) // if strings are the same length +-2
			result++;

		for (int i = 0; i < first.length() && i < second.length(); i++) {
			if (first.substring(i, i + 1).equalsIgnoreCase(second.substring(i, i + 1))) {
				result++;
			}
		}

		return result;
	}
	
	/**
	 * Overloaded version of the method: accepts parent strings of the first and second strings. These will
	 * be used for an additional test of similarity.
	 * 
	 * @param firstParent parent string of first
	 * @param first child string of firstParent
	 * @param secondParent parent string of second
	 * @param second child string of secondParent
	 * @return returns an int score of similarity, 0 being the lowest possible score.
	 */
	public static int compareStrings(String firstParent, String first, String secondParent, String second) {
		return compareStrings(first, second) + compareStrings(firstParent, secondParent);
	}

}
