package com.employeeApplication;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.connection.ConnectionClass;

public class CreateAccount extends JPanel {

	private static final long serialVersionUID = 1L;

	private int flag = 0;

	private double accNumber;

	private JLabel name;
	private JLabel userName;
	private JLabel age;
	private JLabel gender;
	private JLabel phoneNumber;
	private JLabel branch;
	private JLabel city;

	private JTextField nameTextField;
	private JTextField userNameTextField;
	private JTextField ageTextField;
	private JTextField genderTextField;
	private JTextField phoneNumberTextField;
	private JTextField branchTextField;
	private JTextField cityTextField;

	private JButton submit;
	private JButton cancel;

	private ResultSet resultSet;
	private Statement statement;

	private static PreparedStatement preparedStatement;

	public CreateAccount() {

		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		name = new JLabel("Name :");
		userName = new JLabel("Username :");
		age = new JLabel("Age :");
		gender = new JLabel("Gender :");
		phoneNumber = new JLabel("Phone Number :");
		branch = new JLabel("Branch :");
		city = new JLabel("City :");

		nameTextField = new JTextField();
		userNameTextField = new JTextField();
		ageTextField = new JTextField();
		genderTextField = new JTextField();
		phoneNumberTextField = new JTextField();
		branchTextField = new JTextField();
		cityTextField = new JTextField();

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");

		resultSet = ConnectionClass.getResultSet();
		statement = ConnectionClass.getStatement();

		preparedStatement = ConnectionClass.getPreparedStatement();

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				workingOfSubmitButton();
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				nameTextField.setText(null);
				userNameTextField.setText(null);
				ageTextField.setText(null);
				genderTextField.setText(null);
				phoneNumberTextField.setText(null);
				branchTextField.setText(null);
				cityTextField.setText(null);
			}
		});

		setPositions();
		addComponents();
		generateAccountNumberForCustomer();

		setVisible(true);
	}

	private void setPositions() {
		setBounds(20, 20, 500, 600);

		name.setBounds(20, 20, 150, 30);
		userName.setBounds(20, 70, 150, 30);
		age.setBounds(20, 120, 150, 30);
		gender.setBounds(20, 170, 150, 30);
		phoneNumber.setBounds(20, 220, 150, 30);
		branch.setBounds(20, 270, 150, 30);
		city.setBounds(20, 320, 150, 30);

		nameTextField.setBounds(250, 20, 150, 30);
		userNameTextField.setBounds(250, 70, 150, 30);
		ageTextField.setBounds(250, 120, 150, 30);
		genderTextField.setBounds(250, 170, 150, 30);
		phoneNumberTextField.setBounds(250, 220, 150, 30);
		branchTextField.setBounds(250, 270, 150, 30);
		cityTextField.setBounds(250, 320, 150, 30);

		submit.setBounds(50, 400, 100, 30);
		cancel.setBounds(220, 400, 100, 30);
	}

	private void addComponents() {
		add(name);
		add(userName);
		add(age);
		add(gender);
		add(phoneNumber);
		add(branch);
		add(city);

		add(nameTextField);
		add(userNameTextField);
		add(ageTextField);
		add(genderTextField);
		add(phoneNumberTextField);
		add(branchTextField);
		add(cityTextField);

		add(submit);
		add(cancel);
	}

	private void generateAccountNumberForCustomer() {
		try {
			resultSet = statement
					.executeQuery("select * from customer_details");
			resultSet.last();
			accNumber = Double.parseDouble(resultSet.getString(5)) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void workingOfSubmitButton() {
		generateAccountNumberForCustomer();
		try {

			if (nameTextField.getText().length() > 0
					&& userNameTextField.getText().length() > 0
					&& ageTextField.getText().length() > 0
					&& genderTextField.getText().length() > 0
					&& phoneNumberTextField.getText().length() > 0
					&& branchTextField.getText().length() > 0
					&& cityTextField.getText().length() > 0) {

				resultSet = statement
						.executeQuery("select * from customer_details");

				resultSet.beforeFirst();

				while (resultSet.next()) {

					if (userNameTextField.getText().equals(
							(resultSet.getString(2)))) {

						flag = 1;

					} else {

						preparedStatement.setString(1, null);
						preparedStatement.setString(2,
								userNameTextField.getText());
						preparedStatement.setString(3, "pass");
						preparedStatement.setString(4, nameTextField.getText());
						preparedStatement.setDouble(5, accNumber);
						preparedStatement.setString(6, ageTextField.getText());
						preparedStatement.setString(7,
								genderTextField.getText());
						preparedStatement.setString(8,
								phoneNumberTextField.getText());
						preparedStatement.setString(9,
								branchTextField.getText());
						preparedStatement
								.setString(10, cityTextField.getText());
						preparedStatement.setString(11, "000");

						preparedStatement.execute();

						userNameTextField.setText(null);
						nameTextField.setText(null);
						ageTextField.setText(null);
						genderTextField.setText(null);
						phoneNumberTextField.setText(null);
						branchTextField.setText(null);
						cityTextField.setText(null);

						JOptionPane.showMessageDialog(null,
								"Account Created Successfully", "Success", 1);
					}
				}
				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "Duplicate Username",
							"Error", 0);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Fill All Fields", "Error",
						0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}