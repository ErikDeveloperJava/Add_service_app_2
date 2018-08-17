package net.adsService.interceptor;

import net.adsService.config.security.UserDetailsImpl;
import net.adsService.model.User;
import net.adsService.model.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetailsImpl){
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userDetails.getUser();
            LOGGER.debug("authenticated user : {}",user);
            request.setAttribute("user",user);
        }else {
            LOGGER.debug("not authenticated user");
            request.setAttribute("user",User.builder().role(UserRole.ANONYMOUS).build());
        }

        return true;
    }
}
