package com.capgemini.bankapp.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.capgemini.bankapp.entities.BankAccount;
import com.capgemini.bankapp.repository.BankAccountRepository;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * private HashSet<BankAccount> bankAccounts;
	 * 
	 * public BankAccountRepositoryImpl() { super(); bankAccounts = new HashSet<>();
	 * bankAccounts.add(new BankAccount(1234, "John", "Savings", 30000));
	 * bankAccounts.add(new BankAccount(1235, "alex", "Current", 25000));
	 * bankAccounts.add(new BankAccount(1236, "bob", "Savings", 40000));
	 * 
	 * }
	 */

	@Override
	public double getBalance(long accountId) {
		double balance = jdbcTemplate.queryForObject("SELECT accountBalance FROM bank WHERE accountId=?",
				new Object[] { accountId }, Double.class);
		return balance;
	}

	@Override
	public boolean updateBalance(long accountId, double newBalance) {
		int count = jdbcTemplate.update("UPDATE bank SET accountBalance=? WHERE accountId=?",
				new Object[] {newBalance,accountId });

		return count != 0;
	}

	@Override
	public boolean addBankAccount(BankAccount account) {
		int count = jdbcTemplate.update("INSERT INTO bank VALUES(?,?,?,?)", new Object[] { account.getAccountId(),
				account.getAccountHolderName(), account.getAccountType(), account.getAccountBalance() });
		
		return count != 0;
	}

	@Override
	public BankAccount findBankAccountById(long accountId) {

		return jdbcTemplate.queryForObject("SELECT * FROM bank WHERE accountid=?",new Object[] {accountId},new BankAccountRowMapper());
	}

	@Override
	public List<BankAccount> findAllbankAccounts() {
		return jdbcTemplate.query("SELECT *FROM bank",new Object[] {},new BankAccountRowMapper());	
		
	}

	@Override
	public BankAccount updateBankAccount(BankAccount account) {
jdbcTemplate.update("UPDATE bank SET accountHolderName=?,accountType=? WHERE accountid=?" , new Object[] {account.getAccountHolderName(),account.getAccountType(),account.getAccountId()});
		return account;
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		int count=jdbcTemplate.update("DELETE FROM bank WHERE accountId=?", new Object[] {accountId});
		
		return count!=0;
	}
	private class BankAccountRowMapper implements RowMapper<BankAccount>{

		@Override
		public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
BankAccount account=new BankAccount();
account.setAccountId(rs.getLong(1));
account.setAccountHolderName(rs.getString(2));
account.setAccountType(rs.getString(3));
account.setAccountBalance(rs.getDouble(4));
			return account;
		}
		
	}
}
