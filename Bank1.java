package com.bank.jdbc;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

public class Bank1 {

	public void createAccounts(int accountNo, String name) {
		double initalbalance = 0.0;
		Account account = new Account();
		account.setAccountNumber(accountNo);
		account.setName(name);
		account.setBalance(initalbalance);
		String SQL1 = "INSERT INTO bank_details(accountNo, name,balance)VALUES(?,?,?)";

		String SQL = "SELECT *FROM bank_details WHERE accountno=?";
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement to_check_Accno = connection.prepareStatement(SQL);
			to_check_Accno.setInt(1, accountNo);

			ResultSet result = to_check_Accno.executeQuery();

			if (!result.next()) {

				PreparedStatement ps = connection.prepareStatement(SQL1);
				{
					ps.setInt(1, account.getAccountNumber());
					ps.setString(2, account.getName());
					ps.setDouble(3, account.getBalance());
					int executeUpdate = ps.executeUpdate();

					if (executeUpdate == 1) {
						System.out.println("Account is created ::" + "\nAccount Number :: " + accountNo
								+ "\nAccount Name :: " + name + "\nBalance ::" + initalbalance);

					}
				}

			} else {
				System.out.println("Account Already registered with :: " + "\nAccount Number :: " + result.getInt(1)
						+ "\nAccount Name :: " + result.getString(2));
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Some thing went wrong..");
		}
	}

	/*
	 * public void createAccount(int accountNo, String name) { double initalbalance
	 * = 0.0; Account account = new Account(); account.setAccountNumber(accountNo);
	 * account.setName(name); account.setBalance(initalbalance);
	 * 
	 * String SQL =
	 * "INSERT INTO bank_details(accountNo, name,balance)VALUES(?,?,?)"; try
	 * (Connection connection = DBConnection.getConnection(); PreparedStatement ps =
	 * connection.prepareStatement(SQL)) { ps.setInt(1, account.getAccountNumber());
	 * ps.setString(2, account.getName()); ps.setDouble(3, account.getBalance());
	 * int executeUpdate = ps.executeUpdate();
	 * 
	 * if (executeUpdate == 1) { System.out.println("Account is created.."); } }
	 * catch (Exception e) { e.printStackTrace();
	 * System.out.println("Account is Not created.."); } }
	 */

	public void deposit_By_AccountNo(Integer accno, double amount) {
		String SQL1 = "UPDATE bank_details set balance=balance+? WHERE accountno=?;";
		String SQL = "SELECT *FROM bank_details WHERE accountno=?";
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement to_check_Accno = connection.prepareStatement(SQL);
			to_check_Accno.setInt(1, accno);

			ResultSet result = to_check_Accno.executeQuery();
			if (result.next()) {

				double presentbalance = result.getDouble(3);
				String name = result.getString(2);
				
				PreparedStatement ps = connection.prepareStatement(SQL1);
				{
					ps.setDouble(1, amount);
					ps.setInt(2, accno);

					int executeUpdate = ps.executeUpdate();

					if (executeUpdate == 1) {
						System.out.println("deposit Done.." + "\nAccount Number ::" + accno + "\nAccount Name :: "
								+ name + "\nPresent Amount ::" + presentbalance + "\nDeposite Amount ::" + amount
								+ "\nTotal Amount ::" + (presentbalance + amount));

					} else {

						System.out.println("deposit Not Done.." + "\nAccount Number ::" + accno + "\nAccount Name :: "
								+ name + "\nTotal Amount ::" + (presentbalance + amount));

					}
				}

			} else {
				System.out.println("Account Number Notfound..");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Some thing went wrong..");
		}
	}

	/*
	 * public void deposit(double amount, Integer accno) {
	 * 
	 * String SQL = "UPDATE bank_details set balance=balance+? WHERE accountno=?;";
	 * try (Connection connection = DBConnection.getConnection(); PreparedStatement
	 * ps = connection.prepareStatement(SQL)) {
	 * 
	 * ps.setDouble(1, amount); ps.setInt(2, accno);
	 * 
	 * int executeUpdate = ps.executeUpdate();
	 * 
	 * if (executeUpdate == 1) { System.out.println("deposit Done.."); } else {
	 * System.out.println("Account Number Notfound.."); } } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 */

	public void withdraw_By_Accno(int accno, double amount) {
		String update_WithDraw = "UPDATE bank_details set balance=? WHERE accountno=?;";
		String SQL = "SELECT *FROM bank_details WHERE accountno=?";
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement to_check_Accno = connection.prepareStatement(SQL);
			to_check_Accno.setInt(1, accno);
			// connection.setAutoCommit(false);

			ResultSet result = to_check_Accno.executeQuery();

			if (result.next()) {

				double presentbalance = result.getDouble(3);
				String name = result.getString(2);

				// Savepoint sp = connection.setSavepoint();

				if (presentbalance > amount) {
					// connection.rollback(sp);
					
					PreparedStatement update = connection.prepareStatement(update_WithDraw);
					{

						update.setInt(2, accno);
						update.setDouble(1, presentbalance - amount);

						int executeUpdate = update.executeUpdate();

						if (executeUpdate == 1) {
							System.out.println("WithDraw Done.." + "\nAccount Number ::" + accno + "\nAccount Name :: "
									+ name + "\nPresent Amount ::" + presentbalance + "\nWithdraw Amount ::" + amount
									+ "\nTotal Amount ::" + (presentbalance - amount));

						} else {
							System.out.println("WithDraw Not Done.." + "\nAccount Number ::" + accno
									+ "\nAccount Name :: " + name + "\nTotal Amount ::" + (presentbalance - amount));

						}

						// connection.commit();

					}

				} else {
					System.out.println("Inefficient Balance");
				}

			} else {
				System.out.println("Invalid Account no");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();

		}
	}

	/*
	 * public void withdraw(double amount, Integer accno) {
	 * 
	 * String SQL = "UPDATE bank_details set balance=balance-? WHERE accountno=?;";
	 * try (Connection connection = DBConnection.getConnection(); PreparedStatement
	 * ps = connection.prepareStatement(SQL)) {
	 * 
	 * ps.setDouble(1, amount); ps.setInt(2, accno);
	 * 
	 * int executeUpdate = ps.executeUpdate();
	 * 
	 * if (executeUpdate == 1) { System.out.println("withdraw Done.."); } else {
	 * System.out.println("withdraw Not Done.."); } } catch (Exception e) {
	 * 
	 * e.printStackTrace(); System.out.println("withdraw Not Done.."); } }
	 */
	public void display_By_Accno(int accno) {

		String SQL = "SELECT *FROM bank_details WHERE accountno=?";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, accno);

			ResultSet result = ps.executeQuery();

			if (result.next()) {
				int accountno = result.getInt(1);
				String name = result.getString(2);
				double balance = result.getDouble(3);

				String output = " %s - %s - %s";
				System.out.println(String.format(output, accountno, name, balance));
			} else {
				System.out.println("Account Number Not Registered...");
			}

		} catch (SQLException ex) {
			System.out.println("Some Thing Wentwrong...");
			ex.printStackTrace();
		}
	}

	public void display() {

		String SQL = "SELECT * FROM bank_details";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ResultSet result = ps.executeQuery(SQL);

			while (result.next()) {
				int accountno = result.getInt(1);
				String name = result.getString(2);
				double balance = result.getDouble(3);

				String output = " %s - %s - %s";
				System.out.println(String.format(output, accountno, name, balance));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}