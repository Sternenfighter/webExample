package eu.sternenfighter.example.security;

import eu.sternenfighter.example.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MySecurityConfig {
    MyUserDetailsService myUserDetailsService;

    public MySecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers("/jakarta.faces.resource/**",
                                "/actuator/health").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER")
                                .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login.xhtml")
                        .failureUrl("/login.xhtml?error=true")
                        .defaultSuccessUrl("/home.xhtml", true)
                        // Parameter are changed because of the html form
                        .usernameParameter("login:username").passwordParameter("login:password").permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout")
                        .logoutSuccessUrl("/login.xhtml").invalidateHttpSession(true))
                .authenticationProvider(authenticationProvider()).build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
