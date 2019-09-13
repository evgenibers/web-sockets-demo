package net.evgenibers.wsd.config;

import net.evgenibers.wsd.domain.security.UserRoles;
import net.evgenibers.wsd.services.security.MockedUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Security configuration.<br>
 * Used for "non-socket" REST endpoints.
 *
 * @author Evgeni Bokhanov
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * User details service.
	 *
	 * @see MockedUserDetailsService
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable() // disable CSRF
				.httpBasic() // use basic authentication
				.and()
				.authorizeRequests()
				.antMatchers("/api/public/**").permitAll() // for all requesters (authenticated and non-authenticated)
				.mvcMatchers("/api/secured/admin/**").hasAuthority(UserRoles.ROLE_ADMIN.name()) // for admins only
				.mvcMatchers("/api/secured/**").authenticated(); // for all authenticated users
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	/**
	 * User password encoder.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

	/**
	 * CORS configuration.
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
			}
		};
	}
}
