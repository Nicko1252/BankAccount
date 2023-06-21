import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import org.json.JSONException;
import org.json.JSONObject;

public class GUI {

	BankAccountManager bam = new BankAccountManager();
	BankAccount currentAccount = null;

	private JFrame frame;
	private CardLayout layout = new CardLayout(0, 0);
	final String MAIN_VIEW_ID = "MAIN VIEW";
	final String LOGREG_VIEW_ID = "LOGIN REGISTER VIEW";
	final String LOG_VIEW_ID = "LOG VIEW";
	final String REGISTER_VIEW_ID = "REGISTER VIEW";
	final String DEPOSIT_VIEW_ID = "DEPOSIT VIEW";
	final String WITHDRAW_VIEW_ID = "WITHDRAW VIEW";
	final String TRANSFER_VIEW_ID = "TRANSFER VIEW";
	final String CHNG_PSWD_VIEW_ID = "CHANGE PASSWORD";
	final String ACCNT_INFO_VIEW_ID = "ACCOUNT INFO";
	final String LOGIN_VIEW_ID = "LOGIN VIEW";
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField depositAmtTF;
	private JTextField withdrawAmtTF;
	private JTextField transferAmtField;
	private JTextField transferToTF;

	JLabel loginJL;

	final int MAX_ACCOUNT_LENGTH=8;
	final int MAX_PSWD_LENGTH=5;
	private JTextField firstNameLoginTF;
	private JTextField lastNameloginTF;
	private JTextField acctNumLoginTF;
	private JPasswordField passwordField;
	private JTextField curentPswdTF;

	//BankAccount current = null;

	/**
	 * Launch the application.
	 */

