package com.CarolinaCAT.busIntel.view;


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
import javax.swing.filechooser.FileNameExtensionFilter;





import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;

import com.CarolinaCAT.busIntel.matching.CustomerMatcher;

public class MatcherStart extends JFrame {

	/**
	 * Auto-gen serializable version id
	 */
	private static final long serialVersionUID = -6712151586227499454L;
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
	private File inputFile;
	private JLabel lblErrFirstRow;
	private JLabel lblErrInflCol;
	private JLabel lblErrPhoneCol;
	private JLabel lblErrZipCol;
	private JLabel lblErrAddrCol;
	private JLabel lblErrNameCol;
	private JLabel lblIgnore;
	private JCheckBox ckbxIgnrName;
	private JCheckBox ckbxIgnrAddr;
	private JCheckBox ckbxIgnrZip;
	private JCheckBox ckbxIgnrPhone;
	private JCheckBox ckbxIgnrInfl;
	private ProgressBar progBarFrame;
	//tells main class whether to start executing or not
	public static volatile boolean matcherStart = false;
	
//	*****************ONLY NEEDED FOR TESTING*************************
	public static void main(String args[]){
		//create the GUI frame
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
	setBounds(100, 100, 705, 482);
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
	
	lblSelectInputFile = new JLabel("No File Selected");
	
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
	btnRunMatcher.setEnabled(false);
	
	lblErrNameCol = new JLabel("*");
	lblErrNameCol.setFont(new Font("Tahoma", Font.BOLD, 13));
	lblErrNameCol.setForeground(Color.RED);
	
	lblErrAddrCol = new JLabel("*");
	lblErrAddrCol.setFont(new Font("Tahoma", Font.BOLD, 13));
	lblErrAddrCol.setForeground(Color.RED);
	
	lblErrZipCol = new JLabel("*");
	lblErrZipCol.setFont(new Font("Tahoma", Font.BOLD, 13));
	lblErrZipCol.setForeground(Color.RED);
	
	lblErrPhoneCol = new JLabel("*");
	lblErrPhoneCol.setFont(new Font("Tahoma", Font.BOLD, 13));
	lblErrPhoneCol.setForeground(Color.RED);
	
	lblErrInflCol = new JLabel("*");
	lblErrInflCol.setFont(new Font("Tahoma", Font.BOLD, 13));
	lblErrInflCol.setForeground(Color.RED);
	
	lblErrFirstRow = new JLabel("*");
	lblErrFirstRow.setFont(new Font("Tahoma", Font.BOLD, 13));
	lblErrFirstRow.setForeground(new Color(255, 0, 0));
	
	ckbxIgnrName = new JCheckBox("");
	
	
	ckbxIgnrAddr = new JCheckBox("");
	
	ckbxIgnrZip = new JCheckBox("");
	
	ckbxIgnrPhone = new JCheckBox("");
	
	ckbxIgnrInfl = new JCheckBox("");
	
	lblIgnore = new JLabel("Ignore?");

	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(241)
				.addComponent(btnRunMatcher)
				.addGap(317))
			.addGroup(gl_contentPane.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addComponent(ckbxIgnrName)
					.addComponent(ckbxIgnrAddr)
					.addComponent(ckbxIgnrZip)
					.addComponent(ckbxIgnrPhone)
					.addComponent(ckbxIgnrInfl)
					.addComponent(lblIgnore))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblCustZipCol)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblErrZipCol))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblCustInfCol)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblErrInflCol))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblFirstRow)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblErrFirstRow))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblMatchFileName)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtOutputFileName, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblCustNameCol)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblErrNameCol))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblCustAddrCol)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblErrAddrCol))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCustPhoneCol)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblErrPhoneCol)))
								.addGap(52)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(rdbtnUCC)
									.addComponent(rdbtnCustom)
									.addComponent(rdbtnDom))
								.addGap(257)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblModifyCustomerName)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnSelectInputFile)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblSelectInputFile, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(96, Short.MAX_VALUE))))
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnSelectInputFile)
					.addComponent(lblSelectInputFile))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblIgnore)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustNameCol)
							.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrNameCol)
							.addComponent(ckbxIgnrName))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustAddrCol)
							.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrAddrCol)
							.addComponent(ckbxIgnrAddr))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustZipCol)
							.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrZipCol)
							.addComponent(ckbxIgnrZip))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustPhoneCol)
							.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrPhoneCol)
							.addComponent(ckbxIgnrPhone))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustInfCol)
							.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrInflCol)
							.addComponent(ckbxIgnrInfl))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblFirstRow)
							.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrFirstRow)))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(3)
						.addComponent(rdbtnCustom)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnUCC)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnDom)))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblMatchFileName)
					.addComponent(txtOutputFileName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblModifyCustomerName))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnRunMatcher)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	);
	contentPane.setLayout(gl_contentPane);
}
	
	
////////////////////////////////////////////
///This method contains all of the code for creating events
////////////////////////////////////////////
	private void createEvents() {
		//TODO need to do radio buttons
		
		btnRunMatcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Initiate popup for progress bar
				//TODO handle if user exes out program bar popup before done running
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							progBarFrame = new ProgressBar();
							System.out.println("created prog bar");
							progBarFrame.setVisible(true);
							System.out.println("prog bar visible");
							CustomerMatcher matcherProg = null;
							//create array of column locations
							int[] colLocs = new int[6];
							colLocs[0] =  getTxtFirstRow();
							colLocs[1] = (getTxtCustNameCol() == -1) ?  -1 : getTxtCustNameCol();
							colLocs[2] = (getTxtCustInfCol() == -1) ?  -1 : getTxtCustInfCol();
							colLocs[3] = (getTxtCustAddrCol() == -1) ?  -1 : getTxtCustAddrCol();
							colLocs[4] = (getTxtCustPhoneCol() == -1) ?  -1 : getTxtCustPhoneCol();
							colLocs[5] = (getTxtCustZipCol() == -1) ?  -1 : getTxtCustZipCol();
							try {
								System.out.println("starting run");
								matcherProg = new CustomerMatcher(getTxtInputFileAndAbsPath(),getTxtOutputFileAndAbsPath(), progBarFrame, colLocs);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
					inputFile = openFileChooser.getSelectedFile();
					//System.out.println(inputFile.getCanonicalPath());
					lblSelectInputFile.setText(inputFile.getAbsolutePath());
					//System.out.println(lblSelectInputFile.getText());
					int start = inputFile.getAbsolutePath().lastIndexOf("\\") + 1;
					System.out.println(start);
					int end = inputFile.getAbsolutePath().lastIndexOf(".");
					System.out.println(end);
					//int start = lblSelectInputFile.getText().substring(lblSelectInputFile.getText().lastIndexOf("\\"+1));
					String outfile = openFileChooser.getSelectedFile().getAbsolutePath().substring(start,end) + "_match";
					txtOutputFileName.setText(outfile);
					checkReadyToRun();
				} else {
					lblSelectInputFile.setText("No File Choosen");
				}
			}
		});
		
		///////////////////////////  DEFINE PRESETS FOR THE RADIO BUTTONS ///////////////////////////
		rdbtnUCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnUCC.isSelected()){
					//TODO set up presets for a UCC EDA file
					//TODO give room for a concatenation field
					txtCustNameCol.setText("H");
					txtCustAddrCol.setText("I");
					txtCustZipCol.setText("M");
					txtCustPhoneCol.setText("N");
					txtCustInfCol.setText("C");
					txtFirstRow.setText("2");
				}
			}
		});
		rdbtnDom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnDom.isSelected()){
					//TODO set up presets for a UCC EDA file
					txtCustNameCol.setText("A");
					txtCustAddrCol.setText("D");
					txtCustZipCol.setText("G");
					txtCustPhoneCol.setText("K");
					txtCustInfCol.setText("B");
					txtFirstRow.setText("2");
				}
			}
		});		
		rdbtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnCustom.isSelected()){
					txtCustNameCol.setText("");
					txtCustAddrCol.setText("");
					txtCustZipCol.setText("");
					txtCustPhoneCol.setText("");
					txtCustInfCol.setText("");
					txtFirstRow.setText("");
				}
			}
		});		
		
		////////////////////////////   LISTENERS FOR TEXT BOX VALIDATION ////////////////////////////
		// TODO Check if focus listener is really what want, maybe a change listener
		// TODO on listener, make uppercase for readability
		txtCustNameCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustNameCol.getText())){
					txtCustNameCol.setText(txtCustNameCol.getText().toUpperCase());
					lblErrNameCol.setText("");
					checkReadyToRun();
				} else {
					lblErrNameCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		txtCustAddrCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustAddrCol.getText())){
					txtCustAddrCol.setText(txtCustAddrCol.getText().toUpperCase());
					lblErrAddrCol.setText("");
					checkReadyToRun();
				} else {
					lblErrAddrCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		txtCustPhoneCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustPhoneCol.getText())){
					txtCustPhoneCol.setText(txtCustPhoneCol.getText().toUpperCase());
					lblErrPhoneCol.setText("");
					checkReadyToRun();
				} else {
					lblErrPhoneCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		txtCustZipCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustZipCol.getText())){
					txtCustZipCol.setText(txtCustZipCol.getText().toUpperCase());
					lblErrZipCol.setText("");
					checkReadyToRun();
				} else {
					lblErrZipCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		
		txtCustInfCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustInfCol.getText())){
					txtCustInfCol.setText(txtCustInfCol.getText().toUpperCase());
					lblErrInflCol.setText("");
					checkReadyToRun();
					//TESTING ONLY System.out.println(getTxtCustInfCol());
				} else {
					lblErrInflCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		
		txtFirstRow.addFocusListener(new FocusAdapter() {
			@Override
			//TODO this isn't correct need to check agaist number not checkCol method
			public void focusLost(FocusEvent arg0) {
				if (txtFirstRow.getText().length() > 0 && txtFirstRow.getText().matches("-?\\d+")){
					lblErrFirstRow.setText("");
					checkReadyToRun();
				} else {
					lblErrFirstRow.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		///////////////////////***	CHECK BOX LISTENERS AND BUTTON ENABLE ***////////////////////
		ckbxIgnrName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrName.isSelected() ) { 
					lblErrNameCol.setText("");
					txtCustNameCol.setText("");
					txtCustNameCol.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustNameCol.setEditable(true);
					lblErrNameCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		ckbxIgnrAddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrAddr.isSelected() ) { 
					lblErrAddrCol.setText("");
					txtCustAddrCol.setText("");
					txtCustAddrCol.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustAddrCol.setEditable(true);
					lblErrAddrCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		ckbxIgnrZip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrZip.isSelected() ) { 
					lblErrZipCol.setText("");
					txtCustZipCol.setText("");
					txtCustZipCol.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustZipCol.setEditable(true);
					lblErrZipCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		ckbxIgnrPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrPhone.isSelected() ) { 
					lblErrPhoneCol.setText("");
					txtCustPhoneCol.setText("");
					txtCustPhoneCol.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustPhoneCol.setEditable(true);
					lblErrPhoneCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
		
		ckbxIgnrInfl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrInfl.isSelected() ) { 
					lblErrInflCol.setText("");
					txtCustInfCol.setText("");
					txtCustInfCol.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustInfCol.setEditable(true);
					lblErrInflCol.setText("*");
					btnRunMatcher.setEnabled(false);
				}
			}
		});
	}

	//////////////////////////////*** GETTERS FOR PROGRAM INPUT ****//////////////////////////////////////////////////////////////////////
	/**
	 * @return the txtCustNameCol
	 */
	public int getTxtCustNameCol() {
		String tmp = txtCustNameCol.getText();
		if(tmp.length()==1){
			char c1 = tmp.charAt(0);
			//A is 65
			return ((int) c1) - 65;
		} else {
			char c1 = tmp.charAt(0);
			char c2 = tmp.charAt(1);
			int i = 26 + ((int) c1) - 65 + ((int) c2) - 65;
			return i;
		}
	}

	/**
	 * @return the txtCustAddrCol
	 */
	public int getTxtCustAddrCol() {
		if(txtCustAddrCol.getText() == null ) {return -1;}
		String tmp = txtCustAddrCol.getText();
		if(tmp.length()==1){
			char c1 = tmp.charAt(0);
			//A is 65
			return ((int) c1) - 65;
		} else {
			char c1 = tmp.charAt(0);
			char c2 = tmp.charAt(1);
			int i = 26 + ((int) c1) - 65 + ((int) c2) - 65;
			return i;
		}
	}

	/**
	 * @return the txtCustZipCol
	 */
	public int getTxtCustZipCol() {
		if(txtCustZipCol.getText() == null ) {return -1;}
		String tmp = txtCustZipCol.getText();
		if(tmp.length()==1){
			char c1 = tmp.charAt(0);
			//A is 65
			return ((int) c1) - 65;
		} else {
			char c1 = tmp.charAt(0);
			char c2 = tmp.charAt(1);
			int i = 26 + ((int) c1) - 65 + ((int) c2) - 65;
			return i;
		}
	}

	/**
	 * @return the txtCustPhoneCol
	 */
	public int getTxtCustPhoneCol() {
		if(txtCustPhoneCol.getText() == null ) {return -1;}
		String tmp = txtCustPhoneCol.getText();
		if(tmp.length()==1){
			char c1 = tmp.charAt(0);
			//A is 65
			return ((int) c1) - 65;
		} else {
			char c1 = tmp.charAt(0);
			char c2 = tmp.charAt(1);
			int i = 26 + ((int) c1) - 65 + ((int) c2) - 65;
			return i;
		}
	}

	/**
	 * @return the txtCustInfCol
	 */
	public int getTxtCustInfCol() {
		if(txtCustInfCol.getText() == null ) {return -1;}
		String tmp = txtCustInfCol.getText().toUpperCase();
		if(tmp.length()==1){
			char c1 = tmp.charAt(0);
			//A is 65
			return ((int) c1) - 65;
		} else {
			char c1 = tmp.charAt(0);
			char c2 = tmp.charAt(1);
			int i = 26 + ((int) c1) - 65 + ((int) c2) - 65;
			return i;
		}
	}

	/**
	 * @return the txtFirstRow
	 */
	public int getTxtFirstRow() {
		return Integer.parseInt(txtFirstRow.getText());
	}

	/**Gets the absolute path of the output file as a string
	 * @return the txtOutputFileName
	 */
	public String getTxtOutputFileAndAbsPath() {
		return inputFile.getParent() + "\\" +txtOutputFileName.getText()+".xlsx";
	}

	/** This represents the inpt file selected
	 * @return the lblSelectInputFile
	 */
	public String getTxtInputFileAndAbsPath() {
		return lblSelectInputFile.getText();
	}

	/**
	 * @return the selected input file
	 */
	public File getSelectedInputFile() {
		return inputFile;
	}
	
	/**
	 * @return the spnrNameTol
	 */
	public int getSpnrNameTol() {
		return (int) spnrNameTol.getValue();
	}
	////////////////////////////////////END GETTERS////////////////////////////////////////////
	
	//////////////////////////*** UPDATE PROGRESS BARS ***/////////////////////////////////////
	public void updateDBSLoadStatus(int pct){
		progBarFrame.setPBImportDBS(pct);
	}
	
	public void updateReadExcelCustomersStatus(int pct){
		progBarFrame.setPBReadCusts(pct);
	}
	
	public void updateCustomerMatchingStatus(int pct){
		progBarFrame.setPBGenMatches(pct);
	}
	
	////////////////////////////////////HELPER METHODS/////////////////////////////////////////
	//check columns are appropriate format as entered
	private boolean checkCol(String col){
		if(col.trim().length()>0 && col.trim().length() < 3 && col.trim().matches("[a-zA-Z]+")){
			return true;
		} else {
			return false;
		}
	}
	//checks for error indicators, will enable button if no indicators exist
	private void checkReadyToRun(){
		
		//probably should put error labels in an array
		if(lblErrNameCol.getText().equals("*") || lblErrAddrCol.getText().equals("*")|| lblErrZipCol.getText().equals("*") || 
			lblErrPhoneCol.getText().equals("*") || lblErrInflCol.getText().equals("*") || lblErrFirstRow.getText().equals("*")
			|| lblSelectInputFile.getText().equals("No File Choosen") || txtOutputFileName.getText().trim().length() < 1 ){
			btnRunMatcher.setEnabled(false);
		} else {
			btnRunMatcher.setEnabled(true);
		}
	}
	
	public boolean getMatcherStart(){
		return matcherStart;
	}
	
}
