package com.hexdump95.organization.config;

import com.hexdump95.organization.security.JwtAuthenticationFilter;
import com.hexdump95.organization.security.JwtAuthorizationFilter;
import com.hexdump95.organization.services.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.hexdump95.organization.controllers.AuthController.LOGIN_ENDPOINT;
import static com.hexdump95.organization.controllers.EmployeeController.EMPLOYEE_ENDPOINT;
import static com.hexdump95.organization.security.RolesConstants.ADMIN;
import static com.hexdump95.organization.security.RolesConstants.USER;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v3/api-docs/swagger-config",
                "/v3/api-docs",
                "/v3/api-docs.yaml",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-ui*/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, EMPLOYEE_ENDPOINT + "**").hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
                .antMatchers("/**").hasAnyRole(ADMIN).and()
                .csrf().disable()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtService), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter authenticationFilter =
                new JwtAuthenticationFilter(authenticationManager(), jwtService);
        authenticationFilter.setFilterProcessesUrl(LOGIN_ENDPOINT);
        return authenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles(USER)
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(ADMIN, USER)
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
