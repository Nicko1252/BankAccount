import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONException;
import org.json.JSONObject;

public class GUI {

	private JFrame frame;
	private CardLayout layout = new CardLayout(0, 0);
	private JTextField accountNumberField;
	private JPasswordField passwordField;
	final String IntroCardID = "IntroCard";
	final String LogInCardID = "LogInCard";
	final String RegisterCardID = "RegisterCard";
	final String AccountScreenCardID = "AccountScreenCard";
	final String DepostCardID= "Depsoit Card";
	final String WithdrawCardID= "Withdraw Card";
	final String TransferCardID = "Transfer Card";
	final String SuccessfulCardID = "Successful Card";
	final String AccountInfoCardID = "Account Info Card";
	final String TransactionCardID = "Transaction Card";
	private JTextField FirstNameField;
	private JTextField LastNameField;
	private JTextField DepositAmtFIELD;
	private JTextField WithdrawAmtFIELD;
	private JTextField TransferAmountFIELD;
	private JTextField TransferAccountFIELD;

	final int MAX_ACCOUNT_LENGTH=7;
	final int MAX_PSWD_LENGTH=5;
/*
	BankAccount b1 = new BankAccount(MAX_ACCOUNT_LENGTH, 0, "account", "1", MAX_PSWD_LENGTH);
	BankAccount b2 = new BankAccount(MAX_ACCOUNT_LENGTH, 0, "account", "2", MAX_PSWD_LENGTH);
	BankAccount b3 = new BankAccount(MAX_ACCOUNT_LENGTH, 0, "account", "3", MAX_PSWD_LENGTH);
	BankAccount b4 = new BankAccount(MAX_ACCOUNT_LENGTH, 0, "account", "4", MAX_PSWD_LENGTH);
	BankAccount b5 = new BankAccount(MAX_ACCOUNT_LENGTH, 0, "account", "5", MAX_PSWD_LENGTH);
	BankAccount b6 = new BankAccount(MAX_ACCOUNT_LENGTH, 0, "account", "6", MAX_PSWD_LENGTH);
*/
	OnlineBankAccount current = null;
	OnlineBankAccount temp = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	
	void login() {
		HttpURLConnectionATM http = new HttpURLConnectionATM();
		try {
			String param1 = http.url+"login.php";
			String param2 = "acctNum=" + accountNumberField.getText() + "&password="+ String.valueOf(passwordField.getPassword());
			
			System.out.println(param1 + "    " + param2);
			http.sendPost(param1, param2);
			
			if (http.response !=null) {
				if (http.response.toString().trim().equals("login-success")) {
					getUserInfo(Integer.parseInt(accountNumberField.getText()), "login");
					layout.show(frame.getContentPane(), AccountScreenCardID);
				}
				else if(http.response.toString().trim().equals("login-fail")) {
					JOptionPane.showMessageDialog(null, "Incorrect Account Number or Password");
				}
				else {
					System.out.println("Php Error!\n"+http.response.toString());
					JOptionPane.showMessageDialog(null, "Incorrect Account Number or Password");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Server Unreachable.\nPlease try again after checking your Internet connection");
		}
				
	}
	
	public boolean register(String firstName, String lastName){
		try {
			HttpURLConnectionATM http = new HttpURLConnectionATM();

			int accountNum = BankAccount.genAcctNum(8);
			int balance = 0;
			String password = BankAccount.genPswd(5);
			String log = "";
			
			current = new OnlineBankAccount(accountNum, balance, firstName, lastName, password, log);
			System.out.println("AccountNumber: "+current.acctNum+ "     Password: "+ current.pswd);
			System.out.print("FirstName: " + current.fName + "     LastName: "+current.lName+"     AccountNumber: "+current.acctNum+"     Password: "+current.pswd);

			
			
			String param1 = http.url + "register.php?";
			String param2 = "acctNum=" + accountNum + "&balance="+ balance + "&log="+log+ "&first_name="+firstName + "&last_name="+lastName + "&password="+password; 
			
			http.sendPost(param1, param2);

			String response = http.response.toString().trim();

			if (http.response !=null) {
				if (response.equals("register-success")) {
					//System.out.print("worked!!!!!!");
					return true;
				} else{
					System.out.println("fail" + response);
					System.out.println(response);
					return false;
				}
			}
			
		} catch (Exception e) {e.printStackTrace(); }
		System.out.println("try failed");
		return false;
	}


	
	void getUserInfo(int acctNumber, String type) {
		HttpURLConnectionATM http = new HttpURLConnectionATM();
		
		try {
			String param1 = http.url + "getAccountInfo.php?";
			String param2 = "acctNum=" + acctNumber;
			
			http.sendPost(param1, param2);
			
			if (http.response !=null) {
				parseJSONAccountString(http.response.toString(), type);
			} else {
				System.out.println("Account Not Found");
			}
		} catch (Exception e) {e.printStackTrace(); }
		//current.display();
	}

	 void parseJSONAccountString(String result, String type) {
		try {
			if (result !=null && result.startsWith("{")) {
				JSONObject obj = new JSONObject(result);
				
				String fName = obj.getString("first_name");
				String lName = obj.getString("last_name");
				String pswd = obj.getString("password");
				String log = obj.getString("log");
				int acctNum = obj.getInt("acct_num");
				double balance = obj.getDouble("balance");
				
				
				System.out.println(acctNum);
				System.out.println(balance);
				System.out.println(fName);
				System.out.println(lName);
				System.out.println(pswd);
				System.out.println(log);
				
				if (type.equals("login")){
					current = new OnlineBankAccount (acctNum, balance, fName, lName, pswd, log);
				} else if (type.equals("transfer")) {
					temp = new OnlineBankAccount (acctNum, balance, fName, lName, pswd, log);
				}
			} else {
				System.out.println("result = "+result);
			}
		} catch(JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public double myRound(double amt) {
		
		double rounded = Math.round(amt * 100.0) / 100.0; // => 1.23
		return rounded;
		
	}
	
	private void initialize() {		

		//BankAccountManager accountManager = new BankAccountManager();
/*
		accountManager.addAcct(b1);
		accountManager.addAcct(b2);
		accountManager.addAcct(b3);
		accountManager.addAcct(b4);
		accountManager.addAcct(b5);
		accountManager.addAcct(b6);

		System.out.println("B1 account number: "+b1.acctNum+"     password: "+b1.pswd);
		System.out.println("B2 account number: "+b2.acctNum+"     password: "+b2.pswd);
		System.out.println("B3 account number: "+b3.acctNum+"     password: "+b3.pswd);
		System.out.println("B4 account number: "+b4.acctNum+"     password: "+b4.pswd);
		System.out.println("B5 account number: "+b5.acctNum+"     password: "+b5.pswd);
		System.out.println("B6 account number: "+b6.acctNum+"     password: "+b6.pswd);
*/
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(layout);
		frame.setResizable(false);

		JPanel TitlePanel = new JPanel();
		TitlePanel.setBackground(new Color(245, 255, 250));
		frame.getContentPane().add(TitlePanel, IntroCardID);
		TitlePanel.setLayout(null);

		JLabel HeadingLabel = new JLabel("Welcome To BankAccount");
		HeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadingLabel.setFont(new Font("Bell MT", Font.PLAIN, 30));
		HeadingLabel.setBounds(0, 0, 434, 66);
		TitlePanel.add(HeadingLabel);

		JButton LogInBTN = new JButton("Log In");

		LogInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LogInCardID);

			}
		});
		LogInBTN.setBounds(172, 100, 89, 23);
		TitlePanel.add(LogInBTN);

		JButton RegisterBTN = new JButton("Register");

		RegisterBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), RegisterCardID);

			}
		});
		RegisterBTN.setBounds(172, 142, 89, 23);
		TitlePanel.add(RegisterBTN);

		JLabel companyLabel = new JLabel("By AdelCo Savings™");
		companyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		companyLabel.setFont(new Font("Bell MT", Font.PLAIN, 15));
		companyLabel.setBounds(2, 228, 432, 31);
		TitlePanel.add(companyLabel);

		JPanel LogInPanel = new JPanel();
		LogInPanel.setBackground(new Color(245, 255, 250));
		frame.getContentPane().add(LogInPanel, LogInCardID);
		LogInPanel.setLayout(null);

		JLabel LogInHeadingLabel = new JLabel("Log In");
		LogInHeadingLabel.setFont(new Font("Bell MT", Font.PLAIN, 30));
		LogInHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LogInHeadingLabel.setBounds(0, 0, 434, 66);
		LogInPanel.add(LogInHeadingLabel);

		accountNumberField = new JTextField();
		accountNumberField.setBounds(173, 106, 89, 20);
		LogInPanel.add(accountNumberField);
		accountNumberField.setColumns(10);

		JLabel AccountNumberLogInLabel = new JLabel("Account Number");
		AccountNumberLogInLabel.setHorizontalAlignment(SwingConstants.CENTER);
		AccountNumberLogInLabel.setFont(new Font("Bell MT", Font.PLAIN, 20));
		AccountNumberLogInLabel.setBounds(0, 77, 434, 20);
		LogInPanel.add(AccountNumberLogInLabel);

		JLabel PasswordLogInLabel = new JLabel("Password");
		PasswordLogInLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordLogInLabel.setFont(new Font("Bell MT", Font.PLAIN, 20));
		PasswordLogInLabel.setBounds(0, 137, 434, 20);
		LogInPanel.add(PasswordLogInLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(173, 165, 89, 20);
		LogInPanel.add(passwordField);
		passwordField.setColumns(10);


		JButton SubmitLogInBTN = new JButton("Log In");
		SubmitLogInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				login();
				
				
				//uses account Manager
/*
				int userAccountNum = Integer.parseInt(accountNumberField.getText());
				String userPassword = String.valueOf(passwordField.getPassword());

				if (accountManager.isAcctInArray(userAccountNum)) {
					BankAccount compare = accountManager.getAcct(userAccountNum);

					if(compare.pswd.equals(userPassword) && compare.acctNum == userAccountNum) {
						current = compare;

						JOptionPane.showMessageDialog(frame , "Successfully Logged In");
						layout.show(frame.getContentPane(), AccountScreenCardID);
						accountNumberField.setText("");
						passwordField.setText("");
					} else {
						JOptionPane.showMessageDialog(frame , "Incorrect Credentials");
						accountNumberField.setText("");
						passwordField.setText("");
					}

				} else {
					JOptionPane.showMessageDialog(frame , "Incorrect Credentials");
					accountNumberField.setText("");
					passwordField.setText("");
				}
				*/

				
			}
		});
		SubmitLogInBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		SubmitLogInBTN.setBounds(173, 205, 89, 25);
		LogInPanel.add(SubmitLogInBTN);

		JButton backLogInBTN = new JButton("Back");
		backLogInBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		backLogInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), IntroCardID);

			}
		});
		backLogInBTN.setBounds(10, 11, 89, 23);
		LogInPanel.add(backLogInBTN);

		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBackground(new Color(245, 255, 250));
		frame.getContentPane().add(RegisterPanel, RegisterCardID);
		RegisterPanel.setLayout(null);

		JLabel RegiserHeadingLabel = new JLabel("Register");
		RegiserHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RegiserHeadingLabel.setFont(new Font("Bell MT", Font.PLAIN, 30));
		RegiserHeadingLabel.setBounds(0, 2, 432, 61);
		RegisterPanel.add(RegiserHeadingLabel);

		FirstNameField = new JTextField();
		FirstNameField.setBounds(175, 96, 86, 20);
		RegisterPanel.add(FirstNameField);
		FirstNameField.setColumns(10);

		JLabel FirstNameLabel = new JLabel("First Name");
		FirstNameLabel.setFont(new Font("Bell MT", Font.PLAIN, 15));
		FirstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		FirstNameLabel.setBounds(0, 74, 432, 14);
		RegisterPanel.add(FirstNameLabel);

		LastNameField = new JTextField();
		LastNameField.setColumns(10);
		LastNameField.setBounds(175, 149, 86, 20);
		RegisterPanel.add(LastNameField);

		JLabel LastNameLabel = new JLabel("Last Name");
		LastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LastNameLabel.setFont(new Font("Bell MT", Font.PLAIN, 15));
		LastNameLabel.setBounds(0, 127, 432, 14);
		RegisterPanel.add(LastNameLabel);

		JButton FinsihRegisterBTN = new JButton("Register");
		FinsihRegisterBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		FinsihRegisterBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String userFirstName = FirstNameField.getText();
				String userLastName = LastNameField.getText();	
				
				if (userFirstName.equals("") || userLastName.equals("")) {
					JOptionPane.showMessageDialog(frame ,"Please input values");
					return;
				}
				
				FirstNameField.setText("");
				LastNameField.setText("");
				register(userFirstName, userLastName);
				
				current = null;

				layout.show(frame.getContentPane(),IntroCardID );
				
