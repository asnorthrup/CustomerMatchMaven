package com.CarolinaCAT.busIntel.view;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
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






import javax.swing.text.MaskFormatter;

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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
/**
 * Start class where Jframe is created for user input
 * @author ANorthrup
 *
 */
public class MatcherStart extends JFrame {

	//Auto-gen serializable version id	
	private static final long serialVersionUID = -6712151586227499454L;
	//Customer matcher main panel
	private JPanel contentPane;
	//text boxes for user input about excel file setup
	private JTextField txtCustNameCol;
	private JTextField txtCustNameCol2;
	private JTextField txtCustAddrCol;
	private JTextField txtCustAddrCol2;
	private JTextField txtCustZipCol;
	private JTextField txtCustZipCol2;
	private JTextField txtCustPhoneCol;
	private JTextField txtCustInfCol;
	private JTextField txtCustInfCol2;
	private JTextField txtCustInfCol3;
	private JTextField txtCustInfCol4;
	private JTextField txtFirstRow;
	private JTextField txtOutputFileName;
	private JSpinner spnrNameTol;
	private JTextField txtTabName;
	//opens file browser
	private final JFileChooser openFileChooser = new JFileChooser();
	//button that opens the file chooser
	private JButton btnSelectInputFile; 
	private JLabel lblSelectInputFile;
	//radio buttons for pre-populated columns of standard reports
	private JRadioButton rdbtnUCC;
	private JRadioButton rdbtnDom;
	private JRadioButton rdbtnCustom;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnRunMatcher;
	//labels for user input text boxes
	private File inputFile;
	private File accessDBFile;
	private JLabel lblErrFirstRow;
	private JLabel lblErrInflCol;
	private JLabel lblErrPhoneCol;
	private JLabel lblErrZipCol;
	private JLabel lblErrAddrCol;
	private JLabel lblErrNameCol;
	private JLabel lblIgnore;
	private JLabel lblErrTabName;
	//check boxes to ignore certain columns
	private JCheckBox ckbxIgnrName;
	private JCheckBox ckbxIgnrAddr;
	private JCheckBox ckbxIgnrZip;
	private JCheckBox ckbxIgnrPhone;
	private JCheckBox ckbxIgnrInfl;

