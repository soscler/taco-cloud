package com.chrisssanti.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * By only creating this class a new authentication page will be created instead of the http prompt
 * Taking us a step further
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Since we have a service for fetching user information, we can use this same service for authentication

    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }

    @Autowired
    SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    /**
     * The password encoder could also be a bcryptEncoder
     * @return
     */
    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Used for a user authentication
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(encoder());
    }

    /**
     * Secure the http request: block some page to unlogged users and redirect them to loginform
     * and() is a bridge to say, hey I am done with something let do something else
     * The order is important, the security first (design, orders), then the low secure
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/design", "/orders")
          .access("hasRole('ROLE_USER')")
        .antMatchers("/" , "/**").access("permitAll")

      .and()
        .formLogin()
          .loginPage("/login")
            .defaultSuccessUrl("/design")

      .and()
        .logout()
          .logoutSuccessUrl("/")

      .and()
        .csrf()
          .ignoringAntMatchers("/h2-console/**")

      // Allow pages to be loaded in frames from the same origin; needed for H2-Console
      .and()
        .headers()
          .frameOptions()
            .sameOrigin()
      ;
  }
}