/*
				String userFirstName = FirstNameField.getText();
				FirstNameField.setText("");
				String userLastName = LastNameField.getText();	
				LastNameField.setText("");

				BankAccount newAcct = new BankAccount (MAX_ACCOUNT_LENGTH, 0, userFirstName, userLastName, MAX_PSWD_LENGTH);
				accountManager.addAcct(newAcct);

				JOptionPane.showMessageDialog(frame , "Account Number: "+newAcct.acctNum+"\nPassword: "+newAcct.pswd);


				layout.show(frame.getContentPane(), IntroCardID);

				System.out.println(newAcct.fName + "account Number: "+newAcct.acctNum+"     password: "+newAcct.pswd);
*/

				/*
				if (userFirstName.length() >0 && userLastName.length()>0) {
					JOptionPane.showMessageDialog(frame , "Account Created");
					layout.show(frame.getContentPane(), AccountScreenCardID);
				} else {
					JOptionPane.showMessageDialog(frame , "Fields Cannot Be Empty");

				}

				 */
			}
		});

		FinsihRegisterBTN.setBounds(175, 190, 89, 25);
		RegisterPanel.add(FinsihRegisterBTN);

		JButton backRegisterBTN = new JButton("Back");
		backRegisterBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		backRegisterBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), IntroCardID);
			}
		});
		backRegisterBTN.setBounds(10, 11, 89, 23);
		RegisterPanel.add(backRegisterBTN);

		JPanel AccountScreen = new JPanel();
		AccountScreen.setBackground(new Color(245, 255, 250));
		frame.getContentPane().add(AccountScreen, AccountScreenCardID);
		AccountScreen.setLayout(null);

		JButton LogOutBTN = new JButton("Log Out");
		LogOutBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		LogOutBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current = null;
				layout.show(frame.getContentPane(), IntroCardID);
			}
		});
		LogOutBTN.setBounds(10, 11, 89, 23);
		AccountScreen.add(LogOutBTN);

		JLabel AccountInfoHeadingLABEL = new JLabel("Account");
		AccountInfoHeadingLABEL.setHorizontalAlignment(SwingConstants.CENTER);
		AccountInfoHeadingLABEL.setFont(new Font("Bell MT", Font.PLAIN, 30));
		AccountInfoHeadingLABEL.setBounds(0, 0, 434, 61);
		AccountScreen.add(AccountInfoHeadingLABEL);

		JButton DepositBTM = new JButton("Deposit");
		DepositBTM.setFont(new Font("Bell MT", Font.PLAIN, 15));
		DepositBTM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel DepositPanel = new JPanel();
				DepositPanel.setBackground(new Color(245, 255, 250));
				frame.getContentPane().add(DepositPanel, DepostCardID);
				DepositPanel.setLayout(null);

				JLabel DepositHeadingLabel = new JLabel("Deposit");
				DepositHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
				DepositHeadingLabel.setFont(new Font("Bell MT", Font.PLAIN, 30));
				DepositHeadingLabel.setBounds(0, 0, 434, 59);
				DepositPanel.add(DepositHeadingLabel);

				DepositAmtFIELD = new JTextField();
				DepositAmtFIELD.setBounds(164, 113, 105, 20);
				DepositPanel.add(DepositAmtFIELD);
				DepositAmtFIELD.setColumns(10);

				JLabel DepositIntructions = new JLabel("Deposit Amount");
				DepositIntructions.setHorizontalAlignment(SwingConstants.CENTER);
				DepositIntructions.setFont(new Font("Bell MT", Font.PLAIN, 15));
				DepositIntructions.setBounds(0, 70, 434, 48);
				DepositPanel.add(DepositIntructions);

				JButton CompleteDepBTN = new JButton("Deposit");
				CompleteDepBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
				CompleteDepBTN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							double depositAmt = Double.parseDouble(DepositAmtFIELD.getText());
							DepositAmtFIELD.setText("");
							
							if (depositAmt <=0) {
								current.deposit(depositAmt);
								JOptionPane.showMessageDialog(frame , "Insufficient Amount");
								DepositAmtFIELD.setText("");
								return;
							}
							

							if (current.deposit(depositAmt) == true) {
								JOptionPane.showMessageDialog(frame , "Sucessful");
								layout.show(frame.getContentPane(), AccountScreenCardID);
							} else {
								JOptionPane.showMessageDialog(frame , "Insufficient Amount");
							}
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(frame , "Please input an amount");

						}
					}
				});


				CompleteDepBTN.setBounds(164, 155, 105, 23);
				DepositPanel.add(CompleteDepBTN);

				JButton BackDeposit = new JButton("Back");
				BackDeposit.setFont(new Font("Bell MT", Font.PLAIN, 15));
				BackDeposit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), AccountScreenCardID);
					}
				});
				BackDeposit.setBounds(10, 11, 89, 23);
				DepositPanel.add(BackDeposit);

				layout.show(frame.getContentPane(), DepostCardID);
			}
		});
		DepositBTM.setBounds(144, 72, 149, 23);
		AccountScreen.add(DepositBTM);

		JButton WithdrawBTN = new JButton("Withdraw");
		WithdrawBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		WithdrawBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel WithdrawPanel = new JPanel();
				WithdrawPanel.setBackground(new Color(245, 255, 250));
				WithdrawPanel.setLayout(null);
				frame.getContentPane().add(WithdrawPanel, WithdrawCardID);

				JLabel WithdrawHeadingLabel = new JLabel("Withdraw");
				WithdrawHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
				WithdrawHeadingLabel.setFont(new Font("Bell MT", Font.PLAIN, 30));
				WithdrawHeadingLabel.setBounds(0, 0, 434, 59);
				WithdrawPanel.add(WithdrawHeadingLabel);

				WithdrawAmtFIELD = new JTextField();
				WithdrawAmtFIELD.setColumns(10);
				WithdrawAmtFIELD.setBounds(154, 114, 125, 20);
				WithdrawPanel.add(WithdrawAmtFIELD);

				JLabel WithdrawInstructions = new JLabel("Withdraw Amount");
				WithdrawInstructions.setHorizontalAlignment(SwingConstants.CENTER);
				WithdrawInstructions.setFont(new Font("Bell MT", Font.PLAIN, 15));
				WithdrawInstructions.setBounds(0, 70, 434, 48);
				WithdrawPanel.add(WithdrawInstructions);

				JButton CompleteWithdrawBTN = new JButton("Withdraw");
				CompleteWithdrawBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
				CompleteWithdrawBTN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							double withdrawAmt = Double.parseDouble(WithdrawAmtFIELD.getText());
							WithdrawAmtFIELD.setText("");
							
							if (withdrawAmt <=0) {
								current.withdraw(withdrawAmt);
								JOptionPane.showMessageDialog(frame , "Invalid");
								WithdrawAmtFIELD.setText("");
								return;
							}
							
							if (withdrawAmt > current.balance) {
								JOptionPane.showMessageDialog(frame , "Insufficient Amount");
								WithdrawAmtFIELD.setText("");
								return;
							}

							if (current.withdraw(withdrawAmt)) {
								JOptionPane.showMessageDialog(frame , "Sucessful");
								layout.show(frame.getContentPane(), AccountScreenCardID);
							} else {
								JOptionPane.showMessageDialog(frame , "Insufficient Funds");
								layout.show(frame.getContentPane(), AccountScreenCardID);
							}
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(frame , "Please Input An Amount");

						}
					}
				});
				CompleteWithdrawBTN.setBounds(154, 161, 125, 23);
				WithdrawPanel.add(CompleteWithdrawBTN);

				JButton BackWithdraw = new JButton("Back");
				BackWithdraw.setFont(new Font("Bell MT", Font.PLAIN, 15));
				BackWithdraw.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), AccountScreenCardID);
					}
				});
				BackWithdraw.setBounds(10, 11, 89, 23);
				WithdrawPanel.add(BackWithdraw);

				layout.show(frame.getContentPane(), WithdrawCardID);
			}
		});
		WithdrawBTN.setBounds(144, 106, 149, 23);
		AccountScreen.add(WithdrawBTN);

		JButton TransferBTN = new JButton("Transfer");
		TransferBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		TransferBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel TransferPanel = new JPanel();
				TransferPanel.setBackground(new Color(245, 255, 250));
				TransferPanel.setLayout(null);
				frame.getContentPane().add(TransferPanel, TransferCardID);

				JLabel TransferHeadingLabel = new JLabel("Transfer");
				TransferHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
				TransferHeadingLabel.setFont(new Font("Bell MT", Font.PLAIN, 30));
				TransferHeadingLabel.setBounds(0, 0, 434, 66);
				TransferPanel.add(TransferHeadingLabel);

				TransferAmountFIELD = new JTextField();
				TransferAmountFIELD.setColumns(10);
				TransferAmountFIELD.setBounds(165, 102, 104, 20);
				TransferPanel.add(TransferAmountFIELD);

				JLabel TransferAmtInstructions = new JLabel("Transfer Amount");
				TransferAmtInstructions.setHorizontalAlignment(SwingConstants.CENTER);
				TransferAmtInstructions.setFont(new Font("Bell MT", Font.PLAIN, 15));
				TransferAmtInstructions.setBounds(0, 64, 434, 48);
				TransferPanel.add(TransferAmtInstructions);

				JButton TransferCompleteBTN = new JButton("Transfer");
				TransferCompleteBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
				TransferCompleteBTN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
						double transferAmt = Double.parseDouble(TransferAmountFIELD.getText());
						TransferAmountFIELD.setText("");

						int transferAcctNum = Integer.parseInt(TransferAccountFIELD.getText());
						TransferAccountFIELD.setText("");
						
						getUserInfo(transferAcctNum, "transfer");
						
						if (transferAmt <=0) {
							current.transferTo(transferAmt, temp);
							JOptionPane.showMessageDialog(frame , "Invalid Amount");
							TransferAccountFIELD.setText("");
							TransferAmountFIELD.setText("");
							return;
						} else if (transferAmt > current.balance) {
							JOptionPane.showMessageDialog(frame , "Invalid Amount");
							TransferAccountFIELD.setText("");
							TransferAmountFIELD.setText("");
							return;
						} else if (current.acctNum == temp.acctNum) {
							JOptionPane.showMessageDialog(frame , "Can't transfer money to yourself");
						} else if (current.transferTo(transferAmt, temp)) {
							JOptionPane.showMessageDialog(frame , "Success");
							layout.show(frame.getContentPane(), AccountScreenCardID);

						} else {
							JOptionPane.showMessageDialog(frame , "Insufficient Funds or Invalid Account Number");
						}
						
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(frame , "Account isn't in our system");

						}


					}
				});
				TransferCompleteBTN.setBounds(165, 200, 104, 25);
				TransferPanel.add(TransferCompleteBTN);

				JButton BackTransfer = new JButton("Back");
				BackTransfer.setFont(new Font("Bell MT", Font.PLAIN, 15));
				BackTransfer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), AccountScreenCardID);
					}
				});
				BackTransfer.setBounds(10, 11, 89, 23);
				TransferPanel.add(BackTransfer);

				JLabel TransferAccountInstructions = new JLabel("To Which Account");
				TransferAccountInstructions.setHorizontalAlignment(SwingConstants.CENTER);
				TransferAccountInstructions.setFont(new Font("Bell MT", Font.PLAIN, 15));
				TransferAccountInstructions.setBounds(0, 123, 434, 48);
				TransferPanel.add(TransferAccountInstructions);

				TransferAccountFIELD = new JTextField();
				TransferAccountFIELD.setColumns(10);
				TransferAccountFIELD.setBounds(165, 161, 104, 20);
				TransferPanel.add(TransferAccountFIELD);

				layout.show(frame.getContentPane(), TransferCardID);
			}
		});
		TransferBTN.setBounds(144, 140, 149, 23);
		AccountScreen.add(TransferBTN);


		JButton AccountDetailsBTN = new JButton("Account Details");
		AccountDetailsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel AccountInfoPanel = new JPanel();
				AccountInfoPanel.setBackground(new Color(245, 255, 250));
				frame.getContentPane().add(AccountInfoPanel, AccountInfoCardID);
				AccountInfoPanel.setLayout(null);

				JLabel AccountInfoHeading = new JLabel("Account Info");
				AccountInfoHeading.setHorizontalAlignment(SwingConstants.CENTER);
				AccountInfoHeading.setFont(new Font("Bell MT", Font.PLAIN, 30));
				AccountInfoHeading.setBounds(0, 0, 434, 61);
				AccountInfoPanel.add(AccountInfoHeading);

				JButton AccountInfoBackBTN = new JButton("Back");
				AccountInfoBackBTN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), AccountScreenCardID);
					}
				});
				AccountInfoBackBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
				AccountInfoBackBTN.setBounds(10, 11, 89, 23);
				AccountInfoPanel.add(AccountInfoBackBTN);


				JLabel AccountNumberDisplayLabel = new JLabel("Account Number: " + current.acctNum);
				AccountNumberDisplayLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				AccountNumberDisplayLabel.setBounds(10, 70, 240, 23);
				AccountInfoPanel.add(AccountNumberDisplayLabel);

				JLabel PasswordDisplayLabel = new JLabel("Password: "+current.pswd);
				PasswordDisplayLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				PasswordDisplayLabel.setBounds(10, 100, 240, 23);
				AccountInfoPanel.add(PasswordDisplayLabel);

				JLabel BalanceDisplayLabel = new JLabel("Balance: "+ myRound(current.balance));
				BalanceDisplayLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				BalanceDisplayLabel.setBounds(10, 130, 240, 23);
				AccountInfoPanel.add(BalanceDisplayLabel);

				JLabel FirstNameDisplayLabel = new JLabel("First Name: "+current.fName);
				FirstNameDisplayLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				FirstNameDisplayLabel.setBounds(10, 160, 240, 23);
				AccountInfoPanel.add(FirstNameDisplayLabel);

				JLabel LastNameDisplayLabel = new JLabel("Last Name: "+current.lName);
				LastNameDisplayLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				LastNameDisplayLabel.setBounds(10, 190, 240, 23);
				AccountInfoPanel.add(LastNameDisplayLabel);

				layout.show(frame.getContentPane(), AccountInfoCardID);
			}
		});
		AccountDetailsBTN.setFont(new Font("Bell MT", Font.PLAIN, 15));
		AccountDetailsBTN.setBounds(144, 174, 149, 23);
		AccountScreen.add(AccountDetailsBTN);

		JButton TransactionBTM = new JButton("View Transactions");
		TransactionBTM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel TransactionPanel = new JPanel();
				TransactionPanel.setBackground(new Color(245, 255, 250));
				frame.getContentPane().add(TransactionPanel, TransactionCardID);
				TransactionPanel.setLayout(new BorderLayout(0, 0));

				JScrollPane scrollPane = new JScrollPane();
				TransactionPanel.add(scrollPane);

				JTextArea txtrThisIsA = new JTextArea(current.log);
				txtrThisIsA.setFont(new Font("Monospaced", Font.PLAIN, 13));
				txtrThisIsA.setBackground(new Color(245, 255, 250));
				scrollPane.setViewportView(txtrThisIsA);

				JTextArea txtrTransactions = new JTextArea();
				txtrTransactions.setFont(new Font("Bell MT", Font.PLAIN, 30));
				txtrTransactions.setText("                Transactions");
				txtrTransactions.setBackground(new Color(245, 255, 250));
				scrollPane.setColumnHeaderView(txtrTransactions);

				JButton btnNewButton_1 = new JButton("Back");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), AccountScreenCardID);
					}
				});
				btnNewButton_1.setFont(new Font("Bell MT", Font.PLAIN, 15));
				TransactionPanel.add(btnNewButton_1, BorderLayout.SOUTH);
				layout.show(frame.getContentPane(), TransactionCardID);
			}
		});
		TransactionBTM.setFont(new Font("Bell MT", Font.PLAIN, 15));
		TransactionBTM.setBounds(144, 208, 149, 23);
		AccountScreen.add(TransactionBTM);


	}
}
