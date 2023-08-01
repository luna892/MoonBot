package com.moonbot.MoonBot.services;

import org.springframework.stereotype.Service;

import com.moonbot.MoonBot.listeners.EventListener;
import com.moonbot.MoonBot.listeners.MessageListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateService extends MessageListener implements EventListener<MessageCreateEvent>{

	@Override
	public Class<MessageCreateEvent> getEventType() {
		return MessageCreateEvent.class;
	}

	@Override
	public Mono<Void> execute(MessageCreateEvent event) {
		return processMessage(event.getMessage());
	}

}
