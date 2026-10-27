package ru.garipov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.garipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.garipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.garipov.MySecondTestAppSpringBoot.model.*;
import ru.garipov.MySecondTestAppSpringBoot.service.AnnualBonusService;
import ru.garipov.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.garipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.garipov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.garipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

	private final ValidationService validationService;
	private final ModifyResponseService modifySystemTimeResponseService;
	private final ModifyRequestService modifySystemNameRequestService;
	private final ModifyRequestService modifySourceRequestService;
	private final ModifyRequestService modifySystemTimeRequestService;
	private final AnnualBonusService annualBonusService;

	@PostMapping(value = "/feedback")
	public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
		modifySystemTimeRequestService.modify(request);

		log.info("request: {}", request);

		Response response = Response.builder()
				.uid(request.getUid())
				.operationUid(request.getOperationUid())
				.systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
				.positions(request.getPosition())
				.salary(request.getSalary())
				.bonus(request.getBonus())
				.workDays(request.getWorkDays())
				.annualBonus(annualBonusService.calculate(request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays()))
				.code(Codes.SUCCESS)
				.errorCode(ErrorCodes.EMPTY)
				.errorMessage(ErrorMessages.EMPTY)
				.build();

		log.info("response: {}", response);

		try {
			validationService.isValid(bindingResult);
			validationService.checkCode(request.getUid());
		} catch (ValidationFailedException e) {
			log.error(e.toString());
			response.setCode(Codes.FAILED);
			response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
			response.setErrorMessage(ErrorMessages.VALIDATION);
			log.info("response (validation exception): {}", response);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (UnsupportedCodeException e) {
			log.error(e.toString());
			response.setCode(Codes.FAILED);
			response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
			response.setErrorMessage(ErrorMessages.UNSUPPORTED);
			log.info("response (unsupported exception): {}", response);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error(e.toString());
			response.setCode(Codes.FAILED);
			response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
			response.setErrorMessage(ErrorMessages.UNKNOWN);
			log.info("response (unknown exception): {}", response);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response = modifySystemTimeResponseService.modify(response);
		modifySourceRequestService.modify(request);
		modifySystemNameRequestService.modify(request);
		log.info("response (modified): {}", response);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
