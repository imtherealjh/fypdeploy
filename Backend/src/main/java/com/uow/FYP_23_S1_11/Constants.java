package com.uow.FYP_23_S1_11;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.uow.FYP_23_S1_11.domain.UserAccount;

import jakarta.servlet.http.HttpServletRequest;

public class Constants {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static UserAccount getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal() != null ? (UserAccount) authentication.getPrincipal() : null;
    }

    public static String makeUrl(HttpServletRequest request) {
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }

    public static Map<String, Object> convertToResponse(final Page<?> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("current_page", page.getNumber());
        response.put("total_items", page.getTotalElements());
        response.put("total_pages", page.getTotalPages());
        return response;
    }
}
