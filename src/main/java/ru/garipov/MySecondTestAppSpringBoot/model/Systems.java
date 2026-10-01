package ru.garipov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {

	ERP("Enterprise Resource Planning"),
	CRM("Customer Relationship Management"),
	WMS("Warehouse Management System");

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
