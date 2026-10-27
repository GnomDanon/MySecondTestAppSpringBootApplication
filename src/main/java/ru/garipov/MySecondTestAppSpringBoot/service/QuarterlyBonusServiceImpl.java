package ru.garipov.MySecondTestAppSpringBoot.service;

import ru.garipov.MySecondTestAppSpringBoot.model.Positions;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;

public class QuarterlyBonusServiceImpl implements AnnualBonusService {

	@Override
	public double calculate(Positions positions, double salary, double bonus, int workDays) {
		return positions.isManager() ? salary * bonus * getDays() * positions.getPositionCoefficient() / workDays : 0;
	}

	private long getDays() {
		LocalDate today = LocalDate.now();

		LocalDate firstDayOfQuarter = today.with(IsoFields.DAY_OF_QUARTER, 1);
		LocalDate firstDayOfNextQuarter = firstDayOfQuarter.plusMonths(3);

		return ChronoUnit.DAYS.between(firstDayOfQuarter, firstDayOfNextQuarter);
	}
}
