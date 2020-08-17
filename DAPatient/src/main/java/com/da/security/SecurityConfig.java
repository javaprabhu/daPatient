package com.da.security;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public TokenFilter tokenAuthenticationFilter() {
		return new TokenFilter();
	}
	
	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public AuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					AuthenticationException e) throws IOException, ServletException {
				Map<String, Object> errorObject = new HashMap<String, Object>();
				int errorCode = 401;
				errorObject.put("message", "Access Denied");
				errorObject.put("error", HttpStatus.UNAUTHORIZED);
				errorObject.put("code", errorCode);
				errorObject.put("timestamp", new Timestamp(new Date().getTime()));
				httpServletResponse.setContentType("application/json;charset=UTF-8");
				httpServletResponse.setStatus(errorCode);
				httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfigurationSource()).and().csrf()
		.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
		.authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeRequests()
		.anyRequest().authenticated();
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	 @Bean
	  public CorsConfigurationSource corsConfigurationSource() {
	      final CorsConfiguration configuration = new CorsConfiguration();
	     
	      configuration.setAllowedOrigins(Arrays.asList("*"));
	      configuration.setAllowedMethods(Arrays.asList("*"));
	      configuration.setAllowCredentials(true);
	      configuration.setAllowedHeaders(Arrays.asList("*"));
	      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      source.registerCorsConfiguration("/**", configuration);
	      return source;
	  }
}