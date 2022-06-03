package com.rijio.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.rijio.blog.entity.Role;
import com.rijio.blog.entity.User;
import com.rijio.blog.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> 
            new UsernameNotFoundException("User not found with username: " + usernameOrEmail));
            System.out.println("roles>>>");
            System.out.println(user.getRoles());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
       System.out.println(roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    
}
