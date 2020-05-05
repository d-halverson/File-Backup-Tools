package filestructure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FolderNodeTest {

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
	
	protected static final FolderNode tree1Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1", null);
	protected static final FolderNode tree2Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2", null);
	protected static final FolderNode AppleTree1 = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/Apple", tree1Folder);
	protected static final FolderNode AppleTree2WithTree1ParentNode = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/Apple", tree1Folder);
	protected static final FolderNode AppleTree2 = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/Apple", tree2Folder);
	protected static final FileNode madison = new FileNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/Apple/madison skyline.jpeg", AppleTree1);
	
	@BeforeAll
	static void addChildrenToFolders() {
		AppleTree1.addChild(madison);
	}
	
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
	void test001_equals() {
		assertFalse(AppleTree1.equals(AppleTree2));
		assertFalse(AppleTree1.equals(AppleTree2WithTree1ParentNode));
	}
	
	/**
	 * Tests the contains() method. 
	 */
	@Test
	void test002_contains() {
		assertTrue(AppleTree1.contains(madison));
		assertFalse(AppleTree1.contains(doggy));
		assertFalse(tree1Folder.contains(madison)); //tree1 does not have madison in the immediate folder contents
	}


}