package co.m16mb.secco.advent2023;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Advent03 {

	private static final String filenamePath = "files/file03.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		HashMap<Point, String> engineSchematic = new HashMap<Point, String>();

		int lineNumber = 0;
		int MAX_X = 0;
		for (String line : fileContents.split("\\r?\\n")) {
			int offset = 0;
			for (String symbol : line.split("")) {
				engineSchematic.put(new Point(offset, lineNumber), symbol);
				offset++;
				MAX_X = offset;
			}
			lineNumber++;

		}
		int MAX_Y = lineNumber;

		System.out.println("ANSWER1: " + part1(engineSchematic, MAX_X, MAX_Y));
		// 536576
		System.out.println("ANSWER2: " + part2(engineSchematic, MAX_X, MAX_Y));
		// 75741499

	}

	private static int part1(HashMap<Point, String> engineSchematic, int MAX_X, int MAX_Y) {
		int sum = 0;
		for (int y = 0; y < MAX_Y; y++) {
			String currentNumber = "";
			boolean isCurrentNumberValid = false;
			for (int x = 0; x < MAX_X; x++) {
				Point p = new Point(x, y);
				String symbol = engineSchematic.get(p);
				if ("0123456789".contains(symbol)) {
					currentNumber += symbol;
					for (Point n : p.getDiagonalNeighbours()) {
						String neighbourSymbol = engineSchematic.get(n);
						if (neighbourSymbol != null && !"0123456789.".contains(neighbourSymbol)) {
							isCurrentNumberValid = true;
						}
					}
					if (x + 1 == MAX_X) {
						if (!currentNumber.equals("")) {
							if (isCurrentNumberValid) {
								sum += Integer.parseInt(currentNumber);
							}
							currentNumber = "";
							isCurrentNumberValid = false;
						}
					}
					// System.out.println("number");
				} else {
					if (!currentNumber.equals("")) {
						if (isCurrentNumberValid) {
							sum += Integer.parseInt(currentNumber);
						}
						currentNumber = "";
						isCurrentNumberValid = false;
					}
				}

			}
		}
		return sum;

	}

	private static int part2(HashMap<Point, String> engineSchematic, int MAX_X, int MAX_Y) {

		HashMap<Point, ArrayList<Integer>> cogwheelMap = new HashMap<Point, ArrayList<Integer>>();
		for (int y = 0; y < MAX_Y; y++) {
			for (int x = 0; x < MAX_X; x++) {
				cogwheelMap.put(new Point(x, y), new ArrayList<Integer>());
			}
		}

		for (int y = 0; y < MAX_Y; y++) {
			String currentNumber = "";
			boolean isCurrentNumberValid = false;
			Point cogwheel = null;
			for (int x = 0; x < MAX_X; x++) {
				Point p = new Point(x, y);
				String symbol = engineSchematic.get(p);

				// System.out.println(p + " " + symbol);
				if ("0123456789".contains(symbol)) {
					currentNumber += symbol;
					for (Point n : p.getDiagonalNeighbours()) {
						String neighbourSymbol = engineSchematic.get(n);
						if (neighbourSymbol != null && !"0123456789.".contains(neighbourSymbol)) {
							isCurrentNumberValid = true;
							if ("*".equals(neighbourSymbol)) {
								// cog wheel
								cogwheel = n;
							}
						}
					}
					if (x + 1 == MAX_X) {
						if (!currentNumber.equals("")) {
							System.out.println(currentNumber + " " + isCurrentNumberValid + " EOL");
							if (isCurrentNumberValid && cogwheel != null) {
								// System.out.println("cogwheel " + cogwheel);
								ArrayList<Integer> tempArray = cogwheelMap.get(cogwheel);
								tempArray.add(Integer.parseInt(currentNumber));
								cogwheelMap.put(cogwheel, tempArray);
							}

							currentNumber = "";
							isCurrentNumberValid = false;
							cogwheel = null;

						}
					}
					// System.out.println("number");
				} else {
					if (!currentNumber.equals("")) {
						if (isCurrentNumberValid && cogwheel != null) {
							// System.out.println("cogwheel " + cogwheel);
							ArrayList<Integer> tempArray = cogwheelMap.get(cogwheel);
							tempArray.add(Integer.parseInt(currentNumber));
							cogwheelMap.put(cogwheel, tempArray);
						}

						currentNumber = "";
						isCurrentNumberValid = false;
						cogwheel = null;
					}
				}

			}
		}
		int sum = 0;
		for (HashMap.Entry<Point, ArrayList<Integer>> entry : cogwheelMap.entrySet()) {
			Point key = entry.getKey();
			ArrayList<Integer> val = entry.getValue();
			if (val.size() > 1) {
				System.out.println(key + " " + val.size() + " " + val);
				sum += (val.get(0) * val.get(1));
			}

		}

		return sum;

	}

	private static final record Point(int x, int y) {

		List<Point> getDiagonalNeighbours() {
			return List.of(new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1), new Point(x, y + 1),
					new Point(x - 1, y - 1), new Point(x - 1, y + 1), new Point(x + 1, y - 1), new Point(x + 1, y + 1));
		}

	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

}
