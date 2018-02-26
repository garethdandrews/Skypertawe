package login;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.EmptyBorder;

import mainGUI.MainGUI;
import messageManagement.DBMngr;
import messageManagement.Profile;

/**
 * @file LoginGUI.java
 * @author Gareth Andrews, Ben Lloyd-Roberts
 * @date 09 Dec 2016
 *
 * GUI that can allow a user to login or create a new profile.
 */
public class LoginGUI extends JFrame {

	private JFrame m_loginFrame;
	private JPanel m_loginPane;
	private JTextField m_usernameField;

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		m_loginFrame = new JFrame();
		m_loginFrame.setFont(new Font("Arial", Font.PLAIN, 14));
		m_loginFrame.setTitle("Skypertawe Login");
		m_loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_loginFrame.setBounds(100, 100, 335, 300);
		
		m_loginPane = new JPanel();
		m_loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_loginPane);
		m_loginPane.setLayout(null);
		
		m_usernameField = new JTextField();
		m_usernameField.setBounds(95, 100, 135, 20);
		m_loginPane.add(m_usernameField);
		m_usernameField.setColumns(10);
		m_usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		m_usernameField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					invokeMain();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		Label usernameLabel = new Label("     Username");
		usernameLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		usernameLabel.setBounds(115, 80, 90, 22);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(115, 160, 90, 23);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invokeMain();
			};
		});		
		
		JButton btnNewButton = new JButton("Sign Up");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invokeRegistration();
			}
		});
		
		
		btnNewButton.setBounds(115, 195, 90, 23);

		m_loginFrame.add(m_loginPane);
		m_loginPane.add(usernameLabel);
		m_loginPane.add(loginButton);
		m_loginPane.add(btnNewButton);
		m_loginFrame.setVisible(true);
	}

	/**
	 * Checks if the user exists.
	 */
	public void invokeMain() {
		Profile pro = DBMngr.ManageDB.checkProfile(m_usernameField.getText());

		if(pro == null) {
			JOptionPane.showMessageDialog(m_loginFrame, "User '" + m_usernameField.getText() + "' not found");
		} else {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					new MainGUI(pro);
					m_loginFrame.setVisible(false);
				}
			});
		}
	}

	/**
	 * Closes the LoginGUI and opens the CreateProfileGUI.
	 */
	public void invokeRegistration() {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new CreateProfileGUI();
				m_loginFrame.setVisible(false);
			}
		});
	}
}