package ru.assaulov.utilitybills2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.LoginRequest;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.payload.respose.UserResponse;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserServiceImpl userService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserServiceImpl userService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User userDetails = (User) authentication.getPrincipal();
		return ResponseEntity.ok(new UserResponse(
				userDetails.getLogin(),
				userDetails.getFullName(),
				userDetails.getGender().getDescription(),
				userDetails.getEmail()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> save(@RequestBody RegistrationRequest user){
		return userService.saveUser(user);
	}
}
