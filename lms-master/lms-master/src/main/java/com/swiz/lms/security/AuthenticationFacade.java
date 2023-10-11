package com.swiz.lms.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationFacade {
    UserDetails getCurrentUser();

}
