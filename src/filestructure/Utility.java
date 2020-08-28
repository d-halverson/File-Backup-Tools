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
	 * @param destFolder contains the path to be copied to (IMPORTANT: must just include a path to the folder to be copied to).
	 * 
	 * @return returns true if the operation was successful, false otherwise.
	 */
	public static boolean copyFile(File source, File destFolder) {
		//first: check does the destFolder path need to be constructed?
		destFolder.mkdirs();
	
		try {
			Files.copy(source.toPath(), new File(destFolder.getPath()+"/"+source.getName()).toPath());
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Copies a list of Files to a list of Destinations. Plural form of copyFile method.
	 * 
	 * sources and dests lists MUST be associated: the first File in sources must correspond to the first
	 * item in dests, and so on.
	 * 
	 * @param sources A list of File objects to be copied.
	 * @param dests A list of File objects that contain the path of folders that the corresponding source File objects will be
	 * copied too. 
	 * 
	 * @return returns true if successful, false otherwise.
	 */
	public static boolean copyFiles(List<File> sources, List<File> dests) {
		if(sources.size()!=dests.size()) {
			throw new IllegalArgumentException("Sources and dests must be the same size, and items must be associated.");
		}
		
		//converting lists to arrays for better efficiency (don't have to call .get() over and over).
		Object[] sourcesArray = sources.toArray();
		Object[] destsArray = dests.toArray();
		
		boolean success = false;
		for(int i=0; i<sources.size(); i++) {
			success = copyFile((File)sourcesArray[i], (File)destsArray[i]);
			
			if(!success) {
				return false;
			}
		}
		
		return success;
	}
}
