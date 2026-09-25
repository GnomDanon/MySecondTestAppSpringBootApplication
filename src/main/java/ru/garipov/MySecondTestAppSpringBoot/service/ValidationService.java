package ru.garipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.garipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.garipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Service
public interface ValidationService {

	void isValid(BindingResult bindingResult) throws ValidationFailedException;

	void checkCode(String code) throws UnsupportedCodeException;
}
