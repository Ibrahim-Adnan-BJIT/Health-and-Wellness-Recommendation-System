package com.example.groupservice.config;



import com.example.groupservice.security.JwtAuthenticationFilter;
import com.example.groupservice.utils.Constrains;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

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
                       /* .requestMatchers("/**").permitAll()*/ // This line allows access to all paths
                        .requestMatchers(HttpMethod.POST, "/api/v1/group/create").hasRole(Constrains.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/group/getAllGroups").hasRole(Constrains.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/group/GetAllGroupsAndUsers").hasRole(Constrains.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/group/getAllPostsWithGroupId/{id}").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/group/getAllUsersWithGroupId/{id}").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/group/getAllGroupByUserId").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/post/getAllPostsByUserId").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/group/join").hasAnyRole(Constrains.ROLE_USER,Constrains.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/post/create").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/post/getAllPosts").hasRole(Constrains.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v1/interaction/getAllInteractionByPostId/{id}").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/interaction/create").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/group/deleteGroup/{id}").hasRole(Constrains.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/post/deletePost/{id}").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/interaction/deleteInteraction/{id}").hasAnyRole(Constrains.ROLE_ADMIN,Constrains.ROLE_USER)
                       /* .requestMatchers(HttpMethod.GET, "/api/v1/interaction/getAllInteraction").hasRole(Constrains.ROLE_ADMIN)*/
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
