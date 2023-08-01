package com.moonbot.MoonBot.listeners;

import java.util.ArrayList;

import com.moonbot.MoonBot.MoonBotApplication;
import com.moonbot.discordevents.Giveaway;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
	
	private String author = "UNKNOWN";
	
	public Mono<Void> processMessage(final Message eventMessage) {

		String returnContent = this.createMessage(eventMessage);

		return Mono.just(eventMessage)
				.filter(message -> {
					final boolean isNotBot = message.getAuthor()
							.map(user -> !user.isBot()) 
							.orElse(false);
					
					if (isNotBot) {
						message.getAuthor().ifPresent(user -> author = user.getUsername());
					}
					return isNotBot;
				})
				.filter(message -> returnContent != "")
				.flatMap(Message::getChannel)
				.flatMap(channel -> channel.createMessage(returnContent))
				.then();
				
	}

	private String createMessage(Message eventMessage) {
		String content = eventMessage.getContent().toLowerCase();

		if (eventMessage.getAuthor().isPresent()) {
			author = eventMessage.getAuthor().get().getUsername();
		} 

		if (content.contains("femboy")) {
			return "Did someone say femboys? <:AYAYA:1135604005733675018>"; 
		}

		if (content.contains("warrior")) {
			return "Warrior <:LUL:1135980471235391660>";
		}

		if (content.contains("priest")) {
			return "Delete Priest <:madge:1135980699875299551>";
		}

		if (content.contains("cat")) {
			return "Did smeone say cats? <:AYAYA:1135604005733675018>";
		}

		if (content.contains ("naxx out")) {
			return "Naxx Out? <:HUH:1077714659639042090>";
		}

		if (content.contains ("yogg")) {
			return "The shadow of my corpse will choke this land for all eternity.";
		}

		if (content.contains("esports")) {
			return "esports <:LUL:1135980471235391660>";
		}

		if (content.contains("horny")) {
			return "go to horny jail <a:Awau_mad_FB:1135982009789976748>";
		}

		ArrayList<Role> authorRoles = new ArrayList<>();
		
		Mono<Member> monoMember = eventMessage.getAuthorAsMember();

		try {
			if(monoMember!=null) {
				Member member = monoMember.block();

				if (member!=null) {
					Flux<Role> fluxAuthorsRoles = member.getRoles();
					fluxAuthorsRoles.collectList().subscribe(authorRoles::addAll);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		

		//Todo allow any Admin to start a giveaway
		if (author.equals("lunaloveee") && content.equals("!startgiveaway")) {
			MoonBotApplication.giveawayRunning = true;
			return "@everyone A new giveaway has started for a Titans bundle! <:luunaPrayge:1037964836950257724>\n" + 
			"Twitch Subs and VIPs will automatically get 2 entries!\n" + 
			"Type !giveaway or !enter or !raffle to enter\n" +
			"A winner will be chosen in about 1 hour";
		}

		// Allows for giveaway entry
		if(content.equals("!giveaway") || content.equals("!enter") || content.equals("!raffle")) {

			if(!MoonBotApplication.giveawayRunning) {
				return ("There is no giveaway running! <:HUH:1077714659639042090>");
			}

			if(!MoonBotApplication.giveaway.hasEntered(author)) {
				int entries = 1;

				for(Role role : authorRoles) {
					if (role.getName().contains("Twitch Subscriber") || role.getName().contains("VIP")) {
						entries = 2;
						break;
					}
			}

				MoonBotApplication.giveaway.addContestant(author, entries);

				String entryString = entries == 1 ? "1 entry!" : "2 entries!";
				return (author + " has entered the giveaway with " + entryString);

			} else {
				return ("<:Kanna_oof_FB:1135982233958764614> DONT CHEAT " + author + " you already entered! <:luunaSassy:1037964750748925972>");
			}
		}

		//Picking a winner for giveaway
		if (author.equals("lunaloveee") && content.equals("!choosewinner")) {

			if (!MoonBotApplication.giveawayRunning) {
				return "There is no giveaway running <:HUH:1077714659639042090>";
			}

			String winner = MoonBotApplication.giveaway.pickWinner();
			return("@here Congratulations! The winner is " + winner + "!");
		}

		if(author.equals("lunaloveee") && content.equals("!showcontestants")) {
			return(MoonBotApplication.giveaway.getContestants().toString());
		}

		if(author.equals("lunaloveee") && content.contains("show") && content.contains("command")) {
			return (
				"!startgiveaway to start a new giveaway\n" + 
				"!giveaway to enter a giveaway\n" +
				"!choosewinner to pick a winner\n" +
				"!showcontestants to show contestants"
			);
		}

		if (content.contains("rigged")) {
			return "LMAO OKAY <:luunaAngry:1037964972069752872>";
		}

		return "";
	}
}
