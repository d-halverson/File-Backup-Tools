import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BackupTools {
	
	private final static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		//generating FileTrees
		FileTree source = null; //getting source FileTree
		System.out.print("Please input a \"source\" path pointing a folder:");
		source = setTreeFromInput();
		System.out.println(source==null);

		FileTree backup = null; //getting backup FileTree
		System.out.print("Now input a \"backup\" path pointing to a folder:");
		backup = setTreeFromInput();
		
		//main command loop:
		String userInput;
		boolean loop = true;
		while(loop) {
			System.out.print("Enter a command (type help for a list of commands):");
			userInput = input.nextLine();	
			
			if(userInput.equals("help")) {
				System.out.println("Available commands: \"quit\", \"extra files\"");
			}
			else if(userInput.equals("quit") || userInput.equals("exit")){
				System.out.println("Quiting...");
				loop = false;
			}
			else if(userInput.equals("extra files")) {
				extraFilesCommand(source, backup);
			}
			else {
				System.out.println("Command not recognized. Please try again.");
			}
			System.out.println();
		}
		
		input.close();
	}
	
	/**
	 * Called when the extra files command is entered
	 * 
	 * @param source the source filetree
	 * @param backup the backup filetree
	 */
	private static void extraFilesCommand(FileTree source, FileTree backup) {
		boolean loop = true;
		String userInput;
		
		while(loop) {
			System.out.println("Would you like to find extra files in the backup folder, or source?");
			System.out.print("(Type \"backup\" or \"source\"):");
			userInput = input.nextLine();
			
			if(userInput.equals("backup")) {
				findExtraFilesOutput(source, backup);
				loop = false;
			}
			else if(userInput.equals("source")) {
				findExtraFilesOutput(backup, source);
				loop = false;
			}
			else {
				System.out.println("Command not recognized. Please try again.");
			}
		}
	}
	
	/**
	 * Called when the user wants to find extra files in the backup fileTree.
	 * 
	 * @param source the source filetree
	 * @param backup the backup filetree
	 */
	private static void findExtraFilesOutput(FileTree source, FileTree backup) {
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
		
		//deleting
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
	 * Continues a loop to get valid input from the user, and then uses that input to generate
	 * a FileTree object and return it.
	 * 
	 * @return returns the FileTree generated
	 */
	private static FileTree setTreeFromInput() {
		boolean failed = true;
		FileTree tree = null;
		
		while (failed == true) {
			try {
				tree = new FileTree(input.nextLine());
				failed = false;
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage() + " Please try again.");
				failed = true;
			}
		}
		
		return tree;
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
