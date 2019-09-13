package net.evgenibers.wsd.services.security;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.wsd.config.SecurityConfig;
import net.evgenibers.wsd.domain.security.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Mocked user details service.<br>
 * Used in security.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
@Service
@SuppressWarnings("unused")
public class MockedUserDetailsService implements UserDetailsService {
	/**
	 * In-memory users storage.<br>
	 * Key is username (login).<br>
	 * Value is user.
	 */
	private final Map<String, User> users = new HashMap<>();

	/**
	 * Password encoder.
	 *
	 * @see SecurityConfig#passwordEncoder()
	 */
	private final PasswordEncoder passwordEncoder;

	/**
	 * In-memory user storage initialization.
	 *
	 * @see MockedUserDetailsService#users
	 */
	@PostConstruct
	public void init() {
		users.put("user", new User(
				"user",
				passwordEncoder.encode("userpassword"),
				new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority(UserRoles.ROLE_USER.name())))
		));
		users.put("admin", new User(
				"admin",
				passwordEncoder.encode("adminpassword"),
				new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority(UserRoles.ROLE_ADMIN.name())))
		));
	}

	@Autowired
	public MockedUserDetailsService(final PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Load user from in-memory storage by given username (login).
	 *
	 * @param login Username (login) received from client
	 * @return User from storage
	 * @see UserDetailsService#loadUserByUsername(String)
	 */
	@Override
	public UserDetails loadUserByUsername(String login) {
		log.info("loadUserByUsername: login = {}", login);
		return Optional.ofNullable(users.get(login))
				.orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
	}
}
