package ru.tehsystem.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.tehsystem.demo.services.impl.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
//     http.authorizeRequests()
//             .antMatchers("/templates/**", "/static/**", "/login", "/user").permitAll()
//            .antMatchers("/**","/ordersCreate").authenticated()
//            .antMatchers("/**").authenticated()
//            .antMatchers("/**").authenticated()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .and()
//            .logout()
//            .permitAll().and().csrf().disable().httpBasic();
    @Override
    protected void configure(HttpSecurity http) throws Exception {
             http.authorizeRequests()
                     .antMatchers("/templates/**", "/static/**", "/registration", "/login", "/user").permitAll()
                    .antMatchers("/**","/ordersCreate").authenticated()
                    .antMatchers("/**").authenticated()
                    .antMatchers("/**").authenticated()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll().and().csrf().disable().httpBasic();




//        http.authorizeRequests()
//            .antMatchers("/templates/**", "/**", "/static/**", "/registration").permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .and()
//            .logout()
//            .permitAll().and().csrf().disable().httpBasic();
        http.csrf().disable().httpBasic();

    }

}
