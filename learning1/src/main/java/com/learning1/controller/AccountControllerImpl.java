package com.learning1.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learning1.model.BankAccount;
import com.learning1.model.TransactionHistory;
import com.learning1.service.BankAccountManager;
import com.learning1.service.CountryManager;
import com.learning1.service.UserManagerImpl;
import com.learning1.util.SessionData;

@Component
@Path("/transactions")
public class AccountControllerImpl implements AccountController {

	@Autowired
	private BankAccountManager bankAccountManager;
	
	@Autowired
	private SessionData sessionData;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountControllerImpl.class);
	
	@Path("/rest/getallaccounts")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<BankAccount> getAllAccounts() {

		logger.info("In getAllAccounts");

		List<BankAccount> accounts = this.bankAccountManager.getAll();

		logger.info("accounts list size: " + accounts.size());

		return accounts;
	}
	
	@Path("/rest/getallaccountsofuser")
	@GET
	//@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<BankAccount> getAllAccountsOfUser() {

		logger.info("In getAllAccountsOfUser");
		
		List<BankAccount> accounts = null;

		if(sessionData!=null && sessionData.getUser()!=null) {
			accounts = this.bankAccountManager.getAllAccountsOfUser(sessionData.getUser().getId());
		}

		logger.info("user accounts list size: " + accounts.size());

		return accounts;
	}

	@Path("/rest/gettransactionbyid/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public BankAccount getAccountById(@PathParam("id") long id) {
		logger.info("In getUserById");
		logger.info("id: " + id);
		BankAccount s = null;
		if (id > 0) {
			s = bankAccountManager.getById(id);
			logger.info("Account Number: " + s.getAccountNumber());
		}
		return s;
	}
	
	
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	@Path("/rest/transfer")
	@Override
	public void transfer(TransactionHistory tH) {

		logger.info("In transfer");
		if(sessionData!=null && sessionData.getUser()!=null) {
			logger.info("Logged in user in session: " + sessionData.getUser().toString());
			tH.setUser(sessionData.getUser());
			bankAccountManager.transfer(tH);
		} else {
			logger.error("Cannot transfer as no user is currently logged in");
		}

	}


	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	@Path("/rest/createnewaccount")
	@Override
	public void createNewAccount(BankAccount bA) {

		logger.info("In createNewUser");

		bankAccountManager.create(bA);

	}

}
