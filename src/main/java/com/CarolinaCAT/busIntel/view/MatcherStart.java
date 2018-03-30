package com.CarolinaCAT.busIntel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MatcherStart extends JFrame {

	private JPanel contentPane;
	private JTextField txtCustNameCol;
	private JTextField txtCustAddrCol;
	private JTextField txtCustZipCol;
	private JTextField txtCustPhoneCol;
	private JTextField txtCustInfCol;
	private JTextField txtFirstRow;
	private JTextField txtOutputFileLocation;
	private final JFileChooser openFileChooser;
	private JButton btnSelectInputFile; 
	private JLabel lblSelectInputFile;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MatcherStart frame = new MatcherStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MatcherStart() {

		initComponents();
		createEvents();
		


	}
	
////////////////////////////////////////////
///This method contains all of the code for creating and initializing
///components.
////////////////////////////////////////////
private void initComponents() {
	setTitle("Customer Matcher Program");
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 705, 408);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	
	JLabel lblCustNameCol = new JLabel("Customer Name Column:");
	
	JLabel lblCustAddrCol = new JLabel("Customer Address Column:");
	
	JLabel lblCustZipCol = new JLabel("Customer Zip Column:");
	
	JLabel lblCustPhoneCol = new JLabel("Customer Phone Column:");
	
	JLabel lblCustInfCol = new JLabel("Customer Influencer Column:");
	
	JLabel lblFirstRow = new JLabel("First Row of Customers:");
	
	txtCustNameCol = new JTextField();
	txtCustNameCol.setText("Col Letter");
	txtCustNameCol.setColumns(10);
	
	txtCustAddrCol = new JTextField();
	txtCustAddrCol.setText("Col Letter");
	txtCustAddrCol.setColumns(10);
	
	txtCustZipCol = new JTextField();
	txtCustZipCol.setText("Col Letter");
	txtCustZipCol.setColumns(10);
	
	txtCustPhoneCol = new JTextField();
	txtCustPhoneCol.setText("Col Letter");
	txtCustPhoneCol.setColumns(10);
	
	txtCustInfCol = new JTextField();
	txtCustInfCol.setText("Col Letter");
	txtCustInfCol.setColumns(10);
	
	txtFirstRow = new JTextField();
	txtFirstRow.setText("Col Letter");
	txtFirstRow.setColumns(10);
	
	btnSelectInputFile = new JButton("Select Input File...");

	
	JButton btnSelectOutputLocation = new JButton("Select Output Location:");
	
	txtOutputFileLocation = new JTextField();
	txtOutputFileLocation.setColumns(10);
	
	lblSelectInputFile = new JLabel("New label");
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustNameCol)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustAddrCol)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustZipCol)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustPhoneCol)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustInfCol)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblFirstRow)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(btnSelectInputFile)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblSelectInputFile, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(btnSelectOutputLocation)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtOutputFileLocation, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)))
				.addContainerGap())
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(7)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnSelectInputFile)
					.addComponent(lblSelectInputFile))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustNameCol)
					.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustAddrCol)
					.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustZipCol)
					.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustPhoneCol)
					.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustInfCol)
					.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblFirstRow)
					.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnSelectOutputLocation)
					.addComponent(txtOutputFileLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(41, Short.MAX_VALUE))
	);
	contentPane.setLayout(gl_contentPane);
}
	
	
////////////////////////////////////////////
///This method contains all of the code for creating events
////////////////////////////////////////////
	private void createEvents() {
		// TODO Auto-generated method stub
		//TODO this should probably be in action events
		openFileChooser = new JFileChooser();
		btnSelectInputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				openFileChooser.setFileFilter(new FileNameExtensionFilter("XLSX files","xlsx"));
				int returnValue = openFileChooser.showOpenDialog(rootPane);
				if (returnValue == JFileChooser.APPROVE_OPTION){
					lblSelectInputFile.setText(openFileChooser.getSelectedFile().getAbsolutePath());
				} else {
					lblSelectInputFile.setText("No File Choosen");
				}
			}
		});
	}
}
