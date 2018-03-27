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

public class MatcherStart extends JFrame {

	private JPanel contentPane;
	private JTextField txtColLetter;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

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
	setBounds(100, 100, 450, 408);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	
	JLabel lblCustomerNameColumn = new JLabel("Customer Name Column:");
	
	JLabel lblCustomerAddressColumn = new JLabel("Customer Address Column:");
	
	JLabel lblCustomerZipColumn = new JLabel("Customer Zip Column:");
	
	JLabel lblCustomerPhoneColumn = new JLabel("Customer Phone Column:");
	
	JLabel lblCustomerInfluencerColumn = new JLabel("Customer Influencer Column:");
	
	JLabel lblFirstRowOf = new JLabel("First Row of Customers:");
	
	txtColLetter = new JTextField();
	txtColLetter.setText("Col Letter");
	txtColLetter.setColumns(10);
	
	textField = new JTextField();
	textField.setText("Col Letter");
	textField.setColumns(10);
	
	textField_1 = new JTextField();
	textField_1.setText("Col Letter");
	textField_1.setColumns(10);
	
	textField_2 = new JTextField();
	textField_2.setText("Col Letter");
	textField_2.setColumns(10);
	
	textField_3 = new JTextField();
	textField_3.setText("Col Letter");
	textField_3.setColumns(10);
	
	textField_4 = new JTextField();
	textField_4.setText("Col Letter");
	textField_4.setColumns(10);
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustomerNameColumn)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtColLetter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustomerAddressColumn)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustomerZipColumn)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustomerPhoneColumn)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblCustomerInfluencerColumn)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblFirstRowOf)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(114, Short.MAX_VALUE))
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(50)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustomerNameColumn)
					.addComponent(txtColLetter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustomerAddressColumn)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustomerZipColumn)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustomerPhoneColumn)
					.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblCustomerInfluencerColumn)
					.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblFirstRowOf)
					.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(92, Short.MAX_VALUE))
	);
	contentPane.setLayout(gl_contentPane);
}
	
	
////////////////////////////////////////////
///This method contains all of the code for creating events
////////////////////////////////////////////
	private void createEvents() {
		// TODO Auto-generated method stub
		
	}
}
