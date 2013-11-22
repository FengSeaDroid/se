package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import control.MainControl;
import domain.PatientManager;
import net.miginfocom.swing.MigLayout;

public class LoginWindow extends JFrame implements ActionListener{

	private JPanel longinPanel=new JPanel(new MigLayout("wrap 3","[grow][grow][grow]","[][][][]"));
	private JLabel userNameLabel=new JLabel("Username");
	private JLabel passwordLabel=new JLabel("Password");
	private JTextField usernameTextField=new JTextField(10);
	private JPasswordField passwordTextField=new JPasswordField(10);
	private JButton connectButton=new JButton("Connect");
	private JButton cancelButton=new JButton("Cancel");

	public LoginWindow() {
		super("Login Window");
		this.setSize(250,130);
		longinPanel.setLayout (new MigLayout()); 
		longinPanel.add(userNameLabel,"alignx center,aligny center");
		longinPanel.add(usernameTextField,"wrap,alignx center,aligny center");
		longinPanel.add(passwordLabel,"center");
		longinPanel.add(passwordTextField,"wrap, center");
		longinPanel.add(connectButton,"left");
		longinPanel.add(cancelButton, "right");
		
		connectButton.addActionListener(this);
		cancelButton.addActionListener(this);	
		usernameTextField.addActionListener(this);
		passwordTextField.addActionListener(this);
	
		getContentPane().add(longinPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusableWindowState(true);
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


	public boolean checkUserPass() throws Exception{
		
		String physician_id=MainControl.getMainControl().getPhysicianID();
		System.out.println("physician id is"+physician_id);
		//physician table (physician_id	name	username	password	signature)
		ResultSet userpassResult=PatientManager.getPatientManager().getDBConnection().execQuery("select username,password from physician where"
				+ " physician_id="+physician_id);
		
		userpassResult.next();
		if(userpassResult.getString("username").equals(this.getUsername())){
			System.out.println("Correct UserName");
			System.out.println("User from textField"+userpassResult.getString("username"));
			System.out.println("User from textField"+this.getUsername());
			if(userpassResult.getString("password").equals(this.getPassword())){
				System.out.println("Correct Password");
				this.setVisible(false);
				return true;
			}
			else{
				System.out.println("Wrong Password");
			}
		}
		else{
			System.out.println("Wrong UserName");
			System.out.println("User from textField"+userpassResult.getString("username"));
			System.out.println("User from textField"+this.getUsername());
		}
		return false;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==connectButton || e.getSource()==usernameTextField || e.getSource()==passwordTextField){
			 try {
				if(this.checkUserPass()){
					System.out.println("correct everything");
					MainWindow.createAndShowGUI();
					 
				}
				else{
					this.setUsernameTextField("");
					this.setPasswordTextField("");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==cancelButton){
			this.setVisible(false);
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
		LoginWindow test=new LoginWindow();
		
		//test.checkUserPass();
	}

	
}
