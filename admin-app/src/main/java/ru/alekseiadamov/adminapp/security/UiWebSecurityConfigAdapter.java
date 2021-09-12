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
                .antMatchers("/shop/product").authenticated()
                .antMatchers("/shop/category").authenticated()
                .antMatchers("/shop/brand").authenticated()
                .antMatchers("/shop/user").hasRole("ADMIN")
                .antMatchers("/shop/user/**", "/shop/product/**", "/shop/category/**", "/shop/brand/**").hasRole("ADMIN")

                .and()
                .formLogin()
                .loginPage("/shop/login")
                .defaultSuccessUrl("/shop/product")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/shop/access_denied");
    }
}
