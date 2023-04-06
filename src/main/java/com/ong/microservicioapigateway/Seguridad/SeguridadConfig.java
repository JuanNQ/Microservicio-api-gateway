package com.ong.microservicioapigateway.Seguridad;

import com.ong.microservicioapigateway.Entidades.Rol;
import com.ong.microservicioapigateway.Seguridad.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
//                .withUser("Juan")
//                .password(new BCryptPasswordEncoder().encode("aujnsdjk"))
//                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("rasd"))
//                .and()
//                .withUser("asdasd")
//                .password(new BCryptPasswordEncoder().encode("asdasd"))
//                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("asdasd"))
//                .and()
//                .passwordEncoder(new BCryptPasswordEncoder());
//                .passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        return http
                .csrf().disable().cors().disable()
                .authorizeHttpRequests()
                .antMatchers("/v1/authentication/registrar",
                        "/v1/authentication/login").permitAll()
//                .antMatchers("/v1/authentication/perfil").hasRole(Rol.ADMIN.name())
//                .anyRequest().authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();

    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

}
