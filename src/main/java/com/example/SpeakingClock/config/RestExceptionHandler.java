package com.example.SpeakingClock.config;

import com.example.SpeakingClock.models.common.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/** The type Rest exception handler. */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/** The Object mapper. */
	ObjectMapper objectMapper;

	/**
	 * Instantiates a new Rest exception handler.
	 * @param mapperBuilder the mapper builder
	 */
	public RestExceptionHandler(Jackson2ObjectMapperBuilder mapperBuilder) {
		super();
		this.objectMapper = mapperBuilder.build();
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		GenericResponse response = GenericResponse.builder().message(error)
				.statusCode(HttpStatus.BAD_REQUEST.value()).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle all exceptions response entity.
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		String requestDetails = getRequestDetails(request);
		log.error("Unhandled exception at {}. Reason: {}", requestDetails, ex.getMessage(), ex);
		GenericResponse response = GenericResponse.builder().message(ex.getMessage())
				.statusCode(HttpStatus.BAD_REQUEST.value()).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	private String getRequestDetails(WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		return servletWebRequest.getRequest().getRequestURI();
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		GenericResponse response = GenericResponse.builder().message(details.toString())
				.statusCode(HttpStatus.BAD_REQUEST.value()).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
