package ru.assaulov.utilitybills2.payload.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class RegistrationRequest {
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;

}
