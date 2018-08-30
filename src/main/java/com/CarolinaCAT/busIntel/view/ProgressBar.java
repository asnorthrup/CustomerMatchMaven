package com.CarolinaCAT.busIntel.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class ProgressBar extends JFrame {

	/**
	 * Auto-gen serial version
	 */
	private static final long serialVersionUID = 6850691244875716569L;
	private JPanel contentPane;
	private JProgressBar pbImportDBS;
	private JProgressBar pbReadWBofUnknownCusts;
	private JProgressBar pbGenMatches;
	private JButton btnCancel;
	
	/**
	 * Create the frame for the progress bar inside of the EDT thread.
	 */
	public ProgressBar() {
		initComponents();
		createEvents();
	}
	
	//create events, which only event should be clicking cancel button
	private void createEvents() {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO how to cancel, probably need to look at having customer matcher called in a thread
			}
		});
	}

	//initialize components of progress bar
	private void initComponents() {
		
		//added
		//JFrame frame = new JFrame("Matcher Progress");
		//end of added
		//also added frame. to below
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		

		
		//JPanel panel = new JPanel();
		contentPane.setOpaque(true);

		//create progress bars		
		pbReadWBofUnknownCusts = new JProgressBar();
		pbGenMatches = new JProgressBar();
		pbImportDBS = new JProgressBar();
		JLabel lblReadCustFileStatus = new JLabel("Reading Customer File...");
		JLabel lblImportDBSStatus = new JLabel("Importing DBS Customers...");
		JLabel lblGenMatchStatus = new JLabel("Generating Matches...");
		
		btnCancel = new JButton("Cancel");

		//setup layout for progress bar panel
		GroupLayout gl_panel = new GroupLayout(contentPane);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(173)
					.addComponent(btnCancel)
					.addContainerGap(178, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(150, Short.MAX_VALUE)
					.addComponent(lblReadCustFileStatus)
					.addGap(131))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(152, Short.MAX_VALUE)
					.addComponent(lblImportDBSStatus)
					.addGap(123))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(94)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(pbImportDBS, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
						.addComponent(pbReadWBofUnknownCusts, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
						.addComponent(pbGenMatches, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
					.addGap(81))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(239, Short.MAX_VALUE)
					.addComponent(lblGenMatchStatus)
					.addGap(139))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addComponent(lblImportDBSStatus)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pbImportDBS, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblReadCustFileStatus)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pbReadWBofUnknownCusts, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblGenMatchStatus)
					.addGap(5)
					.addComponent(pbGenMatches, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addContainerGap())
		);

		//jpanel isn't showing up during second run
		contentPane.setLayout(gl_panel);
		contentPane.validate();
		//frame.add(contentPane);//added
		//frame.setVisible(true); //added frame.
		setVisible(true);
	}
	
	///////////////////*** SETTERS FOR UPDATING STATUS BARS ***////////////////////
	/**
	 * Allows program to update the progress bar for importing customers
	 * @param pct as integer for percent complete
	 */
	public void setPBImportDBS(int pct){
		pbImportDBS.setValue(pct);
		pbImportDBS.update(pbImportDBS.getGraphics());
	}
	/**
	 * Allows program to update the progress bar for reading in of customers in selected Excel file
	 * @param pct as integer for percent complete
	 */
	public void setPBReadWBofUnknownCusts(int pct){
		pbReadWBofUnknownCusts.setValue(pct);
		pbReadWBofUnknownCusts.update(pbReadWBofUnknownCusts.getGraphics());
	}
	/**
	 * Allows program to update the progress bar for creating matches of customer in CRM to customers in selected Excel file
	 * @param pct as integer for percent complete
	 */	
	public void setPBGenMatches(int pct){
		pbGenMatches.setValue(pct);
		pbGenMatches.update(pbGenMatches.getGraphics());
	}
}
