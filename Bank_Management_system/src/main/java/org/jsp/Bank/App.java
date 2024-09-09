package org.jsp.Bank;

import java.util.Random;
import java.util.Scanner;

import org.jsp.Bank.dataaccessobject.BankDAO;
import org.jsp.Bank.dataaccessobject.CustomerDesk_Helper;
import org.jsp.Bank.model.Bank;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BankDAO dao = CustomerDesk_Helper.customerHelpDesk();
		boolean status = true;
		while (status) {
			System.out.println("Welcome to the Bank");
			System.out.println("Select the option from the below service by pressing NUMBER \n"
					+ "1. User Registration \n " + "2. Credit \n " + "3. Debit \n " + "4. Changing The Password \n "
					+ "5. Mobile To Mobile Transation \n" + " 6. check Balance ");
			int response = sc.nextInt();
			System.out.println("Your Response is " + response);

			switch (response) {
			case 1:
				System.out.println("Enter the data as instructed");
				System.out.println("enter your First Name :");
				String firstName = sc.next();
				System.out.println("enter your Last Name :");
				String lastName = sc.next();
				System.out.println("enter your Email Id :");
				String emailId = sc.next();
				System.out.println("enter your password :");
				boolean stauts = true;
				while (status) {
					String password = sc.next();
					if (passwordChecking(password)) {
						System.out.println("enter your Mobile Number :");
						while (true) {
							String mobileNumber = sc.next();
							if (mobileNumberChecking(mobileNumber)) {
								System.out.println("enter your Address :");
								String address = sc.next();
								double amount = 0.0;
								String accountNo = generateAccountNumber();
								System.out.println(accountNo);
								Bank bank = new Bank(0, firstName, lastName, emailId, password, mobileNumber, address,
										amount, accountNo);
								dao.userRegistration(bank);
								status = false;
								break;
							} else {
								System.out.println("re-enter mobile number(Ten Digits) : ");
							}
						}
					} else {
						System.out.println("re-enter password as specified : ");
					}
				}
				break;
			case 2:
				System.out.println("your going to credit the amount to the others");
				System.out.println("Enter their Account Number :");
				while (true) {
					try {
						String accountNumber = sc.next();
						if (accountNumber.length() == 11) {
							System.out.println("Enter User Mobile Number :");
							while (true) {
								try {
									String mobileNo = sc.next();
									if (mobileNumberChecking(mobileNo)) {
										dao.credit(accountNumber, mobileNo);
										break;
									} else {
										throw new IllegalArgumentException();
									}
								} catch (Exception e) {
									System.out.println("Invalid mobile number");
									System.out.println("re-enter user mobile number :");
								}
							}
							break;

						} else {
							throw new IllegalArgumentException();
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("invalid Account Number");
						System.out.println("re-enter user Account number(which is eleven digits) : ");
					}
				}

				break;
			case 3:
				System.out.println("your going to credit the amount to the others");
				System.out.println("Enter their Account Number :");
				while (true) {
					try {
						String accountNumber = sc.next();
						if (accountNumber.length() == 11) {
							System.out.println("Enter User Password :");
							while (true) {
								try {
									String password = sc.next();
									if (passwordChecking(password)) {
										dao.debit(accountNumber, password);
										break;
									} else {
										throw new IllegalArgumentException();
									}
								} catch (Exception e) {
									System.out.println("Invalid password");
									System.out.println("re-enter user password :");
								}
							}
							break;

						} else {
							throw new IllegalArgumentException();
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("invalid Account Number");
						System.out.println("re-enter user Account number(which is eleven digits) : ");
					}
				}

				break;
			case 4:
				System.out.println("You have choose Change password....!");
				System.out.println("Enter your Account number :");
				while (true) {
					try {
						String accountNo = sc.next();
						if (accountNo.length() == 11) {
							System.out.println("enter your password :");
							while (true) {
								try {
									String password = sc.next();
									if (passwordChecking(password)) {
										dao.changingThePassword(accountNo, password);
										break;
									} else {
										throw new IllegalArgumentException();
									}
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println("invalid password");
									System.out.println("Re - Enter the password : ");
								}
							}
							break;
						} else {
							throw new IllegalArgumentException();
						}
					} catch (Exception e) {
						System.out.println("Invalid Account number");
						System.out.print("\n Enter the Account number which is 11 digits : ");
					}
				}
				break;
			case 5:
				System.out.print("Enter yuor Mobile Number :");
				while (true) {
					try {
						String userMobileNumber = sc.next();
						if (userMobileNumber.length() == 10) {
							dao.mobileToMobileTransation(userMobileNumber);
							break;
						} else {
							throw new IllegalArgumentException();
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("Enter valid Mobile Number(Which contains 10 digits) :");

					}
				}
				break;
			case 6:
				System.out.println("enter your Account Number : ");
				while (true) {
					try {
						String accountNo = sc.next();
						if (accountNo.length() == 11) {
							System.out.println("Enter Your Password :");
							while (true) {
								try {
									String password = sc.next();
									if (passwordChecking(password)) {
										dao.checkBalance(accountNo, password);
										break;
									} else {
										throw new IllegalArgumentException();
									}
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println("invalid password");
									System.out.println("re-enter your password : ");
								}
							}
							break;
						} else {
							throw new IllegalArgumentException();
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("invalid Account Number");
						System.out.println("re-enter user Account number(which is eleven digits) : ");
					}
				}
				break;
			default:
				break;
			}
			System.out.println("enter 'yes' to another operation (or) 'no' to stop  ");
			String condition = sc.next();
			if (condition.equalsIgnoreCase("yes")) {
				status = true;
			} else {
				status = false;
			}
		}

	}

	private static String generateAccountNumber() {
		Random r = new Random();
		long acc = r.nextLong(10000000000l);
		String accountNumber = "" + acc;
		if (acc < 10000000000l) {
			return Long.toString(acc + 10000000000L);
		}
		return accountNumber;
	}

	private static boolean passwordChecking(String password) {
		char[] passwordArray = password.toCharArray();
		int numberCount = 0;
		int characterCount = 0;
		int specialCharacterCount = 0;
		for (int i = 0; i < passwordArray.length; i++) {
			if (Character.isAlphabetic(passwordArray[i])) {
				characterCount++;
			} else if (Character.isDigit(passwordArray[i])) {
				numberCount++;
			} else {
				specialCharacterCount++;
			}
		}
		if ((numberCount >= 1 && specialCharacterCount >= 1 && characterCount >= 1)
				&& (password.length() >= 8 && password.length() <= 12)) {
			return true;
		}
		return false;
	}

	private static boolean mobileNumberChecking(String mobileNo) {
		char[] moblieNoArray = mobileNo.toCharArray();
		int numberCount = 0;
		for (int i = 0; i < moblieNoArray.length; i++) {
			if (Character.isDigit(moblieNoArray[i])) {
				numberCount++;
			}
		}
		if (numberCount == 10 && mobileNo.length() == numberCount)
			return true;
		else
			return false;
	}
}
