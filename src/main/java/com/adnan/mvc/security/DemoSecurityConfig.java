package com.adnan.mvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
		
		UserDetails harsh = User.builder().username("harsh").password("{noop}test123").roles("EMPLOYEE").build();
		UserDetails abhay = User.builder().username("abhay").password("{noop}test123").roles("EMPLOYEE", "MANAGER").build();
		UserDetails adnan = User.builder().username("adnan").password("{noop}test123").roles("EMPLOYEE", "MANAGER", "ADMIN").build();
		
		return new InMemoryUserDetailsManager(harsh, abhay, adnan);
		
	}
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.authorizeHttpRequests(configurer -> configurer
    														.requestMatchers("/").hasRole("EMPLOYEE")
    														.requestMatchers("/leaders/**").hasRole("MANAGER")
    														.requestMatchers("/systems/**").hasRole("ADMIN")
    														.anyRequest().authenticated())
    									.formLogin(form -> form.loginPage("/showMyLoginPage").loginProcessingUrl("/authenticateTheUser").permitAll())
    									.logout(logout -> logout.permitAll())
    									.exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
    	
    	return http.build();
    }
	
}
