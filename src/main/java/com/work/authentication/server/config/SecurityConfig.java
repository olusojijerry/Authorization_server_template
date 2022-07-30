package com.work.authentication.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.Filter;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CurrentUsersDetails currentUsersDetails;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider provider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(currentUsersDetails);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }
    @Bean(name = "customAuthenticationProvider")
    protected CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //this is to igmore some files
        web.ignoring().antMatchers("/css/**", "/js/**", "/assets/**", "/favicon/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/actuator/**","/h2-console/**","/login-reset/**",
                        "/login","/profile/**","/oauth/authorize","" +
                                "/.well-known/jwks.json").permitAll()//state all url you want to go without authorisation
                .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll();//state the path in the static folder
//                .loginProcessingUrl("/login")//state the url to the login page
//                .failureUrl("/index"); //url to the failure page

//        http.logout().logoutSuccessUrl("/login"); //url to the logout page

        http.csrf().disable();
        http.cors().disable();

        //add the authentication filter for the use of token for authenticating the user
        http.addFilter(customUsernamePasswordAuthenticationFilter());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
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
     Filter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomPasswordAuthenticationFilter filter = new CustomPasswordAuthenticationFilter();
        //set the authentication manager
        filter.setAuthenticationManager(authenticationManagerBean());
        //set the success handler
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
        //set the failure handler
        filter.setAuthenticationFailureHandler(customFailureHandler());

        return filter;
    }
}
