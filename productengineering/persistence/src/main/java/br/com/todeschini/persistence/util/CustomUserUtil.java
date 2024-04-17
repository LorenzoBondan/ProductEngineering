package br.com.todeschini.persistence.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserUtil {

    public String getLoggedUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
