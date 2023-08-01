package com.moonbot.discordevents;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

import com.moonbot.MoonBot.MoonBotApplication;

import io.netty.util.internal.ThreadLocalRandom;

public class Giveaway {

    //String represents contestant's username
    //Integere represents number of entries that contestant has
    private HashMap<String, Integer> contestants = new HashMap<>();
    private final String filepath = "C:/Users/beason/eclipse-workspace/MoonBot/src/main/java/com/moonbot/discordevents/giveaway.csv";
    
    public Giveaway() {
        loadGiveawayFile();
    }

    public Giveaway(HashMap<String, Integer> contestants) {
        loadGiveawayFile();
        // for (String username : contestants.keySet()) {
        //     addContestant(username, contestants.get(username));
        // }
    }

    public void addContestant(String username, int entries) {
        contestants.put(username, entries);
        addContestantToFile(username, entries);
    }

    public String pickWinner() {
        int totalEntries = getTotalEntries();
        int winningEntry = ThreadLocalRandom.current().nextInt(0, totalEntries);
        int currentEntry = 0;

        String winner = "";

        for (String username : contestants.keySet()) {
            int entries = contestants.get(username);
            winner = "";

            while (entries > 0) {
                if (currentEntry == winningEntry) {
                    winner = username;
                    break;
                } else {
                    entries--;
                    currentEntry++;
                }
            }
            
            if (winner != "") break;
        }

        MoonBotApplication.giveawayRunning = false;
        return winner;
    }

    public int getTotalEntries() {
        int totalEntries = 0;

        for (String username : contestants.keySet()) {
            int entry = contestants.get(username);
            totalEntries += entry;
        }

        return totalEntries;
    }

    public boolean hasEntered(String username) {
        return contestants.containsKey(username);
    }

    public HashMap<String, Integer> getContestants() {
        return contestants;
    }

    private void addContestantToFile(String username, int entries) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filepath, true))) {
            String content = username + "," + entries + "\n";

            System.out.println(content + " has been added to the csv");

            bw.write(content);
            bw.close();

        } catch (Exception i) {
            i.printStackTrace();
        }
    }

    private void loadGiveawayFile() {
        try {
            Scanner sc = new Scanner(new File(this.filepath));

            while(sc.hasNext()) {
                String line = sc.next();
                System.out.println(line);

                String[] contestant = line.split(",");
                this.contestants.put(contestant[0], Integer.parseInt(contestant[1]));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Finished reading giveaway.csv");
    }
}
