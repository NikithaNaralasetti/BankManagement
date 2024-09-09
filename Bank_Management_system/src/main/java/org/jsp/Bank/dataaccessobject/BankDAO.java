package org.jsp.Bank.dataaccessobject;

import org.jsp.Bank.model.Bank;

public interface BankDAO {

    void userRegistration(Bank bank);
	void credit(String accountNumber, String mobileNumber);
	void debit(String accountNumber, String password);
	void changingThePassword(String accountNumber, String password);
	void mobileToMobileTransation(String mobileNo);
	void checkBalance(String accountNumber, String password);
    
}
