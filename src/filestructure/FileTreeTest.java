package filestructure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileTreeTest {

	protected static FileTree tree2;
	protected static FileTree tree1;

	protected static final FileNode zebra = new FileNode(
			"/Users/drewhalverson/OneDrive - " + "UW-Madison/Github/File_Backup_Tools/files_for_testing/zebra.rtf",
			null);
	protected static final FileNode doggy = new FileNode(
			"/Users/drewhalverson/OneDrive - " + "UW-Madison/Github/File_Backup_Tools/files_for_testing/doggy.pdf",
			null);
	protected static final FileNode Doggy = new FileNode(
			"/Users/drewhalverson/OneDrive - " + "UW-Madison/Github/File_Backup_Tools/files_for_testing/Doggy.pdf",
			null);

	protected static final FolderNode tree1Folder = new FolderNode(
			"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1", null);
	protected static final FolderNode tree2Folder = new FolderNode(
			"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2", null);
	protected static final FolderNode AppleTree1 = new FolderNode(
			"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/Apple", tree1Folder);
	protected static final FolderNode AppleTree2WithTree1ParentNode = new FolderNode(
			"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/Apple", tree1Folder);
	protected static final FolderNode AppleTree2 = new FolderNode(
			"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/Apple", tree2Folder);
	protected static final FileNode madison = new FileNode(
			"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/Apple/madison skyline.jpeg",
			AppleTree1);

	@BeforeEach
	void setUp() throws Exception {
		tree1 = new FileTree("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1");
		tree2 = new FileTree("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Basic testing of the compareString() method.
	 */
	@Test
	void test001_compareStrings() {
		assertEquals(FileTree.compareStrings("Doggy", "Doggo"), 5);
		assertEquals(FileTree.compareStrings("dog", "Doggo"), 4);
		assertEquals(FileTree.compareStrings("hidoggo", "Doggo"), 1);
	}

	/**
	 * Checks to see the results of several calls to the contains() method to see if
	 * they are correct.
	 */
	@Test
	void test002_containsTests() {
		try {
			tree1.contains(null);
			fail("IllegalArgumentException should have been thrown.");
		} catch (IllegalArgumentException e) {
			// expected
		}

		assertTrue(tree1.contains(zebra));
		assertTrue(tree1.contains(doggy));
		assertFalse(tree1.contains(Doggy));
	}

	/**
	 * Tests the getPathAfterThisTree() method in FileTree class.
	 */
	@Test
	void test003_PathAfterThisTree() {
		FileNode doggyInTree1 = new FileNode(
				new File("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/doggo/doggy.pdf"),
				null);
		assertEquals("doggo/doggy.pdf", tree1.getPathAfterThisTree(doggyInTree1));
	}

	/**
	 * Tests the isFileAtPath() method in FileTree class.
	 */
	@Test
	void test004_isFileAtPath() {
		assertFalse(tree1.isFileAtPath(doggy, doggy.getPath().getPath()));
		assertTrue(tree1.isFileAtPath(doggy, tree1.getPathAfterThisTree(
				"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/doggo/doggy.pdf")));
		assertFalse(tree2.isFileAtPath(madison, tree1.getPathAfterThisTree(
				"/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/Apple/madison skyline.jpeg")));
	}
	
	/**
	 * Tests the duplicate files method with tree2 (pretending this is the backup)
	 */
	@Test
	void test005_duplicateFiles() {
		ArrayList<File> duplicates = tree2.findDuplicateFiles();
		
		assertEquals("doggy.pdf", duplicates.get(0).getName());
	}

}
