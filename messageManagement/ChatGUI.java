package messageManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import collaberativeDrawingEnvironment.DrawingGUI;

/**
 * Created by Jacki on 05/12/2016.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @file ChatGUI.java
 * @author Jacky Liu, Sion Rees
 * @date 10 Dec 2016
 *
 * GUI that shows conversation history between 2 users, and allows for the sending of messages back and forth.
 */
public class ChatGUI {
    private String m_windowName = "Conversation";
    private JFrame m_newFrame = new JFrame(m_windowName);
    private JButton m_send;
    static JTextField m_messageBox;
    private static JTextArea m_chatBox;
    private DateTimeFormatter m_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static Profile m_currentUser;
    private static Profile m_contact;

    public ChatGUI(Profile currentUser, Profile contact) {
        this.m_currentUser = currentUser;
        this.m_contact = contact;
        display();
    }

    //Loads basic messages from an array into the chat window
    public void loadConv(ArrayList<Message> messArray){
        while(!messArray.isEmpty()){
            String messBlock = messArray.get(0).getMessageBlock();
            m_chatBox.append("<" + messArray.get(0).getSenderName() + ">:  " + messBlock + "\n");
            messArray.remove(0);
        }
    }

    public void display() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel m_MessageType = new JPanel();
        m_MessageType.setBounds(100, 100, 692, 470);

        JPanel m_sendMessage = new JPanel();
        m_MessageType.add(m_sendMessage);
        m_sendMessage.setBounds(0, 354, 674, 69);
        m_sendMessage.setBackground(Color.blue);

        m_send = new JButton("Send Message");
        m_sendMessage.add(m_send);
        m_send.addActionListener(new sendMessageButtonListener());

        JButton m_URL = new JButton("Send URL");
        m_sendMessage.add(m_URL);
        m_URL.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            SendURL window = new SendURL();
                            window.m_sendURL.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        JButton m_browse = new JButton("Send file");
        m_sendMessage.add(m_browse);
        m_browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            PickFile window = new PickFile();
                            window.m_pickFile.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        JButton m_drawButton = new JButton("Draw");
        m_sendMessage.add(m_drawButton);
        m_drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run() {
                        new DrawingGUI(m_currentUser, m_contact);
                    }
                });
            }
        });

        JButton m_Clear = new JButton("Clear");
        m_sendMessage.add(m_Clear);
        m_Clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //m_messageBox.setText("");
                m_messageBox.setText(null); //or use this
            }
        });

        JPanel southPanel = new JPanel();
        //Sets out text box and button at the bottom
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        m_messageBox = new JTextField(30);
        m_messageBox.setText("Write your message here...");
        m_messageBox.requestFocusInWindow();
        m_messageBox.addKeyListener(new EnterPress());
        m_messageBox.addMouseListener(new MouseAdapter(){

            boolean firstTime = true;
            @Override
            public void mouseClicked(MouseEvent e){
                while(firstTime){
                    m_messageBox.setText("");
                    firstTime = false;
                }
            }

        });

        m_chatBox = new JTextArea();
        m_chatBox.setEditable(false);
        m_chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        m_chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(m_chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        GridBagConstraints bottom = new GridBagConstraints();
        bottom.insets = new Insets(0, 10, 0, 0);
        bottom.anchor = GridBagConstraints.LINE_END;
        bottom.fill = GridBagConstraints.NONE;
        bottom.weightx = 1.0D;
        bottom.weighty = 1.0D;

        southPanel.add(m_messageBox, left);
        southPanel.add(m_sendMessage, right);
        //     southPanel.add(m_testing3, GridBagConstraints.SOUTH);
        southPanel.add(m_MessageType);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        m_newFrame.add(mainPanel);
        m_newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        m_newFrame.setSize(5000, 1000);
        m_newFrame.setVisible(true);

        //Loads previous messages into chat window
        loadConv(MailBox.MailMan.getMessages(m_contact.getProfileId(), m_currentUser.getProfileId()));
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            postFromMessBox();
        }
    }
    class EnterPress implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                postFromMessBox();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    /**
     * Sends the message to the conversation.
     */
    public static void postFromMessBox(){
        m_messageBox.toString();

        if (m_messageBox.getText().length() < 1) {
            // do nothing
        } else {
            String creationTime = dateTimeNow();
            Message newMessage = new Message(m_messageBox.getText(), m_currentUser.getProfileId(),
                    m_contact.getProfileId(), creationTime, m_currentUser.getM_userName());
            MailBox.MailMan.sendToSave(newMessage);
            m_chatBox.append("<" + m_currentUser.getM_userName() + ">:  " + m_messageBox.getText()
                    + "\n");
            m_messageBox.setText("");
        }
        m_messageBox.requestFocusInWindow();
    }

    private static String dateTimeNow(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }


}


