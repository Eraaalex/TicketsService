package com.hse.software.construction.ticketsapp.filter;

import com.hse.software.construction.ticketsapp.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@AllArgsConstructor
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        log.info("1");
        String authHeader = request.getHeader("Authorization");
        log.info("2");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("3");
            filterChain.doFilter(request, response);
            log.info("4");
            return;
        }
        log.info("5");
        String jwt = authHeader.substring(7);
        log.info("6");
        if (!jwtService.isExpired(jwt)) {
            log.info("7");
            String username = jwtService.getUsernameFromToken(jwt);
            log.info("8");
            log.info(username);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList()
                );
                log.info("" + response);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("" + response);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        log.info("" + response);
        filterChain.doFilter(request, response);
    }
}