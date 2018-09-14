package com.CarolinaCAT.busIntel.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NewProgressBar extends JFrame {

	private JPanel contentPane;
	private JButton btnCancel;
	private JProgressBar pbGenMatches;
	private JLabel lblGeneratingMatches;


	private JLabel lblReadStatus;
	private JLabel lblReadStatusLabel;
	private JProgressBar pbImportCustomers;
	private JLabel lblImportCustomers;
	private JLabel lblLabelConnectingToData;
	private JLabel lblStatusConnection;
	private boolean canceled;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					NewProgressBar frame = new NewProgressBar();
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
	public NewProgressBar() {
		initComponents();
		canceled = false;
		createEvents();
	}

	private void createEvents() {
		// TODO Auto-generated method stub
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canceled = true;
			}
		});
		
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblImportCustomers = new JLabel("Importing Customers...");
		
		pbImportCustomers = new JProgressBar();
		
		pbGenMatches = new JProgressBar();
		
		lblReadStatusLabel = new JLabel("Excel File Read Status:");
		
		lblReadStatus = new JLabel("File Reading Not Yet Begun");
		lblReadStatus.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		lblGeneratingMatches = new JLabel("Generating Matches...");
		
		btnCancel = new JButton("Cancel");
		
		lblLabelConnectingToData = new JLabel("Connecting to Data Sources:");
		
		lblStatusConnection = new JLabel("Establishing Connection");
		lblStatusConnection.setFont(new Font("Tahoma", Font.ITALIC, 13));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 86, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblImportCustomers)
										.addGap(108))
									.addComponent(pbImportCustomers, GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblLabelConnectingToData)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblStatusConnection, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)))
								.addComponent(pbGenMatches, GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(87)
									.addComponent(lblGeneratingMatches))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblReadStatusLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblReadStatus, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(170)
							.addComponent(btnCancel)))
					.addContainerGap(34, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLabelConnectingToData)
						.addComponent(lblStatusConnection))
					.addGap(18)
					.addComponent(lblImportCustomers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pbImportCustomers, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReadStatusLabel)
						.addComponent(lblReadStatus))
					.addGap(18)
					.addComponent(lblGeneratingMatches)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pbGenMatches, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	/**
	 * @param lblReadStatus the lblReadStatus to set
	 */
	public void setLblReadStatus(String status) {
		lblReadStatus.setText(status);
		
		if(status.equals("Reading File")){
			lblReadStatus.setForeground(Color.ORANGE);
			lblReadStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		} else if (status.equals("File Read Complete")){
			lblReadStatus.setForeground(Color.GREEN);
			lblReadStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
	}
	/**
	 * @param lblStatusConnection the lblStatusConnection to set
	 */
	public void setLblStatusConnection(String status) {
		lblStatusConnection.setText(status);
		if(status.equals("Connection Established")){
			lblStatusConnection.setForeground(Color.GREEN);
			lblStatusConnection.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
	}

	public void setPBImportCustomers(int num){
		pbImportCustomers.setValue(num);
	}
	public void setPBGenMatches(int num){
		pbGenMatches.setValue(num);
	}
	public boolean isCanceled(){
		return canceled;
	}
	
	public void restoreDefaults(){
		setPBImportCustomers(0);
		setPBGenMatches(0);
		lblReadStatus.setText("File Reading Not Yet Begun");
		lblReadStatus.setForeground(Color.BLACK);
		lblReadStatus.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblStatusConnection.setText("Establishing Connection");
		lblStatusConnection.setForeground(Color.BLACK);
		lblStatusConnection.setFont(new Font("Tahoma", Font.ITALIC, 13));
		canceled = false;
	}
}
