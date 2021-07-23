package ru.assaulov.utilitybills2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.dto.UserLoginDto;
import ru.assaulov.utilitybills2.dto.UserDto;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthController {

	final AuthenticationManager authenticationManager;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDto userLoginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getLogin(), userLoginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User userDetails = (User) authentication.getPrincipal();
		return ResponseEntity.ok(new UserDto(
				userDetails.getLogin(),
				userDetails.getFullName(),
				userDetails.getGender().getDescription(),
				userDetails.getEmail()));
	}
}
