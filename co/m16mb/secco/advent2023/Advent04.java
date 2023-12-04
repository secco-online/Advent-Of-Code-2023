package co.m16mb.secco.advent2023;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Advent04 {

	private static final String filenamePath = "files/file04.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		ArrayList<Card> cards = new ArrayList<Card>();
		for (String line : fileContents.split("\\r?\\n")) {
			cards.add(new Card(line));
		}

		System.out.println("ANSWER1: " + part1(cards));
		// 23847
		System.out.println("ANSWER2: " + part2(cards));
		// 8570000

	}

	private static int part1(ArrayList<Card> cards) {
		int sum = 0;
		for (Card card : cards) {
			int numFound = 0;
			for (String winningNumber : card.winningNumbers) {
				for (String ownedNumber : card.ownedNumbers) {
					if (winningNumber.trim().equals(ownedNumber.trim()))
						numFound++;
				}
			}
			if (numFound >= 1) {
				sum += Math.pow(2, numFound - 1);
			}
		}
		return sum;

	}

	private static int part2(ArrayList<Card> cards) {

		HashMap<Integer, Integer> numberOfScratchCards = new HashMap<Integer, Integer>();

		// beginning = 199 cards from input
		for (Card card : cards) {
			numberOfScratchCards.put(card.cardID, 1);
		}

		for (Card card : cards) {
			int numFound = 0;
			for (String winningNumber : card.winningNumbers) {
				for (String ownedNumber : card.ownedNumbers) {
					if (winningNumber.trim().equals(ownedNumber.trim()))
						numFound++;
				}
			}

			for (int i = 1; i <= numFound; i++) {
				numberOfScratchCards.put(card.cardID + i,
						numberOfScratchCards.get(card.cardID + i) + numberOfScratchCards.get(card.cardID));
			}
			// System.out.println(card.cardID + " " + numFound);
		}
		int sum = 0;
		for (HashMap.Entry<Integer, Integer> entry : numberOfScratchCards.entrySet()) {
			sum += entry.getValue();
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		return sum;

	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

	static class Card {
		final int cardID;
		final String[] winningNumbers;
		final String[] ownedNumbers;
		int worthPoints = 0;

		Card(String line) {
			String[] cardLine = line.split(":");

			cardID = Integer.parseInt(cardLine[0].replaceAll("Card", "").trim());
			String[] numbers = cardLine[1].split("\\|");
			winningNumbers = (numbers[0].replaceAll("  ", " ").trim()).split(" ");
			ownedNumbers = (numbers[1].replaceAll("  ", " ").trim()).split(" ");

		}
	}
}
