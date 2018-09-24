package com.capgemini.bankapp.service.impl;

public class LowBalanceException extends Exception {
	public LowBalanceException(String message) {
		super (message);
	}

}
