package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import filestructure.*;

/**
 * This class handles user input and calls appropriate methods in the rest of
 * the project.
 * 
 * @author drewhalverson
 */
public class BackupTools {

	private final static Scanner input = new Scanner(System.in);
	private static FileTree source = null;
	private static FileTree backup = null;
	private static String sourceRoot;
	private static String backupRoot;

	public static void main(String[] args) {
		// generating FileTrees
		System.out.print("Please input a \"source\" path pointing a folder:");
		source = setTreeFromInput();
		sourceRoot = source.getRoot().getPath().getName();

		System.out.print("Now input a \"backup\" path pointing to a folder:");
		backup = setTreeFromInput();
		backupRoot = backup.getRoot().getPath().getName();

		// main command loop:
		String userInput;
		boolean loop = true;
		while (loop) {
			System.out.print("Enter a command (type help for a list of commands):");
			userInput = input.nextLine();

			if (userInput.equals("help")) {
				System.out.println("Available commands: \"quit\", \"extra files\", \"search\", \"duplicate files\"");
			} else if (userInput.equals("quit") || userInput.equals("exit")) {
				System.out.println("Quiting...");
				loop = false;
			} else if (userInput.equals("extra files")) {
				extraFilesCommand();
			} else if (userInput.equals("search")) {
				searchCommand();
			} else if (userInput.equals("duplicate files")) {
				duplicateFilesCommand();
			} else {
				System.out.println("Command not recognized. Please try again.");
			}
			System.out.println();
		}

		input.close();
	}

	/**
	 * Helper method that is called when executing the searchCommand. Prompts the
	 * user to ask which filtree to search in, and then asks for the filename to
	 * look for.
	 * 
	 * @param source the source FileTree
	 * @param backup the backup FileTree.
	 */
	private static void searchCommand() {
		int srcBakAns = getUserInput("Would you like to search the source or backup? (Type \"source\" or \"backup\"):",
				new ArrayList<String>(Arrays.asList("source", "backup")));
		System.out.print("Please enter the name of the file, including its extenstion: ");
		String fileName = input.nextLine();
		boolean found;

		if (srcBakAns == 0) {
			System.out.println("Searching for the file: \"" + fileName + "\" in the " + "folder: \""
					+ source.getRoot().getPath().getName() + "\"...");
			found = source.containsFileName(fileName);
			if (found) {
				System.out.println("Found!");
			} else {
				System.out.println("Not found.");
			}
		} else {
			System.out.println("Searching for the file: \"" + fileName + "\" in the " + "folder: \""
					+ backup.getRoot().getPath().getName() + "\"...");
			found = backup.containsFileName(fileName);
			if (found) {
				System.out.println("Found!");
			} else {
				System.out.println("Not found.");
			}
		}

	}

	/**
	 * Private helper method for getting user input when asking a question. Returns
	 * an integer corresponding to the index in which the answer was found in the
	 * list answers parameter.
	 * 
	 * Precondition: answers must be non empty and non null.
	 * 
	 * @param prompt  a String to prompt the user (the question)
	 * @param answers a list of acceptable answers to this question
	 * @return Returns an integer corresponding to the index in which the answer was
	 *         found in the list answers parameter.
	 */
	private static int getUserInput(String prompt, ArrayList<String> answers) {
		String userInput;

		while (true) {
			System.out.print(prompt);
			userInput = input.nextLine();

			if (answers.contains(userInput)) {
				return answers.indexOf(userInput);
			} else {
				System.out.println("Command not recognized. Please try again.");
			}
		}
	}
	
	/**
	 * Reloads FileTree objects based on stored root instance fields.
	 * Called when files are deleted.
	 */
	private static void rebuild() {
		System.out.println("Reloading files from source and backup folders...");
		source = new FileTree(sourceRoot);
		backup = new FileTree(backupRoot);
	}

