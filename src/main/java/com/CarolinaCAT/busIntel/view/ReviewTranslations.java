package com.CarolinaCAT.busIntel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ReviewTranslations extends JFrame {

	private JPanel contentPane;
	private JButton btnSaveClose;
	private JTextArea taRmvBeg;
	private JTextArea taRmvAftr;
	private JTextArea taNameTrans;
	private JTextArea taPObox;
	private String strDirName;
	private Path userHome;
	
	private File directory;
	private File poTrans;
	private File nameTrans;
	private File dbaTrans;
	private File rmvAfterTrans;
	/**
	 * for testing
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ReviewTranslations frame = new ReviewTranslations();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ReviewTranslations() {

		initComponents();
		createEvents();
		readInFiles();
		
	}
	
	private void readInFiles() {
		// TODO Auto-generated method stub
		//TODO load these with what is in text files
		//jtextArea1.read(reader, "jTextArea1")
		/*
		 * JTextArea area = ...
try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(yourFile))) {
    area.write(fileOut);
}taRmvBeg   
		 */
		FileReader reader = null;
		strDirName = System.getProperty("user.home") + "/CustomerTranslations";
	    try {
	    	//what to do if files don't exist yet
	    	directory = new File(strDirName);
    		poTrans = new File (strDirName + File.separator + "POboxTranslations.txt");
    		nameTrans = new File (strDirName + File.separator + "NameTranslations.txt");
    		dbaTrans = new File (strDirName + File.separator + "DoingBusAsLookups.txt");
    		rmvAfterTrans = new File (strDirName + File.separator + "RemoveEndings.txt");
	    	if (directory.mkdir()){	    	    
    	        FileWriter fw = new FileWriter(poTrans.getAbsoluteFile());
    	        BufferedWriter bw = new BufferedWriter(fw);
    	        //Need to write correct values to files
    	        bw.write("PO BOX" + System.lineSeparator() + "POST OFFICE BOX" + System.lineSeparator() + "P.O BOX" +
    	        		System.lineSeparator() + "PO. BOX" + System.lineSeparator() + "P O BOX");
    	        bw.close();
    	        //repeats
    	        fw = new FileWriter(nameTrans.getAbsoluteFile());
    	        bw = new BufferedWriter(fw);
    	        bw.write("MTN TO MOUNTAIN" + System.lineSeparator() + "CONST TO CONSTRUCTION" + System.lineSeparator() + "CONTR TO CONTRACTOR" +
    	        		System.lineSeparator() + "GRDNG TO GRADING" + System.lineSeparator() + "CO TO COMPANY" + System.lineSeparator() + "CORP TO CORPORATION"); 	        
    	        bw.close();
    	        fw = new FileWriter(dbaTrans.getAbsoluteFile());
    	        bw = new BufferedWriter(fw);
    	        bw.write("D/B/A" + System.lineSeparator() + "DBA" + System.lineSeparator() + "D B A" +
    	        		System.lineSeparator() + "D.B.A." + System.lineSeparator() + "D BA" + System.lineSeparator() + "DB A"); 
    	        bw.close();
    	        fw = new FileWriter(rmvAfterTrans.getAbsoluteFile());
    	        bw = new BufferedWriter(fw);
    	        bw.write("CASH SALE" + System.lineSeparator() + "C ASH SALE" + System.lineSeparator() + "CA SH SALE" +
    	        		System.lineSeparator() + "CAS H SALE" + System.lineSeparator() + "CASH S ALE" + System.lineSeparator() + "CASH SA LE" +
    	        		System.lineSeparator() + "CASH SAL E" + System.lineSeparator() + "ASH SALE" + System.lineSeparator() + "ASH SA LE" +
    	        		System.lineSeparator() + "CASH SAL");
    	        bw.close();
    	        
				reader = new FileReader(poTrans.getAbsoluteFile());
				taPObox.read(reader, poTrans.getAbsoluteFile());
				reader.close();
				reader = null;
				  
				reader = new FileReader(nameTrans.getAbsoluteFile());
				taNameTrans.read(reader, nameTrans.getAbsoluteFile());
				reader.close();
				reader = null;
				  
				reader = new FileReader(dbaTrans.getAbsoluteFile());
				taRmvBeg.read(reader, dbaTrans.getAbsoluteFile());
				reader.close();
				reader = null;
				  
				reader = new FileReader(rmvAfterTrans.getAbsoluteFile());
				taRmvAftr.read(reader, rmvAfterTrans.getAbsoluteFile());
				reader.close();
				reader = null;
    	        
	    	} else {
	    	//check if files exist
				reader = new FileReader(poTrans.getAbsoluteFile());
				taPObox.read(reader, poTrans.getAbsoluteFile());
				reader.close();
				reader = null;
				  
				reader = new FileReader(nameTrans.getAbsoluteFile());
				taNameTrans.read(reader, nameTrans.getAbsoluteFile());
				reader.close();
				reader = null;
				  
				reader = new FileReader(dbaTrans.getAbsoluteFile());
				taRmvBeg.read(reader, dbaTrans.getAbsoluteFile());
				reader.close();
				reader = null;
				  
				reader = new FileReader(rmvAfterTrans.getAbsoluteFile());
				taRmvAftr.read(reader, rmvAfterTrans.getAbsoluteFile());
				reader.close();
				reader = null;
	    	}
	    } catch (IOException exception) {
		      System.err.println("Load oops");
		      exception.printStackTrace();
	    } finally {
	      if (reader != null) {
	        try {
	          reader.close();
	        } catch (IOException exception) {
	          System.err.println("Error closing reader");
	          exception.printStackTrace();
	        }
	      }
	    }
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 826, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane spRmvBeg = new JScrollPane();
		
		JLabel lblRemoveThisAnd = new JLabel("Remove This and Before");
		
		JScrollPane spRmvAftr = new JScrollPane();
		
		JLabel lblRemoveThisAnd_1 = new JLabel("Remove This and After");
		
		JScrollPane spNameTrans = new JScrollPane();
		
		JLabel lblTranslationsForAbbreviatiosn = new JLabel("Translations for Abbreviations");
		
		JScrollPane spPObox = new JScrollPane();
		
		JLabel lblPoBoxTrans = new JLabel("PO Box Trans");
		
		btnSaveClose = new JButton("Save and Close");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(spRmvBeg, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRemoveThisAnd))
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(spRmvAftr, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRemoveThisAnd_1))
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(spNameTrans, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTranslationsForAbbreviatiosn))
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(spPObox, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPoBoxTrans))
					.addContainerGap(34, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(327)
					.addComponent(btnSaveClose)
					.addContainerGap(374, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(53)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRemoveThisAnd)
						.addComponent(lblRemoveThisAnd_1)
						.addComponent(lblTranslationsForAbbreviatiosn)
						.addComponent(lblPoBoxTrans))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(spPObox, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(spRmvBeg, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
							.addComponent(spRmvAftr, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
							.addComponent(spNameTrans, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
					.addComponent(btnSaveClose)
					.addContainerGap())
		);
		
		taPObox = new JTextArea();
		spPObox.setViewportView(taPObox);
		
		taNameTrans = new JTextArea();
		spNameTrans.setViewportView(taNameTrans);
		
		taRmvAftr = new JTextArea();
		spRmvAftr.setViewportView(taRmvAftr);
		
		taRmvBeg = new JTextArea();
		spRmvBeg.setViewportView(taRmvBeg);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void createEvents() {
		btnSaveClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(poTrans.getAbsoluteFile()))) {
					taPObox.write(fileOut);
					fileOut.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(nameTrans.getAbsoluteFile()))) {
					taNameTrans.write(fileOut);
					fileOut.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(dbaTrans.getAbsoluteFile()))) {
					taRmvBeg.write(fileOut);
					fileOut.close();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(rmvAfterTrans.getAbsoluteFile()))) {
					taRmvAftr.write(fileOut);
					fileOut.close();
				} catch (IOException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				setInvisible();
			}
		});
	}

	public void setVisible(){
		setVisible(true);
	}
	public void setInvisible(){
		setVisible(false);
	}
}