	//class that invokes the popup window for showing progress on matching
	private ProgressBar progBarFrame;
	//tells main class whether to start executing or not
	public static volatile boolean matcherStart = false;
	private boolean readyToRun = false;
	private JLabel lblAccessDBLocation;
	private JButton btnNavigateToDb;




	
//	*****************ONLY NEEDED FOR TESTING*************************
//	public static void main(String args[]){
//		//create the GUI frame
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MatcherStart frame = new MatcherStart();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	/**
	 * Constructor to create the GUI frame and events
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
	setBounds(100, 100, 988, 619);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	
	////////////// CREATE LABELS FOR EXCEL INPUT COLUMNS /////////////////
	JLabel lblCustNameCol = new JLabel("Company Name Column:");
	JLabel lblCustAddrCol = new JLabel("Customer Address Column:");
	JLabel lblCustZipCol = new JLabel("Customer Zip Column:");
	JLabel lblCustPhoneCol = new JLabel("Customer Phone Column:");
	JLabel lblCustInfCol = new JLabel("Customer Influencer Column:");
	JLabel lblFirstRow = new JLabel("First Row of Customers:");
	
	/////////////////  CREATE TEXT FIELDS FOR EXCEL COLUMNS  //////////////////
	txtCustNameCol = new JTextField();
	txtCustNameCol.setColumns(2);

	
	txtCustNameCol2 = new JTextField();
	txtCustNameCol2.setToolTipText("Optional second column in Excel sheet to concatenate to the first column (i.e. company name split across two columns)");
	txtCustNameCol2.setColumns(2);
	
	txtCustAddrCol = new JTextField();
	txtCustAddrCol.setColumns(2);
	
	txtCustAddrCol2 = new JTextField();
	txtCustAddrCol2.setToolTipText("Optional second address column. Treated as a second address to use in lookup. This is NOT concatenated tot he first address column. If no second zip column provided, zip will be used from first column.");
	txtCustAddrCol2.setColumns(2);
	
	txtCustZipCol = new JTextField();
	txtCustZipCol.setColumns(2);
	
	txtCustZipCol2 = new JTextField();
	txtCustZipCol2.setToolTipText("Optional second zip. Used with second address column if second address column is supplied.");
	txtCustZipCol2.setColumns(2);
	
	txtCustPhoneCol = new JTextField();
	txtCustPhoneCol.setColumns(2);
	
	//note influencer columns aren't used yet in matching algorithm, still trying to figure out best way to do a match
	txtCustInfCol = new JTextField();
	txtCustInfCol.setToolTipText("First and Last Column OR First Name to be concatenated with (optional) last name column 1, if supplied.");
	txtCustInfCol.setColumns(2);
	
	txtCustInfCol2 = new JTextField();
	txtCustInfCol2.setToolTipText("Optional last name column 1 to concatenate to first column entered");
	txtCustInfCol2.setColumns(2);
	
	txtCustInfCol3 = new JTextField();
	txtCustInfCol3.setToolTipText("Optional first and last column for second influencer OR second influencers first name.");
	txtCustInfCol3.setColumns(2);
	
	txtCustInfCol4 = new JTextField();
	txtCustInfCol4.setToolTipText("Second influencer last name will be concatenated with optional second influencer first name if this field is filled in");
	txtCustInfCol4.setColumns(2);
	
	txtFirstRow = new JTextField();
	txtFirstRow.setColumns(3);

	btnSelectInputFile = new JButton("Select Input File...");
	
	txtOutputFileName = new JTextField();
	txtOutputFileName.setColumns(10); //test, don't think necessary
	
	lblSelectInputFile = new JLabel("No File Selected");
	
	JLabel lblMatchFileName = new JLabel("Matches File Name");
	
	//create preset radio buttons
	rdbtnUCC = new JRadioButton("EDA UCC Download");
	buttonGroup.add(rdbtnUCC);
	rdbtnDom = new JRadioButton("DOM File");
	buttonGroup.add(rdbtnDom);
	rdbtnCustom = new JRadioButton("Custom");
	rdbtnCustom.setSelected(true);
	buttonGroup.add(rdbtnCustom);
	
	//spinner
	JLabel lblModifyCustomerName = new JLabel("Modify Customer Name Tolerance:");
	spnrNameTol = new JSpinner();
	spnrNameTol.setModel(new SpinnerNumberModel(new Integer(90), null, null, new Integer(1)));
	
	btnRunMatcher = new JButton("Run Matcher!");
	btnRunMatcher.setEnabled(true);
	
	//error labels if required fields not set
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
	
	//check boxes to ignore certain input options
	ckbxIgnrName = new JCheckBox("");
	ckbxIgnrAddr = new JCheckBox("");
	ckbxIgnrZip = new JCheckBox("");
	ckbxIgnrPhone = new JCheckBox("");
	ckbxIgnrInfl = new JCheckBox("");
	lblIgnore = new JLabel("Ignore?");
	
	JLabel lblNewLabel = new JLabel("Tab Name:");
	
	//setup textbox for tab names
	txtTabName = new JTextField();
	txtTabName.setColumns(10);
	lblErrTabName = new JLabel("*");
	lblErrTabName.setForeground(Color.RED);
	lblErrTabName.setFont(new Font("Tahoma", Font.BOLD, 13));
	
	btnNavigateToDb = new JButton("Navigate to DB");
	
	lblAccessDBLocation = new JLabel("No File Selected");
	
	JButton btnClearDBSelection = new JButton("Clear");
	
	//setup the layout of the GUI
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(62)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(rdbtnCustom, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addComponent(rdbtnDom)
						.addGap(31)
						.addComponent(rdbtnUCC))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnSelectInputFile)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(btnClearDBSelection, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel))
								.addGap(44))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(ckbxIgnrAddr)
									.addComponent(ckbxIgnrName)
									.addComponent(ckbxIgnrZip)
									.addComponent(ckbxIgnrPhone)
									.addComponent(ckbxIgnrInfl))
								.addGap(11))
							.addComponent(lblIgnore))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(txtTabName, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblErrTabName, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnNavigateToDb, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblAccessDBLocation, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCustZipCol)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addGap(2)
										.addComponent(lblErrZipCol)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtCustZipCol2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCustInfCol)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addGap(2)
										.addComponent(lblErrInflCol)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtCustInfCol2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtCustInfCol3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtCustInfCol4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblFirstRow)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addGap(2)
										.addComponent(lblErrFirstRow))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblMatchFileName)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtOutputFileName, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblCustAddrCol)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblCustNameCol)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
												.addGap(2)
												.addComponent(lblErrNameCol)))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblErrAddrCol)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtCustAddrCol2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
											.addComponent(txtCustNameCol2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
										.addGap(53))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCustPhoneCol)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addGap(2)
										.addComponent(lblErrPhoneCol))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblModifyCustomerName)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(btnRunMatcher)
											.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(18)
								.addComponent(lblSelectInputFile, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)))))
				.addContainerGap())
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(rdbtnCustom)
					.addComponent(rdbtnDom)
					.addComponent(rdbtnUCC))
				.addGap(7)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblSelectInputFile, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnSelectInputFile))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addComponent(txtTabName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblErrTabName))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnClearDBSelection)
					.addComponent(btnNavigateToDb)
					.addComponent(lblAccessDBLocation))
				.addGap(19)
				.addComponent(lblIgnore)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addComponent(ckbxIgnrName)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCustNameCol)
						.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblErrNameCol)
						.addComponent(txtCustNameCol2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCustAddrCol)
						.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblErrAddrCol)
						.addComponent(txtCustAddrCol2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addComponent(ckbxIgnrAddr))
				.addGap(15)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCustZipCol)
						.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblErrZipCol)
						.addComponent(txtCustZipCol2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addComponent(ckbxIgnrZip))
				.addGap(15)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCustPhoneCol)
						.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblErrPhoneCol))
					.addComponent(ckbxIgnrPhone))
				.addGap(15)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustInfCol)
							.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrInflCol)
							.addComponent(txtCustInfCol2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtCustInfCol3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtCustInfCol4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(13)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblFirstRow)
							.addComponent(txtFirstRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrFirstRow))
						.addGap(15)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblMatchFileName)
							.addComponent(txtOutputFileName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblModifyCustomerName))
						.addGap(18)
						.addComponent(btnRunMatcher))
					.addComponent(ckbxIgnrInfl))
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
				//Initiate popup for progress bar
				//TODO handle if user exes out program bar popup before done running
				if (readyToRun == true){
					//thread where GUI is processesed is EDT thread.
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								if (progBarFrame == null){
									progBarFrame = new ProgressBar();
									System.out.println("created prog bar");
								}
								//TODO try setting progree bars to 0 then set visible, without setting to null below
								
								progBarFrame.setVisible(true);
								System.out.println("prog bar visible");
								//CustomerMatcher matcherProg = null;
								//create array of column locations, -1 is used to indicate that this isn't going to be read in (i.e. left blank by user)
								//TODO should this be in the run block of the event queue?
								int[] colLocs = new int[12];
								colLocs[0] =  getTxtFirstRow();
								colLocs[1] = (getTxtCustNameCol() == -1) ?  -1 : getTxtCustNameCol();
								colLocs[2] = (getTxtCustNameCol2() == -1) ?  -1 : getTxtCustNameCol2();
								colLocs[3] = (getTxtCustInfCol() == -1) ?  -1 : getTxtCustInfCol();
								colLocs[4] = (getTxtCustInfCol2() == -1) ?  -1 : getTxtCustInfCol2();
								colLocs[5] = (getTxtCustInfCol3() == -1) ?  -1 : getTxtCustInfCol3();
								colLocs[6] = (getTxtCustInfCol4() == -1) ?  -1 : getTxtCustInfCol4();
								colLocs[7] = (getTxtCustAddrCol() == -1) ?  -1 : getTxtCustAddrCol();
								colLocs[8] = (getTxtCustAddrCol2() == -1) ?  -1 : getTxtCustAddrCol2();
								colLocs[9] = (getTxtCustPhoneCol() == -1) ?  -1 : getTxtCustPhoneCol();
								colLocs[10] = (getTxtCustZipCol() == -1) ?  -1 : getTxtCustZipCol();
								colLocs[11] = (getTxtCustZipCol2() == -1) ?  -1 : getTxtCustZipCol2();
								//TODO should this be inside the run event queue?
								try {
									System.out.println("starting run");
									CustomerMatcher matcherProg = null;
									String accessDBloc = null;
									//TODO need to set string for access DB
									if (lblAccessDBLocation.getText().trim().length() > 0){
										accessDBloc = lblAccessDBLocation.getText().trim();
									}
									//check new location db
									if (accessDBloc == null){
										new CustomerMatcher(null, getTxtInputFileAndAbsPath(),getTxtOutputFileAndAbsPath(), progBarFrame, colLocs, getTabName(),getSpnrNameTol());
									} else {
										new CustomerMatcher(accessDBloc, getTxtInputFileAndAbsPath(),getTxtOutputFileAndAbsPath(), progBarFrame, colLocs, getTabName(),getSpnrNameTol());
									}
									
									
									JOptionPane.showMessageDialog(null,
										    "Matching Complete!",
										    "Complete",
										    JOptionPane.INFORMATION_MESSAGE);
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, 
						                    "Error, file not loaded properly. Check file exists, tab name is correct", 
						                    "Cannot Write Warning", 
						                    JOptionPane.WARNING_MESSAGE);
								}
								progBarFrame.setVisible(false);
								//TODO check if setting this to null is creating an issue re-painting
								progBarFrame = null;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					//TODO should this be in the run block of the event queue?
					//CustomerMatcher matcherProg = null;
					//create array of column locations, -1 is used to indicate that this isn't going to be read in (i.e. left blank by user)
//					int[] colLocs = new int[12];
//					colLocs[0] =  getTxtFirstRow();
//					colLocs[1] = (getTxtCustNameCol() == -1) ?  -1 : getTxtCustNameCol();
//					colLocs[2] = (getTxtCustNameCol2() == -1) ?  -1 : getTxtCustNameCol2();
//					colLocs[3] = (getTxtCustInfCol() == -1) ?  -1 : getTxtCustInfCol();
//					colLocs[4] = (getTxtCustInfCol2() == -1) ?  -1 : getTxtCustInfCol2();
//					colLocs[5] = (getTxtCustInfCol3() == -1) ?  -1 : getTxtCustInfCol3();
//					colLocs[6] = (getTxtCustInfCol4() == -1) ?  -1 : getTxtCustInfCol4();
//					colLocs[7] = (getTxtCustAddrCol() == -1) ?  -1 : getTxtCustAddrCol();
//					colLocs[8] = (getTxtCustAddrCol2() == -1) ?  -1 : getTxtCustAddrCol2();
//					colLocs[9] = (getTxtCustPhoneCol() == -1) ?  -1 : getTxtCustPhoneCol();
//					colLocs[10] = (getTxtCustZipCol() == -1) ?  -1 : getTxtCustZipCol();
//					colLocs[11] = (getTxtCustZipCol2() == -1) ?  -1 : getTxtCustZipCol2();
//					//TODO should this be inside the run event queue?
//					try {
//						System.out.println("starting run");
//						CustomerMatcher matcherProg = new CustomerMatcher(getTxtInputFileAndAbsPath(),getTxtOutputFileAndAbsPath(), progBarFrame, colLocs, getTabName(),getSpnrNameTol());
//						JOptionPane.showMessageDialog(null,
//							    "Matching Complete!",
//							    "Complete",
//							    JOptionPane.INFORMATION_MESSAGE);
//					} catch (Exception e) {
//						e.printStackTrace();
//						JOptionPane.showMessageDialog(null, 
//			                    "Error, file not loaded properly. Check file exists, tab name is correct", 
//			                    "Cannot Write Warning", 
//			                    JOptionPane.WARNING_MESSAGE);
//					}
//					progBarFrame.setVisible(false);
//					//TODO check if setting this to null is creating an issue re-painting
//					progBarFrame = null;
					
				} else {
					JOptionPane.showMessageDialog(null,
						    "Cannot Run, Please add required fields",
						    "Not Ready to Run",
						    JOptionPane.ERROR_MESSAGE);
				}
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
					//TODO see if it is possible to run matcher with no file choosen
				}
			}
		});
		
		//TODO need to clean these up to get right files and lbls correct
		btnNavigateToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileChooser.setFileFilter(new FileNameExtensionFilter("ACCDB files","accdb"));
				int returnValue = openFileChooser.showOpenDialog(rootPane);
				if (returnValue == JFileChooser.APPROVE_OPTION){
					//accessDBfile is equiv to inputFile in prior action listener, changed to accessDBFile
					accessDBFile = openFileChooser.getSelectedFile();
					//System.out.println(inputFile.getCanonicalPath());
					lblAccessDBLocation.setText(accessDBFile.getAbsolutePath());
					//System.out.println(lblSelectInputFile.getText());
					int start = accessDBFile.getAbsolutePath().lastIndexOf("\\") + 1;
					System.out.println(start);
					int end = accessDBFile.getAbsolutePath().lastIndexOf(".");
					System.out.println(end);
					//int start = lblSelectInputFile.getText().substring(lblSelectInputFile.getText().lastIndexOf("\\"+1));
//					String outfile = openFileChooser.getSelectedFile().getAbsolutePath().substring(start,end) + "_match";
//					lblAccessDBloc.setText(outfile);
					checkReadyToRun();
				} else {
					lblAccessDBLocation.setText("No File Choosen");
					//TODO see if it is possible to run matcher with no file choosen
				}
			}
		});
		
		
		///////////////////////////  DEFINE PRESETS FOR THE RADIO BUTTONS ///////////////////////////
		rdbtnUCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnUCC.isSelected()){
					txtTabName.setText("QueryResults");
					lblErrTabName.setText(""); //clears error indicator, same below
					txtCustNameCol.setText("H");
					lblErrNameCol.setText(""); 
					txtCustAddrCol.setText("I");
					txtCustAddrCol2.setText("J");
					lblErrAddrCol.setText("");
					txtCustZipCol.setText("M");
					lblErrZipCol.setText("");
					txtCustPhoneCol.setText("N");
					lblErrPhoneCol.setText("");
					txtCustInfCol.setText("C");
					lblErrInflCol.setText("");
					txtFirstRow.setText("2");
					lblErrFirstRow.setText("");
					checkReadyToRun();
				}
			}
		});
		
		//TODO when manually add a tab name, check after presets are set
		rdbtnDom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnDom.isSelected()){
					txtTabName.setText("Results");
					lblErrTabName.setText("");
					txtCustNameCol.setText("A");
					lblErrNameCol.setText(""); //clears error indicator
					txtCustAddrCol.setText("D");
					lblErrAddrCol.setText("");
					txtCustZipCol.setText("G");
					lblErrZipCol.setText("");
					txtCustPhoneCol.setText("K");
					lblErrPhoneCol.setText("");
					txtCustInfCol.setText("B");
					lblErrInflCol.setText("");
					txtFirstRow.setText("2");
					lblErrFirstRow.setText("");
					checkReadyToRun();
				}
			}
		});
		//default start of program
		rdbtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnCustom.isSelected()){
					txtCustNameCol.setText("");
					lblErrNameCol.setText("*");
					txtCustAddrCol.setText("");
					lblErrAddrCol.setText("*");
					txtCustZipCol.setText("");
					lblErrZipCol.setText("*");
					txtCustPhoneCol.setText("");
					lblErrPhoneCol.setText("*");
					txtCustInfCol.setText("");
					lblErrInflCol.setText("*");
					txtFirstRow.setText("");
					lblErrFirstRow.setText("*");
					checkReadyToRun();
				}
			}
		});		
		
		////////////////////////////   LISTENERS FOR TEXT BOX VALIDATION ////////////////////////////
		// TODO Check if focus listener is really what want, maybe a change listener	
		//TODO notes - formatted text field text and value are 2 diferent properties (value typ lags behind text), text property always reflects
		//what the field displays, the value property might not, while user types, text property changes, but value doesn't change until the changes
		//are committed. Value can be set by set value or commitEdit method.The commitEdit method sets the value to whatever object the formatter determines is represented by the field's text. The commitEdit method is automatically called when either of the following happens:
		//When the user presses Enter while the field has the focus.
		//By default, when the field loses the focus, for example, when the user presses the Tab key to change the focus to another component. You can use the setFocusLostBehavior method to specify a different outcome when the field loses the focus. 
		//https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html
		
		//checkColTextInput(JTextField tf, JLabel jl, KeyEvent ke)
		txtCustNameCol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkColTextInput(txtCustNameCol, lblErrNameCol, arg0);
			}
		});
		
		txtCustNameCol2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkExtraColTextInput(txtCustNameCol2, arg0);
			}
		});
		
		txtCustAddrCol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkColTextInput(txtCustAddrCol, lblErrAddrCol, arg0);
			}
		});
		
		txtCustAddrCol2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkExtraColTextInput(txtCustAddrCol2, arg0);
			}
		});
		
		txtCustPhoneCol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkColTextInput(txtCustPhoneCol, lblErrPhoneCol, arg0);
			}
		});
		
		txtCustZipCol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkColTextInput(txtCustZipCol, lblErrZipCol, arg0);
			}
		});
		
		txtCustZipCol2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkExtraColTextInput(txtCustZipCol2, arg0);
			}
		});
		
		txtCustInfCol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkColTextInput(txtCustInfCol, lblErrInflCol, arg0);
			}
		});
		//TODO this should be key listener
		txtFirstRow.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtFirstRow.getText().length() > 0 && txtFirstRow.getText().matches("-?\\d+")){
					lblErrFirstRow.setText("");
					checkReadyToRun();
				} else {
					lblErrFirstRow.setText("*");
					readyToRun = false;
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
					txtCustNameCol2.setText("");
					txtCustNameCol2.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustNameCol.setEditable(true);
					lblErrNameCol.setText("*");
					txtCustNameCol2.setEditable(true);
					readyToRun = false;
				}
			}
		});
		
		ckbxIgnrAddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrAddr.isSelected() ) { 
					lblErrAddrCol.setText("");
					txtCustAddrCol.setText("");
					txtCustAddrCol.setEditable(false);
					txtCustAddrCol2.setText("");
					txtCustAddrCol2.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustAddrCol.setEditable(true);
					lblErrAddrCol.setText("*");
					txtCustAddrCol2.setEditable(true);
					readyToRun = false;
				}
			}
		});
		
		ckbxIgnrZip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrZip.isSelected() ) { 
					lblErrZipCol.setText("");
					txtCustZipCol.setText("");
					txtCustZipCol.setEditable(false);
					txtCustZipCol2.setText("");
					txtCustZipCol2.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustZipCol.setEditable(true);
					lblErrZipCol.setText("*");
					txtCustZipCol2.setEditable(true);
					readyToRun = false;
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
					readyToRun = false;
				}
			}
		});
		
		ckbxIgnrInfl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( ckbxIgnrInfl.isSelected() ) { 
					lblErrInflCol.setText("");
					txtCustInfCol.setText("");
					txtCustInfCol.setEditable(false);
					txtCustInfCol2.setText("");
					txtCustInfCol2.setEditable(false);
					txtCustInfCol3.setText("");
					txtCustInfCol3.setEditable(false);
					txtCustInfCol4.setText("");
					txtCustInfCol4.setEditable(false);
					checkReadyToRun();
				} else {
					txtCustInfCol.setEditable(true);
					lblErrInflCol.setText("*");
					txtCustInfCol2.setEditable(true);
					txtCustInfCol3.setEditable(true);
					txtCustInfCol4.setEditable(true);
					readyToRun = false;
				}
			}
		});
	}

	//////////////////////////////*** GETTERS FOR PROGRAM INPUT ****//////////////////////////////////////////////////////////////////////

