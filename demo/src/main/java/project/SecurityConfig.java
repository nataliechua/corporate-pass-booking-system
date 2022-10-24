package project;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // replace default login page w our customised ver

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] staticResources  =  {
            "/css/**",
            "/vendor/fontawesome-free/css/**",
            "/vendor/jquery/**",
            "/vendor/bootstrap/js/**",
            "/vendor/jquery-easing/**",
            "/js/**",
        };
        http
            .authorizeRequests()
            .antMatchers(staticResources).permitAll()
            .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login") // use a customised login page
                .permitAll()
            .and()
                .logout((logout) -> logout.permitAll());
                // .logout()
                // .logoutSuccessUrl("/");
    }
    
}
