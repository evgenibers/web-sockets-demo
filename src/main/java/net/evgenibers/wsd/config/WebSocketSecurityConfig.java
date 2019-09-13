package net.evgenibers.wsd.config;

import net.evgenibers.wsd.domain.security.UserRoles;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * WebSocket security configuration.<be>
 * Used for "socket endpoints"
 *
 * @author Evgeni Bokhanov
 */
@Configuration
@SuppressWarnings("unused")
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry registry) {
		registry
				.simpDestMatchers("/api/secured/admin/**").hasAuthority(UserRoles.ROLE_ADMIN.name())
				.simpDestMatchers("/api/secured/**").authenticated()
				.anyMessage().authenticated()
				.simpSubscribeDestMatchers("/topic/secured/admin/**").hasAuthority(UserRoles.ROLE_ADMIN.name())
				.simpSubscribeDestMatchers("/topic/secured/**").authenticated();
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true; // set do not require CSRF token
	}
}
