package ru.alekseiadamov.adminapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class UiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/product").authenticated()
                .antMatchers("/category").authenticated()
                .antMatchers("/user").hasRole("ADMIN")
                .antMatchers("/user/**", "/product/**", "/category/**", "/brand/**").hasRole("ADMIN")

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/product")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/access_denied");
    }
}
