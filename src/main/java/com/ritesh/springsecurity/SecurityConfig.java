package com.ritesh.springsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  /*
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
  	httpSecurity.authorizeRequests().antMatchers("/css/**", "/index").permitAll().antMatchers("/user/**")
  			.hasRole("USER").and().formLogin().loginPage("/login").failureUrl("/login-error");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
  	authentication.inMemoryAuthentication().withUser("user").password("password").roles("USER");
  }*/

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
  }
}
