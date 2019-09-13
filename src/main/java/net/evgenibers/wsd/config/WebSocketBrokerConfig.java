package net.evgenibers.wsd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket broker configuration.
 *
 * @author Evgeni Bokhanov
 */
@Configuration
@SuppressWarnings("unused")
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
	/**
	 * Configure broker.
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry configuration) {
		configuration
				.enableSimpleBroker("/topic/secured"); // enable broker with given destination prefixes
	}

	/**
	 * Register STOMP endpoints.
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// STOMP endpoints will be used by client, see resources/static/app.js
		registry
				.addEndpoint("/api/secured/stomp", "/api/secured/admin/stomp")
				.withSockJS();
	}
}
