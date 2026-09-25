package ru.garipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.garipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.garipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Service
public class RequestValidationService implements ValidationService{

	@Override
	public void isValid(BindingResult bindingResult) throws ValidationFailedException {
		if (bindingResult.hasErrors()) {
			throw new ValidationFailedException(bindingResult.getFieldError().toString());
		}
	}

	@Override
	public void checkCode(String code) throws UnsupportedCodeException {
		if (code.equals("123")) {
			throw new UnsupportedCodeException("Unsupported code");
		}
	}
}
