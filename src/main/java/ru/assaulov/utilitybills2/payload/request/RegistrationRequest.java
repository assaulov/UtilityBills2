package ru.assaulov.utilitybills2.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;

}
