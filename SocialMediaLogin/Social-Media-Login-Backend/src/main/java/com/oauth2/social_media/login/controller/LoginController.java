package com.oauth2.social_media.login.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.oauth2.social_media.login.dao.UserRepository;
import com.oauth2.social_media.login.dto.UserLoginDTO;
import com.oauth2.social_media.login.service.DefaultUserService;

@Controller
public class LoginController {
    private final DefaultUserService userService;
    private final UserRepository userRepo;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public LoginController(DefaultUserService userService, UserRepository userRepo,
            OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/githubProfile")
    public ResponseEntity<?> getGitHubProfile(Authentication authentication) {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String providerName = "github";
        String username = authentication.getName();
        OAuth2AuthorizedClient authorizedClient =
                authorizedClientService.loadAuthorizedClient(providerName, username);

        if (authorizedClient == null) {
            // Handle case when authorized client is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No authorized client found for provider: " + providerName);
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        String accessTokenValue = accessToken.getTokenValue();

        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue)
                .build();

        String userData = webClient.get()
                .uri("https://api.github.com/user")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(userData);
    }

    @GetMapping("/googleProfile")
    public ResponseEntity<?> getGoogleProfile(Authentication authentication) {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String providerName = "google";
        String username = authentication.getName();
        OAuth2AuthorizedClient authorizedClient =
                authorizedClientService.loadAuthorizedClient(providerName, username);

        if (authorizedClient == null) {
            // Handle case when authorized client is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No authorized client found for provider: " + providerName);
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        String accessTokenValue = accessToken.getTokenValue();

        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue)
                .build();

        String userData = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v1/userinfo?alt=json")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(userData);
    }
}
