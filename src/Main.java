
public class Main {

	public static void main(String[] args) {
		FileTree tree = new FileTree("/Users/drewhalverson/OneDrive - UW-Madison/Github/File_Backup_Tools");
		FileNode node = new FileNode("poopy.java", null);
		System.out.println(tree.contains(node));
	}	

}
