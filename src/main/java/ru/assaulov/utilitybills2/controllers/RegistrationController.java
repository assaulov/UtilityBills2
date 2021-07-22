package ru.assaulov.utilitybills2.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("registration")
public class RegistrationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

	private final UserServiceImpl userService;

	@GetMapping
	public List<User> all(){
		return userService.findFllUsers();
	}

	@PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User save(@RequestBody User user){

		LOGGER.info(user.getPassword() + " " + user.getLogin());
		return userService.saveUser(user);
	}
}
