package com.CarolinaCAT.busIntel.view;

import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

public class ProgressListener {
	private JProgressBar bar;

	void InputProgressListener() {} 

	public void InputProgressListener(JProgressBar b) {
		this.bar = b;
		bar.setValue(0);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		//Determine whether the property is progress type
		if ("progress".equals(evt.getPropertyName())) { 
			bar.setValue((int) evt.getNewValue());
		}
	}
}
