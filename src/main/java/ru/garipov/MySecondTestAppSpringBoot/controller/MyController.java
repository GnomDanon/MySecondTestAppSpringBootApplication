package ru.garipov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.garipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.garipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.garipov.MySecondTestAppSpringBoot.model.*;
import ru.garipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.garipov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.garipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

	private final ValidationService validationService;
	private final ModifyResponseService modifyResponseService;

	@Autowired
	public MyController(ValidationService validationService,
						@Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
		this.validationService = validationService;
		this.modifyResponseService = modifyResponseService;
	}

	@PostMapping(value = "/feedback")
	public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
		log.info("request: {}", request);

		Response response = Response.builder()
				.uid(request.getUid())
				.operationUid(request.getOperationUid())
				.systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
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
		}catch (Exception e) {
			log.error(e.toString());
			response.setCode(Codes.FAILED);
			response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
			response.setErrorMessage(ErrorMessages.UNKNOWN);
			log.info("response (unknown exception): {}", response);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response = modifyResponseService.modify(response);
		log.info("response (modified): {}", response);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
