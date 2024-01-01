package com.ku.quizzical.app.helper;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ku.quizzical.common.helper.ListHelper;

/**
 * Helper class for User Operations
 */
public class UserHelper {
    /**
     * Make default authority List
     */
    public static List<SimpleGrantedAuthority> makeDefaultAuthorityList() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Make default role List
     */
    public static List<String> makeDefaultRoleList() {
        return ListHelper.map(UserHelper.makeDefaultAuthorityList(),
                SimpleGrantedAuthority::getAuthority);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private UserHelper() {
        super();
    }
}
