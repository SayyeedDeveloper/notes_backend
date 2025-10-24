package sayyeed.com.notesbackend.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sayyeed.com.notesbackend.config.security.CustomUserDetails;

public class SpringSecurityUtil {

    public static String currentProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getId();
    }

}
