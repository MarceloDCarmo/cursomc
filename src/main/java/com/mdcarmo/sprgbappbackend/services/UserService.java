package com.mdcarmo.sprgbappbackend.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.security.UserSS;

@Service
public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception ex) {
			return null;
		}
	}

}
