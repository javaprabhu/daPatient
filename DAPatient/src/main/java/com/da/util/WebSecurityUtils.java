package com.da.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

@Component
public class WebSecurityUtils {
  

  public String getTokenFromRequest(HttpServletRequest request) {
    String token = null;
    Cookie cookieToken = WebUtils.getCookie(request, "token");
    if (cookieToken != null) {
      token = cookieToken.getValue();
    } else {
      String bearerToken = request.getHeader("X-Firebase-Authorization");
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        token = bearerToken.substring(7, bearerToken.length());
      }
    }
    return token;
  }
}
