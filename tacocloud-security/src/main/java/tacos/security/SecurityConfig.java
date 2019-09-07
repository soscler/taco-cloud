package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userRepositoryUserDetailsService")
    @Autowired
    private UserDetailsService userDetails;


    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception {
         httpSecurity
                 .authorizeRequests()
                 .antMatchers(HttpMethod.OPTIONS).permitAll()
                 .antMatchers("/design", "/orders/**").permitAll()
                 .antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                 .antMatchers("/**").access("permitAll")

                 .and()
                    .formLogin()
                        .loginPage("/login")

                 .and()
                    .httpBasic()
                        .realmName("Taco Cloud")

                 .and()
                    .logout()
                        .logoutSuccessUrl("/")

                 .and()
                    .csrf()
                        .ignoringAntMatchers("h2-console/**", "ingredients/**", "/design/**", "/orders/**")

                 .and()
                    .headers()
                        .frameOptions()
                            .sameOrigin()
                 ;
     }

     @Bean
     public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
     }

     @Override
     protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .userDetailsService(userDetails)
                .passwordEncoder(encoder());

    }
}
