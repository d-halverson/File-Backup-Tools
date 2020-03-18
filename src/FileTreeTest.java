import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileTreeTest {

	@BeforeEach
	void setUp() throws Exception {
		FileTree tree1 = new FileTree("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1");
		System.out.println("Hi");
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

}
