package com.example.projectappchat.configuration;


import com.example.projectappchat.entity.User;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.service.userDetails.UserDetailsServiceImpl;
import com.example.projectappchat.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Set service to find User in database
        //Set PasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //hạn chế được việc tấn công Csrf
        http.csrf().disable();

        //Page dont require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout", "/index")
                .permitAll();

        //Page /userInfo require User have role is ROLE_USER or ROLE_ADMIN
        //If not login yet, it will be redirect to /login
        http.authorizeRequests().antMatchers("/userInfo")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

        //Page only AMIN can access
        http.authorizeRequests().antMatchers("/admin")
                .access("hasRole('ROLE_ADMIN')");

        //When user login success, with role XX
        //But access to page require roll YY,
        //Exception AccessDeniedException will throw away
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        //Config for Login Form
        http.authorizeRequests().and().formLogin()//
                //submit url of login page
                .loginProcessingUrl("/j_spring_security_check") //submit url
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        Authentication authentication) throws IOException, ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        User user = userService.findUserByAccount(userDetails.getUsername());
                        user.setOnline(AppUtils.isOnline);
                        userService.save(user);
                        httpServletResponse.sendRedirect("/");
                    }
                })
                .failureUrl("/login-failed")
                //config for Logout page
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        User user = userService.findUserByAccount(userDetails.getUsername());
                        user.setOnline(AppUtils.isOffline);
                        userService.save(user);
                        httpServletResponse.sendRedirect("/logoutSuccessful");
                    }
                })
                .logoutSuccessUrl("/logoutSuccessful");

        //Config Remember me
        http.authorizeRequests().and().rememberMe().tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h


    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
