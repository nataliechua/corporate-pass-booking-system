package project;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    
    /** 
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("Admin")) {
            httpServletResponse.sendRedirect("/");
        } else if (roles.contains("Staff")) {
            httpServletResponse.sendRedirect("/bookAPass");
        } else if (roles.contains("GOP")) {
            httpServletResponse.sendRedirect("/gopReturnPass");
        }
    }
}