package com.mbe.ada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbe.ada.model.user.User;
import com.mbe.ada.model.user.dto.UserDTO;
import com.mbe.ada.repository.IUserRepository;


@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	IUserRepository userRepos;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>>  index() {		
		
		List<User> data = userRepos.findAll();
		
		if(data.size() == 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		List<UserDTO> dataDTO = data.stream()
		.map(user -> new UserDTO(user))
		.toList();
		
		return new ResponseEntity<List<UserDTO>>(dataDTO, HttpStatus.OK);
	}

}
