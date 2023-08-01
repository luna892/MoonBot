package com.moonbot.MoonBot;

import java.util.HashMap;

import com.moonbot.discordevents.Giveaway;

public class GiveawayTests {

	public static void main(String[] args) {
		HashMap<String, Integer> contestants  = new HashMap<String, Integer>() {{
			put("Dovah", 3);
			put("Jespine", 2);
			put("Derpager", 2);
			put("Rando", 1);
			put("Rando2", 1);
			put("Rando3", 1);
		}};

		HashMap<String, Integer> winners = new HashMap<String, Integer>();

		Giveaway testGiveaway = new Giveaway(contestants);
		int i = 10000;
		while (i > 0) {
			String winner = testGiveaway.pickWinner();
			int numOfWins = 0;

			if (winners.containsKey(winner)) {
				numOfWins = winners.get(winner);
			}

			numOfWins++;
			winners.put(winner, numOfWins);
			
			i--;
		}

		System.out.println(winners);
	}
}
