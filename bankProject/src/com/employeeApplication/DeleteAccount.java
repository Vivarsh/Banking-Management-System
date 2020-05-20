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

public class DeleteAccount extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel accountNumber;
	private JLabel accountHoldersPassword;

	private JTextField accountNumberTextField;
	private JTextField accountHoldersPasswordTextField;

	private JButton verify;
	private JButton delete;
	private JButton cancel;

	private ResultSet resultSet;
	private Statement statement;

	public DeleteAccount() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		accountNumber = new JLabel("Account Number :");
		accountHoldersPassword = new JLabel("Password :");
		accountNumberTextField = new JTextField();
		accountHoldersPasswordTextField = new JTextField();
		verify = new JButton("Verify");
		delete = new JButton("Delete Account");
		cancel = new JButton("Cancel");

		verify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				workingOfVerifyButton();
			}
		});

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				workingOfDeleteButton();
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				accountNumberTextField.setText(null);
				accountHoldersPasswordTextField.setText(null);
			}
		});

		setPositions();
		addComponents();
		getResultSetAndStatement();

		setVisible(true);
	}

	private void setPositions() {
		accountNumber.setBounds(20, 20, 150, 30);
		accountHoldersPassword.setBounds(20, 100, 150, 30);
		accountNumberTextField.setBounds(250, 20, 150, 30);
		accountHoldersPasswordTextField.setBounds(250, 100, 150, 30);
		verify.setBounds(20, 180, 100, 30);
		delete.setBounds(20, 250, 150, 30);
		cancel.setBounds(220, 250, 100, 30);
	}

	private void addComponents() {
		add(accountNumber);
		add(accountHoldersPassword);
		add(accountNumberTextField);
		add(accountHoldersPasswordTextField);
		add(verify);
		add(delete);
		add(cancel);
	}

	private void getResultSetAndStatement() {
		resultSet = ConnectionClass.getResultSet();
		statement = ConnectionClass.getStatement();
	}

	private void workingOfVerifyButton() {
		try {

			resultSet = statement
					.executeQuery("select * from customer_details where Account_Number = '"
							+ Double.parseDouble(accountNumberTextField
									.getText()) + "'");

			resultSet.beforeFirst();
			resultSet.next();

			if (accountHoldersPasswordTextField.getText().equals(
					resultSet.getString(3))) {
				JOptionPane.showMessageDialog(null, "Verified", "Correct", 1);
			} else {
				JOptionPane.showMessageDialog(null,
						"Account Number and Password Do Not Match", "Error", 0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Invalid Account Number Or Password", "Error", 0);
		}
	}

	private void workingOfDeleteButton() {
		try {
			if ((accountHoldersPasswordTextField.getText().equals(resultSet
					.getString(3)))
					&& accountNumberTextField.getText().equals(
							resultSet.getString(5))) {

				statement
						.execute("delete from customer_details where Account_Number = '"
								+ resultSet.getString(5) + "'");

				accountNumberTextField.setText(null);
				accountHoldersPasswordTextField.setText(null);

				JOptionPane.showMessageDialog(null,
						"Account Deleted Successfully", "Success", 1);
			} else {
				JOptionPane.showMessageDialog(null, "Verify Account", "Error",
						0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Verify Account", "Error", 0);
		}
	}
}
