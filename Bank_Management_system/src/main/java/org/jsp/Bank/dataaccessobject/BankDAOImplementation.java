package org.jsp.Bank.dataaccessobject;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsp.Bank.model.Bank;

import com.mysql.cj.jdbc.Driver;

public class BankDAOImplementation implements BankDAO {

	Scanner sc = new Scanner(System.in);
	String url = "jdbc:mysql://localhost:3306/s32?user=root&password=root";

	public void userRegistration(Bank bank) {
		// TODO Auto-generated method stub
		String insert = "insert into bank(firstname, lastname, mobileNumber, emailid, password, address, amount, accountnumber) values(?,?,?,?,?,?,?,?)";
		Connection connection;
		try {
			connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, bank.getFirstName());
			preparedStatement.setString(2, bank.getLastName());
			preparedStatement.setString(3, bank.getMobileNumber());
			preparedStatement.setString(4, bank.getEmailId());
			preparedStatement.setString(5, bank.getPassword());
			preparedStatement.setString(6, bank.getAddress());
			preparedStatement.setDouble(7, bank.getAmount());
			preparedStatement.setString(8, bank.getAccountNumber());
			int result = preparedStatement.executeUpdate();
			if (result != 0) {
				System.out.println("registration Successful");
			} else {
				System.out.println("registration Not Successful");
			}
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		}
	}

	public void credit(String accountNumber, String mobileNumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from bank where accountnumber=? and mobileNumber =?");
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, mobileNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("enter the Amount : ");
				while (true) {
					try {
						double creditAmount = sc.nextDouble();
						if (creditAmount > 0) {
							double userAmount = resultSet.getDouble("amount");
							double totalAmount = userAmount + creditAmount;
							PreparedStatement preparedStatementUpdate = connection.prepareStatement(
									"update bank set amount =? where accountnumber =? and  mobileNumber = ?");
							preparedStatementUpdate.setDouble(1, totalAmount);
							preparedStatementUpdate.setString(2, accountNumber);
							preparedStatementUpdate.setString(3, mobileNumber);
							int result = preparedStatementUpdate.executeUpdate();
							if (result != 0) {
								System.out.println("account updated...");
							}
							break;
						} else {
							throw new IllegalArgumentException();
						}
					} catch (IllegalArgumentException e) {
						// TODO: handle exception
						System.out.println("Invalid Amount");
						System.out.print("Enter the amount greater than zero : ");
					}
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void debit(String accountNumber, String password) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from bank_management_system_crud where Accountnumber=? and password = ?");
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("enter the Amount : ");
				while (true) {
					try {
						double debitAmount = sc.nextDouble();
						if (debitAmount > 0) {
							double accountBalance = resultSet.getDouble("amount");
							if (accountBalance >= debitAmount) {
								double remainingAmount = accountBalance - debitAmount;
								String update = "update bank set Amount = ? where Accountnumber = ? and Password = ? ";
								PreparedStatement preparedStatement2 = connection.prepareStatement(update);
								preparedStatement2.setDouble(1, remainingAmount);
								preparedStatement2.setString(2, accountNumber);
								preparedStatement2.setString(3, password);
								int result = preparedStatement2.executeUpdate();
								if (result != 0) {
									System.out.println("Amount Withdrawn Successful");
								} else {
									System.out.println("server Issue");
								}
								break;
							} else {
								System.out.println("Insufficent balance");
							}
						} else {
							System.out.println("invalid Amount");
							System.out.print("\n enter the amount greater than zero");
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			} else {
				System.out.println("Data not found....");
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	public void changingThePassword(String accountNumber, String password) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from bank where accountnumber =? and password = ?");
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("Enter your New Password : ");
				while (true) {
					String newPassword = sc.next();
					if ((newPassword.length() >= 8 && newPassword.length() <= 12) && !newPassword.equals(password)) {
						System.out.println("confirm password :");
						String confirmPassword = sc.next();
						if (newPassword.equals(confirmPassword)) {
							PreparedStatement preparedStatementUpdate = connection.prepareStatement(
									"update bank set password = ? where accountnumber = ?");
							preparedStatementUpdate.setString(1, newPassword);
							preparedStatementUpdate.setString(2, accountNumber);
							int result = preparedStatementUpdate.executeUpdate();
							if (result != 0) {
								System.out.println("password changed successfully");
								break;
							} else {
								System.out.println("issue occured!");
							}
						} else {
							System.out.println("invalid confirm password entered");
							System.out.println("enter your New Password again : ");
						}
					} else {
						System.out.println("invalid password entered");
						System.out.print("enter your newpassword : ");
					}
				}
			} else {
				System.out.println("invalid Account Number or invalid password");
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	public void mobileToMobileTransation(String mobileNo) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from bank where MobileNumber = ? ");
			preparedStatement.setString(1, mobileNo);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				System.out.print("enter the receiver's mobile number : ");
				boolean receiversMobileNumberStatus = true;
				while (receiversMobileNumberStatus) {
					String receiversMobileNumber = sc.next();
					if (receiversMobileNumber.length() == 10) {
						receiversMobileNumberStatus = false;
						PreparedStatement preparedStatementRec = connection
								.prepareStatement("select * from bank where MobileNumber = ?");
						preparedStatementRec.setString(1, receiversMobileNumber);
						ResultSet resultSetRec = preparedStatementRec.executeQuery();
						if (resultSetRec.next()) {
							System.out.print("enter amount : ");
							boolean receiverAmountStatus = true;
							while (receiverAmountStatus) {
								double receiversAmount = sc.nextDouble();
								if (receiversAmount > 0) {
									receiverAmountStatus = false;
									double senderDBAmount = resultSet.getDouble("Amount");
									double recDBAmount = resultSetRec.getDouble("Amount");
									if (senderDBAmount >= receiversAmount) {
										double totalRecAmount = recDBAmount + receiversAmount;
										double totalSenderAmount = senderDBAmount - receiversAmount;
										PreparedStatement preparedStatementUpdaterec = connection
												.prepareStatement("update bank set amount =? where mobileNumber = ?");
										preparedStatementUpdaterec.setDouble(1, totalRecAmount);
										preparedStatementUpdaterec.setString(2, receiversMobileNumber);
										preparedStatementUpdaterec.executeUpdate();
										PreparedStatement preparedStatementUpdateuser = connection
												.prepareStatement("update bank set amount =? where mobileNumber = ?");
										preparedStatementUpdateuser.setDouble(1, totalSenderAmount);
										preparedStatementUpdateuser.setString(2, mobileNo);
										int userResult = preparedStatementUpdateuser.executeUpdate();
										if (userResult != 0) {
											try {
												for (int i = 0; i < 3; i++) {
													Thread.sleep(1000);
													System.out.print('.');
												}

											} catch (Exception e) {

											}
											System.out.println("sent successfully");
										} else {
											System.out.println("server issue");
										}
									} else {
										System.out.println("Insufficent Balance");

										System.out.println("enter password to display of the balance : ");
										String senderPassword = sc.next();
										if (senderPassword.equals(resultSet.getString("password"))) {
											System.out.println("your account balance is " + senderDBAmount + "/-");
										}
										receiversMobileNumberStatus = true;
										System.out.print("enter reciver's mobile number : ");
									}
								} else {
									System.out.println("enter valid amount : ");
								}
							}
						} else {
							System.out.println("enter valid receiver mobile number : ");
							System.out.println("refer your receiver to the teca52 Bank ");
						}
					} else {
						System.out.print("enter valid 10 digit mobile number : ");
						receiversMobileNumberStatus = true;
					}
				}
			} else {
				System.out.println("Invalid Mobile Number");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkBalance(String accountNumber, String password) {
		// TODO Auto-generated method stub
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from bank where Accountnumber = ? and Password = ?");
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				double amountBalance = resultSet.getDouble("amount");
				System.out.println("your account balance is " + amountBalance + "/-");
			} else {
				System.out.println("invalid data");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
