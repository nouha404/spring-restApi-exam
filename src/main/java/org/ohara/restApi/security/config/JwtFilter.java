package org.ohara.restApi.security.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ohara.restApi.security.services.impl.JwtService;
import org.ohara.restApi.security.services.impl.SecurityServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SecurityServiceImpl securityService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String authorizationHeader=  request.getHeader("Authorization");
      String token=null;
      String username=null;

      if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
             token=authorizationHeader.substring(7);
            //Extraire le Username du token
           if (!jwtService.isTokenExpired(token)){
                username= jwtService.extractUsername(token);
           }

      }


      if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = securityService.loadUserByUsername(username);
          if (jwtService.validateToken(token, userDetails)) {
              UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
      }
        filterChain.doFilter(request, response);

    }
}
