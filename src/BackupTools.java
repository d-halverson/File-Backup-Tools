import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BackupTools {

	public static void main(String[] args) {
		final Scanner input = new Scanner(System.in);
		FileTree source = null;
		FileTree backup = null;
		boolean failed = true;

		// getting source filetree
		while (failed == true) {
			System.out.print("Please input a \"source\" path pointing a folder:");
			try {
				source = new FileTree(input.nextLine());
				failed = false;
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage() + " Please try again.");
				failed = true;
			}
		}

		// getting backup filetree
		failed = true;
		while (failed == true) {
			System.out.print("Now input a \"backup\" path pointing to a folder:");
			try {
				backup = new FileTree(input.nextLine());
				failed = false;
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage() + " Please try again.");
				failed = true;
			}
		}

		System.out.println("\nChecking for extra files...");
		ArrayList<File> extraFiles = source.findExtraFiles(backup);
		System.out.println("Extra files found in \"" + 
				backup.getRoot().getPath().getName() +"\":");
		printArray(extraFiles);

		//asking if user wants files to be deleted.
		System.out.print("\nWould you like these files to be deleted? Just for clarifation,"
				+ "these are files that are inside the backup folder you provided: "
				+ backup.getRoot().getPath().getName() + ".\nType Yes/No:");
		String tempInput = input.nextLine();
		while (!(tempInput.equalsIgnoreCase("y") || tempInput.equalsIgnoreCase("n") 
				|| tempInput.equalsIgnoreCase("yes") || tempInput.equalsIgnoreCase("no"))) {
			System.out.print("Please type yes, y, no, or n:");
			tempInput = input.nextLine();
		}
		input.close();
		
		if(tempInput.equalsIgnoreCase("yes") || tempInput.equalsIgnoreCase("y")) {
			boolean success = deleteFiles(extraFiles);
			
			if(success)
				System.out.println("All files successfully deleted.");
			else
				System.out.println("Not all files were successfully deleted.");
		}
		else if(tempInput.equalsIgnoreCase("no") || tempInput.equalsIgnoreCase("n")) {
			System.out.println("Ok, not deleting.");
		}
			
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
				if (!array.get(i).delete()) // if file couldn't be deleted, set result to false.
					result = false;
			} catch (Exception e) {
				System.out.println("Error: file \"" + array.get(i).getName() + 
						"\" could not be deleted. Error message:" + e.getMessage());
				result = false;
			}
		}

		return result;
	}

}
