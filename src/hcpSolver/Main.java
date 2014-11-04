package hcpSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

	private int dim = 0;
	private boolean[][] hcpMatrix;
	private int[][] tspMatrix;
	private String fName;

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {
		Main main = new Main();
		main.reader(args[0]);
		main.writer();
	}

	private void reader(String filename) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filename));
		boolean readEdges = false;
		boolean done = false;
		while (s.hasNextLine() && !done) {

			String tokens[] = s.nextLine().trim().split("\\s+");
			
			if (tokens[0].equals("-1"))
			{
				break;
			}

			int nodeA, nodeB;

			if (tokens[0].contains("NAME")) {	
				fName = tokens[2];

			}

			if (tokens[0].equals("DIMENSION")) {
				dim = Integer.parseInt(tokens[2]);
				createHcpMatrix();
				createTspMatrix();
			}

			if (tokens[0].equals("EDGE_DATA_SECTION")) {
				readEdges = true;
				tokens = s.nextLine().trim().split("\\s+");
			}

			if (readEdges == true) {
				nodeA = Integer.parseInt(tokens[0]);
				nodeB = Integer.parseInt(tokens[1]);
				if (nodeA < nodeB)
				{
					int temp = nodeB;
					nodeB = nodeA;
					nodeA = temp;
				}
				createHcpEdge(nodeA, nodeB);
				createTspEdge(nodeA, nodeB);
			}
		}
		s.close();
	}

	private void writer() throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fName+".tsp", "UTF-8");
		writer.println("NAME : "+fName);
		writer.println("COMMENT : ");
		writer.println("TYPE : TSP");
		writer.println("DIMENSION : " + dim);
		writer.println("EDGE_WEIGHT_TYPE: EXPLICIT");
		writer.println("EDGE_WEIGHT_FORMAT: LOWER_DIAG_ROW");
		writer.println("EDGE_WEIGHT_SECTION");
		for (int i = 1; i <= dim; i++)
			for (int j =1; j < hcpMatrix[i].length; j++) {
				{
					int value = tspMatrix[i][j];
					
					if (value == 1) {
						writer.println(1);
					}
					if (value == 2) {
						writer.println(2);
					}

				}
			}
		writer.println(-1);
		writer.println("EOF");
		writer.close();
	}

	private void createHcpMatrix() {
		hcpMatrix = new boolean[dim + 1][];
		for (int i = 0; i <= dim; ++i) {
			hcpMatrix[i] = new boolean[i + 1];
		}
	}

	private void createHcpEdge(int row, int col) {
		// assume row > col
		// false = 0, true = 1
		hcpMatrix[row][col] = true;
	}

	private void createTspMatrix() {
		tspMatrix = new int[dim + 1][];
		for (int i = 0; i <= dim; ++i) {
			tspMatrix[i] = new int[i + 1];
		}
	}

	private void createTspEdge(int row, int col) {
		for (int i = 0; i <= dim; i++) {
			for (int j = 0; j < hcpMatrix[i].length; j++) {
				if (i == j) {
					tspMatrix[i][j] = 2;
				} else {
					if (hcpMatrix[i][j] == true) {
						tspMatrix[i][j] = 1;
					} else {
						tspMatrix[i][j] = 2;
					}
				}

			}
		}
	}
}
