package com.sam.tillsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sam.tillsystem.jwt.JwtAuthenticationFilter;
import com.sam.tillsystem.jwt.JwtAuthorisationFilter;
import com.sam.tillsystem.jwt.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	 private UserDetailsServiceImpl userDetailsService;
	 private BCryptPasswordEncoder bCryptPasswordEncoder;

	    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
	        this.userDetailsService = userDetailsService;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable().authorizeRequests()
	        		//We want all Post and get requests at these urls to be authenticated
	        		.antMatchers(HttpMethod.POST, "/page/**", "/chart/**", "/product/**", "/seller/**", "/transaction/**", "/jwtuser/**").authenticated()
	        		.antMatchers(HttpMethod.GET, "/page/**", "/chart/**", "/product/**", "/seller/**", "/transaction/**", "/jwtuser/tokentest").authenticated()
	                //Anything else can be anonymously accessed, this means the angular app is still accesible
	        		.antMatchers(HttpMethod.GET, "**").anonymous()
	        		.and()
	        		//User our authentication and authorisation fitlers
	                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
	                .addFilter(new JwtAuthorisationFilter(authenticationManager()))
	                // this disables session creation on Spring Security
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    }

	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    }


	
	
}
