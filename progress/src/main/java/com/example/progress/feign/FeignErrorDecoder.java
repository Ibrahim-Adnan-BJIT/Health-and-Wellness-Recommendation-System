package com.example.progress.feign;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import feign.Response;

import feign.codec.ErrorDecoder;

import com.example.progress.exception.FeignException;
import com.example.progress.exception.ErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FeignErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		try {
			String responseBody = StreamUtils.copyToString(response.body().asInputStream(),
					java.nio.charset.StandardCharsets.UTF_8);

			ObjectMapper objectMapper = new ObjectMapper();

			ErrorHandler customResponse = objectMapper.readValue(responseBody, ErrorHandler.class);

			String errorMessage = customResponse.getMessage();
			Date timestamp = customResponse.getTimestamp();
			String details = customResponse.getDetails();

			HttpStatus httpStatus = HttpStatus.valueOf(response.status());

			return new FeignException(timestamp, errorMessage, httpStatus);
		} catch (IOException e) {
			return errorDecoder.decode(methodKey, response);
		}
	}

}
