package net.adsService.config.security;

import net.adsService.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("UDS")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**","/login","/","/category","/category-by-child")
                .permitAll()
                .antMatchers("/register")
                .anonymous()
                .antMatchers("/ad/add/**",
                        "/user/update", "/like","/dislike",
                        "/comment/add","/message","/message/**")
                .hasAuthority(UserRole.USER.name())
                .antMatchers("/ad/*","/category/*")
                .hasAnyAuthority("ROLE_ANONYMOUS",UserRole.USER.name())
                .antMatchers("/admin","/admin/**")
                .hasAuthority(UserRole.ADMIN.name())
        .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/")
                .defaultSuccessUrl("/login?param=success")
                .failureUrl("/login?param=error")
        .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("RM")
        .and()
                .rememberMe()
                .rememberMeCookieName("RM")
                .tokenValiditySeconds(20000000)
                .rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService);
    }
}
