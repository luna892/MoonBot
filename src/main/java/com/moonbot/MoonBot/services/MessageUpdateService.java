package com.moonbot.MoonBot.services;

import org.springframework.stereotype.Service;

import com.moonbot.MoonBot.listeners.EventListener;
import com.moonbot.MoonBot.listeners.MessageListener;

import discord4j.core.event.domain.message.MessageUpdateEvent;
import reactor.core.publisher.Mono;

@Service
public class MessageUpdateService extends MessageListener implements EventListener<MessageUpdateEvent>{

	@Override
	public Class<MessageUpdateEvent> getEventType() {
		return MessageUpdateEvent.class;
	}

	@Override
	public Mono<Void> execute(MessageUpdateEvent event) {
		return Mono.just(event)
				.filter(MessageUpdateEvent::isContentChanged)
				.flatMap(MessageUpdateEvent::getMessage)
				.flatMap(super::processMessage);
	}

}
