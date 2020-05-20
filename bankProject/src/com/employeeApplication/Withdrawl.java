package com.employeeApplication;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.connection.ConnectionClass;

public class Withdrawl extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel accountNumber;
	private JLabel amount;

	private JTextField accountNumberTextField;
	private JTextField amountTextField;

	private JButton withdrawl;
	private JButton cancel;

	private ResultSet resultSet;
	private Statement statement;

	private String query;

	public Withdrawl() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		accountNumber = new JLabel("Account NO. :");
		amount = new JLabel("Amount :");
		accountNumberTextField = new JTextField();
		amountTextField = new JTextField();
		withdrawl = new JButton("Withdrawl");
		cancel = new JButton("Cancel");

		withdrawl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				workingOfWithdrawlButton();
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				accountNumberTextField.setText(null);
				amountTextField.setText(null);
			}
		});

		setPositions();
		addComponents();
		getResultSetAndStatement();

		setVisible(true);
	}

	private void getResultSetAndStatement() {
		statement = ConnectionClass.getStatement();
		resultSet = ConnectionClass.getResultSet();
	}

	private void setPositions() {
		accountNumber.setBounds(20, 20, 150, 30);
		amount.setBounds(20, 80, 150, 30);
		accountNumberTextField.setBounds(220, 20, 150, 30);
		amountTextField.setBounds(220, 80, 150, 30);
		withdrawl.setBounds(75, 200, 100, 30);
		cancel.setBounds(220, 200, 100, 30);
	}

	private void addComponents() {
		add(accountNumber);
		add(amount);
		add(accountNumberTextField);
		add(amountTextField);
		add(withdrawl);
		add(cancel);
	}

	private void workingOfWithdrawlButton() {
		try {
			if (accountNumberTextField.getText().length() > 0 && amountTextField.getText().length() > 0) {
				query = "select * from customer_details where Account_Number = '" + accountNumberTextField.getText()
						+ "'";
				resultSet = statement.executeQuery(query);
				resultSet.next();

				double amountFromAmountTextField = Double.parseDouble(amountTextField.getText());

				double amountFromCustomersAccount = Double.parseDouble(resultSet.getString(11));

				if (amountFromAmountTextField <= amountFromCustomersAccount) {

					double setAmountInCustomersAccount = amountFromCustomersAccount - amountFromAmountTextField;

					statement.execute("update customer_details set Balance = '" + setAmountInCustomersAccount
							+ "' where Account_Number = '" + accountNumberTextField.getText() + "'");

					accountNumberTextField.setText(null);
					amountTextField.setText(null);

					JOptionPane.showMessageDialog(null, "Withdrawn Successfully", "Success", 1);
				} else {
					JOptionPane.showMessageDialog(null, "Insufficient Amount", "Error", 0);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Fill All Fields", "Error", 0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Account Number", "Error", 0);
		}
	}
}
