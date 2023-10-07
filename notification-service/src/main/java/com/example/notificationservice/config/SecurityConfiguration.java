package com.example.notificationservice.config;




import com.example.notificationservice.security.JwtAuthenticationFilter;
import com.example.notificationservice.utils.Constants;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/api/v1/event/create").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/preference/create").hasRole(Constants.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/getAllEvent").hasAnyRole(Constants.ROLE_USER,Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/getByName/{str}").hasAnyRole(Constants.ROLE_USER,Constants.ROLE_ADMIN)

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
