package ru.assaulov.utilitybills2.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.assaulov.utilitybills2.payload.respose.MessageResponse;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImp;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	private final AuthenticationManager authenticationManager;
	private final UserServiceImp userService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserServiceImp userService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User userDetails = (User) authentication.getPrincipal();
		return ResponseEntity.ok(new MessageResponse(userDetails.getLogin() + " login successfully!"));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> save(@RequestBody RegistrationRequest user){
		LOGGER.info(user.toString());
		return userService.saveUser(user);
	}
}
