package com.accounts.api.AccountsAPI.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accounts.api.AccountsAPI.exceptions.model.ExceptionResponse;
import com.accounts.api.AccountsAPI.model.Proposition;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/propositions")
@Api(value = "Propositions Operation Microservice.", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PropositionsController {

	@ApiOperation(value = "Get Details of All Propositions.", notes = "Get All Propositions", response = Iterable.class, tags = {
			"Propositions Information" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrive All Propositions", response = Iterable.class),
			@ApiResponse(code = 400, message = "Bad request", response = ExceptionResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponse.class) })
	@GetMapping
	public Iterable<Proposition> propositions() {
		return getActivePropositionsFromUrls();
	}

	/**
	 * Method to find Active Propositions
	 *
	 * @param urls
	 * @param app
	 * @return List of active propositions
	 */
	private static List<Proposition> getActivePropositionsFromUrls() {
		final List<Proposition> propositions = new ArrayList<Proposition>();

		ObjectMapper objectMapper = new ObjectMapper();

		File resource = null;
		File resource1 = null;

		try {
			resource = new ClassPathResource("proposition1.txt").getFile();
			resource1 = new ClassPathResource("proposition2.txt").getFile();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		String propJson = null, prop2Json = null;
		Proposition proposition = null, proposition2 = null;
		try {
			propJson = new String(Files.readAllBytes(resource.toPath()));
			prop2Json = new String(Files.readAllBytes(resource1.toPath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			proposition = objectMapper.readValue(propJson, Proposition.class);
			proposition2 = objectMapper.readValue(prop2Json, Proposition.class);

			System.out.println("Prop name = " + proposition.getName());
			System.out.println("Prop 2 name = " + proposition2.getName());

		} catch (IOException e) {
			e.printStackTrace();
		}

		propositions.add(proposition);
		propositions.add(proposition2);

		return propositions;

	}

}