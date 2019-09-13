package net.evgenibers.wsd.controllers.error;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.wsd.domain.error.ErrorMessageOutDto;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Exception adviser.<br>
 * Used to handle exceptions occurred in controllers.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
@ControllerAdvice
@SuppressWarnings("unused")
public class ExceptionAdviser {
	/**
	 * Handle all socket message exceptions.
	 *
	 * @param ex Exception occurred
	 * @return Output message
	 */
	@MessageExceptionHandler(Exception.class)
	@SendToUser(value = "/topic/secured/error", broadcast = false)
	public ErrorMessageOutDto unknownExceptionHandlerMessage(Exception ex) {
		log.info("unknownExceptionHandlerMessage: {}", ex.getMessage());
		return new ErrorMessageOutDto(ex.getMessage());
	}

	/**
	 * Handel all non-socket exceptions.
	 *
	 * @param ex Exception occurred
	 * @return Output message
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorMessageOutDto unknownExceptionHandler(HttpServletResponse response, Exception ex) {
		log.info("unknownExceptionHandler: {}", ex.getMessage());
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return new ErrorMessageOutDto(ex.getMessage());
	}
}
