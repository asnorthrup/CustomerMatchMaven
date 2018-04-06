package com.CarolinaCAT.busIntel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

public class ProgressBar extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ProgressBar() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JProgressBar pbReadCusts = new JProgressBar();
		
		JProgressBar pbGenMatches = new JProgressBar();
		
		JButton btnCancel = new JButton("Cancel");
		
		JLabel lblReadCustFileStatus = new JLabel("Reading Customer File...");
		
		JProgressBar pbImportDBS = new JProgressBar();
		
		JLabel lblImportDBSStatus = new JLabel("Importing DBS Customers...");
		
		JLabel lblGenMatchStatus = new JLabel("Generating Matches...");
		GroupLayout gl_panel = new GroupLayout(panel);
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
						.addComponent(pbReadCusts, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
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
					.addComponent(pbReadCusts, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblGenMatchStatus)
					.addGap(5)
					.addComponent(pbGenMatches, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}

}
