package net.evgenibers.wsd.controllers;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.wsd.domain.EchoInDto;
import net.evgenibers.wsd.domain.EchoOutDto;
import net.evgenibers.wsd.services.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Broadcast echo messages controller.<br>
 * Used to send echo messages to all users and sessions.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
@Controller
@SuppressWarnings("unused")
public class BroadcastEchoController {
	private final EchoService echoService;

	@Autowired
	public BroadcastEchoController(final EchoService echoService) {
		this.echoService = echoService;
	}

	@MessageMapping("/api/secured/broadcast/echo")
	@SendTo(value = "/topic/secured/broadcast/echo")
	public EchoOutDto echoBroadcast(@NotNull @Valid EchoInDto data, @AuthenticationPrincipal User requester) {
		log.info("echoBroadcast: data = {}, requester = {}", data, requester.getUsername());
		return echoService.echo(requester, data);
	}
}
