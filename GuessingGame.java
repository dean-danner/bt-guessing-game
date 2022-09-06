package danproj3;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GuessingGame {
	private boolean hasWon, quit;

	public GuessingGame() { // Constructor that sets win and quit conditions.
		hasWon = false;
		quit = false;
	}

	public void loadFile(Scanner file, BinaryTree bt) { // Creates a binary tree from the loaded file.
		List<String> elements = new ArrayList<String>();

		for (String e : file.nextLine().split(",")) // Splits the elements by comma and adds them to the list.
			elements.add(e);
		for (String e : elements) { // Splits each element into its data and its direction.
			String[] data = e.split(":");

			if (data.length == 1)
				bt.add(data[0], "");
			else
				bt.add(data[0], data[1]); // Adds the data to the tree after making sure it's not the root.
		}
	}

	public String inputChoice(Scanner in, BinaryTree bt, String ptr) { // Input method for the users yes or no choice.
		String answer = "";

		do {
			System.out.println(bt.get(ptr));
			if (in.hasNextLine())
				answer = in.nextLine(); // If the user enters an input it is returned as the answer.
		} while (answer.equals(""));

		return answer;
	}

	public String inputAnimal(Scanner in) { // Input method for the users animal of choice.
		String answer = "";

		do {
			System.out.println("Aw man! What is your animal?");
			if (in.hasNextLine())
				answer = in.nextLine(); // If the user enters an input it is returned as the answer.
		} while (answer.equals(""));

		return answer;
	}

	public String inputQuestion(Scanner in, BinaryTree bt, String ptr, String animal) { // Input method for the users question for their animal.
		String answer = "";

		do {
			System.out.println("Enter a question where the answer is yes for a(n) " + animal + " and no for a(n) "
					+ bt.get(ptr).substring(bt.get(ptr).indexOf(")") + 2, bt.get(ptr).indexOf("?")) + ".");
			if (in.hasNextLine())
				answer = in.nextLine(); // If the user enters an input it is returned as the answer.
		} while (answer.equals(""));

		return answer;
	}

	public String inputReplay(Scanner in) { // Input method for if the user wants to replay or not.
		String answer = "";

		do {
			System.out.println("Would you like to play again?");
			if (in.hasNextLine())
				answer = in.nextLine(); // If the user enters an input it is returned as the answer.
		} while (answer.equals(""));

		return answer;
	}

	public void runGame(Scanner in, BinaryTree bt, String ptr, boolean hasThought, PrintWriter out) {
		if (!hasThought) // Makes sure this phrase is not repeated.
			System.out.println("Think of an animal.");
		String choice = inputChoice(in, bt, ptr);

		if (bt.isLeaf(bt.get(ptr), bt.leaves())) { // If the current node is a leaf this stuff happens.
			if (choice.equalsIgnoreCase("yes"))
				doWin(bt, out); // If the animal is correctly guessed the doWin method is called.
			else if (choice.equalsIgnoreCase("no"))
				askQuestions(in, bt, ptr, out); // If the animal is not guessed then these questions are asked.
			else
				System.out.println("Invalid input.");
		} else { // If the current node is not a leaf this stuff happens.
			hasThought = true;
			if (choice.equalsIgnoreCase("no"))
				runGame(in, bt, ptr + "L", hasThought, out); // Recursively calls the method to move down in the tree.
			else if (choice.equalsIgnoreCase("yes"))
				runGame(in, bt, ptr + "R", hasThought, out); // Recursively calls the method to move down in the tree.
			else
				System.out.println("Invalid input.");
		}
	}

	public void askQuestions(Scanner in, BinaryTree bt, String ptr, PrintWriter out) { // If the program does not guess their animal these are the questions that are asked.
		String animal = inputAnimal(in);
		String question = inputQuestion(in, bt, ptr, animal);

		bt.swap(question, ptr, animal); // Calls the swap method in BinaryTree to move the nodes to their respective locations.

		String replay = inputReplay(in);
		if (replay.equalsIgnoreCase("no")) // If the user does not want to play again the doQuit method is called.
			doQuit(bt, out);
	}

	public void doWin(BinaryTree bt, PrintWriter out) { // If the user wins the tree is printed to the file user the toFile method.
		out.print(bt.toFile());
		hasWon = true;
	}

	public void doQuit(BinaryTree bt, PrintWriter out) { // If the user quits the tree is printed to the file user the toFile method.
		out.print(bt.toFile());
		quit = true;
	}

	public boolean getWin() { // Getter method for the hasWon condition.
		return hasWon;
	}

	public boolean getQuit() { // Getter method for the quit condition.
		return quit;
	}
}
