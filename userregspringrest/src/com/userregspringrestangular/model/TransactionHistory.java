package com.userregspringrestangular.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "Transaction_Id_Seq_Gen", sequenceName = "transaction_id_seq")
	@GeneratedValue(generator = "Transaction_Id_Seq_Gen")
	private long id;

	@Column(name = "transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@Column(name = "type")
	private long type;

	@Column(name = "amount")
	private double amount;

	/*
	@Column(name = "debit_account_id")
	private long debitAccountId;

	@Column(name = "credit_account_id")
	private long creditAccountId;
	*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "debit_account_id")
	private BankAccount debitAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_account_id")
	private BankAccount creditAccount;


	@Column(name = "status")
	private long status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	/*
	public long getDebitAccountId() {
		return debitAccountId;
	}

	public void setDebitAccountId(long debitAccountId) {
		this.debitAccountId = debitAccountId;
	}

	public long getCreditAccountId() {
		return creditAccountId;
	}

	public void setCreditAccountId(long creditAccountId) {
		this.creditAccountId = creditAccountId;
	}
	*/
		
	public BankAccount getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(BankAccount debitAccount) {
		this.debitAccount = debitAccount;
	}

	public BankAccount getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(BankAccount creditAccount) {
		this.creditAccount = creditAccount;
	}

	public long getStatus() {
		return status;
	}
	
	public void setStatus(long status) {
		this.status = status;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