	private int getTxtCustNameCol() {
		if(txtCustNameCol.getText() == null || txtCustNameCol.getText().equals("")) {return -1;}
		String tmp = txtCustNameCol.getText();
		return getNumberCol(tmp);
	}
	
	private int getTxtCustNameCol2() {
		if(txtCustNameCol2.getText() == null || txtCustNameCol2.getText().equals("")) {return -1;}
		String tmp = txtCustNameCol2.getText();
		return getNumberCol(tmp);
	}

	private int getTxtCustAddrCol() {
		if(txtCustAddrCol.getText() == null || txtCustAddrCol.getText().equals("")) {return -1;}
		String tmp = txtCustAddrCol.getText();
		return getNumberCol(tmp);
	}

	private int getTxtCustAddrCol2() {
		if(txtCustAddrCol2.getText() == null || txtCustAddrCol2.getText().equals("")) {return -1;}
		String tmp = txtCustAddrCol2.getText();
		return getNumberCol(tmp);
	}
	
	private int getTxtCustZipCol() {
		if(txtCustZipCol.getText() == null || txtCustZipCol.getText().equals("")) {return -1;}
		String tmp = txtCustZipCol.getText();
		return getNumberCol(tmp);
	}
	
	private int getTxtCustZipCol2() {
		if(txtCustZipCol2.getText() == null || txtCustZipCol2.getText().equals("")) {return -1;}
		String tmp = txtCustZipCol2.getText();
		return getNumberCol(tmp);
	}

