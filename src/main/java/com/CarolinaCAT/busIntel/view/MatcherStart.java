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
import java.io.File;
import java.awt.Color;
import java.awt.Font;

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
	private File inputFile;
	private JLabel lblErrFirstRow;
	private JLabel lblErrInflCol;
	private JLabel lblErrPhoneCol;
	private JLabel lblErrZipCol;
	private JLabel lblErrAddrCol;
	private JLabel lblErrNameCol;
	
	
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
	setBounds(100, 100, 705, 447);
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

	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
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
								.addComponent(txtOutputFileName, GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnSelectInputFile)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblSelectInputFile, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE))
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
								.addGap(257))))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblModifyCustomerName)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(spnrNameTol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(241)
						.addComponent(btnRunMatcher)))
				.addContainerGap())
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
							.addComponent(txtCustNameCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrNameCol))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustAddrCol)
							.addComponent(txtCustAddrCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrAddrCol))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustZipCol)
							.addComponent(txtCustZipCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrZipCol))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustPhoneCol)
							.addComponent(txtCustPhoneCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrPhoneCol))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustInfCol)
							.addComponent(txtCustInfCol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblErrInflCol))
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
				//TODO check that user has put all the necessary variables before start running
				//check error flag for user input
				
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
					
				} else {
					lblSelectInputFile.setText("No File Choosen");
				}
			}
		});
		
		//TODO need multiple of these for each input box
		txtCustNameCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustNameCol.getText())){
					lblErrNameCol.setText("");
					//TODO check if clickable
				} else {
					lblErrNameCol.setText("*");
				}
			}
		});
		
		txtCustAddrCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustAddrCol.getText())){
					lblErrAddrCol.setText("");
					//TODO check if clickable
				} else {
					lblErrAddrCol.setText("*");
					
				}
			}
		});
		
		txtCustPhoneCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustPhoneCol.getText())){
					lblErrPhoneCol.setText("");
					//TODO check if clickable
				} else {
					lblErrPhoneCol.setText("*");
				}
			}
		});
		
		txtCustZipCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustZipCol.getText())){
					lblErrZipCol.setText("");
					//TODO check if clickable
				} else {
					lblErrZipCol.setText("*");
				}
			}
		});
		
		
		txtCustInfCol.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (checkCol(txtCustInfCol.getText())){
					lblErrInflCol.setText("");
					//TODO check if clickable
				} else {
					lblErrInflCol.setText("*");
				}
			}
		});
		
		
		txtFirstRow.addFocusListener(new FocusAdapter() {
			@Override
			//TODO this isn't correct need to check agaist number not checkCol method
			public void focusLost(FocusEvent arg0) {
				if (txtFirstRow.getText().length() > 0 && txtFirstRow.getText().matches("-?\\d+")){
					lblErrFirstRow.setText("");
					//TODO check if clickable
				} else {
					lblErrFirstRow.setText("*");
				}
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
	
	//helper to check columns are appropriate format as entered
	private boolean checkCol(String col){
		if(col.trim().length()>0 && col.trim().length() < 3 && col.trim().matches("[a-zA-Z]+")){
			return true;
		} else {
			return false;
		}
	}
	private void checkReadyToRun(){
		//TODO set up way to make button clickable
	}

}
