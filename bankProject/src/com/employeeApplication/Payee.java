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

public class Payee extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel payeeAccountNumber;
	private JLabel receiverAccountNumber;
	private JLabel amount;

	private JTextField payeeTextField;
	private JTextField receiverTextField;
	private JTextField amountTextField;

	private JButton transfer;
	private JButton cancel;

	private ResultSet resultSetForPayee;
	private ResultSet resultSetForReceiver;
	private Statement statement;

	private String queryForPayee;
	private String queryForReceiver;

	public Payee() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		payeeAccountNumber = new JLabel("Payee Account NO. :");
		receiverAccountNumber = new JLabel("Receiver Account No. :");
		amount = new JLabel("Amount :");

		payeeTextField = new JTextField();
		receiverTextField = new JTextField();
		amountTextField = new JTextField();

		transfer = new JButton("Transfer");
		cancel = new JButton("Cancel");

		setPositions();
		addComponents();
		getResultSetAndStatement();

		transfer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				workingOfButton();
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payeeTextField.setText(null);
				receiverTextField.setText(null);
				amountTextField.setText(null);
			}
		});

		setVisible(true);
	}

	private void getResultSetAndStatement() {

		statement = ConnectionClass.getStatement();
		resultSetForPayee = ConnectionClass.getResultSet();
		resultSetForReceiver = ConnectionClass.getResultSet();
	}

	private void setPositions() {
		payeeAccountNumber.setBounds(20, 20, 150, 30);
		receiverAccountNumber.setBounds(20, 80, 150, 30);
		amount.setBounds(20, 140, 150, 30);

		payeeTextField.setBounds(250, 20, 150, 30);
		receiverTextField.setBounds(250, 80, 150, 30);
		amountTextField.setBounds(250, 140, 150, 30);

		transfer.setBounds(75, 300, 100, 30);
		cancel.setBounds(250, 300, 100, 30);
	}

	private void addComponents() {
		add(payeeAccountNumber);
		add(receiverAccountNumber);
		add(amount);

		add(payeeTextField);
		add(receiverTextField);
		add(amountTextField);

		add(transfer);
		add(cancel);
	}

	private void workingOfButton() {
		try {
			if (payeeTextField.getText().length() > 0 && receiverTextField.getText().length() > 0
					&& amountTextField.getText().length() > 0) {

				if (!payeeTextField.getText().equals(receiverTextField.getText())) {
					queryForPayee = "select * from customer_details where Account_Number = '" + payeeTextField.getText()
							+ "'";

					queryForReceiver = "select * from customer_details where Account_Number = '"
							+ receiverTextField.getText() + "'";

					resultSetForPayee = statement.executeQuery(queryForPayee);

					resultSetForPayee.next();

					double amountFromAmountTextField = Double.parseDouble(amountTextField.getText());

					double amountFromPayeeAccount = Double.parseDouble(resultSetForPayee.getString(11));

					if (amountFromAmountTextField <= amountFromPayeeAccount) {

						double setAmountInPayeeAccount = amountFromPayeeAccount - amountFromAmountTextField;

						statement.execute("update customer_details set Balance = '" + setAmountInPayeeAccount
								+ "' where Account_Number = '" + payeeTextField.getText() + "'");

						resultSetForReceiver = statement.executeQuery(queryForReceiver);

						resultSetForReceiver.next();

						double amountFromReceiverAccount = Double.parseDouble(resultSetForReceiver.getString(11));

						double setAmountInReceiverAccount = amountFromReceiverAccount + amountFromAmountTextField;

						statement.execute("update customer_details set Balance = '" + setAmountInReceiverAccount
								+ "' where  Account_Number = '" + receiverTextField.getText() + "' ");

						payeeTextField.setText(null);
						receiverTextField.setText(null);
						amountTextField.setText(null);

						JOptionPane.showMessageDialog(null, "Transfered Successfully", "Success", 1);
					} else {
						JOptionPane.showMessageDialog(null, "Insufficient Amount", "Error", 0);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Invalid Account Number", "Error", 0);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Fill All Fields", "Error", 0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Account Number", "Error", 0);
		}
	}
}
