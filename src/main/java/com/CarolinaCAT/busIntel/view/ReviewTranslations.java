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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReviewTranslations extends JFrame {

	private JPanel contentPane;
	private JButton btnSaveClose;
	private JTextArea taRmvBeg;
	private JTextArea taRmvAftr;
	private JTextArea taNameTrans;
	private JTextArea taPObox;

//	/**
//	 * REMOVE NOT NEEDED.
//	 */
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
	    try {
	      reader = new FileReader("POboxTranslations.txt");
	      taPObox.read(reader, "POboxTranslations.txt");
	      reader = null;
	      
	      reader = new FileReader("NameTranslations.txt");
	      taNameTrans.read(reader, "NameTranslations.txt");
	      reader = null;
	      
	      reader = new FileReader("DoingBusAsLookups.txt");
	      taRmvBeg.read(reader, "DoingBusAsLookups.txt");
	      reader = null;
	      
	      reader = new FileReader("RemoveEnding.txt");
	      taRmvAftr.read(reader, "RemoveEnding.txt");
	      reader = null;
	      
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

				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter("POboxTranslations.txt"))) {
					taPObox.write(fileOut);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter("NameTranslations.txt"))) {
					taNameTrans.write(fileOut);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter("DoingBusAsLookups.txt"))) {
					taRmvBeg.write(fileOut);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter("RemoveEnding.txt"))) {
					taRmvAftr.write(fileOut);
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
