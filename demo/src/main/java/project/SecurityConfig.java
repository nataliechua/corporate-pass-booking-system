package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import project.service.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // replace default login page w our customised ver

    @Autowired
    private StaffService staffService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(staffService);
        auth.setPasswordEncoder(new BCryptPasswordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // // uncomment here to enable login screen
        // String[] staticResources  =  {
        //     "/registration**",
        //     "/css/**",
        //     "/vendor/fontawesome-free/css/**",
        //     "/vendor/jquery/**",
        //     "/vendor/bootstrap/js/**",
        //     "/vendor/jquery-easing/**",
        //     "/js/**",
        //     "/staff/**",
        //     "staff**",
        //     "/passes/**",
        //     "passes**",
        //     "loans**",
        //     "/loans/**"
        // };
        // http
        //     .authorizeRequests()
        //     .antMatchers(staticResources).permitAll()
        //     .anyRequest().authenticated()
        //     .and()
        //         .formLogin()
        //         .loginPage("/login") // use a customised login page
        //         .permitAll()
        //     .and()
        //         .logout()
        //         .invalidateHttpSession(true)
        //         .clearAuthentication(true)
        //         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        //         .logoutSuccessUrl("/login?logout")
        //         .permitAll();
        
        // uncomment here to disable login screen
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll();
    }
    
    
}
