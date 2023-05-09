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
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

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
	private JTextField depositAmtField;
	private JTextField withdrawAmtField;
	private JTextField transferAmtField;
	private JTextField transferAcctField;

	final int MAX_ACCOUNT_LENGTH=8;
	final int MAX_PSWD_LENGTH=5;
	private JTextField loginTF;
	private JTextField acctNumTF;
	private JTextField pswdTF;
	private JTextField firstNameLoginTF;
	private JTextField lastNameloginTF;
	private JTextField acctNumLoginTF;
	private JTextField pswdLoginTF;

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
		BankAccount b1 = new BankAccount("Darth", "Vader");
		BankAccount b2 = new BankAccount("Obi-Wan", "Kenobi");
		BankAccount b3 = new BankAccount("Luke", "Skywalker");

		bam.addAcct(b1);
		bam.addAcct(b2);
		bam.addAcct(b3);
		bam.display();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(layout);

		JPanel logRegJP = new JPanel();
		frame.getContentPane().add(logRegJP, LOGREG_VIEW_ID);

		JButton loginBtn = new JButton("LOGIN");
		ImageIcon btnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		loginBtn.setIcon(btnImg);
		loginBtn.setBounds(141, 86, 149, 45);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				layout.show(frame.getContentPane(), LOGIN_VIEW_ID); 

			}
		});


		logRegJP.setLayout(null);

		JLabel loginJL = new JLabel("Login");
		loginJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		loginJL.setBounds(188, 86, 102, 45);
		logRegJP.add(loginJL);
		logRegJP.add(loginBtn);

		JLabel registerJL = new JLabel("Register");
		registerJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		registerJL.setBounds(188, 146, 92, 29);
		logRegJP.add(registerJL);

		loginTF = new JTextField("LOGIN");
		loginTF.setBounds(168, 98, 86, 20);
		logRegJP.add(loginTF);
		loginTF.setColumns(10);


		JLabel lblNewLabel = new JLabel("MONKEY MONEY LTD");
		lblNewLabel.setForeground(new Color(0, 0, 160));
		lblNewLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
		lblNewLabel.setBounds(124, 0, 198, 54);
		logRegJP.add(lblNewLabel);

		JButton registerBtn = new JButton("Register");
		ImageIcon regBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));
		registerBtn.setIcon(regBtnImg);
		registerBtn.setBounds(151, 142, 129, 33);
		logRegJP.add(registerBtn);
		JLabel backRoundJL = new JLabel("Backround");
		ImageIcon img = new ImageIcon(this.getClass().getResource("/Front Page.jpg"));
		backRoundJL.setIcon(img);
		backRoundJL.setBounds(-126, 0, 654, 330);
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
		welcomLbl.setBounds(72, 0, 312, 101);
		mainJP.add(welcomLbl);


		JButton depositBtn = new JButton("Deposit");
		depositBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		depositBtn.setBounds(72, 172, 110, 38);
		mainJP.add(depositBtn);
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), DEPOSIT_VIEW_ID); 
			}
		});

		JButton withdrawBtn = new JButton("Withdraw");
		withdrawBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		withdrawBtn.setBounds(305, 177, 110, 33);
		mainJP.add(withdrawBtn);
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), WITHDRAW_VIEW_ID); 
			}
		});

		JButton transferToBtn = new JButton("Transfers");
		transferToBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		transferToBtn.setBounds(72, 221, 110, 33);
		mainJP.add(transferToBtn);
		transferToBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), TRANSFER_VIEW_ID); 
			}
		});


		JButton accountInfoBtn = new JButton("Account Info");
		accountInfoBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		accountInfoBtn.setBounds(305, 221, 110, 33);
		mainJP.add(accountInfoBtn);
		accountInfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), ACCNT_INFO_VIEW_ID); 
			}
		});

		JButton viewTransactionBtn = new JButton("Transactions");
		viewTransactionBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		viewTransactionBtn.setBounds(72, 265, 110, 33);
		mainJP.add(viewTransactionBtn);
		viewTransactionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOG_VIEW_ID); 
			}
		});


		JButton changePswdBtn = new JButton("Change Pass");
		changePswdBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		changePswdBtn.setBounds(305, 265, 110, 33);
		mainJP.add(changePswdBtn);
		changePswdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHNG_PSWD_VIEW_ID); 
			}
		});

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutBtn.setBounds(394, 0, 85, 23);
		mainJP.add(logoutBtn);
		
		JLabel mainBackroundJL = new JLabel();
		mainBackroundJL.setBounds(0, 0, 479, 391);
		mainJP.add(mainBackroundJL);
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGIN_VIEW_ID); 
			}
		});

		JPanel transactionJPanel = new JPanel();
		frame.getContentPane().add(transactionJPanel, LOG_VIEW_ID);
		transactionJPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 23, 434, 238);
		transactionJPanel.add(scrollPane);

		JTextArea textArea = new JTextArea("Hamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\nHamza\n");
		scrollPane.setViewportView(textArea);

		JButton transacEscapeBtn = new JButton("Escape");
		transacEscapeBtn.setBounds(338, 0, 96, 23);
		transactionJPanel.add(transacEscapeBtn);
		transacEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel depositJP = new JPanel();
		frame.getContentPane().add(depositJP, DEPOSIT_VIEW_ID);
		depositJP.setLayout(null);

		JButton depositEscapeBtn = new JButton("Escape");
		depositEscapeBtn.setBounds(345, 0, 89, 23);
		depositJP.add(depositEscapeBtn);

		depositAmtField = new JTextField("0.00");
		depositAmtField.setBounds(77, 109, 86, 20);
		depositJP.add(depositAmtField);
		depositAmtField.setColumns(10);
		depositEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel withdrawJP = new JPanel();
		frame.getContentPane().add(withdrawJP, WITHDRAW_VIEW_ID);
		withdrawJP.setLayout(null);

		JButton withdrawEscapeBtn = new JButton("Escape");
		withdrawEscapeBtn.setBounds(345, 0, 89, 23);
		withdrawJP.add(withdrawEscapeBtn);

		withdrawAmtField = new JTextField("0.00");
		withdrawAmtField.setBounds(124, 106, 86, 20);
		withdrawJP.add(withdrawAmtField);
		withdrawAmtField.setColumns(10);
		withdrawEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel transferJP = new JPanel();
		frame.getContentPane().add(transferJP, TRANSFER_VIEW_ID);
		transferJP.setLayout(null);

		JButton transferEscapeBtn = new JButton("Escape");
		transferEscapeBtn.setBounds(343, 0, 91, 23);
		transferJP.add(transferEscapeBtn);

		transferAmtField = new JTextField("0.00");
		transferAmtField.setBounds(83, 110, 86, 20);
		transferJP.add(transferAmtField);
		transferAmtField.setColumns(10);

		transferAcctField = new JTextField();
		transferAcctField.setBounds(206, 181, 86, 20);
		transferJP.add(transferAcctField);
		transferAcctField.setColumns(10);
		transferEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel changPswdJP = new JPanel();
		frame.getContentPane().add(changPswdJP, CHNG_PSWD_VIEW_ID);
		changPswdJP.setLayout(null);

		JButton pswdEscapeBtn = new JButton("Escape");
		pswdEscapeBtn.setBounds(345, 0, 89, 23);
		changPswdJP.add(pswdEscapeBtn);
		pswdEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel accountInfoJP = new JPanel();
		frame.getContentPane().add(accountInfoJP, ACCNT_INFO_VIEW_ID);
		accountInfoJP.setLayout(null);

		JButton acctinfoEscapeBtn = new JButton("Escape");
		acctinfoEscapeBtn.setBounds(345, 0, 89, 23);
		accountInfoJP.add(acctinfoEscapeBtn);
		acctinfoEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID); 
			}
		});

		JPanel registerJP = new JPanel();
		frame.getContentPane().add(registerJP, REGISTER_VIEW_ID);
		registerJP.setLayout(null);


		JButton registerEscapeBtn = new JButton("Escape");
		ImageIcon registerEscapeBtnImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel escapeRegisterJL = new JLabel("Escape");
		escapeRegisterJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		escapeRegisterJL.setBounds(416, 2, 63, 19);
		registerJP.add(escapeRegisterJL);
		registerEscapeBtn.setIcon(registerEscapeBtnImg);
		registerEscapeBtn.setBounds(393, 0, 86, 23);
		registerJP.add(registerEscapeBtn);
		registerEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGREG_VIEW_ID); 
			}
		});


		firstNameField = new JTextField("First Name");
		firstNameField.setBounds(10, 21, 103, 28);
		registerJP.add(firstNameField);
		firstNameField.setColumns(10);

		lastNameField = new JTextField("Last Name");
		lastNameField.setBounds(145, 21, 96, 28);
		registerJP.add(lastNameField);
		lastNameField.setColumns(10);

		JLabel registerbackroundJL = new JLabel("New label");
		ImageIcon registerbackroundImg = new ImageIcon(this.getClass().getResource("/animal monkey.jpg"));

		pswdTF = new JTextField("Password");
		pswdTF.setBounds(145, 60, 96, 28);
		registerJP.add(pswdTF);
		pswdTF.setColumns(10);

		JButton submitBtn = new JButton();
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID);
			}
		});
		ImageIcon subitBtnJLImg = new ImageIcon(this.getClass().getResource("/food3.png"));

		JLabel submitBtnJL_1 = new JLabel("Submit");
		submitBtnJL_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		submitBtnJL_1.setHorizontalAlignment(SwingConstants.LEFT);
		submitBtnJL_1.setBounds(301, 108, 103, 63);
		registerJP.add(submitBtnJL_1);
		submitBtn.setIcon(subitBtnJLImg);
		submitBtn.setBounds(288, 99, 94, 72);
		registerJP.add(submitBtn);

		acctNumTF = new JTextField("Account Number");
		acctNumTF.setBounds(10, 60, 103, 28);
		registerJP.add(acctNumTF);
		acctNumTF.setColumns(10);
		registerbackroundJL.setIcon(registerbackroundImg);
		registerbackroundJL.setBounds(-424, 0, 903, 391);
		registerJP.add(registerbackroundJL);

		JPanel loginJP = new JPanel();
		loginJP.setBackground(new Color(245, 255, 250));
		frame.getContentPane().add(loginJP, LOGIN_VIEW_ID);
		loginJP.setLayout(null);

		JButton loginEscapeBtn = new JButton("Escape");
		ImageIcon loginEscapeImg = new ImageIcon(this.getClass().getResource("/fruit.jpg"));

		JLabel loginEscapeJL = new JLabel("Escape");
		loginEscapeJL.setFont(new Font("Tahoma", Font.BOLD, 12));
		loginEscapeJL.setBounds(416, 1, 73, 19);
		loginJP.add(loginEscapeJL);

		JLabel loginIDJL = new JLabel();
		ImageIcon loginIDJLImg = new ImageIcon(this.getClass().getResource("/images.jfif"));

		JLabel submitBtnJL_1_1 = new JLabel("Submit");
		submitBtnJL_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		submitBtnJL_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		submitBtnJL_1_1.setBounds(267, 166, 122, 63);
		loginJP.add(submitBtnJL_1_1);

		JButton loginSubmitBtn = new JButton();
		loginSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_VIEW_ID);
			}
		});
		ImageIcon loginSubmitBtnImg = new ImageIcon(this.getClass().getResource("/food3.png"));
		loginSubmitBtn.setIcon(loginSubmitBtnImg);
		loginSubmitBtn.setBounds(251, 157, 86, 63);
		loginJP.add(loginSubmitBtn);

		pswdLoginTF = new JTextField("Password");
		pswdLoginTF.setBounds(132, 199, 86, 20);
		loginJP.add(pswdLoginTF);
		pswdLoginTF.setColumns(10);

		acctNumLoginTF = new JTextField("Account Number");
		acctNumLoginTF.setBounds(22, 199, 86, 20);
		loginJP.add(acctNumLoginTF);
		acctNumLoginTF.setColumns(10);

		firstNameLoginTF = new JTextField("First Name");
		firstNameLoginTF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		firstNameLoginTF.setBounds(22, 156, 86, 20);
		loginJP.add(firstNameLoginTF);
		firstNameLoginTF.setColumns(10);

		lastNameloginTF = new JTextField("Last Name");
		lastNameloginTF.setBounds(132, 156, 86, 20);
		loginJP.add(lastNameloginTF);
		lastNameloginTF.setColumns(10);
		loginIDJL.setIcon(loginIDJLImg);
		loginIDJL.setBounds(10, 146, 232, 84);
		loginJP.add(loginIDJL);
		loginEscapeBtn.setIcon(loginEscapeImg);
		loginEscapeBtn.setBounds(400, 0, 89, 23);
		loginJP.add(loginEscapeBtn);

		JLabel loginBackRoundJL = new JLabel("");
		ImageIcon loginBackRoundImg = new ImageIcon(this.getClass().getResource("/Monkeyyy.jpg"));
		loginBackRoundJL.setIcon(loginBackRoundImg);
		loginBackRoundJL.setBounds(-41, 1, 530, 390);
		loginJP.add(loginBackRoundJL);
		loginEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGREG_VIEW_ID); 
			}
		});


	}
}




/* MONKEY MONEY LTD
ImageIcon img = new ImageIcon(this.getClass().getResource("/"));
		lblNewLabel.setIcon(img);*/
