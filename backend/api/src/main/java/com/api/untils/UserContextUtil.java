package com.api.untils;

import com.shared.db.entities.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextUtil {

    public static Account getCurrentUser() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isCurrentUser(Long id) {
        return getCurrentUser().getId().equals(id);
    }

}
