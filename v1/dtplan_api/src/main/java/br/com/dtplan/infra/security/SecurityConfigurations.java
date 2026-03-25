package br.com.dtplan.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable()) // Desabilita CSRF
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura política de sessão como STATELESS
				.authorizeHttpRequests(req -> {
					req.requestMatchers(HttpMethod.POST, "/auth/cadastro").permitAll(); // Permite POST no endpoint /login sem autenticação
					req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll(); // Permite POST no endpoint /login sem autenticação
					req.requestMatchers(HttpMethod.GET, "/auth/login").permitAll(); // Permite GET no endpoint /login sem autenticação
					req.anyRequest().authenticated(); // Exige autenticação para todos os outros endpoints
				})
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro personalizado
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}