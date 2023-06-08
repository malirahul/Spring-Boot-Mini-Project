package com.oauth2.social_media.login.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.oauth2.social_media.login.dto.UserRegisteredDTO;
import com.oauth2.social_media.login.model.User;


public interface DefaultUserService extends UserDetailsService{

	User save(UserRegisteredDTO userRegisteredDTO);




	
}
