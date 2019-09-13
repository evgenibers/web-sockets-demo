package net.evgenibers.wsd.services;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.wsd.domain.EchoInDto;
import net.evgenibers.wsd.domain.EchoOutDto;
import net.evgenibers.wsd.utils.DateUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

/**
 * Echo messages service.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
@Service
public class EchoService {
	/**
	 * Generate echo message from given user and input message data.
	 *
	 * @param requester Endpoint requester
	 * @param data      Input message data
	 * @return Output message
	 */
	public EchoOutDto echo(User requester, EchoInDto data) {
		log.debug("echo: requester = {}, data = {}", requester.getUsername(), data);
		return new EchoOutDto(
				String.format("%s %s: %s",
					DateUtils.currentDateAndTime(),
					requester.getUsername(),
					HtmlUtils.htmlEscape(data.getText())
				)
		);
	}
}
