package com.employeeApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class EmployeeApplication extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTabbedPane jTabbedPane;

	private Filter filter;
	private Payee payee;
	private Deposit deposit;
	private Withdrawl withdrawl;
	private CreateAccount createAccount;
	private DeleteAccount deleteAccount;

	private JButton exit;

	public EmployeeApplication() {
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Employee Application");

		jTabbedPane = new JTabbedPane();

		filter = new Filter();
		payee = new Payee();
		deposit = new Deposit();
		withdrawl = new Withdrawl();
		createAccount = new CreateAccount();
		deleteAccount = new DeleteAccount();

		exit = filter.getExit();

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setPositions();
		addTabs();
		setVisible(true);
	}

	private void setPositions() {
		setBounds(20, 20, 1000, 600);
	}

	private void addTabs() {
		jTabbedPane.addTab("Filter", filter);
		jTabbedPane.addTab("Payee", payee);
		jTabbedPane.addTab("Deposit", deposit);
		jTabbedPane.addTab("Withdrawl", withdrawl);
		jTabbedPane.addTab("Create Account", createAccount);
		jTabbedPane.addTab("Delete Account", deleteAccount);

		setContentPane(jTabbedPane);
	}
}
