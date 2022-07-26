package com.work.authentication.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.Filter;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //this is to igmore some files
        web.ignoring().antMatchers("/css/**", "/js/**", "/assets/**", "/favicon/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers().permitAll()//state all url you want to go without authorisation
                .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginPage("")//state the path in the static folder
                .loginProcessingUrl("")//state the url to the login page
                .failureUrl(""); //url to the failure page

        http.logout().logoutSuccessUrl(""); //url to the logout page

        http.csrf().disable();
        http.cors().disable();

        //add the authentication filter for the use of token for authenticating the user
        http.addFilter(customUsernamePasswordAuthenticationFilter());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider());
    }

    @Bean
    protected CustomAuthenticationProvider provider(){
        return new CustomAuthenticationProvider();
    }

    @Bean(name = "customAuthenticationManagerBean")
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler customFailureHandler(){
        return new CustomLoginFailureHandler();
    }

    @Bean
    private Filter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomPasswordAuthenticationFilter filter = new CustomPasswordAuthenticationFilter();
        //set the authentication manager
        filter.setAuthenticationManager(authenticationManager());
        //set the success handler
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
        //set the failure handler
        filter.setAuthenticationFailureHandler(customFailureHandler());

        return filter;
    }
}
