package mainGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import collaberativeDrawingEnvironment.DrawingGUI;
import login.LoginGUI;
import messageManagement.*;

/**
 * @file MainGUI.java
 * @author Gareth Andrews, Tonye Spiff
 * @date 08 Dec 2016
 */
public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame m_mainFrame;
	private static final String FRAME_TITLE = "Skypertawe";
	public static final int FRAME_WIDTH = 500;
	public static final int FRAME_HEIGHT = 700;
	public static final int FRAME_BOUNDARY = 100;
	private static final int PROFILE_PICTURE_SIZE = 50;
	private JPanel profilePanel;
	private static JPanel m_contactsPanel;
	private JTextField m_searchField;
	private BufferedImage m_profilePicture;
	private static BufferedImage m_contactProfilePicture;
	private static int m_contactHeight;
	private static Profile m_currentUser;
	
	public MainGUI(Profile currentUser) {
		this.m_currentUser = currentUser;
		initialize();
		getContactRequests();
		updateContactList();
	}

	/**
	 * Creates the frame.
	 */
	public void initialize() {
		m_mainFrame = new JFrame();
		m_mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		m_mainFrame.setTitle(FRAME_TITLE);
		m_mainFrame.setBounds(FRAME_BOUNDARY, FRAME_BOUNDARY, FRAME_WIDTH, FRAME_HEIGHT);
		m_mainFrame.getContentPane().setLayout(null);
		m_mainFrame.setResizable(false);
		
		profilePanel = new JPanel();
		profilePanel.setBounds(10, 11, 464, 50);
		m_mainFrame.getContentPane().add(profilePanel);
		profilePanel.setLayout(null);
		
		m_profilePicture = m_currentUser.getM_proPic();
		JLabel profilePicture = new JLabel(new ImageIcon(m_profilePicture.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE, Image.SCALE_DEFAULT)));
		profilePicture.setBounds(0, 0, PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE);
		
		JLabel profileName = new JLabel(m_currentUser.getM_firstName() + " " + m_currentUser.getM_lastName() + " (" + m_currentUser.getM_userName() + ")");
		profileName.setFont(new Font("Arial", Font.BOLD, 16));
		profileName.setBounds(62, 11, 192, 28);

		//Displays a GUI with all the profile data.
		JButton viewProfileButton = new JButton("View Profile");
		viewProfileButton.setBounds(270, 5, 90, 40);
		viewProfileButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				invokeProfile();
			} 
		});

		//Returns to the LoginGUI.
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(370, 5, 90, 40);
		logoutButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				m_mainFrame.setVisible(false);
				m_currentUser = null;

				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						new LoginGUI();
					}
				});
			}
		});

		profilePanel.add(profilePicture);
		profilePanel.add(profileName);
		profilePanel.add(viewProfileButton);
		profilePanel.add(logoutButton);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(10, 621, 464, 29);
		m_mainFrame.getContentPane().add(searchPanel);
		searchPanel.setLayout(null);
		
		m_searchField = new JTextField();
		m_searchField.setBounds(0, 0, 361, 29);
		m_searchField.setHorizontalAlignment(SwingConstants.CENTER);
		m_searchField.setColumns(10);
		m_searchField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					invokeRequest();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		//Searches the database for a username, if it exists, give the option to send a contact request.
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(360, 0, 104, 29);
		searchButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				invokeRequest();
			}
		});
		
		searchPanel.add(m_searchField);
		searchPanel.add(searchButton);
		
		m_contactsPanel = new JPanel();
		m_contactsPanel.setBounds(10, 72, 464, 500);
		m_contactsPanel.setLayout(null);
		
//		JScrollPane scrollContacts = new JScrollPane();
	//	scrollContacts.setBounds(10, 72, 464, 539);
		//scrollContacts.add(m_contactsPanel);
	//	m_mainFrame.getContentPane().add(scrollContacts);
		m_mainFrame.getContentPane().add(m_contactsPanel);
		

		m_mainFrame.setLocationRelativeTo(null);
		m_mainFrame.setVisible(true);
	
	}
	
	public void invokeProfile() {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new ProfileGUI(m_currentUser);
			}
		});
	}
	
	public void invokeRequest() {
		Profile pro = DBMngr.ManageDB.checkProfile(m_searchField.getText());
		
		if (pro == null) {
			JOptionPane.showMessageDialog(m_mainFrame, "User '" + m_searchField.getText() + "' does not exist");
		} else {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					new AddContactGUI(m_currentUser, pro);
				}
			});
		}		
	}
	
	public static void invokeChat(Profile contact) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new ChatGUI(m_currentUser, contact);
			}
		});
	}

	/**
	 * Returns a list of contact request messages.
	 */
	private void getContactRequests() {
		ArrayList<Profile> requests = Contacts.ContactInfo.getContReq(m_currentUser.getProfileId());

		for(Profile contact : requests) {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					new RequestGUI(m_currentUser, contact);
				}
			});
		}
	}

	/**
	 * Refreshes the GUI to show the most recent contact list.
	 * Called after a contact is added and when user logs in.
	 */
	public static void updateContactList() {
		m_contactsPanel.removeAll();
		m_contactsPanel.repaint();
		m_contactHeight = 0;
		ArrayList<Profile> contacts = Contacts.ContactInfo.getContactList(m_currentUser.getProfileId());

		for (Profile contact : contacts) {
			JPanel contactProfilePanel = new JPanel();
			contactProfilePanel.setBounds(0, m_contactHeight, 454, 50);
			contactProfilePanel.setLayout(null);

			m_contactProfilePicture = contact.getM_proPic();
			JLabel contactProfilePicture = new JLabel(new ImageIcon(m_contactProfilePicture.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE, Image.SCALE_DEFAULT)));
			contactProfilePicture.setBounds(0, 0, PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE);

			JLabel contactProfileName = new JLabel(contact.getM_firstName() + " " + contact.getM_lastName() + " (" + contact.getM_userName() + ")");
			contactProfileName.setFont(new Font("Arial", Font.PLAIN, 14));
			contactProfileName.setBounds(62, 11, 192, 28);

			JButton chatButton = new JButton("Chat");
			chatButton.setBounds(380, 5, 60, 30);
			chatButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					invokeChat(contact);
				};
			});

			m_contactsPanel.add(contactProfilePanel);
			contactProfilePanel.add(contactProfilePicture);
			contactProfilePanel.add(contactProfileName);
			contactProfilePanel.add(chatButton);
			m_contactHeight+=55;
		}
	}
}

