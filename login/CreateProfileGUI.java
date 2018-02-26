package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.filechooser.*;

import mainGUI.MainGUI;
import messageManagement.DBMngr;
import messageManagement.Profile;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Label;
import java.awt.Font;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.Canvas;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * @file CreateProfileGUI.java
 * @author Ben Lloyd-Roberts, Tonye Spiff
 * @date 08 Dec 2016
 *
 * Creates a GUI which takes user input, creates a new profile, and sends it to be saved to the database.
 */
public class CreateProfileGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame m_createProfileFrame;
	private JPanel m_createProfPanel;
	private static final String FRAME_TITLE = "Create Profile";
	public static final int FRAME_WIDTH = 450;
	public static final int FRAME_HEIGHT = 550;
	public static final int FRAME_BOUNDARY = 100;
	private JLabel m_usernameLabel;
	private JLabel m_firstNameLabel;
	private JLabel m_lastNameLabel;
	private JLabel m_birthdayLabel;
	private JLabel m_cityLabel;
	private JLabel m_phoneNumberLabel;
	private JLabel m_profilePictureLabel;
	private JLabel m_pictureFormatLabel;
	private JLabel m_birthdayFormatLabel;
	private JTextField m_usernameField;
	private JTextField m_firstNameField;
	private JTextField m_lastNameField;
	private JTextField m_dobDayField;
	private JTextField m_dobMonthField;
	private JTextField m_dobYearField;
	private JTextField m_cityField;
	private JTextField m_phoneNumberField;
	private JTextField m_profilePictureField;
	private JButton m_selectPictureButton;
	private JButton m_submitButton;
	private BufferedImage m_profilePicture;
	private final int CURRENT_DAY = Calendar.getInstance().get(Calendar.DATE);
	private final int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
	
	public CreateProfileGUI(){
		initialize();	
	}

	/**
	 * Creates the GUI.
	 */
	public void initialize() {
		m_createProfileFrame = new JFrame();
		m_createProfileFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		m_createProfileFrame.setTitle(FRAME_TITLE);
		m_createProfileFrame.setBounds(FRAME_BOUNDARY, FRAME_BOUNDARY, FRAME_WIDTH, FRAME_HEIGHT);
		m_createProfileFrame.getContentPane().setLayout(null);
		m_createProfileFrame.setResizable(false);

		m_createProfPanel = new JPanel();
		m_createProfPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		m_createProfPanel.setLayout(null);

		m_usernameLabel = new JLabel("Username");
		m_usernameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_usernameLabel.setBounds(30, 25, 65, 25);
		
		m_usernameField = new JTextField();
		m_usernameField.setBounds(160, 25, 210, 25);
		
		m_firstNameLabel = new JLabel("First Name");
		m_firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_firstNameLabel.setBounds(30, 75, 65, 25);
		
		m_firstNameField = new JTextField();
		m_firstNameField.setBounds(160, 75, 210, 25);
		
		m_lastNameLabel = new JLabel("Surname");
		m_lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_lastNameLabel.setBounds(30, 125, 65, 25);
		
		m_lastNameField = new JTextField();
		m_lastNameField.setBounds(160, 125, 210, 25);
		
		m_birthdayLabel = new JLabel ("Date of Birth");
		m_birthdayLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_birthdayLabel.setBounds(30, 175, 75, 25);
			
		Integer[] days = new Integer[31]; 
		for (int i = 0; i < days.length; i++) {
			days[i] = i + 1;
		}
		
		Integer[] months = new Integer[12];
		for (int i = 0; i < months.length; i++) {
			months[i] = i + 1;
		}
		
		int startYear = CURRENT_YEAR - 100;
		Integer[] years = new Integer[101];
		for (int i = 0; i < years.length; i++) {
			years[i] = startYear;
			startYear += 1;
		}
		
		JComboBox<Integer> dayBox = new JComboBox<>(days);
		dayBox.setSelectedItem(CURRENT_DAY);
		dayBox.setBounds(160, 175, 65, 25);
		
		JComboBox<Integer> monthBox = new JComboBox<>(months);
		monthBox.setSelectedItem(CURRENT_MONTH);
		monthBox.setBounds(230, 175, 68, 25);
		
		JComboBox<Integer> yearBox = new JComboBox<>(years);
		yearBox.setSelectedItem(CURRENT_YEAR);
		yearBox.setBounds(305, 175, 65, 25);
		
		m_cityLabel = new JLabel("City/Town");
		m_cityLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_cityLabel.setBounds(30, 225, 65, 25);		
		
		m_cityField = new JTextField();
		m_cityField.setBounds(160, 225, 210, 25);
		
		m_phoneNumberLabel = new JLabel("Phone Number");
		m_phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_phoneNumberLabel.setBounds(30, 275, 100, 25);
				
		m_phoneNumberField = new JTextField();
		m_phoneNumberField.setBounds(160, 275, 210, 25);		
		
		m_selectPictureButton = new JButton("Browse");
		m_selectPictureButton.setFont(new Font("Arial", Font.BOLD, 11));
		m_selectPictureButton.setBounds(160, 325, 90, 25);
		m_selectPictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
				
		        chooser.setCurrentDirectory(new java.io.File("."));
		        chooser.setDialogTitle("Browse the folder to process");
		        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        chooser.setFileFilter(filter);

		        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		        	System.out.println("getCurrentDirectory(): "+ chooser.getCurrentDirectory());
		            System.out.println("getSelectedFile() : "+ chooser.getSelectedFile());
		            try {
						m_profilePicture = ImageIO.read(chooser.getSelectedFile());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					m_profilePictureField.setText(chooser.getSelectedFile().getPath());
		        } else {
		        	System.out.println("No Selection ");
		        }
			}
		});
		   
		
		m_profilePictureLabel = new JLabel("Profile Picture");
		m_profilePictureLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		m_profilePictureLabel.setBounds(30, 325, 100, 25);
		
		m_profilePictureField = new JTextField("");
		m_profilePictureField.setBounds(250, 325, 120, 25);
		m_profilePictureField.setEditable(false);
		
		m_pictureFormatLabel = new JLabel("***Only JPEG files supported***");
		m_pictureFormatLabel.setFont(new Font("Arial", Font.BOLD, 13));
		m_pictureFormatLabel.setBounds(160, 360, 200, 25);
		
		m_submitButton = new JButton("Submit details");
		m_submitButton.setFont(new Font("Arial", Font.BOLD, 11));
		m_submitButton.setBounds(160, 475, 130, 25);
		m_submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = m_usernameField.getText();
				String firstName = m_firstNameField.getText();
				String lastName = m_lastNameField.getText();
				String birthday = dateOfBirth(dayBox, monthBox, yearBox);
				String city = m_cityField.getText();
				String phoneNumber = m_phoneNumberField.getText();
				String lastOnline = "";
				
				Profile pro = DBMngr.ManageDB.insertProfile(username, firstName, lastName, phoneNumber,
												birthday, city, lastOnline);
				
				DBMngr.ManageDB.insertProfileImage(m_profilePicture, pro.getProfileId());				
				
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						new MainGUI(pro);
						m_createProfileFrame.setVisible(false);
					}
				});
			}
		});

		
		m_createProfileFrame.add(m_createProfPanel);
		
		m_createProfPanel.add(m_usernameLabel);
		m_createProfPanel.add(m_usernameField);  
		m_createProfPanel.add(m_firstNameLabel);
		m_createProfPanel.add(m_firstNameField);
		m_createProfPanel.add(m_lastNameLabel);
		m_createProfPanel.add(m_lastNameField);
		m_createProfPanel.add(m_birthdayLabel);
		m_createProfPanel.add(dayBox);
		m_createProfPanel.add(monthBox);
		m_createProfPanel.add(yearBox);
		m_createProfPanel.add(m_cityLabel);
		m_createProfPanel.add(m_cityField);
		m_createProfPanel.add(m_phoneNumberLabel);
		m_createProfPanel.add(m_phoneNumberField);
		m_createProfPanel.add(m_selectPictureButton);
		m_createProfPanel.add(m_profilePictureLabel);
		m_createProfPanel.add(m_submitButton);
		m_createProfPanel.add(m_pictureFormatLabel);
		m_createProfPanel.add(m_profilePictureField);
		
		m_createProfileFrame.setVisible(true);
	}

	/**
	 * Combines the ComboBox data into a string.
	 * @param day
	 * @param month
	 * @param year
     * @return dateOfBirth
     */
	public String dateOfBirth(JComboBox<Integer> day, JComboBox<Integer> month, JComboBox<Integer> year) {
		String dob = "";
		
		int dayOfBirth = (int) day.getSelectedItem();
		if (dayOfBirth <= 9) {
			dob += "0" + dayOfBirth;
		}
		else {
			dob += dayOfBirth;
		}
		
		dob += "/";
		
		int monthOfBirth = (int) month.getSelectedItem();
		if (monthOfBirth <= 9) {
			dob += "0" + monthOfBirth;
		}
		else {
			dob += monthOfBirth;
		}
		
		dob += "/";
				
		dob += (int) year.getSelectedItem();
		
		return dob;
	}
}

