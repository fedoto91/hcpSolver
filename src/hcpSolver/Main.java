package hcpSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

	private int dim = 0;

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Main main = new Main();
		main.reader(args[0]);
		main.writer();
	}

	private void reader(String filename) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filename));

		boolean readEdges = false;
		while (s.hasNextLine()) {

			String tokens[] = s.nextLine().split(" ");

			int x, y;

			if (tokens[0].equals("DIMENSION")) {
				dim = Integer.parseInt(tokens[2]);
			}

			if (tokens[0].equals("EDGE_DATA_SECTION")) {
				readEdges = true;
				tokens = s.nextLine().split(" ");
			}

			if (readEdges == true) {
				x = Integer.parseInt(tokens[0]);
				y = Integer.parseInt(tokens[1]);
				System.out.printf("%d %d\n", x, y);
			}

		}
	}

	private void writer() throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("hcp.tsp", "UTF-8");
		writer.println("NAME : square");
		writer.println("COMMENT : four nodes arranged in a “square”");
		writer.println("TYPE : TSP");
		writer.println("DIMENSION : " + dim);
		writer.println("EDGE_WEIGHT_TYPE: EXPLICIT");
		writer.println("EDGE_WEIGHT_FORMAT: LOWER_DIAG_ROW");
		writer.println("EDGE_WEIGHT_SECTION");
		writer.close();
	}
}
