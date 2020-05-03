package filestructure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileTreeTest {

	protected FileTree tree1;
	protected final FileNode zebra = new FileNode(
			"/Users/drewhalverson/OneDrive - " + "UW-Madison/Github/File_Backup_Tools/files_for_testing/zebra.rtf",
			null);
	protected final FileNode doggy = new FileNode(
			"/Users/drewhalverson/OneDrive - " + "UW-Madison/Github/File_Backup_Tools/files_for_testing/doggy.pdf",
			null);
	protected final FileNode Doggy = new FileNode(
			"/Users/drewhalverson/OneDrive - " + "UW-Madison/Github/File_Backup_Tools/files_for_testing/Doggy.pdf",
			null);

	@BeforeEach
	void setUp() throws Exception {
		tree1 = new FileTree("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1");
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

}
