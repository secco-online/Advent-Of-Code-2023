package co.m16mb.secco.advent2023;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Advent02 {

	private static final String filenamePath = "files/file02.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		System.out.println("ANSWER1: " + part1(fileContents));
		// 2285
		System.out.println("ANSWER2: " + part2(fileContents));
		// 77021

	}

	private static int part1(String fileContents) {
		final int MAX_RED = 12;
		final int MAX_GREEN = 13;
		final int MAX_BLUE = 14;

		int sum = 0;
		for (String line : fileContents.split("\\r?\\n")) {

			String[] game = line.split(": ");
			int gameID = Integer.parseInt(game[0].replaceAll("Game ", ""));
			boolean gamePossible = true;
			for (String subGame : game[1].split(";")) {
				for (String valCol : subGame.split(",")) {
					String[] valColArray = valCol.trim().split(" ");
					if (valColArray[1].equals("green") && Integer.parseInt(valColArray[0]) > MAX_GREEN)
						gamePossible = false;
					else if (valColArray[1].equals("red") && Integer.parseInt(valColArray[0]) > MAX_RED)
						gamePossible = false;
					else if (valColArray[1].equals("blue") && Integer.parseInt(valColArray[0]) > MAX_BLUE)
						gamePossible = false;

				}

			}
			if (gamePossible)
				sum += gameID;
		}
		return sum;

	}

	private static int part2(String fileContents) {

		int sum = 0;
		for (String line : fileContents.split("\\r?\\n")) {
			int minRed = 0;
			int minGreen = 0;
			int minBlue = 0;

			String[] game = line.split(": ");

			for (String subGame : game[1].split(";")) {

				for (String valCol : subGame.split(",")) {

					String[] valColArray = valCol.trim().split(" ");
					int colorValue = Integer.parseInt(valColArray[0]);
					if (valColArray[1].equals("green") && colorValue > minGreen)
						minGreen = colorValue;
					else if (valColArray[1].equals("red") && colorValue > minRed)
						minRed = colorValue;
					else if (valColArray[1].equals("blue") && colorValue > minBlue)
						minBlue = colorValue;
				}

			}
			sum += (minRed * minBlue * minGreen);
		}
		return sum;

	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
