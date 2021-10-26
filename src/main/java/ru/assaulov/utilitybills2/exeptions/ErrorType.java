package ru.assaulov.utilitybills2.exeptions;

public enum ErrorType {
	ENTITY_NOT_FOUND("Entity not found by id: %s"),
	ENTITY_NOT_SAVED("Entity not saved: %s"),
	ENTITY_NOT_FOUND_BY_DATE("Entity not found by date: %s"),
	ENTITY_NOT_FOUND_BY_PERIOD("Entity not found by period: %s"),
	ENTITY_NOT_FOUND_BY_LOGIN("Entity not found by login: %s"),
	ENTITY_NOT_UPDATED("Entity not updated: %s");

	private final String description;

	ErrorType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
