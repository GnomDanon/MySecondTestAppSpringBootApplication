package ru.garipov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessages {

	EMPTY(""),
	VALIDATION("Ошибка валидации"),
	UNKNOWN("Произошла непредвиденная ошибка"),
	UNSUPPORTED("Неподдерживаемая ошибка");

	private final String description;

	ErrorMessages(String description) {
		this.description = description;
	}

	@JsonValue
	public String getName() {
		return description;
	}
}
