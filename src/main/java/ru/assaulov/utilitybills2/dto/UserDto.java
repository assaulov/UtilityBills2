package ru.assaulov.utilitybills2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
	private String login;
	private String fullName;
	private String gender;
	private String email;

}
