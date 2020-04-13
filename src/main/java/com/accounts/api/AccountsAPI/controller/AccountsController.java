package com.accounts.api.AccountsAPI.controller;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.accounts.api.AccountsAPI.exceptions.AccountNotFoundException;
import com.accounts.api.AccountsAPI.exceptions.model.ExceptionResponse;
import com.accounts.api.AccountsAPI.model.Account;
import com.accounts.api.AccountsAPI.repository.AccountRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/accounts")
@Api(value = "Accounts Operation Microservice.", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountsController {

	@Autowired
	AccountRepository accountRepository; 

	@ApiOperation(value = "Get Details of All Account.", notes = "Get All Accounts", response = Iterable.class, tags = {
			"Accounts Information" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrive All Accounts", response = Iterable.class),
			@ApiResponse(code = 400, message = "Bad request", response = ExceptionResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponse.class) })
	@GetMapping
	public Iterable<Account> accounts() {
		return accountRepository.findAll();
		
	}

	@ApiOperation(value = "Create An Account.", notes = "Create An Account", response = Account.class, tags = {
			"Creates a new Account." })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Create New Accounts", response = Account.class),
			@ApiResponse(code = 400, message = "Bad request", response = ExceptionResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponse.class) })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> createAccount(
			@ApiParam(value = "Account Information to be created.", required = true) @Valid @RequestBody Account account) {

		Account newAccount = accountRepository.save(account);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAccount.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@ApiOperation(value = "Get Details of an Account.", notes = "Get Account Details", response = Account.class, tags = {
			"Accounts Information" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retrive Account details by ID.", response = Account.class),
			@ApiResponse(code = 400, message = "Bad request", response = ExceptionResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponse.class) })
	@GetMapping(value = "/{id}")
	public Resource<Account> read(
			@ApiParam(value = "Account ID for which details to be fetched.", required = true) @PathVariable String id) {

		Account accountById = accountRepository.findById(id).get();
		
		// account details  not found
		if (accountById == null) {
			throw new AccountNotFoundException("Account-id :" + id);
		}
		Resource<Account> accountResource = new Resource<Account>(accountById);
		ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).accounts());
		accountResource.add(linkBuilder.withRel("all-accounts"));

		return accountResource;
	}

	@ApiOperation(value = "Update Details of an Account.", notes = "Update Account Details", response = Void.class, tags = {
			"Update Accounts Information" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Account Details updated.", response = Void.class),
			@ApiResponse(code = 400, message = "Bad request", response = ExceptionResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponse.class) })
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(
			@ApiParam(value = "Account Information to be updated.", required = true) @RequestBody Account account) {
		accountRepository.save(account);
	}

	@ApiOperation(value = "Delete Details of an Account by ID.", notes = "Delete Account Details", response = Void.class, tags = {
			"Delete Accounts Information on an Account" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Account Details Deleted.", response = Void.class),
			@ApiResponse(code = 400, message = "Bad request", response = ExceptionResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponse.class) })
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable String id) {
		accountRepository.deleteById(id);
	}

}
