package messageManagement;

import mainGUI.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @file RequestGUI.java
 * @author Gareth Andrews
 * @date 10 Dec 2016
 *
 * Displays a GUI for a contact request. Allows user to accept or decline contact.
 */
public class RequestGUI {

	private static final long serialVersionUID = 1L;
	private JFrame m_requestFrame;
	private static final String FRAME_TITLE = " would like to add you to their contacts";
	public static final int FRAME_WIDTH = 400;
	public static final int FRAME_HEIGHT = 200;
	public static final int FRAME_BOUNDARY = 100;
	private static final int PROFILE_PICTURE_SIZE = 50;
	private JPanel m_contactProfilePanel;
	private JPanel m_buttonPanel;
	private JLabel m_contactName;
	private BufferedImage m_contactProfilePicture;
	private JButton m_acceptButton;
	private JButton m_declineButton;
	private Profile m_currentUser;
	private Profile m_contact;

	public RequestGUI(Profile currentUser, Profile contact) {
		this.m_currentUser = currentUser;
		this.m_contact = contact;
		initialize();
	}

	public void initialize() {
		m_requestFrame = new JFrame();
		m_requestFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		m_requestFrame.setTitle(m_contact.getM_userName() + FRAME_TITLE);
		m_requestFrame.setBounds(FRAME_BOUNDARY, FRAME_BOUNDARY, FRAME_WIDTH, FRAME_HEIGHT);
		m_requestFrame.getContentPane().setLayout(null);
		m_requestFrame.setResizable(false);

		m_contactProfilePanel = new JPanel();
		m_contactProfilePanel.setBounds(10, 10, 365, 500);
		m_contactProfilePanel.setLayout(null);

		m_buttonPanel = new JPanel();
		m_buttonPanel.setBounds(174, 108, 210, 41);
		m_buttonPanel.setLayout(null);

		m_contactName = new JLabel(m_contact.getM_firstName() + " " + m_contact.getM_lastName());
		m_contactName.setFont(new Font("Arial", Font.PLAIN, 17));
		m_contactName.setHorizontalAlignment(SwingConstants.CENTER);
		m_contactName.setBounds(60, 0, 312, 50);;

		m_contactProfilePicture = m_contact.getM_proPic();
		JLabel profilePicture = new JLabel(new ImageIcon(m_contactProfilePicture.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE, Image.SCALE_DEFAULT)));
		profilePicture.setBounds(0, 0, PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE);

		//Deletes the request from the database.
		m_declineButton = new JButton("Decline");
		m_declineButton.setBounds(110, 0, 100, 40);
		m_declineButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				m_requestFrame.setVisible(false);
				Contacts.ContactInfo.deleteContactReq(m_contact.getProfileId(), m_currentUser.getProfileId());
			}
		});

		//Adds friendship to the database.
		m_acceptButton = new JButton("Accept");
		m_acceptButton.setBounds(0, 0, 100, 40);
		m_acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contacts.ContactInfo.addContact(m_currentUser.getProfileId(), m_contact.getProfileId());
				Contacts.ContactInfo.addContact(m_contact.getProfileId(), m_currentUser.getProfileId());
				m_requestFrame.setVisible(false);
				Contacts.ContactInfo.deleteContactReq(m_contact.getProfileId(), m_currentUser.getProfileId());
				MainGUI.updateContactList();
			}
		});

		m_requestFrame.getContentPane().add(m_contactProfilePanel);
		m_requestFrame.getContentPane().add(m_buttonPanel);
		m_contactProfilePanel.add(m_contactName);
		m_contactProfilePanel.add(profilePicture);
		m_buttonPanel.add(m_declineButton);
		m_buttonPanel.add(m_acceptButton);
		m_requestFrame.setVisible(true);
	}
}