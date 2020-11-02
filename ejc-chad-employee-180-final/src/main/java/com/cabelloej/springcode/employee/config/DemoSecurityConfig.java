package com.cabelloej.springcode.employee.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to security data source
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication 	
		auth.jdbcAuthentication().dataSource(securityDataSource);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		
			.antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/delete").hasRole("ADMIN")
			.antMatchers("/employees/**").hasRole("EMPLOYEE")
			
			.antMatchers("/employees/relatives/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/relatives/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/relatives/delete").hasRole("ADMIN")
			.antMatchers("/employees/relatives/**").hasRole("EMPLOYEE")
			
			.antMatchers("/departments/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/departments/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/departments/delete").hasRole("ADMIN")
			.antMatchers("/departments/**").hasRole("EMPLOYEE")
			
			.antMatchers("/sports/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/sports/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/sports/delete").hasRole("ADMIN")
			.antMatchers("/sports/**").hasRole("EMPLOYEE")
			
			.antMatchers("/spokenlanguages/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/spokenlanguages/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/spokenlanguages/delete").hasRole("ADMIN")
			.antMatchers("/spokenlanguages/**").hasRole("EMPLOYEE")
			
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
		
}






