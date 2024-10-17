package com.mbe.ada.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbe.ada.infra.security.TokenService;
import com.mbe.ada.model.auth.dto.LoginDTO;
import com.mbe.ada.model.user.User;
import com.mbe.ada.model.user.dto.RegisterDTO;
import com.mbe.ada.model.user.dto.ResponseDTO;
import com.mbe.ada.repository.IUserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final IUserRepository userRepos;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	
	
	public AuthController(IUserRepository userRepos, PasswordEncoder passwordEncoder, TokenService tokenService) {
		this.userRepos = userRepos;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginDTO data) {
		
		User user = this.userRepos.findByEmail(data.login()).orElseThrow(() -> new RuntimeException("Erro ao buscar usuário"));
		if(passwordEncoder.matches(data.password(), user.getPassword())) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterDTO data) {
		
		Optional<User> user = this.userRepos.findByEmail(data.email());
		
		if(user.isEmpty()) {
			User newUser = new User();
			newUser.setName(data.name());
			newUser.setLastname(data.lastname());
			newUser.setPassword(passwordEncoder.encode(data.password()));
			newUser.setEmail(data.email());
			newUser.setBirthDate(data.birthdate());
			newUser.setCpf(data.cpf());
			newUser.setIsActive(true);
			newUser.setCreatedAt(LocalDateTime.now());
			
			this.userRepos.save(newUser);
			
			String token = this.tokenService.generateToken(newUser);
			return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	

}
