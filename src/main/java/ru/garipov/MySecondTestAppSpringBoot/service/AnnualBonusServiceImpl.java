package ru.garipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.garipov.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

	@Override
	public double calculate(Positions positions, double salary, double bonus, int workDays) {
		return salary * bonus * getDays() * positions.getPositionCoefficient() / workDays;
	}

	private int getDays() {
		return Year.isLeap(Year.now().getValue()) ? 366 : 365;
	}
}
