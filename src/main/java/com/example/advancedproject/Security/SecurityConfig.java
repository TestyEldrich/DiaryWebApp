package com.example.advancedproject.Security;

import com.example.advancedproject.Filter.CustomAuthenticationClass;
import com.example.advancedproject.Filter.CustomAuthenticationClass;
import com.example.advancedproject.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/icons/**", "/css/**").permitAll();
        http.authorizeRequests().antMatchers("/user/token/**").permitAll();
        http.authorizeRequests().antMatchers("/user/roleSave/**").permitAll();
        http.authorizeRequests().antMatchers("/user/register").permitAll();
//        http.formLogin()
//                .permitAll()
//                .loginPage("/login.html")
//                .defaultSuccessUrl("/dailyNote/getDailyNotes", true);
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers("/", "/icons/**", "/css/**").permitAll();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().antMatchers("/user/getUsersPass").authenticated();

        http.authorizeRequests().antMatchers("/user/getUsersDelete").authenticated();

        http.authorizeRequests().antMatchers("/user/**").authenticated();

        http.authorizeRequests().antMatchers("/diary/**").authenticated();

        http.authorizeRequests().antMatchers("/dailyNote/**").authenticated();

        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationClass(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
