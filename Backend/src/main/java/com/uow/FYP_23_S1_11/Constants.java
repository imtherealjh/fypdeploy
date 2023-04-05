package com.uow.FYP_23_S1_11;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.uow.FYP_23_S1_11.domain.UserAccount;

public class Constants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";	

    public static UserAccount getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal() != null ? (UserAccount) authentication.getPrincipal() : null;
    }
}
