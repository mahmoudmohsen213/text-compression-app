

import ui.MainFrame;
import encoders.AdHuffmanEncoder;
import encoders.ArithmaticEncoder;
import encoders.StdHuffmanEncoder;

public class Program {

	public static void main(String[] args) {
		Program.loadClasses();
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
	
	private static void loadClasses(){
		new StdHuffmanEncoder();
		new AdHuffmanEncoder();
		new ArithmaticEncoder();
	}
}
