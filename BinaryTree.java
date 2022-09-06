package danproj3;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
	private class Node {
		public String data;
		public Node left, right;

		public Node(String data, Node left, Node right) { // Constructor for the tree.
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}

	private Node root;

	public BinaryTree() {
		root = new Node("Is your animal a(n) dog?", null, null); // Base question placed at the root.
	}

	public void add(String data, String directions) { // Add method we made as a class but using Strings.
		if (directions.equals(""))
			root = new Node(data, null, null);
		else {
			Node ptr = root;
			String parentPart = directions.substring(0, directions.length() - 1);
			String childPart = directions.substring(directions.length() - 1);

			for (char c : parentPart.toCharArray()) {
				if (c == 'R')
					ptr = ptr.right;
				else
					ptr = ptr.left;
			}
			if (childPart.contentEquals("L"))
				ptr.left = new Node(data, null, null);
			else
				ptr.right = new Node(data, null, null);
		}
	}

	public void swap(String data, String directions, String newData) { // Modified add method, moves the elements down in the tree.
		String newPtr = root.data;
		newData = "Is your animal a(n) " + newData + "?";

		if (directions.equals("")) {
			root.data = data;
			root.left = new Node(newPtr, null, null); // Takes what's in the root and places it to the left.
			root.right = new Node(newData, null, null); // Takes the new data (animal) and places it to the right.
		} else {
			Node ptr = root;
			String parentPart = directions.substring(0, directions.length() - 1);
			String childPart = directions.substring(directions.length() - 1);

			for (char c : parentPart.toCharArray()) {
				if (c == 'R')
					ptr = ptr.right;
				else
					ptr = ptr.left;
			}
			if (childPart.equals("L")) { // Extends the tree down for the new data depending on its direction.
				newPtr = ptr.left.data;
				ptr.left.data = data;
				ptr.left.left = new Node(newPtr, null, null);
				ptr.left.right = new Node(newData, null, null);
			} else {
				newPtr = ptr.right.data;
				ptr.right.data = data;
				ptr.right.left = new Node(newPtr, null, null);
				ptr.right.right = new Node(newData, null, null);
			}
		}
	}

	public String get(String location) { // Get method we made in class, retrieves the value at the given location.
		Node ptr = root;
		String parentPart = location.substring(0, location.length());

		for (char c : parentPart.toCharArray()) {
			if (c == 'R')
				ptr = ptr.right;
			else
				ptr = ptr.left;
		}

		return ptr.data;
	}

	public List<String> leaves() {
		return leaves(root);
	}

	private List<String> leaves(Node r) { // Leaves method we made in class, returns the leaves of the tree.
		List<String> leaf = new ArrayList<String>();

		if (r == null)
			return new ArrayList<String>();
		else {
			if (r.right == null && r.left == null)
				leaf.add(r.data + "");
			else {
				leaf.addAll(leaves(r.right));
				leaf.addAll(leaves(r.left));
			}

			return leaf;
		}
	}

	public boolean isLeaf(String data, List<String> leaves) { // Returns true if the current node is a leaf.
		for (String leaf : leaves) {
			if (leaf.contains(data)) // Loops through and checks if the leaves contain the data.
				return true;
		}
		return false;
	}

	public String toFile() {
		return toFile(root, "");
	}

	private String toFile(Node r, String ptr) { // Recursively prints the tree to a file.
		                                        // answers.txt is formatted (data):(location),
		if (r == null)
			return "";
		else {
			String file = "";
			file += r.data + ":" + ptr + ","; // Adds a comma and colon for each value and ptr and does root.
			file += toFile(r.left, ptr + "L"); // Recursively does the left tree.
			file += toFile(r.right, ptr + "R"); // Recursively does the right tree.

			return file;
		}
	}
}
