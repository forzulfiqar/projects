package com.learning4.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning4.model.BankAccount;
import com.learning4.model.TransactionHistory;
import com.learning4.service.BankAccountManager;
import com.learning4.service.CountryManager;
import com.learning4.service.TransactionHistoryManager;
import com.learning4.service.UserManagerImpl;
import com.learning4.util.QueryConstants;
import com.learning4.util.SessionData;

@RestController
@Component
@RequestMapping("/transactions")
public class AccountControllerImpl implements AccountController {

	@Autowired
	private BankAccountManager bankAccountManager;
	
	@Autowired
	private TransactionHistoryManager transactionHistoryManager;
		
	@Autowired
	private SessionData sessionData;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountControllerImpl.class);
	
	@RequestMapping("/rest/getallaccounts")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public List<BankAccount> getAllAccounts() {

		logger.info("In getAllAccounts");

		List<BankAccount> accounts = this.bankAccountManager.getAll();

		logger.info("accounts list size: " + accounts.size());

		return accounts;
	}
	
	@RequestMapping("/rest/getallaccountsofuser")
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

	@RequestMapping("/rest/getaccountbyid/{id}")
	@GET
	//@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public BankAccount getAccountById(@PathVariable("id") long id) {
		logger.info("In getAccountById");
		logger.info("id: " + id);
		BankAccount bA = null;
		if (id > 0) {
			bA = bankAccountManager.getById(id);
			logger.info("Account Number: " + bA.getId());
		}
		return bA;
	}
	
	
	@RequestMapping("/rest/gettransactionbyid/{id}")
	@GET
	//@Produces({ MediaType.APPLICATION_JSON })
	@Override
	public TransactionHistory getTransactionById(@PathVariable("id") long id) {
		logger.info("In getTransactionById");
		logger.info("id: " + id);
		TransactionHistory tH = null;
		if (id > 0) {
			tH = transactionHistoryManager.getById(id);
			logger.info("Account Number: " + tH.getId());
		}
		return tH;
	}
		
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping("/rest/transfer")
	@Override
	public void transfer(@RequestBody TransactionHistory tH) {

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
	//@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping("/rest/createnewaccount")
	@Override
	public void createNewAccount(@RequestBody BankAccount bA) {

		logger.info("In createNewUser");

		bankAccountManager.create(bA);

	}

	
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	//@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping("/rest/getaccounttransactionsbetweendates")
	@Override	
	public List<TransactionHistory> findTransactionsBetweenDates(Long accountId, String fromtDate, String toDate) {
		
		logger.info("In findTransactionsBetweenDates");
		
		Map<String, Object> result = null;
		
		List<TransactionHistory> transactions = null;

		if(sessionData!=null && sessionData.getUser()!=null) {
			result = this.transactionHistoryManager.findTransactionsBetweenDates(accountId, fromtDate, toDate, null);
			if(result!=null && result.get(QueryConstants.RESULT_ENTITIES_LIST)!=null) {
				transactions = (List<TransactionHistory>)result.get(QueryConstants.RESULT_ENTITIES_LIST);
			}
		}

		logger.info("user accounts list size: " + transactions.size());

		return transactions;
	}
}
