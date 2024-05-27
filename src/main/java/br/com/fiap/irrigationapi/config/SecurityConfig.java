package br.com.fiap.irrigationapi.config;

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
public class SecurityConfig {


    @Autowired
    private RequestFilter requestFilter;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // AREAS
                        .requestMatchers(HttpMethod.POST, "/api/areas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/areas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/areas/all").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/areas/id/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/areas/delete/").hasRole("ADMIN")

                        // NOTIFICATIONS
                        .requestMatchers(HttpMethod.POST, "/api/notifications").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/notifications").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/notifications/all").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/notifications/id/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/notifications/delete/").hasRole("ADMIN")

                        // SCHEDULES
                        .requestMatchers(HttpMethod.POST, "/api/schedules").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/schedules").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/schedules/all").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/schedules/id/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/schedules/delete/").hasRole("ADMIN")

                        // SENSORS
                        .requestMatchers(HttpMethod.POST, "/api/sensors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/sensors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sensors/all").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/sensors/id/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/sensors/delete/").hasRole("ADMIN")

                        // USERS
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/update-info").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/users/activate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/deactivate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/upgrade-permission").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/downgrade-permission").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/id").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/users/email").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/users/all").hasRole("ADMIN")

                        // WEATHERS
                        .requestMatchers(HttpMethod.POST, "/api/weathers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/weathers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/weathers/all").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/weathers/id/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/weathers/").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        requestFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

