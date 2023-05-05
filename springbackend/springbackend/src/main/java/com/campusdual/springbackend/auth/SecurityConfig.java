package com.campusdual.springbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig {

    private static final String USER_QUERY= "SELECT LOGIN, PASSWORD, TRUE AS ENABLED FROM USERS WHERE LOGIN = ?";
    private static final String USER_ROLE_QUERY = "SELECT U.LOGIN, R.ROLE_NAME FROM USER_ROLES UR INNER JOIN USERS U ON U.ID = UR.USER_ID INNER JOIN ROLES R ON R.ID = UR.ROLE_ID WHERE LOGIN = ?";

    @Autowired
    DataSource dataSource;

    @Bean
    protected AuthenticationManager configureAuthenticationManager (HttpSecurity http) throws Exception {

        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(USER_ROLE_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder())
                .and().build();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/test/**", "/public/**").permitAll()
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
