package filestructure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilityTest {

	protected static final FolderNode tree1Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1", null);
	protected static final FolderNode tree2Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2", null);
	protected static final FolderNode copyDestFolder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/copyDest1", null);
	protected static final FolderNode copyDest2Folder = new FolderNode("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/copyDest2", null);
	protected static final File copyTestFile = new File("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/copyTestFile.rtf");
	protected static final File copyTestFile2 = new File("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree1/copyTestFile2.rtf");
	
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
	void test001_copyFile() {
		assertTrue(Utility.copyFile(copyTestFile, new File("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools/tree2/dog/copyTestFile.rtf")));
	}
	
	/**
	 * Tests the copyFiles() method in the utility class.
	 */
	@Test
	void copyFiles() {
		ArrayList<File> sources = new ArrayList<File>();
		ArrayList<File> dests = new ArrayList<File>();
		
		sources.add(copyTestFile);
		sources.add(copyTestFile2);
		
		dests.add(new File(copyDestFolder.getPath().getPath()+"/"+copyTestFile.getName()));
		dests.add(new File(copyDest2Folder.getPath().getPath()+"/"+copyTestFile2.getName()));
		
		assertTrue(Utility.copyFiles(sources, dests));
	}

}
