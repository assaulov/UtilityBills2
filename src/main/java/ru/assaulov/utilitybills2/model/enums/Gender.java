package ru.assaulov.utilitybills2.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
	MALE("Мужской", "М"),
	FEMALE("Женский", "Ж"),
	NONE("Пол не выбран", "");
	private final String description;
	private final String shortDescription;
}
