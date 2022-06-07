package com.github.marcoslsouza.config;

import java.util.Optional;

import com.github.marcoslsouza.exception.BusinessException;
import com.github.marcoslsouza.exception.ExceptionResolver;
import com.github.marcoslsouza.exception.FailedToDeliverMessage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControlExceptionHandler {

	@ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleException(Throwable eThrowable) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(Optional.ofNullable(eThrowable.getMessage()).orElse(eThrowable.toString()))
                .description(ExceptionResolver.getRootException(eThrowable))
                .build();

        HttpHeaders responseHttpHeaders = new HttpHeaders();

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHttpHeaders).body(ex.getOnlyBody());
    }
	
	@ExceptionHandler(FailedToDeliverMessage.class)
	public ResponseEntity<Object> handleException(FailedToDeliverMessage e) {

		FailedToDeliverMessage ex = FailedToDeliverMessage.builder()
				.code(e.getCode())
                .httpStatusCode(e.getHttpStatusCode())
                .message(e.getMessage())
                .description(ExceptionResolver.getRootException(e))
                .build();

        HttpHeaders responseHttpHeaders = new HttpHeaders();

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHttpHeaders).body(ex.getOnlyBody());
    }
}
