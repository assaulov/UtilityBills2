package ru.assaulov.utilitybills2.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImp;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	final UserServiceImp UserServiceImp;

	@Autowired
	public WebSecurityConfig(@Lazy UserServiceImp UserServiceImp) {
		this.UserServiceImp = UserServiceImp;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and().csrf().disable()
				.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/", "/bills/**", "/js/**", "/auth/**").permitAll()
				.anyRequest().authenticated()
				.and().logout()
				.deleteCookies("remove")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll()
				.and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(UserServiceImp)
				.passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