	private int getTxtCustPhoneCol() {
		if(txtCustPhoneCol.getText() == null || txtCustPhoneCol.getText().equals("")) {return -1;}
		String tmp = txtCustPhoneCol.getText();
		return getNumberCol(tmp);
	}

	private int getTxtCustInfCol() {
		if(txtCustInfCol.getText() == null || txtCustInfCol.getText().equals("")) {return -1;}
		String tmp = txtCustInfCol.getText().toUpperCase();
		return getNumberCol(tmp);
	}
	
	private int getTxtCustInfCol2() {
		if(txtCustInfCol2.getText() == null || txtCustInfCol2.getText().equals("")) {return -1;}
		String tmp = txtCustInfCol2.getText().toUpperCase();
		return getNumberCol(tmp);
	}
	
	private int getTxtCustInfCol3() {
		if(txtCustInfCol3.getText() == null || txtCustInfCol3.getText().equals("")) {return -1;}
		String tmp = txtCustInfCol3.getText().toUpperCase();
		return getNumberCol(tmp);
	}
	
	private int getTxtCustInfCol4() {
		if(txtCustInfCol4.getText() == null || txtCustInfCol4.getText().equals("")) {return -1;}
		String tmp = txtCustInfCol4.getText().toUpperCase();
		return getNumberCol(tmp);
	}

