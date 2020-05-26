package com.socialmedia.userservice.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.socialmedia.userservice.exception.UserNotFoundException;
import com.socialmedia.userservice.model.NotificationDTO;
import com.socialmedia.userservice.model.User;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private QueueProducer queueProducer;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(path = "sign-up")
	public ResponseEntity<Object> createAdmin(@Valid @RequestBody User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		System.out.println("user pass word -" + user.getPassword());
		Map<String, String> response = new HashMap<String, String>();

		User _user = UserRepository.save(user);

		if (_user != null) {

			response.put("message", "User save successfully ");

			NotificationDTO notificationDTO = new NotificationDTO("SendMail", user.getUsername());
			try {
				queueProducer.produce(notificationDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ResponseEntity.accepted().body(response);

		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(_user.getUserId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@GetMapping(path = "/user")
	public List<User> retrieveAllUser() {
		return UserRepository.findAll();
	}

	@GetMapping(path = "/user/{id}")
	public Optional<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = UserRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException("user is not Exist : id -" + id);

		return user;
	}

	@PutMapping(path = "/user/updatePassword")
	public User retrieveUser(@RequestParam String newPassword, @RequestParam String username,
			@RequestParam String oldPassword) {
		User user = UserRepository.findByUsername(username);

		if (user != null) {
			if (user.getPassword().equals(bCryptPasswordEncoder.encode(oldPassword))) {

				user.setPassword(bCryptPasswordEncoder.encode(newPassword));
				UserRepository.save(user);

			} else {
				throw new UserNotFoundException("user old password is not correct for  : username -" + username);
			}
		} else {
			throw new UserNotFoundException("user is not Exist : username -" + username);
		}

		return user;
	}

	@DeleteMapping(path = "/user/{id}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {

		UserRepository.deleteById(id);

		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "User Delete successfully ");

		NotificationDTO notificationDTO = new NotificationDTO("UserDeleted", id);
		try {
			queueProducer.produce(notificationDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.accepted().body(response);

	}

}