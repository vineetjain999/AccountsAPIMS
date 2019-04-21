package com.accounts.api.AccountsAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.accounts.api.AccountsAPI.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {


}