	private int getNumberCol(String tmp) {
		if(tmp.length()==1){
			char c1 = tmp.charAt(0);
			//A (uppercase) is 65
			return ((int) c1) - 65;
		} else {
			char c1 = tmp.charAt(0);
			char c2 = tmp.charAt(1);
			int i = 26 + ((int) c1) - 65 + ((int) c2) - 65;
			return i;
		}
		
	}

	/** Allows the program to check for what the firstrow is
	 * @return the txtFirstRow first row of data to start looking in, (start at 1, not 0)
	 */
	public int getTxtFirstRow() {
		return Integer.parseInt(txtFirstRow.getText());
	}

	/**Gets the absolute path of the output file as a string
	 * @return the txtOutputFileName for path of output file as a string
	 */
	public String getTxtOutputFileAndAbsPath() {
		return inputFile.getParent() + "\\" +txtOutputFileName.getText()+".xlsx";
	}

	/** This represents the input file selected
	 * @return the lblSelectInputFile path as a string
	 */
	public String getTxtInputFileAndAbsPath() {
		return lblSelectInputFile.getText();
	}

	/**Gets the tab name the user entered
	 * @return the txtOutputFileName as a string
	 */
	public String getTabName() {
		return txtTabName.getText();
	}

	//TODO does program access file or path name?
	/** Returns the input file
	 * @return the selected input file
	 */
	public File getSelectedInputFile() {
		return inputFile;
	}
	
