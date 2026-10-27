package ru.garipov.MySecondTestAppSpringBoot.service;

import ru.garipov.MySecondTestAppSpringBoot.model.Positions;

public interface QuarterlyBonusService {

	double calculate(Positions positions, double salary, double bonus, int workDays);
}
