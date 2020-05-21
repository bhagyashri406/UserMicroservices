package com.socialmedia.userservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.socialmedia.userservice.controller.UserRepository;

@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository UserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.socialmedia.userservice.model.User user = UserRepository.findByUsername(username);

		System.out.println("repository password : " + user.getPassword());

		if (user != null && username.equals(user.getUsername())) {

			return new User(user.getUsername(), user.getPassword(), new ArrayList<>());

		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}