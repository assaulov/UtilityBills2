package ru.assaulov.utilitybills2.payload.respose;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private String login;
	private String fullName;
	private String gender;
	private String email;




}
