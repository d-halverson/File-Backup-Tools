package filestructure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilityTest {

	protected static final FolderNode tree1Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1", null);
	protected static final FolderNode tree2Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2", null);
	protected static final File copyTestFile = new File("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/copyTestFile.rtf");
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Tests the copyFile() method in the utility class.
	 */
	@Test
	void test001_copyFiles() {
		assertTrue(Utility.copyFile(copyTestFile, new File("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/dog")));
	}

}
