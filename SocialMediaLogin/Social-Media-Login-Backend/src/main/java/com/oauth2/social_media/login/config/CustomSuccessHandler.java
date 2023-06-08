package com.oauth2.social_media.login.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.oauth2.social_media.login.dao.UserRepository;
import com.oauth2.social_media.login.dto.UserRegisteredDTO;
import com.oauth2.social_media.login.service.DefaultUserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DefaultUserService userService;
		
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	        Authentication authentication) throws IOException, ServletException {

	    String redirectUrl = null;
	    if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
	        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
	        String username = userDetails.getAttribute("email") != null ? userDetails.getAttribute("email")
	                : userDetails.getAttribute("login") + "@gmail.com";
	        if (userRepo.findByEmail(username) == null) {
	            UserRegisteredDTO user = new UserRegisteredDTO();
	            user.setEmail_id(username);
	            user.setName(userDetails.getAttribute("email") != null ? userDetails.getAttribute("email")
	                    : userDetails.getAttribute("login"));
	            user.setPassword(("Dummy"));
	            user.setRole("USER");

	            userService.save(user);
	        }
	    }

	    // Check the authentication provider and set the redirect URL accordingly
	    if (authentication instanceof OAuth2AuthenticationToken) {
	        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
	        String provider = oauthToken.getAuthorizedClientRegistrationId();
	        if (provider.equals("google")) {
	            redirectUrl = "http://localhost:3000/googleProfile";
	        } else if (provider.equals("github")) {
	            redirectUrl = "http://localhost:3000/githubProfile";
	        }
	    }

	    new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}


}
