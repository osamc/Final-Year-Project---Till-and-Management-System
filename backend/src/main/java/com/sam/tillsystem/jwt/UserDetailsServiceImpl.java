package com.sam.tillsystem.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	JwtUserAPI userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		JwtUser user = userService.findByUsername(username);
		
		if (user == null) {
            throw new UsernameNotFoundException(username);
		}
		
		return  new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}
