package mainGUI;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.*;

import messageManagement.Profile;

/**
 * @file ProfileGUI.java
 * @author Gareth Andrews.
 * @date 11 Dec 2016
 *
 * GUI that shows all the data in profile.
 */
public class ProfileGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame m_profileFrame;
	private static final String FRAME_TITLE = "Profile";
	public static final int FRAME_WIDTH = 500;
	public static final int FRAME_HEIGHT = 300;
	public static final int FRAME_BOUNDARY = 100;
	private static final int PROFILE_PICTURE_SIZE = 128;
	private JPanel m_profilePanel;
	private JLabel m_userName;
	private JLabel m_firstName;
	private JLabel m_lastName;
	private JLabel m_birthday;
	private JLabel m_city;
	private JLabel m_phoneNumber;
	private JTextField m_firstNameTextField;
	private JTextField m_lastNameTextField;
	private JTextField m_cityTextField;
	private JTextField m_phoneNumberTextField;
	private BufferedImage m_profilePicture;
	private JButton m_closeButton;
	private Profile m_currentUser;
	private final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
	
	public ProfileGUI(Profile currentUser) {
		this.m_currentUser = currentUser;
		initialize();
	}
	
	public void initialize() {
		m_profileFrame = new JFrame();
		m_profileFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		m_profileFrame.setTitle(FRAME_TITLE);
		m_profileFrame.setBounds(FRAME_BOUNDARY, FRAME_BOUNDARY, FRAME_WIDTH, FRAME_HEIGHT);
		m_profileFrame.getContentPane().setLayout(null);
		m_profileFrame.setResizable(false);
		
		m_profilePanel = new JPanel();
		m_profilePanel.setBounds(10, 11, 464, 239);
		m_profilePanel.setLayout(null);
		
		m_userName = new JLabel(m_currentUser.getM_userName());
		m_userName.setFont(new Font("Arial", Font.BOLD, 14));
		m_userName.setBounds(0, 140, 128, 30);
		m_userName.setHorizontalAlignment(SwingConstants.CENTER);
		
		m_firstName = new JLabel("First Name: ");
		m_firstName.setFont(new Font("Arial", Font.PLAIN, 11));
		m_firstName.setBounds(154, 0, 100, 30);

		m_lastName = new JLabel("Last Name: ");
		m_lastName.setFont(new Font("Arial", Font.PLAIN, 11));
		m_lastName.setBounds(154, 35, 100, 30);
		
		m_birthday = new JLabel("Birthday: ");
		m_birthday.setFont(new Font("Arial", Font.PLAIN, 11));
		m_birthday.setBounds(154, 70, 100, 30);
		
		m_city = new JLabel("City: ");
		m_city.setFont(new Font("Arial", Font.PLAIN, 11));
		m_city.setBounds(154, 105, 100, 30);
		
		m_phoneNumber = new JLabel("Phone Number: ");
		m_phoneNumber.setFont(new Font("Arial", Font.PLAIN, 11));
		m_phoneNumber.setBounds(154, 140, 100, 30);
		
		m_firstNameTextField = new JTextField(m_currentUser.getM_firstName());
		m_firstNameTextField.setColumns(10);
		m_firstNameTextField.setBounds(264, 0, 200, 30);
		m_firstNameTextField.setEditable(false);
		
		m_lastNameTextField = new JTextField(m_currentUser.getM_lastName());
		m_lastNameTextField.setColumns(10);
		m_lastNameTextField.setBounds(264, 35, 200, 30);
		m_lastNameTextField.setEditable(false);
		
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
		
		JComboBox<Integer> dayList = new JComboBox<>(days);
		Integer day = Integer.parseInt(m_currentUser.getDOBDay());
		dayList.setSelectedItem(days[Arrays.asList(days).indexOf(day)]);
		dayList.setBounds(264, 70, 61, 30);
		dayList.setEnabled(false);
		
		JComboBox<Integer> monthList = new JComboBox<>(months);
		Integer month = Integer.parseInt(m_currentUser.getDOBMonth());
		monthList.setSelectedItem(months[Arrays.asList(months).indexOf(month)]);
		monthList.setBounds(332, 70, 61, 30);
		monthList.setEnabled(false);
		
		JComboBox<Integer> yearList = new JComboBox<>(years);
		Integer year = Integer.parseInt(m_currentUser.getDOBYear());
		yearList.setSelectedItem(years[Arrays.asList(years).indexOf(year)]);
		yearList.setBounds(403, 70, 61, 30);
		yearList.setEnabled(false);

		m_cityTextField = new JTextField(m_currentUser.getM_city());
		m_cityTextField.setColumns(10);
		m_cityTextField.setBounds(264, 105, 200, 30);
		m_cityTextField.setEditable(false);
		
		m_phoneNumberTextField = new JTextField(m_currentUser.getM_phoneNumber());
		m_phoneNumberTextField.setColumns(10);
		m_phoneNumberTextField.setBounds(264, 140, 200, 30);
		m_phoneNumberTextField.setEditable(false);
		
		m_profilePicture = m_currentUser.getM_proPic();
		JLabel profilePicture = new JLabel(new ImageIcon(m_profilePicture.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE, Image.SCALE_DEFAULT)));
		profilePicture.setBounds(0, 0, PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE);
		
		m_closeButton = new JButton("Close");
		m_closeButton.setBounds(361, 205, 100, 30);
		m_closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_profileFrame.setVisible(false);
			};
		});

		m_profileFrame.add(m_profilePanel);
		m_profilePanel.add(m_userName);
		m_profilePanel.add(m_firstName);
		m_profilePanel.add(m_lastName);
		m_profilePanel.add(m_birthday);
		m_profilePanel.add(m_city);
		m_profilePanel.add(m_phoneNumber);
		m_profilePanel.add(m_firstNameTextField);
		m_profilePanel.add(m_lastNameTextField);
		m_profilePanel.add(dayList);
		m_profilePanel.add(monthList);
		m_profilePanel.add(yearList);
		m_profilePanel.add(m_cityTextField);
		m_profilePanel.add(m_phoneNumberTextField);
		m_profilePanel.add(profilePicture);
		m_profilePanel.add(m_closeButton);
		m_profileFrame.setVisible(true);
	}
}
