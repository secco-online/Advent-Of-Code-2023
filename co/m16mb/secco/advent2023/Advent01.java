package co.m16mb.secco.advent2023;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Advent01 {

	private static final String filenamePath = "files/file01.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		System.out.println("ANSWER1: " + part1(fileContents));
		// 54708
		System.out.println("ANSWER2: " + part2(fileContents));
		// 54087

	}

	private static int part1(String fileContents) {
		int sum = 0;
		for (String line : fileContents.split("\\r?\\n")) {
			sum += getCalibrationValue(line);
		}
		return sum;

	}

	private static int part2(String fileContents) {
		HashMap<String, String> valuesToFind = new HashMap<String, String>();
		valuesToFind.put("0", "0");
		valuesToFind.put("1", "1");
		valuesToFind.put("2", "2");
		valuesToFind.put("3", "3");
		valuesToFind.put("4", "4");
		valuesToFind.put("5", "5");
		valuesToFind.put("6", "6");
		valuesToFind.put("7", "7");
		valuesToFind.put("8", "8");
		valuesToFind.put("9", "9");

		valuesToFind.put("one", "1");
		valuesToFind.put("two", "2");
		valuesToFind.put("three", "3");
		valuesToFind.put("four", "4");
		valuesToFind.put("five", "5");
		valuesToFind.put("six", "6");
		valuesToFind.put("seven", "7");
		valuesToFind.put("eight", "8");
		valuesToFind.put("nine", "9");
		/*
		 * for (Map.Entry<String, String> entry : valuesToFind.entrySet()) { String key
		 * = entry.getKey(); String val = entry.getValue(); System.out.println(key + " "
		 * + val); }
		 */

		int sum = 0;

		for (String line : fileContents.split("\\r?\\n")) {
			sum += getCalibrationValueNumericOrSpelled(line, valuesToFind);
		}

		return sum;

	}

	private static int getCalibrationValue(String line) {
		String firstNumber = "";
		String lastNumber = "";
		for (String s : line.split("")) {
			try {
				Integer.parseInt(s);
				if ("".equals(firstNumber))
					firstNumber = s;
				lastNumber = s;
			} catch (NumberFormatException nfe) {
				// do nothing
			}
		}
		// System.out.println(firstNumber + " " + lastNumber);
		return Integer.parseInt(firstNumber + lastNumber);
	}

	private static int getCalibrationValueNumericOrSpelled(String line, HashMap<String, String> valuesToFind) {
		String firstNumber = "";
		String lastNumber = "";
		int firstIndex = Integer.MAX_VALUE;
		int lastIndex = Integer.MIN_VALUE;

		for (Map.Entry<String, String> entry : valuesToFind.entrySet()) {
			String number = entry.getKey();
			int currentIndexOf = line.indexOf(number);
			if (currentIndexOf < firstIndex && currentIndexOf >= 0) {
				firstNumber = number;
				firstIndex = currentIndexOf;
			}

			currentIndexOf = line.lastIndexOf(number);

			if (currentIndexOf > lastIndex) {
				lastNumber = number;
				lastIndex = currentIndexOf;
			}

		}

		// System.out.println(firstNumber + " " + lastNumber + " |||||| " +
		// valuesToFind.get(firstNumber) + " " +valuesToFind.get(lastNumber));
		return Integer.parseInt(valuesToFind.get(firstNumber) + valuesToFind.get(lastNumber));
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
