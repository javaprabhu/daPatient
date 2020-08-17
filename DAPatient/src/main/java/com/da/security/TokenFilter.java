package com.da.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.da.model.User;
import com.da.util.WebSecurityUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {

  @Autowired
  private WebSecurityUtils webSecurityUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String idToken = webSecurityUtils.getTokenFromRequest(request);
    FirebaseToken decodedToken = null;
    if (idToken != null) {
      try {
        decodedToken = FirebaseAuth.getInstance()
            .verifyIdToken(idToken);
      } catch (FirebaseAuthException e) {
        log.error("Firebase Exception:: ", e.getLocalizedMessage());
      }
    }
    if (decodedToken != null) {
      User user = new User();
      user.setUid(decodedToken.getUid());
      user.setEmail(decodedToken.getEmail());
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          user, decodedToken, null);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext()
          .setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
