import java.io.File;
import java.util.ArrayList;

public class BackupTools {

	public static void main(String[] args) {

	}

	/**
	 * Prints an array of File objects line by line with a number before them
	 * indicating which number in the list it is, starting at 1.
	 * 
	 * @param array the array to be printed
	 */
	private static void printArray(ArrayList<File> array) {
		for (int i = 0; i < array.size(); i++) {
			System.out.println(i + ". " + array.get(i).getName());
		}
	}

	/**
	 * Finds the extra files that are in "backup" FileTree and not in "source"
	 * FileTree.
	 * 
	 * @param source FileTree that is the template for what files should be present
	 *               in backup.
	 * @param backup FileTree that is being checked to see if it has files that are
	 *               not in source
	 */
	public static void listExtraFiles(FileTree source, FileTree backup) {
		printArray(source.findExtraFiles(backup));
	}

	/**
	 * Deletes the files that are referenced as File objects in the array formal
	 * parameter.
	 * 
	 * 
	 * @param array
	 * @return returns true if all files were successfully deleted, false otherwise.
	 */
	private static boolean deleteFiles(ArrayList<File> array) {
		boolean result = true;
		
		for (int i = 0; i < array.size(); i++) {
			try {
				array.get(i).delete();
			}
			catch(Exception e) {
				System.out.println("Error: file \""+ array.get(i).getName()+
						"\" could not be deleted. Error message:"+e.getMessage());
			}
		}
		
		return result;
	}

}
