package com.employeeApplication;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.connection.ConnectionClass;

public class Filter extends JPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox firstCheckBox;
	private JCheckBox secondCheckBox;
	private JCheckBox thirdCheckBox;

	private ResultSet resultSet;
	private Statement statement;

	private JTextField accountNumberTextField;

	private JButton filter;
	private JButton exit;

	private final double zeroBalance;
	private final double fiftyK;
	private final double oneLakh;

	private JScrollPane jScrollPane;
	private JTable jTable;
	private DefaultTableModel defaultTableModel;

	private final String str1;
	private final String str2;
	private final String str3;

	int flag = 0;

	private ArrayList<String> row;

	public Filter() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		firstCheckBox = new JCheckBox("Balance below Rs.50,000");
		secondCheckBox = new JCheckBox("Balance between Rs.50,000 and Rs.1,00,000");
		thirdCheckBox = new JCheckBox("Balance above Rs.1,00,000");

		zeroBalance = 0;
		fiftyK = 50000;
		oneLakh = 100000;

		str1 = "Acount Number";
		str2 = " ";
		str3 = "";
		accountNumberTextField = new JTextField(str1);

		filter = new JButton("Refresh");
		exit = new JButton("Exit");

		row = new ArrayList<String>();

		defaultTableModel = new DefaultTableModel();
		jTable = new JTable(defaultTableModel);

		jScrollPane = new JScrollPane(jTable);

		setPositions();
		addComponents();
		getResultSetAndStatement();
		setTable();
		workingOfAccountNumberTextField();

		filter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				workingOfButton();
			}
		});

		setVisible(true);
	}

	// private void setTable() {
	// try {
	// String[] column = { "id", "name", "account_no", "age", "gender",
	// "phone_no", "branch", "city", "balance" };
	// defaultTableModel.setColumnCount(9);
	// defaultTableModel.setColumnIdentifiers(column);
	// int z = 0;
	// String[] row = new String[9];
	// resultSet.beforeFirst();
	// while (resultSet.next()) {
	// row = new String[9];
	// int x = 0;
	// for (int i = 1; i <= 11; i++) {
	// if (!(i == 2) && !(i == 3)) {
	// row[x] = resultSet.getString(i);
	// x++;
	// }
	// }
	// defaultTableModel.insertRow(z, row);
	// z++;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private void setTable() {
		try {
			String[] column = { "id", "name", "account_no", "age", "gender", "phone_no", "branch", "city", "balance" };
			defaultTableModel.setColumnCount(9);
			defaultTableModel.setColumnIdentifiers(column);

			int z = 0;

			resultSet.beforeFirst();
			while (resultSet.next()) {
				for (int i = 1; i <= 11; i++) {
					if (!(i == 2) && !(i == 3)) {
						row.add(resultSet.getString(i));
					}
				}
				defaultTableModel.insertRow(z, row.toArray());
				z++;
				row = new ArrayList<String>();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getResultSetAndStatement() {
		statement = ConnectionClass.getStatement();
		resultSet = ConnectionClass.getResultSet();

		try {
			resultSet = statement.executeQuery("select * from customer_details");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void setPositions() {

		firstCheckBox.setBounds(20, 20, 250, 30);
		firstCheckBox.setBackground(Color.LIGHT_GRAY);
		secondCheckBox.setBounds(20, 70, 300, 30);
		secondCheckBox.setBackground(Color.LIGHT_GRAY);
		thirdCheckBox.setBounds(20, 120, 250, 30);
		thirdCheckBox.setBackground(Color.LIGHT_GRAY);

		accountNumberTextField.setBounds(20, 170, 100, 30);

		filter.setBounds(250, 170, 100, 30);
		exit.setBounds(875, 480, 75, 30);

		jScrollPane.setBounds(20, 230, 930, 230);
		jScrollPane.setFocusable(false);

		jTable.setFocusable(false);
	}

	private void addComponents() {
		add(firstCheckBox);
		add(secondCheckBox);
		add(thirdCheckBox);
		add(accountNumberTextField);
		add(filter);
		add(exit);
		add(jScrollPane);
	}

	private void workingOfAccountNumberTextField() {
		accountNumberTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!filter.hasFocus()) {
					if (accountNumberTextField.getText().equals(str2)
							|| accountNumberTextField.getText().equals(str3)) {
						accountNumberTextField.setText(str1);
					}
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (accountNumberTextField.getText().equals(str1)) {
					accountNumberTextField.setText(str3);
				}
			}
		});
	}

	private void removeRows() {
		try {

			int noOfRows = defaultTableModel.getRowCount();

			int a = noOfRows - 1;
			while (a >= 0) {
				defaultTableModel.removeRow(a);
				a--;
				if (a == -1) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void workingOfButton() {
		try {
			if (firstCheckBox.isSelected() && !(secondCheckBox.isSelected() || thirdCheckBox.isSelected())) {
				removeRows();

				resultSet = statement.executeQuery("select * from customer_details where Balance < '" + fiftyK + "'");

				int z = 0;

				resultSet.beforeFirst();
				while (resultSet.next()) {
					for (int i = 1; i <= 11; i++) {
						if (!(i == 2) && !(i == 3)) {
							row.add(resultSet.getString(i));
						}
					}
					defaultTableModel.insertRow(z, row.toArray());
					z++;
					row = new ArrayList<String>();
				}
			} else {

				if (secondCheckBox.isSelected() && !(firstCheckBox.isSelected() || thirdCheckBox.isSelected())) {
					removeRows();

					resultSet = statement.executeQuery("select * from customer_details where Balance > '" + fiftyK
							+ "' and Balance < '" + oneLakh + "'");

					int z = 0;

					resultSet.beforeFirst();
					while (resultSet.next()) {
						for (int i = 1; i <= 11; i++) {
							if (!(i == 2) && !(i == 3)) {
								row.add(resultSet.getString(i));
							}
						}
						defaultTableModel.insertRow(z, row.toArray());
						z++;
						row = new ArrayList<String>();
					}
				} else {
					if (thirdCheckBox.isSelected() && !(firstCheckBox.isSelected() || secondCheckBox.isSelected())) {
						removeRows();

						resultSet = statement
								.executeQuery("select * from customer_details where Balance > '" + oneLakh + "'");

						int z = 0;

						resultSet.beforeFirst();
						while (resultSet.next()) {
							for (int i = 1; i <= 11; i++) {
								if (!(i == 2) && !(i == 3)) {
									row.add(resultSet.getString(i));
								}
							}
							defaultTableModel.insertRow(z, row.toArray());
							z++;
							row = new ArrayList<String>();
						}
					} else {
						if (firstCheckBox.isSelected() && secondCheckBox.isSelected()
								&& !(thirdCheckBox.isSelected())) {
							removeRows();

							resultSet = statement
									.executeQuery("select * from customer_details where Balance < '" + oneLakh + "'");

							int z = 0;

							resultSet.beforeFirst();
							while (resultSet.next()) {
								for (int i = 1; i <= 11; i++) {
									if (!(i == 2) && !(i == 3)) {
										row.add(resultSet.getString(i));
									}
								}
								defaultTableModel.insertRow(z, row.toArray());
								z++;
								row = new ArrayList<String>();
							}
						} else {
							if (firstCheckBox.isSelected() && thirdCheckBox.isSelected()
									&& !(secondCheckBox.isSelected())) {

								removeRows();

								resultSet = statement.executeQuery(
										"select * from customer_details where Balance < '" + fiftyK + "'");

								int z = 0;

								resultSet.beforeFirst();
								while (resultSet.next()) {
									for (int i = 1; i <= 11; i++) {
										if (!(i == 2) && !(i == 3)) {
											row.add(resultSet.getString(i));
										}
									}
									defaultTableModel.insertRow(z, row.toArray());
									z++;
									row = new ArrayList<String>();
								}

								resultSet = statement.executeQuery(
										"select * from customer_details where Balance > '" + oneLakh + "'");

								resultSet.beforeFirst();
								while (resultSet.next()) {
									for (int i = 1; i <= 11; i++) {
										if (!(i == 2) && !(i == 3)) {
											row.add(resultSet.getString(i));
										}
									}
									defaultTableModel.insertRow(z, row.toArray());
									z++;
									row = new ArrayList<String>();
								}

							} else {

								if (secondCheckBox.isSelected() && thirdCheckBox.isSelected()
										&& !(firstCheckBox.isSelected())) {
									removeRows();

									resultSet = statement.executeQuery(
											"select * from customer_details where Balance > '" + fiftyK + "'");

									int z = 0;

									resultSet.beforeFirst();
									while (resultSet.next()) {
										for (int i = 1; i <= 11; i++) {
											if (!(i == 2) && !(i == 3)) {
												row.add(resultSet.getString(i));
											}
										}
										defaultTableModel.insertRow(z, row.toArray());
										z++;
										row = new ArrayList<String>();
									}
								} else {
									if (firstCheckBox.isSelected() && secondCheckBox.isSelected()
											&& thirdCheckBox.isSelected()) {
										removeRows();

										resultSet = statement.executeQuery(
												"select * from customer_details where Balance > '" + zeroBalance + "'");

										int z = 0;

										resultSet.beforeFirst();
										while (resultSet.next()) {
											for (int i = 1; i <= 11; i++) {
												if (!(i == 2) && !(i == 3)) {
													row.add(resultSet.getString(i));
												}
											}
											defaultTableModel.insertRow(z, row.toArray());
											z++;
											row = new ArrayList<String>();
										}
									} else {

										if (!(accountNumberTextField.getText().equals(str1))) {
											resultSet = statement.executeQuery("select * from customer_details");

											int noOfRows = 0;
											resultSet.beforeFirst();
											while (resultSet.next()) {
												noOfRows++;
											}

											String[] everyCustomersAccountNumber = new String[noOfRows];
											resultSet.beforeFirst();
											resultSet.next();
											for (int j = 0; j < noOfRows; j++) {
												everyCustomersAccountNumber[j] = resultSet.getString(5);
												resultSet.next();
											}

											for (int k = 0; k < noOfRows; k++) {
												if ((accountNumberTextField.getText()
														.equals(everyCustomersAccountNumber[k]))) {
													removeRows();

													resultSet = statement
															.executeQuery(
																	"select * from customer_details where Account_Number = '"
																			+ Double.parseDouble(
																					accountNumberTextField.getText())
																			+ "'");

													int z = 0;

													resultSet.beforeFirst();
													while (resultSet.next()) {
														for (int i = 1; i <= 11; i++) {
															if (!(i == 2) && !(i == 3)) {
																row.add(resultSet.getString(i));
															}
														}
														defaultTableModel.insertRow(z, row.toArray());
														z++;
														row = new ArrayList<String>();
													}

													flag = 0;
													break;

												} else {
													flag = 1;
												}

											}

											if (flag == 1) {
												JOptionPane.showMessageDialog(null, "Invalid Account Number", "Error",
														0);
											}
										} else {
											removeRows();

											resultSet = statement
													.executeQuery("select * from customer_details where Balance >= '"
															+ zeroBalance + "'");

											int z = 0;

											resultSet.beforeFirst();
											while (resultSet.next()) {
												for (int i = 1; i <= 11; i++) {
													if (!(i == 2) && !(i == 3)) {
														row.add(resultSet.getString(i));
													}
												}
												defaultTableModel.insertRow(z, row.toArray());
												z++;
												row = new ArrayList<String>();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JButton getExit() {
		return exit;
	}

	public void setExit(JButton exit) {
		this.exit = exit;
	}
}
