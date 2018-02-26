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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @file PickFile.java
 * @author Jacky Liu
 * @date 11 Dec 2016
 * Browses for a text file.
 */
public class PickFile extends JFrame {

    public JFrame m_pickFile;
    String m_windowName = "Browse File";
    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    public PickFile() {
        initializeCDE();
    }

    public void initializeCDE() {
        m_pickFile = new JFrame(m_windowName);
        m_pickFile.setBounds(100, 100, 700, 500);
        m_pickFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_pickFile.getContentPane().setLayout(null);

        JPanel m_selection = new JPanel();
        m_pickFile.add(m_selection);
        m_selection.setBounds(0, 354, 674, 69);
        m_selection.setBackground(Color.green);

        JTextField m_fileDcrpt = new JTextField(50);
        m_selection.add(m_fileDcrpt);
        m_fileDcrpt.setText("<File Descrptions>  ");
        m_fileDcrpt.setBounds(0, 0, 312, 69);
        m_fileDcrpt.addMouseListener(new MouseAdapter(){

            boolean firstTime = true;
            @Override
            public void mouseClicked(MouseEvent e){
                while(firstTime){
                    m_fileDcrpt.setText("");
                    firstTime = false;
                }
            }
        });

        JButton m_browse = new JButton("Browse");
        m_selection.add(m_browse);
        m_browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFileChooser m_chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Text File", "txt");
                
                m_chooser.setCurrentDirectory(new java.io.File("."));
                m_chooser.setDialogTitle("Browse the folder to process");
                m_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                m_chooser.setFileFilter(filter);

                if (m_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): "+ m_chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "+ m_chooser.getSelectedFile());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });
        
        JButton m_sendFile = new JButton("Send");
		m_selection.add(m_sendFile);
		m_sendFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				ChatGUI.m_messageBox.setText("<File Descrption>"+"  "+m_fileDcrpt.getText());
			//	ChatGUI.m_messageBox.setText(m_typeURL.getText());
				ChatGUI.postFromMessBox();
				m_pickFile.dispose();
				};
		});

        JButton m_goBack = new JButton("Cancel");
        m_selection.add(m_goBack);
        m_goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                m_pickFile.dispose();  //Remove JFrame 1
                //   m_testing.setVisible(true); //Show other frame
            }
        });
        
        class EnterPress implements KeyListener{
	        @Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode()==KeyEvent.VK_ENTER){
	            	ChatGUI.m_messageBox.setText("<File Descrption>"+"  "+m_fileDcrpt.getText());
					ChatGUI.postFromMessBox();
					m_pickFile.dispose();
	            }
	        }
	        @Override
	        public void keyReleased(KeyEvent e) {
	        }
	    }
    	m_fileDcrpt.addKeyListener(new EnterPress());
		m_selection.addKeyListener(new EnterPress()); 


    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PickFile m_window = new PickFile();
                    m_window.m_pickFile.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}