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
import com.accounts.api.AccountsAPI.model.Account;
import com.accounts.api.AccountsAPI.repository.AccountRepository;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@Autowired
	AccountRepository accountRepository;

	@GetMapping
	public Iterable<Account> accounts() {
		return accountRepository.findAll();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {

		Account newAccount = accountRepository.save(account);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newAccount.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping(value = "/{id}")
	public Resource<Account> read(@PathVariable String id) {
		
		Account accountById = accountRepository.findById(id).get();
		if(accountById == null) {	
			throw new AccountNotFoundException("Account-id :"+ id);
		}
		
		Resource<Account> accountResource = new Resource<Account>(accountById);
		ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).accounts());
		accountResource.add(linkBuilder.withRel("all-accounts"));
		
		return accountResource;
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody Account account) {
		accountRepository.save(account);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable String id) {
		accountRepository.deleteById(id);
	}

}
