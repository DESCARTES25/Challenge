package com.challenge.dijsktra.app.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicateCityException.class)
	public ResponseEntity<Object> handleDuplicateCityException(DuplicateCityException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<Object> handleCityNotFoundException(CityNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoCitiesFoundException.class)
	public ResponseEntity<Object> handleNoCitiesFoundException(NoCitiesFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ItineraryNotFoundException.class)
	public ResponseEntity<Object> handleItineraryNotFoundException(ItineraryNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoItinerariesFoundException.class)
	public ResponseEntity<Object> handleNoItinerariesFoundException(NoItinerariesFoundException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ItinerariesWithZeroTimeException.class)
	public ResponseEntity<Object> handleItinerariesWithZeroTimeException(ItinerariesWithZeroTimeException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(ItinerariesWithNegativeTimeException.class)
	public ResponseEntity<Object> handleItinerariesWithNegativeTimeException(ItinerariesWithNegativeTimeException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(RepeatedItinerariesFoundException.class)
	public ResponseEntity<Object> handleRepeatedItinerariesFoundException(RepeatedItinerariesFoundException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(NoOriginOrDestinationForCityFoundException.class)
	public ResponseEntity<Object> handleNoDestinationForCityFoundException(NoOriginOrDestinationForCityFoundException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	/*
	 * @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(
	 * MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
	 * WebRequest request) {
	 * 
	 * Map<String, Object> body = new LinkedHashMap<>(); body.put("timestamp",
	 * LocalDate.now()); body.put("status", status.value());
	 * 
	 * List<String> errors = ex.getBindingResult() .getFieldErrors() .stream()
	 * .map(x -> x.getDefaultMessage()) .collect(Collectors.toList());
	 * 
	 * body.put("errors", errors);
	 * 
	 * return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); }
	 */
}