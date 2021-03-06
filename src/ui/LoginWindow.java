package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.Beans;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.AbstractDocument;

import control.MainControl;
import domain.PatientManager;
import net.miginfocom.swing.MigLayout;

public class LoginWindow extends JFrame implements ActionListener,FocusListener{

	private JPanel longinPanel=new JPanel(new MigLayout("wrap 3","[grow][grow][grow]","[][][][]"));
	private JLabel userNameLabel=new JLabel("Username");
	private JLabel passwordLabel=new JLabel("Password");
	private JTextField usernameTextField=new JTextField(10);
	private JPasswordField passwordTextField=new JPasswordField(10);
	private JButton connectButton=new JButton("Connect");
	private JButton cancelButton=new JButton("Cancel");
	private JLabel msgLabel=new JLabel("                ");

	public LoginWindow() {
		super("Login Window");
		this.setSize(250,150);
		longinPanel.setLayout (new MigLayout()); 
		longinPanel.add(userNameLabel,"alignx center,aligny center");
		longinPanel.add(usernameTextField,"wrap,alignx center,aligny center");
		longinPanel.add(passwordLabel,"center");
		longinPanel.add(passwordTextField,"wrap, center");
		longinPanel.add(connectButton,"left");
		longinPanel.add(cancelButton, "wrap, right");
		
		usernameTextField.setDocument(new JTextFieldLimit(10));
		passwordTextField.setDocument(new JTextFieldLimit(10));
		
		longinPanel.add(msgLabel,"span");
		msgLabel.setForeground(Color.red);


		connectButton.addActionListener(this);
		cancelButton.addActionListener(this);	
		usernameTextField.addActionListener(this);
		passwordTextField.addActionListener(this);

		getContentPane().add(longinPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusableWindowState(true);
		this.addFocusListener(this);
		usernameTextField.addFocusListener(this);
//		this.addWindowFocusListener(new WindowAdapter() {
//			
//
//public void windowLostFocus(WindowEvent evt) {
//requestFocusInWindow();
//}
//});
		this.setAlwaysOnTop(true);
		setVisible(true);

	}

	public String getUsername() {
		return usernameTextField.getText();
	}

	public void setUsernameTextField(String username) {
		this.usernameTextField.setText(username);
	}

	public String getPassword() {
		return passwordTextField.getText();
	}

	public void setPasswordTextField(String password) {
		this.passwordTextField.setText(password);
	}


	public boolean checkUserPassLocumPin() throws Exception{
		
		return MainControl.getMainControl().getPatientManager().checkUserPassLocumPin(usernameTextField.getText(),passwordTextField.getText());
			
		}

		//String physician_id=MainControl.getMainControl().getPhysicianID();
		//System.out.println("physician id is"+physician_id);
		//physician table (physician_id	name	username	password	signature)
//		ResultSet userpassResult=PatientManager.getPatientManager().getDBConnection().execQuery("select name,username,password,physician_id,locum,pin from physician");
//
//		while (userpassResult.next()){
//			if(userpassResult.getString("username").equals(this.getUsername())){
//				System.out.println("Correct UserName");
//				System.out.println("User from textField"+userpassResult.getString("username"));
//				System.out.println("User from textField"+this.getUsername());
//				if(userpassResult.getString("password").equals(this.getPassword())){
//					System.out.println("Correct Password");
//					this.setVisible(false);
//					MainControl.getMainControl().setPhysicianID(userpassResult.getString("physician_id"));
//					MainControl.getMainControl().setLocum(userpassResult.getString("Locum"));
//					MainControl.getMainControl().setPhysicianUserName(this.getUsername());
//					MainControl.getMainControl().setPhysicianPassword(this.getPassword());
//					MainControl.getMainControl().setPhysicianName(userpassResult.getString("name"));
//					MainControl.getMainControl().setPhysicianPin(userpassResult.getString("pin"));
//					System.out.println("physician ID is"+userpassResult.getString("physician_id"));
//					return true;
//				}
//				else{
//					System.out.println("Wrong Password");
//					msgLabel.setText("Invalid User and/or Password");
//				}
//			}
//			else{
//				msgLabel.setText("Invalid User and/or Password");
//				System.out.println("Wrong UserName");
//				System.out.println("User from textField"+userpassResult.getString("username"));
//				System.out.println("User from textField"+this.getUsername());
//			}
//
////		}
//
//		return false;
//	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==connectButton || e.getSource()==usernameTextField || e.getSource()==passwordTextField){
			try {
				if(this.checkUserPassLocumPin()){
					System.out.println("correct everything");
					MainWindow.createAndShowGUI();
					this.dispose();

				}
				else{
					this.setUsernameTextField("");
					this.setPasswordTextField("");
					msgLabel.setText("Invalid User and/or Password");

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource()==cancelButton){
			this.dispose();
		}

	}
	public static void main(String args[]) throws Exception{
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		LoginWindow loginWindow=new LoginWindow();
		loginWindow.setLocationRelativeTo(null);

		

		//test.checkUserPass();
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		System.out.println("focus gained");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		System.out.println("focus lost");
		//usernameTextField.requestFocusInWindow();
		//this.setFocusableWindowState(true);
		//this.toFront();
		
	}


}
