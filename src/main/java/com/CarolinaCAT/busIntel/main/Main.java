package com.CarolinaCAT.busIntel.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.CarolinaCAT.busIntel.matching.CustomerMatcher;
import com.CarolinaCAT.busIntel.matching.Translators;
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
					System.out.println("step 1");
					JFrame startFrame = new MatcherStart();
					System.out.println("step 2");
					startFrame.setVisible(true);
					System.out.println("step 3");
					startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					System.out.println("step 4");
					CustomerMatcher matcherProg = null;
					

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

		
	}

}
