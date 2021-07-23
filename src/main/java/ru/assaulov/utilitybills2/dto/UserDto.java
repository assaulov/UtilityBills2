package ru.assaulov.utilitybills2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
public class UserDto {
	private String login;
	private String fullName;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;

	public UserDto(String login, String fullName, String gender, String email) {
		this.login = login;
		this.fullName = fullName;
		this.gender = gender;
		this.email = email;
	}

	public UserDto(String login, String firstName, String lastName, String gender, String email) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
	}
}