	/** Gets the spinner tolerance selected by the user
	 * @return the spnrNameTol spinner tolerance for minimum name match score
	 */
	public int getSpnrNameTol() {
		try {
			spnrNameTol.commitEdit();
		} catch ( java.text.ParseException e ) { 
			e.printStackTrace(); 
		}
		return (Integer) spnrNameTol.getValue();
		
	}
	////////////////////////////////////END GETTERS////////////////////////////////////////////
	
	//////////////////////////*** UPDATE PROGRESS BARS ***/////////////////////////////////////
	/**
	 * Access to update the progress bars through this class
	 * @param pct as integer for customer loading status
	 */
	public void updateDBSLoadStatus(int pct){
		progBarFrame.setPBImportDBS(pct);
	}
	/**
	 * Access to update the progress bars through this class
	 * @param pct as integer for reading excel file status
	 */	
	public void updateReadExcelCustomersStatus(int pct){
		progBarFrame.setPBReadCusts(pct);
	}

	/**
	 * Access to update the progress bars through this class
	 * @param pct as integer for customer matching status
	 */	
	public void updateCustomerMatchingStatus(int pct){
		progBarFrame.setPBGenMatches(pct);
	}
	
	////////////////////////////////////HELPER METHODS/////////////////////////////////////////
	
