package com.dtplan.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
//@Profile("dev") // OU prod
//@EnableWebSecurity // Ignorar a configuração original do Sprring Security
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Adiciona o suporte a CORS
                .csrf(csrf -> csrf.disable()) // Desabilita o CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //Acesso ao banco em ec2
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        //.requestMatchers("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.svg").permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/criar").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )

                // libera iframe para o console
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //GAMBIARRA ATÉ RESOLVER A CONFIGURAÇÃO DO CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir todas as origens
        configuration.addAllowedOriginPattern("*"); // Para versões recentes do Spring

        // Se a versão for mais antiga e não suportar padrões, use:
        // configuration.setAllowedOrigins(Collections.singletonList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);  // Atenção: `allowCredentials` não funciona com `"*"` no `AllowedOrigins`.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todas as rotas
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
