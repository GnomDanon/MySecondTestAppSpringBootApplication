package ru.garipov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {

	ERP("Enterprise Resource Planning"),
	CRM("Customer Relationship Management"),
	WMS("Warehouse Management System"),

	SYSTEM1("System 1"),
	SYSTEM2("System 2");

	private String name;

	Systems(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
