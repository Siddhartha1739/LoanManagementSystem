package com.example.LoanManagementSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    @Bean
    public UserDetailsService userDetailsService(){
        return new AdminUserDetails();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.PUT,"/admin/update").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/admin/viewAdmin").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/admin/approve").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/loanApplications/applyLoan").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE,"/loanApplications/delete").hasAnyRole("USER")
                                .requestMatchers(HttpMethod.GET,"/loanApplications/getLoan").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.GET,"/loanApplications/getAllLoans").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/loanApplications/updateLoan").hasAnyRole("USER")
                                .requestMatchers(HttpMethod.POST,"/loanApprovals/approveLoan").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/loanApprovals/getLoanApprovel").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/loanApprovals/update").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/loans/createLoan").hasRole("USER")
                                .requestMatchers(HttpMethod.POST,"/loans/get").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.POST,"/loans/update").hasAnyRole("USER")
                                .requestMatchers(HttpMethod.POST,"/payments/pay").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/payments/get").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.GET,"/paymentSchedules/get").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.GET,"/paymentSchedules/getUserPayments").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/users/addUser").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/users/delete").hasAnyRole("USER")
                                .requestMatchers(HttpMethod.PUT,"/users/update").hasAnyRole("USER")
                                .requestMatchers(HttpMethod.GET,"/users/get").hasAnyRole("ADMIN","USER"));

                http.httpBasic(Customizer.withDefaults());

                //disable cross Site request Forgery(CSRF)
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User.withUsername("admin")
                        .password(passwordEncoder().encode("sid123"))
                        .roles("ADMIN")
                        .build(),
                org.springframework.security.core.userdetails.User.withUsername("user")
                        .password(passwordEncoder().encode("user123"))
                        .roles("USER")
                        .build()
        );
    }*/
}
