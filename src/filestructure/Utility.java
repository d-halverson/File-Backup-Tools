package filestructure;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Utility {

	/**
	 * Prints an array of File objects line by line with a number before them
	 * indicating which number in the list it is, starting at 1.
	 * 
	 * @param array the array to be printed
	 */
	public static void printArray(List<File> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i+1 + ". " + list.get(i).getName());
		}
	}

	/**
	 * Deletes the files that are referenced as File objects in the array formal
	 * parameter.
	 * 
	 * 
	 * @param array the
	 * @return returns true if all files were successfully deleted, false otherwise.
	 */
	public static boolean deleteFiles(List<File> list) {
		boolean result = true;

		for (int i = 0; i < list.size(); i++) {
			try {
				if (!list.get(i).delete()) // if file couldn't be deleted, set result to false.
					result = false;
			} catch (Exception e) {
				System.out.println("Error: file \"" + list.get(i).getName() + 
						"\" could not be deleted. Error message:" + e.getMessage());
				result = false;
			}
		}

		return result;
	}
	
	/**
	 * Copies the source File object to the path given in the dest File object.
	 * 
	 * @param source the File to be copied
	 * @param dest contains the path to be copied to
	 * @return returns true if the operation was successful, false otherwise.
	 */
	public static boolean copyFile(File source, File dest) {
		try {
			Files.copy(source.toPath(), dest.toPath());
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}