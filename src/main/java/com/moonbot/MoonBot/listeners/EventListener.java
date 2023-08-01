package com.moonbot.MoonBot.listeners;

import java.time.LocalDateTime;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Mono;

public interface EventListener<T extends Event> {
	
	Class<T> getEventType();
	
	Mono<Void> execute(T event);
	
	default Mono<Void> handleError(final Throwable error) {
		System.out.println("=======================ERROR ERROR ERROR ERROR ERROR ERROR=======================");
		error.printStackTrace();
		System.out.println("==================================TIME OF ERROR==================================");
		System.out.println(LocalDateTime.now());
		System.out.println("=======================ERROR ERROR ERROR ERROR ERROR ERROR=======================");
		
		return Mono.empty();
	}


}
