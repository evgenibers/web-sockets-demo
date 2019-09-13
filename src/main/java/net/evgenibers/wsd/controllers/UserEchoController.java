package net.evgenibers.wsd.controllers;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.wsd.domain.EchoInDto;
import net.evgenibers.wsd.domain.EchoOutDto;
import net.evgenibers.wsd.services.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * User echo messages controller.<br>
 * Used to send echo messages to all sessions of requester.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
@Controller
@SuppressWarnings("unused")
public class UserEchoController {
	private final EchoService echoService;

	@Autowired
	public UserEchoController(final EchoService echoService) {
		this.echoService = echoService;
	}

	@MessageMapping("/api/secured/echo")
	@SendToUser(value = "/topic/secured/echo")
	public EchoOutDto echoUser(@NotNull @Valid EchoInDto data, @AuthenticationPrincipal User requester) {
		log.info("echoUser: data = {}, requester = {}", data, requester.getUsername());
		return echoService.echo(requester, data);
	}
}
