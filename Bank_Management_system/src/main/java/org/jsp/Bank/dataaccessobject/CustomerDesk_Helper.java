package org.jsp.Bank.dataaccessobject;

public class CustomerDesk_Helper {

	public static BankDAO customerHelpDesk() {
		BankDAO bankDAO = new BankDAOImplementation();
		return bankDAO;
		
	}
	
}
