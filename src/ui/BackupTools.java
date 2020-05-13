package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import filestructure.*;

/**
 * This class handles user input and calls appropriate methods in the rest of the project.
 * @author drewhalverson
 */
public class BackupTools {
	
	private final static Scanner input = new Scanner(System.in);
	private static FileTree source = null;
	private static FileTree backup = null;

	public static void main(String[] args) {
		//generating FileTrees
		System.out.print("Please input a \"source\" path pointing a folder:");
		source = setTreeFromInput();

		System.out.print("Now input a \"backup\" path pointing to a folder:");
		backup = setTreeFromInput();
		
		//main command loop:
		String userInput;
		boolean loop = true;
		while(loop) {
			System.out.print("Enter a command (type help for a list of commands):");
			userInput = input.nextLine();	
			
			if(userInput.equals("help")) {
				System.out.println("Available commands: \"quit\", \"extra files\", \"search\"");
			}
			else if(userInput.equals("quit") || userInput.equals("exit")){
				System.out.println("Quiting...");
				loop = false;
			}
			else if(userInput.equals("extra files")) {
				extraFilesCommand();
			}
			else if(userInput.equals("search")) {
				searchCommand();
			}
			else {
				System.out.println("Command not recognized. Please try again.");
			}
			System.out.println();
		}
		
		input.close();
	}
	
	/**
	 * Helper method that is called when executing the searchCommand. Prompts the user to ask which filtree to 
	 * search in, and then asks for the filename to look for.
	 * 
	 * @param source the source FileTree
	 * @param backup the backup FileTree.
	 */
	private static void searchCommand() {
		int srcBakAns = getUserInput("Would you like to search the source or backup? (Type \"source\" or \"backup\"):"
				, new ArrayList<String>(Arrays.asList("source", "backup")));
		System.out.print("Please enter the name of the file, including its extenstion: ");
		String fileName = input.nextLine();
		boolean found;
		
		if(srcBakAns == 0) {
			System.out.println("Searching for the file: \""+fileName+"\" in the "
					+ "folder: \""+source.getRoot().getPath().getName()+"\"...");
			found = source.containsFileName(fileName);
			if(found) {
				System.out.println("Found!");
			}
			else {
				System.out.println("Not found.");
			}
		}
		else {
			System.out.println("Searching for the file: \""+fileName+"\" in the "
					+ "folder: \""+backup.getRoot().getPath().getName()+"\"...");
			found = backup.containsFileName(fileName);
			if(found) {
				System.out.println("Found!");
			}
			else {
				System.out.println("Not found.");
			}
		}
		
		
	}
	
	/**
	 * Private helper method for getting user input when asking a question. Returns an integer corresponding
	 * to the index in which the answer was found in the list answers parameter.
	 * 
	 * Precondition: answers must be non empty and non null. 
	 * 
	 * @param prompt a String to prompt the user (the question)
	 * @param answers a list of acceptable answers to this question
	 * @return Returns an integer corresponding to the index in which the answer was found in the list 
	 * answers parameter.
	 */
	private static int getUserInput(String prompt, ArrayList<String> answers) {
		String userInput;
		
		while(true) {
			System.out.print(prompt);
			userInput = input.nextLine();
			
			if(answers.contains(userInput)) {
				return answers.indexOf(userInput);
			}
			else {
				System.out.println("Command not recognized. Please try again.");
			}
		}
	}
	
	/**
	 * Called when the extra files command is entered
	 * 
	 * @param source the source filetree
	 * @param backup the backup filetree
	 */
	private static void extraFilesCommand() {
		int answer = getUserInput("Would you like to find extra files in the backup folder, or source?"
				+ " (Type \"backup\" or \"source\"):", 
				new ArrayList<String>(Arrays.asList("source", "backup")));
		
		if(answer==0) {
			findExtraFilesOutput();
		}
		else if(answer==1) {
			findExtraFilesOutput();
		}
	}
	
	/**
	 * Called when the user wants to find extra files in the backup fileTree.
	 * 
	 * @param source the source filetree
	 * @param backup the backup filetree
	 */
	private static void findExtraFilesOutput() {
		System.out.println("\nChecking for extra files...");
		ArrayList<File> extraFiles = source.findExtraFiles(backup);
		System.out.println("Extra files found in \"" + 
				backup.getRoot().getPath().getName() +"\":");
		utility.printArray(extraFiles);

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
			boolean success = utility.deleteFiles(extraFiles);
			
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
	
}