	/**
	 * Command to handle duplicate files command input.
	 */
	private static void duplicateFilesCommand() {
		int answer = getUserInput(
				"Would you like to find duplicate files in the backup folder, or source?"
						+ " (Type \"backup\" or \"source\"):",
				new ArrayList<String>(Arrays.asList("source", "backup")));

		boolean rebuild = false;
		
		if (answer == 0) { // source
			rebuild = duplicateFilesOutput(source, backup);
		} else if (answer == 1) { // backup
			rebuild = duplicateFilesOutput(backup, source);
		}
	
		if(rebuild) {
			rebuild();
		}
			
	}

	/**
	 * Long output and user input handling of the duplicate files command.
	 * 
	 * @param tree      the tree duplicate files are being searched for in
	 * @param otherTree a tree used for as reference in the findDuplicateFiles()
	 *                  method.
	 * @return true if filetrees need to be rebuilt (something was deleted), false otherwise
	 */
	private static boolean duplicateFilesOutput(FileTree tree, FileTree otherTree) {
		System.out.println("\nSearching for duplicate files in \"" + tree.getRoot().getPath().getName() + "\"");
		ArrayList<File> duplicateFiles = tree.findDuplicateFiles(otherTree);
		boolean rebuild = false;

		if (duplicateFiles.size() == 0) {
			System.out.println("None found!");
		} else {
			System.out.println("Duplicate files found in \"" + tree.getRoot().getPath().getName() + "\":");
			utility.printArray(duplicateFiles);

			ArrayList<File> toBeDeleted = new ArrayList<File>();

			// userInput (getUserInput is not complex enough for handling this)
			boolean done = false;
			String userInput;
			while (!done) {
				System.out.println(
						"\nDo you want all of these files to be deleted? (Type \"delete all\" to delete all files.)");
				System.out.println(
						"To view the path a specific file, type \"v\" followed by the number that precedes the file name.");
				System.out.println("To manually select files to be deleted, type the number preceding the file.");
				System.out.println(
						"When you are done selecting files, or do not want to delete any, type \"done\" (you will be displayed your selection before they are deleted).");
				userInput = input.nextLine();

				if (userInput.equals("delete all")) {
					boolean success = utility.deleteFiles(duplicateFiles);

					if (success)
						System.out.println("All files successfully deleted.");
					else
						System.out.println("Not all files were successfully deleted.");
					rebuild = true;
					done = true;
				} else if (userInput.equals("done")) {
					rebuild = doubleCheckDeletion(toBeDeleted);
					done = true;
				} else {
					if (userInput.length() > 0 && userInput.substring(0, 1).equals("v")) {
						try {
							userInput = userInput.substring(1);
							int indexToView = Integer.parseInt(userInput);
							indexToView--;
							if (indexToView < duplicateFiles.size() && indexToView >= 0) { // if valid index
								File file = duplicateFiles.get(indexToView);
								System.out.println("\"" + file.getName() + "\" path: " + file.getPath());
							} else
								System.out.println(
										"Command not recognized. Please try again. (If you are entering a number, make sure it is in valid range.)");
						} catch (NumberFormatException e) {
							// this just means a correct number wasn't entered, continue to command not
							// recognized
							System.out.println(
									"Command not recognized. Please try again. (If you are entering a number, make sure it is in valid range.)");
						}
					}

					else {
						try {
							int indexToDel = Integer.parseInt(userInput);
							indexToDel--;
							if (indexToDel < duplicateFiles.size() && indexToDel >= 0) { // if valid index
								toBeDeleted.add(duplicateFiles.get(indexToDel));
								System.out.println(duplicateFiles.get(indexToDel).getName()
										+ " has been added to the deletion list.");
								duplicateFiles.remove(indexToDel);
							} else
								System.out.println(
										"Command not recognized. Please try again. (If you are entering a number, make sure it is in valid range.)");
						} catch (NumberFormatException e) {
							// this just means a correct number wasn't entered, continue to command not
							// recognized
							System.out.println(
									"Command not recognized. Please try again. (If you are entering a number, make sure it is in valid range.)");
						}
					}
				}
			}
		}
		
		return rebuild;
	}

