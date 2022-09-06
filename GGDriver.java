import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GGDriver {
	public static void main(String[] args) throws IOException { // Puts everything together.
		GuessingGame game = new GuessingGame();
		Scanner in = new Scanner(System.in);
		BinaryTree bt = new BinaryTree();
		PrintWriter out = new PrintWriter(new FileWriter("answers.txt", true)); // File writing object.
		Scanner file = new Scanner(new File("answers.txt")); // File reading object.

		System.out.println("Welcome to \"The Binary Tree Guessing Game\" by Dean Danner. Have fun!");

		if (file.hasNextLine()) { // Makes sure the file is not empty, then loads it.
			System.out.println("Save found! Loading save.\n");
			game.loadFile(file, bt);
		}

		while (true) {
			if (game.getWin()) { // Checks to see if the user won the game.
				System.out.println("I win!");
				break;
			} else if (game.getQuit()) { // Checks to see if the user quit the game.
				System.out.println("Cya later. Hope you had fun!");
				break;
			} else // Runs the game if they haven't quit.
				game.runGame(in, bt, "", false, out);
		}

		out.close();
		file.close();
	}
}
