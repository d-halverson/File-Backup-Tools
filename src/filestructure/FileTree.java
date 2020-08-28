package filestructure;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
				// computing score and index and using it to add Folder's children:
				int index;
				temp = (FolderNode) nodesToTraverse.get(0);
				if (compareStrings(temp.getParentPathString(), temp.getPath().getName(), file.getParentPathString(),
						file.getPath().getName()) >= 3) {
					index = 0;
				} else {
					index = nodesToTraverse.size() - 1;
					if (index < 0)
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
	 * Another version of the contains method; this method searches for a FileNode
	 * that points to a file that has the same file name as the parameter fileName
	 * passed to this method. Does not worry about file size unlike the contains()
	 * method.
	 * 
	 * @param fileName the String of the filename being searched for.
	 * @return returns true if this fileName is found, false otherwise.
	 * @throws IllegalArgumentException when file is null
	 */
	public boolean containsFileName(String fileName) {
		if (fileName == null)
			throw new IllegalArgumentException("file is null");

		@SuppressWarnings("unchecked")
		ArrayList<Node> nodesToTraverse = (ArrayList<Node>) this.root.getChildren().clone();
		FolderNode temp;
		FileNode temp2;

		while (!nodesToTraverse.isEmpty() && nodesToTraverse != null) {
			if (FolderNode.isFolderNode(nodesToTraverse.get(0))) {
				// computing score and index and using it to add Folder's children:
				int index;
				temp = (FolderNode) nodesToTraverse.get(0);
				if (compareStrings(temp.getPath().getName(), fileName) >= 3) {
					index = 0;
				} else {
					index = nodesToTraverse.size() - 1;
					if (index < 0)
						index = 0;
				}
				nodesToTraverse.remove(0);
				addChildrenList(nodesToTraverse, temp.getChildren(), index);

			} else { // FileNode was found
				temp2 = (FileNode) nodesToTraverse.get(0);
				if (temp2.getPath().getName().equals(fileName)) {
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
	 * @param newChildren     the list of nodes being added to nodesToTraverse
	 * @param index           the index of nodesToTraverse at which the newChildren
	 *                        items will be added.
	 */
	private static void addChildrenList(ArrayList<Node> nodesToTraverse, ArrayList<Node> newChildren, int index) {
		for (int i = 0; i < newChildren.size(); i++) {
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
				if (!tempFile.getPath().getPath().equals(".DS_Store") && 
						!this.contains(tempFile)) { // if the file found in tree2 was not found in this tree, it is extra
					extraFiles.add(tempFile.getPath());
				}
			}
			nodesToTraverse.remove(0);
		}
		return extraFiles;
	}

	/**
	 * Finds duplicate files in this tree. Use the overloaded other version of this
	 * method if you want to worry about which duplicate file will be added to the
	 * returned list.
	 * 
	 * @return returns an ArrayList of Files that are found twice in this tree; only
	 *         one of each pair of duplicates will be in this list.
	 */
	public ArrayList<File> findDuplicateFiles() {
		return findDuplicateFiles(null);
	}

	/**
	 * Finds duplicate files in this tree. When one is found, it checks to see if
	 * the two duplicates are in the same path as the otherTree. If one of them is
	 * in the exact same place, it marks the one that is not in the same place as a
	 * duplicate, so that if it is deleted the one in the same place as the
	 * otherTree will be left untouched. In the case that neither are in the same
	 * place in the otherTree, then either file may be marked as duplicate.
	 * 
	 * -Note: ignores ".DS_Store" files from Mac systems.t o
	 * 
	 * @param otherTree another FileTree object to check if files are in the same
	 *                  position as this one (see above) - null is acceptable
	 * @return returns an ArrayList of Files that are found twice in this tree; only
	 *         one of each pair of duplicates will be in this list.
	 */
	public ArrayList<File> findDuplicateFiles(FileTree otherTree) {
		ArrayList<File> duplicates = new ArrayList<File>();
		HashMap<FileNode, FileNode> seen = new HashMap<FileNode, FileNode>();

		@SuppressWarnings("unchecked")
		ArrayList<Node> nodesToTraverse = (ArrayList<Node>) this.root.getChildren().clone();
		FolderNode temp;
		FileNode temp2;

		while (!nodesToTraverse.isEmpty() && nodesToTraverse != null) {
			if (FolderNode.isFolderNode(nodesToTraverse.get(0))) { // FolderNode found; add children
				temp = (FolderNode) nodesToTraverse.get(0);
				nodesToTraverse.remove(0);
				addChildrenList(nodesToTraverse, temp.getChildren(), 0);

			} else { // FileNode was found
				temp2 = (FileNode) nodesToTraverse.get(0);

				if (!temp2.getPath().getName().equals(".DS_Store")) {
					if (!seen.containsValue(temp2)) { // this FileNode hasn't been seen
						seen.put(temp2, temp2); // add to seen

					} else { // filenode has been seen, it is duplicate!
						if (otherTree == null) {
							duplicates.add(temp2.getPath());
						} else {
							// if temp2 is in the same spot in otherTree, add File from seen
							if (otherTree.isFileAtPath(temp2, getPathAfterThisTree(temp2))) {
								duplicates.add(seen.get(temp2).getPath());
							}
							// else, add temp2
							else {
								duplicates.add(temp2.getPath());
							}
						}
					}
				}
				nodesToTraverse.remove(0);
			}
		}

		return duplicates;
	}

	/**
	 * Returns the path to file beginning after the folder at root of this tree. The
	 * 
	 * String returned does not begin with a slash, instead begins with the first
	 * folder in the path to the file, or if file was in root folder, then just
	 * displays file name.
	 * 
	 * Looks for the root folder with a slash before and after it in the path of the
	 * file parameter. Accounts for either forward or back slashes depending on
	 * operating system.
	 * 
	 * Precondition: path parameter must be a path in this tree, IE the root must be
	 * somewhere in the path, AND there must be a slash directly after it.
	 * 
	 * If path does not have the root of this tree somewhere in it, null will be
	 * returned.
	 * 
	 * @param path the file to get the path of.
	 * @return the path to file beginning after the folder at root of this tree.
	 */
	protected String getPathAfterThisTree(String path) {
		if (path.lastIndexOf("/" + this.getRoot().getPath().getName() + "/") < 0) {
			return null;
		}

		int startIndex = path.lastIndexOf("/" + this.getRoot().getPath().getName() + "/") + 2
				+ this.getRoot().getPath().getName().length();

		if (startIndex < 0) { // meaning forward slash was not found, try backward slash in the path
			startIndex = path.lastIndexOf("\\" + this.getRoot().getPath().getName() + "\\") + 2
					+ this.getRoot().getPath().getName().length();
		}

		return path.substring(startIndex);
	}

	/**
	 * Overloaded version of the method that accepts a FileNode object, and passes
	 * its path to the main version of the method.
	 * 
	 * @param file FileNode object to take path from.
	 * @return the path to file beginning after the folder at root of this tree.
	 */
	protected String getPathAfterThisTree(FileNode file) {
		return this.getPathAfterThisTree(file.getPath().getPath());
	}

	/**
	 * Checks to see if *file* is at the path given from *path*.
	 * 
	 * Protected to make sure valid paths are passed to this method.
	 * 
	 * Precondition: path must begin with first folder after the root of this tree
	 * or the file if it is in the root folder. Root folder should not be included
	 * in path String. (See getPathAfterThisTree() method, should be called on path
	 * before)
	 * 
	 * If path is null, false will always be returned.
	 * 
	 * @param file FileNode to search for
	 * @param path path to look for file at
	 * @return true if file was found at the end of path, false otherwise.
	 */
	protected boolean isFileAtPath(FileNode file, String path) {
		if (path == null) {
			return false;
		}

		path = FileTree.replaceBackSlashWithForward(path);

		String[] splitPath = path.split("/");
		FolderNode curFolder = this.getRoot();
		ArrayList<Node> curFolderChildren;

		// iterate through FileTree DS nodes and follow the path
		for (int i = 0; i < splitPath.length; i++) {
			curFolderChildren = curFolder.getChildren();

			// if done (on the last element in splitPath, the filename)
			if (i == splitPath.length - 1) {
				if (!curFolder.contains(file)) {
					return false;
				}
			}

			// else, not done, keep iterating
			else {
				// Check to make sure curFolder has the next folder in the sequence, and then
				// change curFolder to that next folder.
				if (!curFolder.contains(new FolderNode(splitPath[i], null))) {
					return false;
				}

				// change curFolder
				Node tempNode;
				for (int j = 0; j < curFolder.getChildren().size(); j++) {

					tempNode = curFolder.getChildren().get(j);
					if (FolderNode.isFolderNode(tempNode)) {
						if (((FolderNode) tempNode).equalsName(new FolderNode(splitPath[i], null))) {
							curFolder = (FolderNode) tempNode;
							j = curFolder.getChildren().size() + 1;
						}
					}
				}

			}
		}

		return true; // if false wasn't already returned, everything checked out.
	}

	/**
	 * Overloaded version of this method that accepts a java File object as input.
	 * 
	 * Read other version of method for more details.
	 * 
	 * @param file the File object that will be used to create a FileNode object.
	 * @param path the String path.
	 * @return true if file was found at the end of path, false otherwise.
	 */
	protected boolean isFileAtPath(File file, String path) {
		return this.isFileAtPath(new FileNode(file, null), path);
	}

	/**
	 * Replaces \ back slashes with / forward slashes found anywhere in the path
	 * String.
	 * 
	 * @param path the string being looked at
	 * @return path with only forward slashes.
	 */
	public static String replaceBackSlashWithForward(String path) {
		for (int i = 0; i < path.length(); i++) {
			if (path.substring(i, i + 1).equals("\\")) {
				path = path.substring(0, i) + "/" + path.substring(i + 1);
			}
		}
		return path;
	}

	/**
	 * Accessor method for the root instance field.
	 * 
	 * @return the root of this tree.
	 */
	public FolderNode getRoot() {
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
	protected static int compareStrings(String first, String second) {
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
	 * Overloaded version of the method: accepts parent strings of the first and
	 * second strings. These will be used for an additional test of similarity.
	 * 
	 * @param firstParent  parent string of first
	 * @param first        child string of firstParent
	 * @param secondParent parent string of second
	 * @param second       child string of secondParent
	 * @return returns an int score of similarity, 0 being the lowest possible
	 *         score.
	 */
	protected static int compareStrings(String firstParent, String first, String secondParent, String second) {
		return compareStrings(first, second) + compareStrings(firstParent, secondParent);
	}

}