	//checks for error indicators, will enable button if no indicators exist
	private void checkReadyToRun(){
		//probably should put error labels in an array
		if(lblErrNameCol.getText().equals("*") || lblErrAddrCol.getText().equals("*")|| lblErrZipCol.getText().equals("*") || 
			lblErrPhoneCol.getText().equals("*") || lblErrInflCol.getText().equals("*") || lblErrFirstRow.getText().equals("*")
			|| lblSelectInputFile.getText().equals("No File Choosen") || txtOutputFileName.getText().trim().length() < 1
			|| txtTabName.getText().trim().length() < 1){
			readyToRun = false;
		} else {
			readyToRun = true;
		}
	}
	
	//TODO check, I don't think this is used
	public boolean getMatcherStart(){
		return matcherStart;
	}
	
	//this method does the input translations for letter based columns, also sets the required field error flag if no longer a valid input
	private void checkColTextInput(JTextField tf, JLabel jl, KeyEvent ke){
		if((tf.getText() != "" && tf.getText().length() > 1) ){
			ke.consume();
		} else if (ke.getExtendedKeyCode() != KeyEvent.VK_DELETE && ke.getKeyChar() != '\b' && ke.getExtendedKeyCode() != KeyEvent.VK_SHIFT){
			if(Character.toString(ke.getKeyChar()).matches("[a-z]")){
				ke.setKeyChar(Character.toUpperCase(ke.getKeyChar()));
				jl.setText("");
				checkReadyToRun();
			} else if (Character.toString(ke.getKeyChar()).matches("[A-Z]")){
				jl.setText("");
				checkReadyToRun();
			} else {
				ke.consume();
			}
		} else if (ke.getExtendedKeyCode() == KeyEvent.VK_DELETE || ke.getKeyChar() == '\b'){
			if(tf.getText().length()<1){
				jl.setText("*");
				checkReadyToRun();
			}
		}
	}
	
	//This does the translations for the extra column input boxes, difference is it doesn't change the required field indicator
	private void checkExtraColTextInput(JTextField tf, KeyEvent ke){
		if((tf.getText() != "" && tf.getText().length() > 1) ){
			ke.consume();
		} else if (ke.getExtendedKeyCode() != KeyEvent.VK_DELETE && ke.getKeyChar() != '\b' && ke.getExtendedKeyCode() != KeyEvent.VK_SHIFT){
			if(Character.toString(ke.getKeyChar()).matches("[a-z]")){
				ke.setKeyChar(Character.toUpperCase(ke.getKeyChar()));
				checkReadyToRun();
			} else if (Character.toString(ke.getKeyChar()).matches("[A-Z]")){
				checkReadyToRun();
			} else {
				ke.consume();
			}
		} else if (ke.getExtendedKeyCode() == KeyEvent.VK_DELETE || ke.getKeyChar() == '\b'){
			if(tf.getText().length()<1){
				checkReadyToRun();
			}
		}
	}
}
