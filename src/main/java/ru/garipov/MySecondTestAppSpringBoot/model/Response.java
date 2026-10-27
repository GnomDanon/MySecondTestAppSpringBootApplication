package ru.garipov.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

	/***
	 * Уникальный идентификатор сообщения
	 */
	private String uid;

	/***
	 * Уникальный идентификатор операции
	 */
	private String operationUid;

	/***
	 * Время создания сообщения
	 */
	private String systemTime;

	/***
	 * Позиция сотрудника
	 */
	private Positions positions;

	/***
	 * Зарплата сотрудника
	 */
	private Double salary;

	/***
	 * Бонус сотрудника
	 */
	private Double bonus;

	/***
	 * Количество отработанных дней
	 */
	private Integer workDays;

	/***
	 * Годовая премия
	 */
	private Double annualBonus;

	/***
	 * Код
	 */
	private Codes code;

	/***
	 * Код ошибки
	 */
	private ErrorCodes errorCode;

	/***
	 * Сообщение об ошибке
	 */
	private ErrorMessages errorMessage;

	@Override
	public String toString() {
		return '{' +
				"uid='" + uid + '\'' +
				", operationUid='" + operationUid + '\'' +
				", systemTime='" + systemTime + '\'' +
				", code='" + code + '\'' +
				", errorCode='" + errorCode + '\'' +
				", errorMessages='" + errorMessage + '\'' +
				'}';
	}
}
