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
 * Session echo messages controller.<br>
 * Used to send echo messages to the same session of requester.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
@Controller
@SuppressWarnings("unused")
public class SessionEchoController {
	private final EchoService echoService;

	@Autowired
	public SessionEchoController(final EchoService echoService) {
		this.echoService = echoService;
	}

	@MessageMapping("/api/secured/session/echo")
	@SendToUser(value = "/topic/secured/session/echo", broadcast = false)
	public EchoOutDto echoSession(@NotNull @Valid EchoInDto data, @AuthenticationPrincipal User requester) {
		log.info("echoSession: data = {}, requester = {}", data, requester.getUsername());
		return echoService.echo(requester, data);
	}
}