	/*public void CustomCursor() {
		Toolkit t1 = Toolkit.getDefaultToolkit();
		Image cursorImg = t1.getImage("food3.png");
		Point p = new Point(0,0);
		Cursor c= t1.createCustomCursor(cursorImg,  p, "food3.png");
		setCursor(c);
	}*/

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI window = new GUI();
				window.frame.setVisible(true);
			}
		});


	}
	
	void login() {
		HttpURLConnectionATM http = new HttpURLConnectionATM();
		try {
			String param1 = http.url+"login.php";
			String param2 = "acctNum=" + acctNumLoginTF.getText() + "&password="+ String.valueOf(passwordField.getPassword());
			
			System.out.println(param1 + "    " + param2);
			http.sendPost(param1, param2);
			
			if (http.response !=null) {
				if (http.response.toString().trim().equals("login-success")) {
				//	getUserInfo(Integer.parseInt(acctNumLoginTF.getText()), "login");
					layout.show(frame.getContentPane(), MAIN_VIEW_ID);
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
	//Caused by: java.lang.NoClassDefFoundError: org/json/JSONException


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
					currentAccount = new BankAccount (acctNum, balance, fName, lName, pswd, log);
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
	 * Create the application.
	 */
	public GUI() {
		initialize();

		BankAccount b1 = new BankAccount("Nick", "Ostrowka");
		BankAccount b2 = new BankAccount("Mr", "Zamar");
		BankAccount b3 = new BankAccount("Hamza", "Muhammad");

		bam.addAcct(b1);
		bam.addAcct(b2);
		bam.addAcct(b3);
		bam.display();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {





		/*	System.out.println("B1 account number: "+b1.acctNum+"     password: "+b1.pswd);
				System.out.println("B2 account number: "+b2.acctNum+"     password: "+b2.pswd);
				System.out.println("B3 account number: "+b3.acctNum+"     password: "+b3.pswd);
		 */
		/*
		BankAccount b1 = new BankAccount(MAX_ACCOUNT_LENGTH, 0.0, "fName", "lName", "password", "log");
		BankAccount b2 = new BankAccount(MAX_ACCOUNT_LENGTH, 0.0, "fName", "lName", "password", "log");
		BankAccount b3 = new BankAccount(MAX_ACCOUNT_LENGTH, 0.0, "fName", "lName", "password", "log");


		bam.addAcct(b1);
		bam.addAcct(b2);
		bam.addAcct(b3);
		bam.display();*/

		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(layout);
		frame.setBounds(100, 100, 550, 395);
		//frame.setResizable(false);

		JPanel logRegJP = new JPanel();
		frame.getContentPane().add(logRegJP, LOGREG_VIEW_ID);
		System.out.println(this.getClass().getResource("fruit.jpg").toString());
		ImageIcon btnImg = new ImageIcon(this.getClass().getResource("fruit.jpg"));


		logRegJP.setLayout(null);

		JLabel registerJL = new JLabel("Register");
		registerJL.setForeground(new Color(0, 0, 160));
		registerJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		registerJL.setBounds(233, 190, 51, 57);
		logRegJP.add(registerJL);
		registerJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				registerJL.repaint();
			}
		});

		loginJL = new JLabel(" Login");
		loginJL.setForeground(new Color(0, 0, 160));
		loginJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		loginJL.setBounds(233, 146, 47, 45);
		logRegJP.add(loginJL);

		loginJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				loginJL.repaint();
			}
		});

		JButton loginBtn = new JButton("");
		ImageIcon newBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		loginBtn.setIcon(newBtnImg);

		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGIN_VIEW_ID); 

			}
		});
		loginBtn.setBounds(183, 146, 149, 45);
		logRegJP.add(loginBtn);



		JLabel lblNewLabel = new JLabel("MONKEY MONEY LTD");
		lblNewLabel.setForeground(new Color(0, 0, 160));
		lblNewLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
		lblNewLabel.setBounds(159, 11, 312, 101);
		logRegJP.add(lblNewLabel);

		JButton registerBtn = new JButton("");
		ImageIcon regBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		registerBtn.setIcon(regBtnImg);
		registerBtn.setBounds(193, 201, 129, 33);
		logRegJP.add(registerBtn);
		JLabel backRoundJL = new JLabel("Backround");
		ImageIcon img = new ImageIcon(this.getClass().getResource("/Front Page.jpg"));
		backRoundJL.setIcon(img);
		backRoundJL.setBounds(-111, 0, 675, 385);
		logRegJP.add(backRoundJL);
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), REGISTER_VIEW_ID); 
			}
		});

		JPanel mainJP = new JPanel();
		frame.getContentPane().add(mainJP, MAIN_VIEW_ID);
		mainJP.setLayout(null);


		JLabel welcomLbl = new JLabel("WELCOME To Monkey Money LTD!");
		welcomLbl.setForeground(new Color(0, 0, 160));
		welcomLbl.setFont(new Font("Showcard Gothic", Font.BOLD, 17));
		welcomLbl.setHorizontalAlignment(SwingConstants.CENTER);
		welcomLbl.setBounds(116, 11, 312, 101);
		mainJP.add(welcomLbl);


		JButton depositBtn = new JButton("");
		ImageIcon depositBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel depositBtnJL = new JLabel("  Deposit");
		depositBtnJL.setForeground(new Color(0, 0, 160));
		//depositBtnJL.setText("DEPOSIT");
		depositBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		depositBtnJL.setBounds(84, 173, 57, 38);
		mainJP.add(depositBtnJL);
		depositBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				depositBtnJL.repaint();
			}
		});

		JLabel transferToBtnJL = new JLabel("Transfers");
		transferToBtnJL.setForeground(new Color(0, 0, 160));
		transferToBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		transferToBtnJL.setBounds(88, 222, 62, 37);
		mainJP.add(transferToBtnJL);
		transferToBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				transferToBtnJL.repaint();
			}
		});

		JLabel viewTransactionBtnJL = new JLabel("Transactions");
		viewTransactionBtnJL.setForeground(new Color(0, 0, 160));
		viewTransactionBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		viewTransactionBtnJL.setBounds(84, 261, 77, 38);
		mainJP.add(viewTransactionBtnJL);
		viewTransactionBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				viewTransactionBtnJL.repaint();
			}
		});

		JLabel withdrawBtnJL = new JLabel("Withdraw");
		withdrawBtnJL.setForeground(new Color(0, 0, 160));
		withdrawBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		withdrawBtnJL.setBounds(361, 172, 62, 40);
		mainJP.add(withdrawBtnJL);
		withdrawBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				withdrawBtnJL.repaint();
			}
		});

		JLabel accontInfoBtnJL = new JLabel("Account Info");
		accontInfoBtnJL.setForeground(new Color(0, 0, 160));
		accontInfoBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		accontInfoBtnJL.setBounds(351, 220, 85, 38);
		mainJP.add(accontInfoBtnJL);
		accontInfoBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				accontInfoBtnJL.repaint();
			}
		});

		JLabel changePswdBtnJL = new JLabel("Change Password");
		changePswdBtnJL.setForeground(new Color(0, 0, 160));
		changePswdBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		changePswdBtnJL.setBounds(335, 256, 113, 48);
		mainJP.add(changePswdBtnJL);
		changePswdBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				changePswdBtnJL.repaint();
			}
		});

		JLabel logoutBtnJL = new JLabel("Logout");
		logoutBtnJL.setForeground(new Color(0, 0, 160));
		logoutBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		logoutBtnJL.setBounds(464, -4, 46, 27);
		mainJP.add(logoutBtnJL);
		logoutBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				logoutBtnJL.repaint();
			}
		});
		depositBtn.setIcon(depositBtnImg);
		depositBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		depositBtn.setBounds(59, 173, 121, 38);
		mainJP.add(depositBtn);
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), DEPOSIT_VIEW_ID); 


			}
		});

		JButton withdrawBtn = new JButton("");
		ImageIcon withdrawBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		withdrawBtn.setIcon(withdrawBtnImg);
		withdrawBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		withdrawBtn.setBounds(331, 176, 123, 33);
		mainJP.add(withdrawBtn);
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), WITHDRAW_VIEW_ID); 
			}
		});

		JButton transferToBtn = new JButton("");
		ImageIcon transferToBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		transferToBtn.setIcon(transferToBtnImg);
		transferToBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		transferToBtn.setBounds(59, 222, 121, 33);
		mainJP.add(transferToBtn);
		transferToBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), TRANSFER_VIEW_ID); 
			}
		});


		JButton accountInfoBtn = new JButton("");
		ImageIcon accountInfoBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		accountInfoBtn.setIcon(accountInfoBtnImg);
		accountInfoBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		accountInfoBtn.setBounds(331, 222, 123, 33);
		mainJP.add(accountInfoBtn);
		accountInfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), ACCNT_INFO_VIEW_ID); 


				JPanel accountInfoJP = new JPanel();
				frame.getContentPane().add(accountInfoJP, ACCNT_INFO_VIEW_ID);
				accountInfoJP.setLayout(null);


				ImageIcon acctinfoEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

				JLabel acctInfoNameJL = new JLabel(currentAccount.fName + currentAccount.lName);
				acctInfoNameJL.setFont(new Font("Tahoma", Font.BOLD, 11));
				acctInfoNameJL.setForeground(new Color(0, 0, 160));
				acctInfoNameJL.setBounds(72, 260, 139, 23);
				accountInfoJP.add(acctInfoNameJL);
				JLabel lblNewLabel_2 = new JLabel(currentAccount.balance+" Bananas");
				lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblNewLabel_2.setForeground(new Color(0, 0, 160));
				lblNewLabel_2.setBounds(248, 258, 84, 27);
				accountInfoJP.add(lblNewLabel_2);
				JLabel acctInfoNumJL = new JLabel(currentAccount.acctNum+" ");
				acctInfoNumJL.setFont(new Font("Tahoma", Font.BOLD, 11));
				acctInfoNumJL.setForeground(new Color(0, 0, 160));
				acctInfoNumJL.setBounds(362, 260, 102, 23);
				accountInfoJP.add(acctInfoNumJL);

				JLabel accountInfoEscapeBtnJL = new JLabel("Escape");
				accountInfoEscapeBtnJL.setForeground(new Color(0, 0, 160));
				accountInfoEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
				accountInfoEscapeBtnJL.setBounds(468, 3, 95, 14);
				accountInfoJP.add(accountInfoEscapeBtnJL);

				JLabel accountInfoNameJL = new JLabel("Account Name");
				accountInfoNameJL.setForeground(new Color(0, 0, 160));
				accountInfoNameJL.setFont(new Font("Tahoma", Font.BOLD, 12));
				accountInfoNameJL.setBounds(86, 149, 95, 35);
				accountInfoJP.add(accountInfoNameJL);

				JLabel accountInfoAcctNumJL = new JLabel("Account Number");
				accountInfoAcctNumJL.setForeground(new Color(0, 0, 160));
				accountInfoAcctNumJL.setFont(new Font("Tahoma", Font.BOLD, 12));
				accountInfoAcctNumJL.setBounds(362, 151, 102, 31);
				accountInfoJP.add(accountInfoAcctNumJL);



				JLabel lblAccountInfo = new JLabel("Account Information ");
				lblAccountInfo.setHorizontalAlignment(SwingConstants.CENTER);
				lblAccountInfo.setForeground(new Color(0, 0, 160));
				lblAccountInfo.setFont(new Font("Showcard Gothic", Font.BOLD, 17));
				lblAccountInfo.setBounds(119, 11, 312, 101);
				accountInfoJP.add(lblAccountInfo);
				JButton acctinfoEscapeBtn = new JButton("Escape");
				acctinfoEscapeBtn.setIcon(acctinfoEscapeBtnImg);
				acctinfoEscapeBtn.setBounds(445, 0, 89, 23);
				accountInfoJP.add(acctinfoEscapeBtn);

				JLabel balanceInfoJL = new JLabel("Balance");
				balanceInfoJL.setForeground(new Color(0, 0, 160));
				balanceInfoJL.setFont(new Font("Tahoma", Font.BOLD, 12));
				balanceInfoJL.setBounds(249, 149, 95, 35);
				accountInfoJP.add(balanceInfoJL);

				JLabel accountInfoBackRoundJL = new JLabel("");
				ImageIcon accountInfoBackRoundJLImg = new ImageIcon(this.getClass().getResource("/drawImg.jpg"));
				accountInfoBackRoundJL.setIcon(accountInfoBackRoundJLImg);
				accountInfoBackRoundJL.setBounds(-527, -343, 1148, 699);
				accountInfoJP.add(accountInfoBackRoundJL);
				acctinfoEscapeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
					}
				});
			}
		});

		JButton viewTransactionBtn = new JButton("");
		ImageIcon viewTransactionBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		viewTransactionBtn.setIcon(viewTransactionBtnImg);
		viewTransactionBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		viewTransactionBtn.setBounds(59, 266, 121, 33);
		mainJP.add(viewTransactionBtn);
		viewTransactionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOG_VIEW_ID); 

				JPanel transactionJPanel = new JPanel();
				frame.getContentPane().add(transactionJPanel, LOG_VIEW_ID);
				transactionJPanel.setLayout(null);

				JScrollPane scrollPane = new JScrollPane();
				/*ImageIcon scrollPaneImg = new ImageIcon(this.getClass().getResource("/rainforest.jpg"));
				scrollPane.setIcon(scrollPaneImg);*/
				scrollPane.setBounds(0, 23, 549, 362);
				transactionJPanel.add(scrollPane);

				JTextArea textArea = new JTextArea(currentAccount.log);
				scrollPane.setViewportView(textArea);

				JButton transacEscapeBtn = new JButton("Escape");
				ImageIcon transacEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

				JLabel transactionEscapeBtnJL = new JLabel("Escape");
				transactionEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 17));
				transactionEscapeBtnJL.setBounds(220, 0, 118, 23);
				transactionJPanel.add(transactionEscapeBtnJL);
				transacEscapeBtn.setIcon(transacEscapeBtnImg);
				transacEscapeBtn.setBounds(0, 0, 549, 23);
				transactionJPanel.add(transacEscapeBtn);
				transacEscapeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
					}
				});
			}
		});


		JButton changePswdBtn = new JButton("");
		ImageIcon changePswdBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		changePswdBtn.setIcon(changePswdBtnImg);
		changePswdBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		changePswdBtn.setBounds(330, 266, 123, 33);
		mainJP.add(changePswdBtn);
		changePswdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHNG_PSWD_VIEW_ID); 
			}
		});

		JButton logoutBtn = new JButton("");
		ImageIcon logoutBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		logoutBtn.setIcon(logoutBtnImg);
		logoutBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutBtn.setBounds(449, 0, 85, 23);
		mainJP.add(logoutBtn);

		JLabel mainBackroundJL = new JLabel();
		ImageIcon mainBackroundImg = new ImageIcon(this.getClass().getResource("/backroundforest.jpg"));
		mainBackroundJL.setIcon(mainBackroundImg);
		mainBackroundJL.setBounds(0, 0, 549, 385);
		mainJP.add(mainBackroundJL);
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGIN_VIEW_ID); 
			}
		});/*

		JPanel transactionJPanel = new JPanel();
		frame.getContentPane().add(transactionJPanel, LOG_VIEW_ID);
		transactionJPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		/*ImageIcon scrollPaneImg = new ImageIcon(this.getClass().getResource("/rainforest.jpg"));
		scrollPane.setIcon(scrollPaneImg);*/
		/*	scrollPane.setBounds(0, 23, 549, 362);
		transactionJPanel.add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JButton transacEscapeBtn = new JButton("Escape");
//		ImageIcon transacEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel transactionEscapeBtnJL = new JLabel("Escape");
		transactionEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 17));
		transactionEscapeBtnJL.setBounds(220, 0, 118, 23);
		transactionJPanel.add(transactionEscapeBtnJL);
	//	transacEscapeBtn.setIcon(transacEscapeBtnImg);
		transacEscapeBtn.setBounds(0, 0, 549, 23);
		transactionJPanel.add(transacEscapeBtn);
		transacEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});*/


		JPanel depositJP = new JPanel();
		frame.getContentPane().add(depositJP, DEPOSIT_VIEW_ID);
		depositJP.setLayout(null);

		JButton depositEscapeBtn = new JButton("");
		ImageIcon depositEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel depositEscapeBtnJL = new JLabel("Escape");
		depositEscapeBtnJL.setVerticalAlignment(SwingConstants.TOP);
		depositEscapeBtnJL.setForeground(new Color(0, 0, 160));
		depositEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		depositEscapeBtnJL.setBounds(467, 3, 46, 30);
		depositJP.add(depositEscapeBtnJL);
		depositEscapeBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				depositEscapeBtnJL.repaint();
			}
		});

		JLabel depositSubmit = new JLabel("Submit");
		depositSubmit.setForeground(new Color(0, 0, 160));
		depositSubmit.setFont(new Font("Tahoma", Font.BOLD, 12));
		depositSubmit.setBounds(404, 125, 89, 18);
		depositJP.add(depositSubmit);

		JButton depositSubmitBtn = new JButton();
		ImageIcon depositSubmitBtnImg = new ImageIcon(this.getClass().getResource("/food.png"));
		depositSubmitBtn.setIcon(depositSubmitBtnImg);
		depositSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double depAmt = Double.parseDouble(depositAmtTF.getText());

				double depositAmt = Double.parseDouble(depositAmtTF.getText());
				depositAmtTF.setText("");

				if (depositAmt <=0) {
					currentAccount.deposit(depositAmt);
					JOptionPane.showMessageDialog(frame , "Insufficient Amount");
					depositAmtTF.setText("");
					return;
				}


				if (currentAccount.deposit(depositAmt) == true) {
					JOptionPane.showMessageDialog(frame , "Sucessful");
				} else {
					JOptionPane.showMessageDialog(frame , "Insufficient Amount");
				}
			} 


		});
		depositSubmitBtn.setBounds(417, 182, 21, 20);
		depositJP.add(depositSubmitBtn);

		JLabel depositAmtJL = new JLabel("Deposit Ammount");
		depositAmtJL.setForeground(new Color(0, 0, 160));
		depositAmtJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		depositAmtJL.setBounds(214, 123, 116, 23);
		depositJP.add(depositAmtJL);

		JLabel lblDeposits = new JLabel("Deposits");
		lblDeposits.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeposits.setForeground(new Color(0, 0, 160));
		lblDeposits.setFont(new Font("Showcard Gothic", Font.BOLD, 17));
		lblDeposits.setBounds(113, 11, 312, 101);
		depositJP.add(lblDeposits);
		depositEscapeBtn.setIcon(depositEscapeBtnImg);
		depositEscapeBtn.setBounds(445, 0, 89, 23);
		depositJP.add(depositEscapeBtn);

		depositAmtTF = new JTextField("0.00");
		depositAmtTF.setHorizontalAlignment(SwingConstants.CENTER);
		depositAmtTF.setBounds(214, 182, 116, 20);
		depositJP.add(depositAmtTF);
		depositAmtTF.setColumns(10);

		JLabel depositBackroundJL = new JLabel("");
		ImageIcon depositBackroundImg = new ImageIcon(this.getClass().getResource("/newVault.jpg"));
		depositBackroundJL.setIcon(depositBackroundImg);
		depositBackroundJL.setBounds(-206, 3, 740, 393);
		depositJP.add(depositBackroundJL);
		depositEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel withdrawJP = new JPanel();
		frame.getContentPane().add(withdrawJP, WITHDRAW_VIEW_ID);
		withdrawJP.setLayout(null);

		JButton withdrawEscapeBtn = new JButton("");
		ImageIcon withdrawEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel withdrawEscapeBtnJL = new JLabel("Escape");
		withdrawEscapeBtnJL.setVerticalAlignment(SwingConstants.TOP);
		withdrawEscapeBtnJL.setForeground(new Color(0, 0, 160));
		withdrawEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		withdrawEscapeBtnJL.setBounds(464, 3, 41, 33);
		withdrawJP.add(withdrawEscapeBtnJL);
		withdrawEscapeBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				withdrawEscapeBtnJL.repaint();
			}
		});

		JLabel withdrawSubmitJL = new JLabel("Submit");
		withdrawSubmitJL.setForeground(new Color(0, 0, 160));
		withdrawSubmitJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		withdrawSubmitJL.setBounds(404, 125, 89, 18);
		withdrawJP.add(withdrawSubmitJL);

		JButton withdrawSubmitBtn = new JButton();
		ImageIcon withdrawSubmitBtnImg = new ImageIcon(this.getClass().getResource("/food.png"));
		withdrawSubmitBtn.setIcon(withdrawSubmitBtnImg);
		withdrawSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double withAmt = Double.parseDouble(withdrawAmtTF.getText());

				double withdrawAmt = Double.parseDouble(withdrawAmtTF.getText());
				withdrawAmtTF.setText("");

				if (withdrawAmt <=0) {
					currentAccount.widthdraw(withdrawAmt);
					JOptionPane.showMessageDialog(frame , "Invalid");
					withdrawAmtTF.setText("");
					return;
				}

				if (withdrawAmt > currentAccount.balance) {
					JOptionPane.showMessageDialog(frame , "Insufficient Amount");
					withdrawAmtTF.setText("");
					return;
				}

				if (currentAccount.widthdraw(withdrawAmt)) {
					JOptionPane.showMessageDialog(frame , "Sucessful");
				} else {
					JOptionPane.showMessageDialog(frame , "Insufficient Funds");
				}



			}
		});
		withdrawSubmitBtn.setBounds(417, 182, 21, 20);
		withdrawJP.add(withdrawSubmitBtn);

		JLabel lblWithdraws = new JLabel("Withdraws");
		lblWithdraws.setHorizontalAlignment(SwingConstants.CENTER);
		lblWithdraws.setForeground(new Color(0, 0, 160));
		lblWithdraws.setFont(new Font("Showcard Gothic", Font.BOLD, 17));
		lblWithdraws.setBounds(113, 11, 312, 101);
		withdrawJP.add(lblWithdraws);

		JLabel withdrawAmtJL = new JLabel("Withdraw Ammount");
		withdrawAmtJL.setForeground(new Color(0, 0, 160));
		withdrawAmtJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		withdrawAmtJL.setBounds(214, 123, 125, 23);
		withdrawJP.add(withdrawAmtJL);
		withdrawEscapeBtn.setIcon(withdrawEscapeBtnImg);
		withdrawEscapeBtn.setBounds(445, 0, 89, 23);
		withdrawJP.add(withdrawEscapeBtn);

		withdrawAmtTF = new JTextField("0.00");
		withdrawAmtTF.setHorizontalAlignment(SwingConstants.CENTER);
		withdrawAmtTF.setBounds(214, 182, 116, 20);
		withdrawJP.add(withdrawAmtTF);
		withdrawAmtTF.setColumns(10);

		JLabel withdrawBackRoundImgJL = new JLabel("");
		ImageIcon withdrawBackRoundImgJLImg = new ImageIcon(this.getClass().getResource("/depwithImg.jpg"));
		withdrawBackRoundImgJL.setIcon(withdrawBackRoundImgJLImg);
		withdrawBackRoundImgJL.setBounds(-297, 0, 831, 503);
		withdrawJP.add(withdrawBackRoundImgJL);
		withdrawEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel transferJP = new JPanel();
		frame.getContentPane().add(transferJP, TRANSFER_VIEW_ID);
		transferJP.setLayout(null);

		JButton transferEscapeBtn = new JButton("");
		ImageIcon transferEscapeBtnimg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));



		JLabel transferEscapeBtnJL = new JLabel("Esacpe");
		transferEscapeBtnJL.setVerticalAlignment(SwingConstants.TOP);
		transferEscapeBtnJL.setForeground(new Color(0, 0, 160));
		transferEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		transferEscapeBtnJL.setBounds(467, 3, 41, 30);
		transferJP.add(transferEscapeBtnJL);
		transferEscapeBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				transferEscapeBtnJL.repaint();
			}
		});


		JLabel transferSubmitJL = new JLabel("Submit");
		transferSubmitJL.setForeground(new Color(0, 0, 160));
		transferSubmitJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		transferSubmitJL.setBounds(394, 206, 89, 18);
		transferJP.add(transferSubmitJL);

		JButton transferSubmitBtn = new JButton();
		ImageIcon transferSubmitBtnImg = new ImageIcon(this.getClass().getResource("/food.png"));
		transferSubmitBtn.setIcon(transferSubmitBtnImg);
		transferSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					double transferAmt = Double.parseDouble(transferAmtField.getText());
					transferAmtField.setText("");

					int transferAcctNum = Integer.parseInt(transferToTF.getText());
					transferToTF.setText("");


					if (transferAmt <=0) {
						JOptionPane.showMessageDialog(frame , "Invalid Amount");
						transferToTF.setText("");
						transferAmtField.setText("");
						return;
					} else if (transferAmt > currentAccount.balance) {
						JOptionPane.showMessageDialog(frame , "Invalid Amount");
						transferToTF.setText("");
						transferAmtField.setText("");
						return;
					}  else {


						for(int i = 0; i<bam.numAccts; i++) {
							if(transferAcctNum == bam.accountArray[i].acctNum) {
								currentAccount.transferTo(transferAmt, bam.accountArray[i]);
								JOptionPane.showMessageDialog(frame , "Transfer Successful");
							}
						}
						//	JOptionPane.showMessageDialog(frame , "Insufficient Funds or Invalid Account Number");
					}

				} catch(Exception e1) {
					JOptionPane.showMessageDialog(frame , "Account isn't in our system");

				}


			}

		}
				);
		transferSubmitBtn.setBounds(404, 238, 21, 20);
		transferJP.add(transferSubmitBtn);

		JLabel lblTransfers = new JLabel("Transfers");
		lblTransfers.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransfers.setForeground(new Color(0, 0, 160));
		lblTransfers.setFont(new Font("Showcard Gothic", Font.BOLD, 17));
		lblTransfers.setBounds(113, 11, 312, 101);
		transferJP.add(lblTransfers);

		JLabel lblNewLabel_12 = new JLabel("Transfer To ");
		lblNewLabel_12.setForeground(new Color(0, 0, 160));
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_12.setBounds(231, 124, 116, 23);
		transferJP.add(lblNewLabel_12);
		transferEscapeBtn.setIcon(transferEscapeBtnimg);
		transferEscapeBtn.setBounds(443, 0, 91, 23);
		transferJP.add(transferEscapeBtn);

		JLabel transferAmtJL = new JLabel("Transfer Ammount");
		transferAmtJL.setForeground(new Color(0, 0, 160));
		transferAmtJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		transferAmtJL.setBounds(214, 204, 116, 23);
		transferJP.add(transferAmtJL);

		transferAmtField = new JTextField("0.00");
		transferAmtField.setHorizontalAlignment(SwingConstants.CENTER);
		transferAmtField.setBounds(214, 238, 116, 20);
		transferJP.add(transferAmtField);
		transferAmtField.setColumns(10);

		transferToTF = new JTextField();
		transferToTF.setBounds(214, 158, 116, 20);
		transferJP.add(transferToTF);
		transferToTF.setColumns(10);
		transferEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JLabel transferBackRoundJL = new JLabel("");
		ImageIcon transferBackRoundJLImg = new ImageIcon(this.getClass().getResource("/monkeyONE.jpg"));
		transferBackRoundJL.setIcon(transferBackRoundJLImg);
		transferBackRoundJL.setBounds(-127, -121, 830, 512);
		transferJP.add(transferBackRoundJL);

		JPanel changPswdJP = new JPanel();
		frame.getContentPane().add(changPswdJP, CHNG_PSWD_VIEW_ID);
		changPswdJP.setLayout(null);

		JButton pswdEscapeBtn = new JButton("");
		pswdEscapeBtn.setForeground(new Color(0, 0, 160));
		ImageIcon pswdEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel lblChangeReset = new JLabel("Password Reset");
		lblChangeReset.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangeReset.setForeground(new Color(0, 0, 160));
		lblChangeReset.setFont(new Font("Showcard Gothic", Font.BOLD, 17));
		lblChangeReset.setBounds(109, 11, 312, 101);
		changPswdJP.add(lblChangeReset);

		JLabel changePswdJL = new JLabel("Submit");
		changePswdJL.setForeground(new Color(0, 0, 160));
		changePswdJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		changePswdJL.setBounds(450, 161, 89, 18);
		changPswdJP.add(changePswdJL);

		JButton changePswdSubmitBtn = new JButton();
		ImageIcon changePswdSubmitBtnImg = new ImageIcon(this.getClass().getResource("/food.png"));
		changePswdSubmitBtn.setIcon(changePswdSubmitBtnImg);
		changePswdSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String oldPassowrd = curentPswdTF.getText();
				curentPswdTF.setText("");

				if (oldPassowrd.length() >0) {
					String newPass = currentAccount.genPswd(MAX_PSWD_LENGTH);
					currentAccount.pswd = newPass;
					JOptionPane.showMessageDialog(frame , "Password Reset "+currentAccount.pswd);
					System.out.println("New password: "+currentAccount.pswd);
				} else {
					JOptionPane.showMessageDialog(frame , "Fields Cannot Be Empty");
					layout.show(frame.getContentPane(), CHNG_PSWD_VIEW_ID);

				}

			}
		});
		changePswdSubmitBtn.setIcon(changePswdSubmitBtnImg);
		changePswdSubmitBtn.setBounds(460, 216, 21, 20);
		changPswdJP.add(changePswdSubmitBtn);

		curentPswdTF = new JTextField();
		curentPswdTF.setBounds(215, 216, 97, 20);
		changPswdJP.add(curentPswdTF);
		curentPswdTF.setColumns(10);

		JLabel currentPswdJL = new JLabel("Current Password");
		currentPswdJL.setForeground(new Color(0, 0, 160));
		currentPswdJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		currentPswdJL.setBounds(204, 159, 116, 23);
		changPswdJP.add(currentPswdJL);
		currentPswdJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				currentPswdJL.repaint();
			}
		});


		JLabel changePswdEscapeBtnJL = new JLabel("Escape");
		changePswdEscapeBtnJL.setVerticalAlignment(SwingConstants.TOP);
		changePswdEscapeBtnJL.setForeground(new Color(0, 0, 160));
		changePswdEscapeBtnJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		changePswdEscapeBtnJL.setBounds(470, 3, 41, 32);
		changPswdJP.add(changePswdEscapeBtnJL);
		pswdEscapeBtn.setIcon(pswdEscapeBtnImg);
		pswdEscapeBtn.setBounds(450, 0, 89, 23);
		changPswdJP.add(pswdEscapeBtn);

		JLabel changePswdBackRoundJL = new JLabel("");
		ImageIcon changePswdBackRoundJLImg = new ImageIcon(this.getClass().getResource("/rainforest.jpg"));
		changePswdBackRoundJL.setIcon(changePswdBackRoundJLImg);
		changePswdBackRoundJL.setBounds(-25, -96, 644, 550);
		changPswdJP.add(changePswdBackRoundJL);
		pswdEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});


		JPanel registerJP = new JPanel();
		frame.getContentPane().add(registerJP, REGISTER_VIEW_ID);
		registerJP.setLayout(null);


		JButton registerEscapeBtn = new JButton("");
		ImageIcon registerEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		ImageIcon btnNewButtonImg = new ImageIcon(this.getClass().getResource("/"));

		JLabel escapeRegisterJL = new JLabel("Escape");
		escapeRegisterJL.setVerticalAlignment(SwingConstants.TOP);
		escapeRegisterJL.setForeground(new Color(0, 0, 160));
		escapeRegisterJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		escapeRegisterJL.setBounds(471, 2, 41, 44);
		registerJP.add(escapeRegisterJL);
		escapeRegisterJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				escapeRegisterJL.repaint();
			}
		});

		JLabel fNameRegJL = new JLabel("First Name");
		fNameRegJL.setForeground(new Color(0, 0, 160));
		fNameRegJL.setFont(new Font("Tahoma", Font.BOLD, 11));
		fNameRegJL.setBounds(110, 32, 86, 14);
		registerJP.add(fNameRegJL);

		JLabel lNameRegJL = new JLabel("Last Name");
		lNameRegJL.setForeground(new Color(0, 0, 160));
		lNameRegJL.setFont(new Font("Tahoma", Font.BOLD, 11));
		lNameRegJL.setBounds(239, 32, 63, 14);
		registerJP.add(lNameRegJL);
		registerEscapeBtn.setIcon(registerEscapeBtnImg);
		registerEscapeBtn.setBounds(448, 1, 86, 23);
		registerJP.add(registerEscapeBtn);
		registerEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGREG_VIEW_ID); 
			}
		});


		firstNameField = new JTextField("");
		firstNameField.setBounds(93, 57, 103, 28);
		registerJP.add(firstNameField);
		firstNameField.setColumns(10);

		lastNameField = new JTextField("");
		lastNameField.setBounds(226, 57, 96, 28);
		registerJP.add(lastNameField);
		lastNameField.setColumns(10);

		JLabel registerbackroundJL = new JLabel("New label");
		ImageIcon registerbackroundImg = new ImageIcon(this.getClass().getResource("/animal monkey.jpg"));

		JButton submitBtn = new JButton();
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {



				String userFirstName = firstNameField.getText();
				firstNameField.setText("");
				String userLastName = lastNameField.getText();	
				lastNameField.setText("");


				BankAccount newAcct = new BankAccount (userFirstName, userLastName);
				bam.addAcct(newAcct);

				JOptionPane.showMessageDialog(frame , "Account Number: "+newAcct.acctNum+"\nPassword: "+newAcct.pswd);


				layout.show(frame.getContentPane(), LOGIN_VIEW_ID);

				System.out.println(newAcct.fName + "account Number: "+newAcct.acctNum+"     password: "+newAcct.pswd);



				if (userFirstName.length() >0 && userLastName.length()>0) {
					JOptionPane.showMessageDialog(frame , "Account Created");
				} else {
					JOptionPane.showMessageDialog(frame , "Fields Cannot Be Empty");
					layout.show(frame.getContentPane(), REGISTER_VIEW_ID);

				}

			}
		});
		ImageIcon subitBtnJLImg = new ImageIcon(this.getClass().getResource("/food3.png"));

		JLabel regSubmitBtnJL = new JLabel("Submit");
		regSubmitBtnJL.setForeground(new Color(0, 0, 160));
		regSubmitBtnJL.setFont(new Font("Tahoma", Font.BOLD, 13));
		regSubmitBtnJL.setHorizontalAlignment(SwingConstants.LEFT);
		regSubmitBtnJL.setBounds(366, 107, 52, 64);
		registerJP.add(regSubmitBtnJL);
		submitBtn.setIcon(subitBtnJLImg);
		submitBtn.setBounds(355, 101, 86, 70);
		registerJP.add(submitBtn);
		registerbackroundJL.setIcon(registerbackroundImg);
		registerbackroundJL.setBounds(-352, 0, 901, 391);
		registerJP.add(registerbackroundJL);
		regSubmitBtnJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				regSubmitBtnJL.repaint();
			}
		});



		JPanel loginJP = new JPanel();
		loginJP.setBackground(new Color(245, 255, 250));
		frame.getContentPane().add(loginJP, LOGIN_VIEW_ID);
		loginJP.setLayout(null);

		JButton loginEscapeBtn = new JButton("");
		ImageIcon loginEscapeImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel loginEscapeJL = new JLabel("Escape");
		loginEscapeJL.setVerticalAlignment(SwingConstants.TOP);
		loginEscapeJL.setForeground(new Color(0, 0, 160));
		loginEscapeJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		loginEscapeJL.setBounds(473, 1, 41, 36);
		loginJP.add(loginEscapeJL);
		loginEscapeJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				loginEscapeJL.repaint();
			}
		});

		JLabel loginIDJL = new JLabel();
		//	ImageIcon loginIDJLImg = new ImageIcon(this.getClass().getResource("images.jfif"));

		JButton revealPswdLoginBtn = new JButton("");
		ImageIcon revealPswdLoginBtnImg = new ImageIcon(this.getClass().getResource("/food.png"));
		revealPswdLoginBtn.setIcon(revealPswdLoginBtnImg);
		revealPswdLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(revealPswdLoginBtn.isSelected()) {
				}
				passwordField.setEchoChar((char)0);
			}
		});
		revealPswdLoginBtn.setBounds(226, 202, 19, 17);
		loginJP.add(revealPswdLoginBtn);

		JLabel acctNameLoginJL = new JLabel("Accont Number");
		acctNameLoginJL.setForeground(new Color(0, 0, 160));
		acctNameLoginJL.setFont(new Font("Tahoma", Font.BOLD, 11));
		acctNameLoginJL.setBounds(22, 185, 86, 19);
		loginJP.add(acctNameLoginJL);

		JLabel lNameLoginJL = new JLabel("Last Name");
		lNameLoginJL.setForeground(new Color(0, 0, 160));
		lNameLoginJL.setFont(new Font("Dialog", Font.BOLD, 12));
		lNameLoginJL.setBounds(141, 145, 68, 14);
		loginJP.add(lNameLoginJL);

		JLabel fNameLoginJL = new JLabel("First Name");
		fNameLoginJL.setForeground(new Color(0, 0, 160));
		fNameLoginJL.setFont(new Font("Tahoma", Font.BOLD, 11));
		fNameLoginJL.setBounds(35, 146, 73, 14);
		loginJP.add(fNameLoginJL);

		JLabel pswdLoginJL = new JLabel("Password");
		pswdLoginJL.setForeground(new Color(0, 0, 160));
		pswdLoginJL.setFont(new Font("Tahoma", Font.BOLD, 11));
		pswdLoginJL.setBounds(142, 187, 86, 14);
		loginJP.add(pswdLoginJL);

		passwordField = new JPasswordField("");
		passwordField.setBounds(131, 200, 86, 20);
		loginJP.add(passwordField);

		JLabel submitLoginJL = new JLabel("Submit");
		submitLoginJL.setForeground(new Color(0, 0, 160));
		submitLoginJL.setHorizontalAlignment(SwingConstants.LEFT);
		submitLoginJL.setFont(new Font("Tahoma", Font.BOLD, 13));
		submitLoginJL.setBounds(284, 157, 50, 73);
		loginJP.add(submitLoginJL);
		submitLoginJL.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me) {

				//loginJL.revalidate();
				//loginJL.setVisible(true);
				submitLoginJL.repaint();
			}
		});

		JButton loginSubmitBtn = new JButton();
		loginSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				login();

			/*	try {
					int userAccountNum = Integer.parseInt(acctNumLoginTF.getText());
					String userPassword = String.valueOf(passwordField.getPassword());

					if (bam.isAcctInArray(userAccountNum)) {
						BankAccount compare = bam.getAcct(userAccountNum);

						if(compare.pswd.equals(userPassword) && compare.acctNum == userAccountNum) {
							currentAccount = compare;

							JOptionPane.showMessageDialog(frame , "Successfully Logged In");
							layout.show(frame.getContentPane(), MAIN_VIEW_ID);
							acctNumLoginTF.setText("");
							passwordField.setText("");
						} else {
							JOptionPane.showMessageDialog(frame , "Incorrect Credentials");
							acctNumLoginTF.setText("");
							passwordField.setText("");
						}

					} else {
						JOptionPane.showMessageDialog(frame , "Incorrect Credentials");
						acctNumLoginTF.setText("");
						passwordField.setText("");
					}
				}
				catch (NumberFormatException exp) {

				}*/

			}
		});
		ImageIcon loginSubmitBtnImg = new ImageIcon(this.getClass().getResource("/food3.png"));
		loginSubmitBtn.setIcon(loginSubmitBtnImg);
		loginSubmitBtn.setBounds(272, 157, 86, 63);
		loginJP.add(loginSubmitBtn);

		acctNumLoginTF = new JTextField("");
		acctNumLoginTF.setBounds(22, 199, 86, 20);
		loginJP.add(acctNumLoginTF);
		acctNumLoginTF.setColumns(10);

		firstNameLoginTF = new JTextField("");
		firstNameLoginTF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		firstNameLoginTF.setBounds(22, 157, 86, 20);
		loginJP.add(firstNameLoginTF);
		firstNameLoginTF.setColumns(10);

		lastNameloginTF = new JTextField("");
		lastNameloginTF.setBounds(131, 157, 86, 20);
		loginJP.add(lastNameloginTF);
		lastNameloginTF.setColumns(10);
		//	loginIDJL.setIcon(loginIDJLImg);
		loginIDJL.setBounds(10, 146, 247, 84);
		loginJP.add(loginIDJL);
		loginEscapeBtn.setIcon(loginEscapeImg);
		loginEscapeBtn.setBounds(445, 0, 89, 23);
		loginJP.add(loginEscapeBtn);

		JLabel loginBackRoundJL = new JLabel("");
		ImageIcon loginBackRoundImg = new ImageIcon(this.getClass().getResource("/Monkeyyy.jpg"));
		loginBackRoundJL.setIcon(loginBackRoundImg);
		loginBackRoundJL.setBounds(-41, 1, 575, 390);
		loginJP.add(loginBackRoundJL);
		loginEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentAccount = null;
				layout.show(frame.getContentPane(), LOGREG_VIEW_ID); 
			}
		});




	}
	
	
}

/*
 * <?php
$account_number = $_POST['acctNum'];
$pswd = $_POST['password'];
$con = new mysqli("localhost", "id20923577_nickatm", "-K2!QXm}v9!n", "nick8ATM!?");
if($con->connect_error){
    die("connection failed: " . $con->connect_error);
}
$table = "Account";
$query = "SELECT * FROM $table WHERE acct_num = '$account_number' AND password = '$pswd'";
$sql = mysqli_query($con, $query);

if(mysqli_num_rows($sql) > 0){
    echo "login=success";
}
else {
    if(!mysqli_query($con, $query)) {
        die('Error: ' . mysqli_error($con));
    } 
    else {
    echo $pswd;
}
} 

*/
 



/* MONKEY MONEY LTD
ImageIcon img = new ImageIcon(this.getClass().getResource("/"));
		lblNewLabel.setIcon(img);  nick8ATM!?  sushi1*/
