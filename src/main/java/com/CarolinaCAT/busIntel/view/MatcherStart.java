package com.CarolinaCAT.busIntel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonModel;
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

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MatcherStart extends JFrame {

	private JPanel contentPane;
	private JTextField txtCustNameCol;
	private JTextField txtCustAddrCol;
	private JTextField txtCustZipCol;
	private JTextField txtCustPhoneCol;
	private JTextField txtCustInfCol;
	private JTextField txtFirstRow;
	private JTextField txtOutputFileName;
	private final JFileChooser openFileChooser = new JFileChooser();
	private JButton btnSelectInputFile; 
	private JLabel lblSelectInputFile;
	private JRadioButton rdbtnUCC;
	private JRadioButton rdbtnDom;
	private JRadioButton rdbtnCustom;
	//TODO need getter on button group
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnRunMatcher;
	//TOD need getter on spinner name tolerance
	private JSpinner spnrNameTol;
	
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

	txtCustNameCol.setColumns(10);
	
	txtCustAddrCol = new JTextField();
	txtCustAddrCol.setColumns(10);
	
	txtCustZipCol = new JTextField();
	txtCustZipCol.setColumns(10);
	
	txtCustPhoneCol = new JTextField();
	txtCustPhoneCol.setColumns(10);
	
	txtCustInfCol = new JTextField();
	txtCustInfCol.setColumns(10);
	
	txtFirstRow = new JTextField();
	txtFirstRow.setColumns(10);
	
	btnSelectInputFile = new JButton("Select Input File...");
	
	txtOutputFileName = new JTextField();
	txtOutputFileName.setColumns(10);
	
	lblSelectInputFile = new JLabel("");
	
	JLabel lblMatchFileName = new JLabel("Matches File Name");
	
	rdbtnUCC = new JRadioButton("EDA UCC Download");
	buttonGroup.add(rdbtnUCC);
	
	rdbtnDom = new JRadioButton("DOM File");
	buttonGroup.add(rdbtnDom);
	
	rdbtnCustom = new JRadioButton("Custom");
	rdbtnCustom.setSelected(true);
	buttonGroup.add(rdbtnCustom);
	
	JLabel lblModifyCustomerName = new JLabel("Modify Customer Name Tolerance:");
	
	spnrNameTol = new JSpinner();
	spnrNameTol.setModel(new SpinnerNumberModel(new Integer(90), null, null, new Integer(1)));
	
	btnRunMatcher = new JButton("Run Matcher!");

	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblCustZipCol)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblCustPhoneCol)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblCustInfCol)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblFirstRow)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblMatchFileName)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtOutputFileName, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCustNameCol)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCustAddrCol)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
									.addComponent(btnSelectInputFile))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(219)
										.addComponent(lblSelectInputFile, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(145)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(rdbtnCustom)
											.addComponent(rdbtnDom)
											.addComponent(rdbtnUCC)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblModifyCustomerName)
												.addGap(18)
												.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))))
						.addContainerGap())
					.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addComponent(btnRunMatcher)
						.addGap(289))))
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(7)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnSelectInputFile)
					.addComponent(lblSelectInputFile))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
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
							.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(rdbtnCustom)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnUCC)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnDom)
						.addGap(33)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblModifyCustomerName)
							.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblMatchFileName)
					.addComponent(txtOutputFileName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(btnRunMatcher)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	);
	contentPane.setLayout(gl_contentPane);
}
	
	
////////////////////////////////////////////
///This method contains all of the code for creating events
////////////////////////////////////////////
	private void createEvents() {
		btnRunMatcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Read values in input boxes

				//Initiate popup for progress bar
				//TODO handle if user exes out program bar popup before done running
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ProgressBar frame = new ProgressBar();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		btnSelectInputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileChooser.setFileFilter(new FileNameExtensionFilter("XLSX files","xlsx"));
				int returnValue = openFileChooser.showOpenDialog(rootPane);
				if (returnValue == JFileChooser.APPROVE_OPTION){
					lblSelectInputFile.setText(openFileChooser.getSelectedFile().getAbsolutePath());
					//System.out.println(lblSelectInputFile.getText());
					int start = openFileChooser.getSelectedFile().getAbsolutePath().lastIndexOf("\\") + 1;
					System.out.println(start);
					int end = openFileChooser.getSelectedFile().getAbsolutePath().lastIndexOf(".");
					System.out.println(end);
					//int start = lblSelectInputFile.getText().substring(lblSelectInputFile.getText().lastIndexOf("\\"+1));
					String outfile = openFileChooser.getSelectedFile().getAbsolutePath().substring(start,end) + "_match";
					txtOutputFileName.setText(outfile);
					
				} else {
					lblSelectInputFile.setText("No File Choosen");
				}
			}
		});
		
		//TODO need multiple of these for each input box
		txtCustNameCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				//TODO figure out what to do with incorrect input
			}
		});
	}

	/**
	 * @return the txtCustNameCol
	 */
	public String getTxtCustNameCol() {
		return txtCustNameCol.getText();
	}

	/**
	 * @return the txtCustAddrCol
	 */
	public String getTxtCustAddrCol() {
		return txtCustAddrCol.getText();
	}

	/**
	 * @return the txtCustZipCol
	 */
	public String getTxtCustZipCol() {
		return txtCustZipCol.getText();
	}

	/**
	 * @return the txtCustPhoneCol
	 */
	public String getTxtCustPhoneCol() {
		return txtCustPhoneCol.getText();
	}

	/**
	 * @return the txtCustInfCol
	 */
	public String getTxtCustInfCol() {
		return txtCustInfCol.getText();
	}

	/**
	 * @return the txtFirstRow
	 */
	public int getTxtFirstRow() {
		return Integer.parseInt(txtFirstRow.getText());
	}

	/**
	 * @return the txtOutputFileName
	 */
	public String getTxtOutputFileName() {
		//TODO add file path to this
		return txtOutputFileName.getText();
	}

	/**
	 * @return the lblSelectInputFile
	 */
	public JLabel getLblSelectInputFile() {
		return lblSelectInputFile;
	}

	/**
	 * @return the spnrNameTol
	 */
	public int getSpnrNameTol() {
		return (int) spnrNameTol.getValue();
	}
}
