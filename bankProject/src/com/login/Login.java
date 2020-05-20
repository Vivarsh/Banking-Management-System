package com.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.connection.ConnectionClass;
import com.employeeApplication.EmployeeApplication;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel username;
	private JLabel password;

	private static JTextField usernameTextfield;
	private static JPasswordField passwordTextfield;

	private JButton login;
	private JButton cancel;

	private EmployeeApplication employeeApplication;

	private static ResultSet resultSetForEmployeeDetails;
	private static Statement statement;

	private static ConnectionClass connectionClass;

	static {
		connectionClass = new ConnectionClass();
	}

	public Login() {
		try {
			setLayout(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setTitle("Login");

			username = new JLabel("Username :");
			password = new JLabel("Password :");
			usernameTextfield = new JTextField();
			passwordTextfield = new JPasswordField();
			login = new JButton("Login");
			cancel = new JButton("Cancel");

			setPosition();
			addComponents();
			getResultSetAndStatement();

			login.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					workingOfButton();
				}
			});

			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getResultSetAndStatement() {
		try {
			statement = ConnectionClass.getStatement();
			resultSetForEmployeeDetails = ConnectionClass.getResultSet();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setPosition() {
		setBounds(20, 20, 400, 400);
		username.setBounds(20, 20, 100, 30);
		password.setBounds(20, 80, 100, 30);
		usernameTextfield.setBounds(180, 20, 150, 30);
		passwordTextfield.setBounds(180, 80, 150, 30);
		login.setBounds(80, 250, 100, 30);
		cancel.setBounds(220, 250, 100, 30);
	}

	private void addComponents() {
		add(username);
		add(password);
		add(usernameTextfield);
		add(passwordTextfield);
		add(login);
		add(cancel);
	}

	private void workingOfButton() {
		try {
			if (usernameTextfield.getText().length() > 0
					&& passwordTextfield.getPassword().length > 0) {

				String query1 = "select * from employee_details where name = '"
						+ usernameTextfield.getText() + "'";

				resultSetForEmployeeDetails = statement.executeQuery(query1);

				resultSetForEmployeeDetails.next();
				if (passwordTextfield.getText().equals(
						resultSetForEmployeeDetails.getString(2))) {
					dispose();
					employeeApplication = new EmployeeApplication();
				} else {
					JOptionPane.showMessageDialog(null,
							"Invalid Username or Password", "Error", 0);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Fill All Feilds", "Error",
						0);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Username or Password",
					"Error", 0);
		}
	}

	public static JTextField getUsernameTextfield() {
		return usernameTextfield;
	}

	public void setUsernameTextfield(JTextField usernameTextfield) {
		Login.usernameTextfield = usernameTextfield;
	}

	public static JPasswordField getPasswordTextfield() {
		return passwordTextfield;
	}

	public void setPasswordTextfield(JPasswordField passwordTextfield) {
		Login.passwordTextfield = passwordTextfield;
	}

	public static void main(String[] args) {
		new Login();
	}

}
