import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		FileTree tree = new FileTree("/Users/drewhalverson/Desktop/Tree1");
		FileTree tree2 = new FileTree("/Users/drewhalverson/Desktop/Tree2");
		FileNode node = new FileNode("terminal.png", null);
		//System.out.println("\n"+tree.contains(node));
		ArrayList<File> extraFiles = tree.findExtraFiles(tree2);
		printArray(extraFiles);
		
		System.out.println("\n"+tree.contains(node));
	}	
	
	public static void printArray(ArrayList<File> array) {
		for(int i=0; i<array.size(); i++) {
			System.out.println(array.get(i).getName());
		}
	}

}
