/**

 * Created by jacky_liu on 10/12/2016.

 */
package messageManagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import java.awt.EventQueue;

/**
 * @file SendURL.java
 * @author Jacky Liu
 * @date 11 Dec 2016
 *
 * Posts a URL message to the ChatGUI along with a description.
 */
public class SendURL extends JFrame{
    public JFrame m_sendURL;
    String m_windowName = "Send URL";
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 830;
    private static final int PANEL_HEIGHT = 515;



    public SendURL() {
        initializeCDE();
    }

    public void initializeCDE() {


        m_sendURL = new JFrame(m_windowName);
        m_sendURL.setBounds(100, 100, 692, 500);
        m_sendURL.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_sendURL.getContentPane().setLayout(null);


        JPanel m_makeURL = new JPanel();
        m_sendURL.add(m_makeURL);
        m_makeURL.setBounds(0, 354, 674, 100);
        m_makeURL.setBackground(Color.blue);

        JTextField m_URLDcrpt = new JTextField(50);
        m_makeURL.add(m_URLDcrpt);
        m_URLDcrpt.setText("<Descrptions>  ");
        m_URLDcrpt.setBounds(0, 0, 312, 69);
        m_URLDcrpt.addMouseListener(new MouseAdapter(){

            boolean firstTime = true;
            @Override
            public void mouseClicked(MouseEvent e){
                while(firstTime){
                    m_URLDcrpt.setText("");
                    firstTime = false;
                }
            }

        });

        JTextField m_typeURL = new JTextField(21);
        m_makeURL.add(m_typeURL);
        m_typeURL.setText("http://");
        m_typeURL.setBounds(0, 0, 312, 69);


        JButton m_sendDURL = new JButton("Send");
        m_makeURL.add(m_sendDURL);
        m_sendDURL.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                ChatGUI.m_messageBox.setText("<URL Descrption>"+"  "+m_URLDcrpt.getText()+"    "+"<URL Link>"+"  "+
                        m_typeURL.getText());
                //	ChatGUI.m_messageBox.setText(m_typeURL.getText());
                ChatGUI.postFromMessBox();
                m_sendURL.dispose();
            };
        });

        class EnterPress implements KeyListener{
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    ChatGUI.m_messageBox.setText("<URL Descrption>"+"  "+m_URLDcrpt.getText()+"    "+"<URL Link>"+"  "+
                            m_typeURL.getText());
                    ChatGUI.postFromMessBox();
                    m_sendURL.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        }

        m_URLDcrpt.addKeyListener(new EnterPress());
        m_typeURL.addKeyListener(new EnterPress());


        JButton m_goBack = new JButton("Cancel");
        m_makeURL.add(m_goBack);
        m_goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                m_sendURL.dispose();  //Remove JFrame 1
                //   m_testing.setVisible(true); //Show other frame
            }
        });

        JButton m_Clear = new JButton("Clear");
        m_makeURL.add(m_Clear);
        m_Clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //txtField.setText("");
                m_typeURL.setText(null); //or use this
            }
        });


    }




    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SendURL m_window = new SendURL();
                    m_window.m_sendURL.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
