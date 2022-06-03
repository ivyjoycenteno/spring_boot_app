package com.rijio.blog.security;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

// @Component
// public class CustomAuthenticationProvider implements AuthenticationProvider{

//     @Resource
//     UserDetailsService userDetailsService;

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        
//         // if (StringUtils)
        
//         return null;
//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return authentication.equals(UsernamePasswordAuthenticationToken.class);
//     }
    
// }
