package com.moonbot.MoonBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.moonbot.discordevents.Giveaway;

@SpringBootApplication
@EnableScheduling
public class MoonBotApplication {

	static int numberOfMinutesAlive = 0;
	public static Giveaway giveaway = new Giveaway();
	public static boolean giveawayRunning = true;

	public static void main(String[] args) {
		SpringApplication.run(MoonBotApplication.class, args);
	}

}
