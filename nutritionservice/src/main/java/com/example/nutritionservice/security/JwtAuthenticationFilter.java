package com.example.nutritionservice.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private JWTService jwtService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwt = authHeader.substring(7);

		userEmail = jwtService.extractUsername(jwt);
		Collection<? extends GrantedAuthority> authorities = jwtService.extractAuthoritiesFromToken(jwt);

		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			String user = jwtService.extractUsername(jwt);

			if (jwtService.isTokenValid(jwt)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
						authorities
				// userDetails.getAuthorities()
				);

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// Update Security CContext
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// After this if condition must have call this
		filterChain.doFilter(request, response);
	}

}
