package com.chrisssanti.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}root")
                .authorities("ROLE_USER")
                .and()
                .withUser("user")
                .password("{noop}passwd")
                .authorities("USER_ROLE");

    }*/

    /*
     * As with defaultPasswordEncoder is deprecated, we use an encoder from the passworrdEncoderFactories
     * */
    /*@Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User.UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password(encoder.encode("password")).roles("USER").build());
        manager.createUser(users.username("admin").password(encoder.encode("password")).roles("USER", "ADMIN").build());
        return manager;

    }*/

    /*@Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enable from Users " +
                                "where username = ?"
                ).groupAuthoritiesByUsername(
                        "SELECT username, authority from UserAuthorities " +
                                "where username = ?"
        ).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
        ;
    }*/

    @Autowired
    private UserDetailsService userDetailsService;

    /*
    * The password encoder could also be a bcryptEncoder
    * */
    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    * To authenticate a user
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    /*
    * The order is important, the security first (design, orders), then the low secure
    * */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/design", "/orders")
                        .hasRole("USER")
                    .antMatchers("/", "/**").permitAll()
                ;
    }
}
