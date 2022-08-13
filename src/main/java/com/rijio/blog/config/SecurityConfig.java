package com.rijio.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rijio.blog.security.JwtAuthenticationEntryPoint;
import com.rijio.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public JwtAuthenticationEntryPoint authenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
            .antMatchers("/api/v1/auth/**").permitAll()
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .anyRequest()
            .authenticated();
            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            // disable page caching
            // http.headers().cacheControl().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // @Bean
    // public DaoAuthenticationProvider authyProvider() {
    //     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    //     authenticationProvider.setPasswordEncoder(passwordEncoder());
    //     authenticationProvider.setUserDetailsService(userDetailsService);
    //     return authenticationProvider;
    // }

    // @Bean
    // public AuthenticationManagerBuilder configureAuth(AuthenticationManagerBuilder auth) throws Exception {
    //     return auth.authenticationProvider(authyProvider());
    // }

  

    // @Bean                                                             
	// public UserDetailsService userDetailsService() throws Exception {
	// 	// ensure the passwords are encoded properly
	// 	// UserBuilder users = User.
       
	// 	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	// 	// manager.createUser(users.username("user").password("password").roles("USER").build());
	// 	// manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
	// 	return manager;
	// }
    
    // @Bean
    // protected InMemoryUserDetailsManager configureAuthentication() {
    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //     userDetailsService.
    //     UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER").build();
    //     UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    // @Bean                                                             
	// public UserDetailsService userDetailsService() throws Exception {
	// 	// ensure the passwords are encoded properly
	// 	// UserBuilder users = userDetailsService;
	// 	// InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	// 	// manager.createUser(userDetailsService);
	// 	// manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
	// 	return userDetailsService;
	// }
    
}
