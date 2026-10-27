package ru.garipov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.garipov.MySecondTestAppSpringBoot.model.Positions;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class QuarterlyBonusServiceImplTest {

	@Test
	void calculate() {
		// given
		Positions positions = Positions.TL;
		double bonus = 2.0;
		int workDays = 243;
		double salary = 100000.00;

		LocalDate today = LocalDate.now();
		LocalDate firstDayOfQuarter = today.with(IsoFields.DAY_OF_QUARTER, 1);
		LocalDate firstDayOfNextQuarter = firstDayOfQuarter.plusMonths(3);
		long currentQuarterDays = ChronoUnit.DAYS.between(firstDayOfQuarter, firstDayOfNextQuarter);

		// when
		double result = new QuarterlyBonusServiceImpl().calculate(positions, salary, bonus, workDays);

		// then
		double expected = salary * bonus * currentQuarterDays * positions.getPositionCoefficient() / workDays;

		assertThat(result).isEqualTo(expected);
	}
}