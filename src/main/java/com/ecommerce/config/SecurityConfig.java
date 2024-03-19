package com.ecommerce.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ecommerce.dtos.UserDTO;
import com.ecommerce.enums.Role;
import com.ecommerce.mappers.UserMapper;
import com.ecommerce.models.User;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.services.CustomOAuth2UserService;
import com.ecommerce.services.UserService;
import com.ecommerce.utils.CustomOAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/userinfo").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.GET, "/").hasRole("USER");
            auth.anyRequest().authenticated();
        })
        //.oauth2Login(Customizer.withDefaults())
         .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oauthUserService)).successHandler(new AuthenticationSuccessHandler() {

                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {

                                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                                String github_id = oauthUser.getAttributes().get("id").toString();

                                Optional<User> userExistOp = userRepository.findByIdentifier(github_id);

                                if(!userExistOp.isPresent()) {
                                    UserDTO userDTO = new UserDTO(oauthUser.getName(),"username", oauthUser.getAttributes().get("id").toString(), oauthUser.getAttributes().get("avatar_url").toString(), Role.USER);

                                    //UserDTO userDto = userMapper.userToUserDTO(userExistOp.get());

                                   // userDto.setRole(Role.USER);

                                    userService.create(userDTO);
                                }

                                response.sendRedirect("/");
                            }
                        })
                )
        .build();
    }

    
}
