package ru.assaulov.utilitybills2.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
	MALE("Мужской", "М"),
	FEMALE("Женский", "Ж");

	private final String description;
	private final String shortDescription;
}
