package com.CarolinaCAT.busIntel.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.CarolinaCAT.busIntel.matching.CustomerMatcher;
import com.CarolinaCAT.busIntel.view.MatcherStart;


/**
 * Launch the application.
 */
public class Main {
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
					JFrame startFrame = new MatcherStart();
					startFrame.setVisible(true);
					startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					CustomerMatcher matcherProg = null;
					try {
						matcherProg = new CustomerMatcher(((MatcherStart) startFrame).getTxtInputFileAndAbsPath(),((MatcherStart) startFrame).getTxtOutputFileAndAbsPath(), (MatcherStart) startFrame);
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

}