	/**
	 * Prints the toBeDeleted files, and then asks the user if they want them to be
	 * deleted.
	 * 
	 * @param toBeDeleted a list of files to be deleted.
	 * @return true if files were deleted, false otherwise
	 */
	private static boolean doubleCheckDeletion(List<File> toBeDeleted) {
		boolean deleted = false;
		
		if (toBeDeleted.size() > 0) {

			System.out.println("Files to be deleted:");
			utility.printArray(toBeDeleted);
			System.out.print("Do you want to delete these files? Type Yes/No:");

			String tempInput = input.nextLine();
			while (!(tempInput.equalsIgnoreCase("y") || tempInput.equalsIgnoreCase("n")
					|| tempInput.equalsIgnoreCase("yes") || tempInput.equalsIgnoreCase("no"))) {
				System.out.print("Please type yes, y, no, or n:");
				tempInput = input.nextLine();
			}

			// deleting
			if (tempInput.equalsIgnoreCase("yes") || tempInput.equalsIgnoreCase("y")) {
				boolean success = utility.deleteFiles(toBeDeleted);
				
				if (success)
					System.out.println("All files successfully deleted.");
				else
					System.out.println("Not all files were successfully deleted.");
				deleted = true;
				
			} else if (tempInput.equalsIgnoreCase("no") || tempInput.equalsIgnoreCase("n")) {
				System.out.println("Ok, not deleting.");
			}
		}
		
		return deleted;
	}

	/**
	 * Called when the extra files command is entered
	 * 
	 * @param source the source filetree
	 * @param backup the backup filetree
	 */
	private static void extraFilesCommand() {
		int answer = getUserInput(
				"Would you like to find extra files in the backup folder, or source?"
						+ " (Type \"backup\" or \"source\"):",
				new ArrayList<String>(Arrays.asList("source", "backup")));

		boolean rebuild = false;
		if (answer == 0) {
			rebuild = findExtraFilesOutput(backup, source);
		} else if (answer == 1) {
			rebuild = findExtraFilesOutput(source, backup);
		}
		
		if(rebuild)
			rebuild();
	}

	/**
	 * Called when the user wants to find extra files in the backup fileTree.
	 * 
	 * @param source the source filetree
	 * @param backup the backup filetree
	 * @return true if files were deleted, false otherwise
	 */
	private static boolean findExtraFilesOutput(FileTree source, FileTree backup) {
		System.out.println("\nChecking for extra files...");
		ArrayList<File> extraFiles = source.findExtraFiles(backup);
		System.out.println("Extra files found in \"" + backup.getRoot().getPath().getName() + "\":");
		utility.printArray(extraFiles);

		// asking if user wants files to be deleted.
		System.out.print("\nWould you like these files to be deleted? Just for clarifation,"
				+ "these are files that are inside the backup folder you provided: "
				+ backup.getRoot().getPath().getName() + ".\nType Yes/No:");
		String tempInput = input.nextLine();
		while (!(tempInput.equalsIgnoreCase("y") || tempInput.equalsIgnoreCase("n") || tempInput.equalsIgnoreCase("yes")
				|| tempInput.equalsIgnoreCase("no"))) {
			System.out.print("Please type yes, y, no, or n:");
			tempInput = input.nextLine();
		}

		boolean deleted = false;
		// deleting
		if (tempInput.equalsIgnoreCase("yes") || tempInput.equalsIgnoreCase("y")) {
			boolean success = utility.deleteFiles(extraFiles);

			if (success)
				System.out.println("All files successfully deleted.");
			else
				System.out.println("Not all files were successfully deleted.");
			deleted = true;
		} else if (tempInput.equalsIgnoreCase("no") || tempInput.equalsIgnoreCase("n")) {
			System.out.println("Ok, not deleting.");
		}
		
		return deleted;
	}

	/**
	 * Continues a loop to get valid input from the user, and then uses that input
	 * to generate a FileTree object and return it.
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

}
