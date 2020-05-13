package filestructure;

import java.io.File;
import java.util.List;

public class utility {

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
}
