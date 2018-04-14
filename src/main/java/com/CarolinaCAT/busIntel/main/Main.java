package com.CarolinaCAT.busIntel.main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.CarolinaCAT.busIntel.matching.CustomerMatcher;
import com.CarolinaCAT.busIntel.view.MatcherStart;


/**
 * Launch the application.
 */
public class Main {
	public static void main(String args[]){
		//create the GUI frame
		final MatcherStart startFrame;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startFrame = new MatcherStart();
					startFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		CustomerMatcher matcherProg;
		try {
			matcherProg = new CustomerMatcher(startFrame.getTxtInputFileAndAbsPath(),startFrame.getTxtOutputFileAndAbsPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
