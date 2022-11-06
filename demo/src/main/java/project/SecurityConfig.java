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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import project.service.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // replace default login page w our customised ver

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

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
        String[] staticResources  =  {
            "/registration**",
            "/css/**",
            "/img/**",
            "/vendor/fontawesome-free/css/**",
            "/vendor/jquery/**",
            "/vendor/bootstrap/js/**",
            "/vendor/jquery-easing/**",
            "/js/**",
            "/staff/**",
            "staff**",
            "/passes/**",
            "passes**",
            "loans**",
            "/loans/**",
            "/updateStaffToActive/**"
        };

        String[] adminViews = {
            "/",
            "/viewPasses",
            "/viewStaffs",
            "/viewLoanHistory",
            "/templateList",
            "/bookingCriteria",
            "/bookAPass",
            "/loanedPasses"
        };

        String[] gopViews = {
            "/gopReturnPass",
        };

        String[] staffAndAdminViews = {
            "/bookAPass",
            "/loanedPasses"
        };

        http
            .authorizeRequests()
            .antMatchers(staticResources).permitAll()
            .antMatchers(adminViews).access("hasAuthority('Admin')")
            .antMatchers(gopViews).access("hasAuthority('GOP')")
            .antMatchers(staffAndAdminViews).access("hasAuthority('Staff') || hasAuthority('Admin')")
            .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login") // use a customised login page
                .successHandler(authenticationSuccessHandler)
                .permitAll()
            .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedPage("/404")    
            ;
        
        // uncomment here to disable login screen
        // http
        //     .csrf().disable()
        //     .authorizeRequests()
        //     .antMatchers("/**").permitAll();
    }
    
    
}
