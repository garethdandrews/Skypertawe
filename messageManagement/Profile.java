package messageManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @file Profile.java
 * @author Sion Rees
 * @date 02 Dec 2016
 *
 * Holds data on a profile.
 */
public class Profile {
    private Integer profileId;
    private String m_userName;
    private String m_firstName;
    private String m_lastName;
    private String m_phoneNumber;
    private String m_dob;
    private String m_city;
    String m_lastOnline;
    private BufferedImage m_proPic;

    public Profile(Integer profileId, String m_userName, String m_firstName, String m_lastName, String m_phoneNumber, String m_dob, String m_city, String m_lastOnline) {
        this.profileId = profileId;
        this.m_userName = m_userName;
        this.m_firstName = m_firstName;
        this.m_lastName = m_lastName;
        this.m_phoneNumber = m_phoneNumber;
        this.m_dob = m_dob;
        this.m_city = m_city;
        this.m_lastOnline = m_lastOnline;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getM_userName() {
        return m_userName;
    }

    public void setM_userName(String m_userName) {
        this.m_userName = m_userName;
    }

    public String getM_firstName() {
        return m_firstName;
    }

    public void setM_firstName(String m_firstName) {
        this.m_firstName = m_firstName;
    }

    public String getM_lastName() {
        return m_lastName;
    }

    public void setM_lastName(String m_lastName) {
        this.m_lastName = m_lastName;
    }

    public String getM_phoneNumber() {
        return m_phoneNumber;
    }

    public void setM_phoneNumber(String m_phoneNumber) {
        this.m_phoneNumber = m_phoneNumber;
    }

    public String getM_dob() {
        return m_dob;
    }

    public void setM_dob(String m_dob) {
        this.m_dob = m_dob;
    }

    public String getM_city() {
        return m_city;
    }

    public void setM_city(String m_city) {
        this.m_city = m_city;
    }

    public String getM_lastOnline() {
        return m_lastOnline;
    }

    public void setM_lastOnline(String m_lastOnline) {
        this.m_lastOnline = m_lastOnline;
    }

    public BufferedImage getM_proPic() {
    	return DBMngr.ManageDB.getProfileImage(profileId);
    }

    public void setM_proPic(BufferedImage m_proPic) {
        this.m_proPic = m_proPic;
    }

	public String getDOBDay() {
		String dob = getM_dob();
		String birthDay = "";
		for (int i = 0; i < 2; i++) {
			char c = dob.charAt(i);
			birthDay += c;
		}
		return birthDay;
	}
	
	public String getDOBMonth() {
		String dob = getM_dob();
		String birthMonth = "";
		for (int i = 3; i < 5; i++) {
			char c = dob.charAt(i);
			birthMonth += c;
		}
		return birthMonth;
	}
	
	public String getDOBYear() {
		String dob = getM_dob();
		String birthYear = "";
		for (int i = 6; i < dob.length(); i++) {
			char c = dob.charAt(i);
			birthYear += c;
		}
		return birthYear;
	}
}