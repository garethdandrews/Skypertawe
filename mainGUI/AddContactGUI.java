package mainGUI;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import messageManagement.*;
/**
 * @file RequestGUI.java
 * @author Gareth Andrews
 * @date 09 Dec 2016
 * @see MainGUI
 *
 * Class that displays a window to add a selected contact.
 */
public class AddContactGUI {

    private static final long serialVersionUID = 1L;
    private JFrame m_addContactFrame;
    private static final String FRAME_TITLE = "Add Contact";
    public static final int FRAME_WIDTH = 400;
    public static final int FRAME_HEIGHT = 200;
    public static final int FRAME_BOUNDARY = 100;
    private static final int PROFILE_PICTURE_SIZE = 50;
    private JPanel m_contactProfilePanel;
    private JPanel m_buttonPanel;
    private JLabel m_contactName;
    private BufferedImage m_contactProfilePicture;
    private JButton m_addContactButton;
    private JButton m_cancelButton;
    private Profile m_currentUser;
    private Profile m_contact;

    public AddContactGUI(Profile profile, Profile contact) {
        this.m_currentUser = profile;
        this.m_contact = contact;
        initialize();
    }

    /**
     * Creates the frame.
     */
    public void initialize() {
        m_addContactFrame = new JFrame();
        m_addContactFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        m_addContactFrame.setTitle(FRAME_TITLE);
        m_addContactFrame.setBounds(FRAME_BOUNDARY, FRAME_BOUNDARY, FRAME_WIDTH, FRAME_HEIGHT);
        m_addContactFrame.getContentPane().setLayout(null);
        m_addContactFrame.setResizable(false);

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

        //Ignore the contact request.
        m_cancelButton = new JButton("Cancel");
        m_cancelButton.setBounds(110, 0, 100, 40);
        m_cancelButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                m_addContactFrame.setVisible(false);
            }
        });

        //Add a contact request to the database.
        m_addContactButton = new JButton("Add Contact");
        m_addContactButton.setBounds(0, 0, 100, 40);
        m_addContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Contacts.ContactInfo.addContactReq(m_currentUser.getProfileId(), m_contact.getProfileId());
                m_addContactFrame.setVisible(false);
            };
        });

        m_addContactFrame.getContentPane().add(m_contactProfilePanel);
        m_addContactFrame.getContentPane().add(m_buttonPanel);
        m_contactProfilePanel.add(m_contactName);
        m_contactProfilePanel.add(profilePicture);
        m_buttonPanel.add(m_cancelButton);
        m_buttonPanel.add(m_addContactButton);

        m_addContactFrame.setVisible(true);
    }
}