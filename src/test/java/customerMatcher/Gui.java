package customerMatcher;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame{
	private final JFileChooser openFileChooser = new JFileChooser();
	private File accessDBFile;
	JButton btnFileselector;
	private String absPath;
	
	/**
	 * @return the absPath
	 */
	public String getAbsPath() {
		return absPath;
	}

	public Gui() {
		initComponents();
		createEvents();
		

	    	openFileChooser.setFileFilter(new FileNameExtensionFilter("ACCDB files","accdb"));
			int returnValue = openFileChooser.showOpenDialog(rootPane);
			if (returnValue == JFileChooser.APPROVE_OPTION){
				//accessDBfile is equiv to inputFile in prior action listener, changed to accessDBFile
				accessDBFile = openFileChooser.getSelectedFile();
				//System.out.println(inputFile.getCanonicalPath());
				//System.out.println(lblSelectInputFile.getText());
				int start = accessDBFile.getAbsolutePath().lastIndexOf("\\") + 1;
				System.out.println(start);
				int end = accessDBFile.getAbsolutePath().lastIndexOf(".");
				System.out.println(end);
			}
	}

	private void createEvents() {
		// TODO Auto-generated method stub
		btnFileselector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileChooser.setFileFilter(new FileNameExtensionFilter("XLSX files","xlsx"));
				int returnValue = openFileChooser.showOpenDialog(rootPane);
				if (returnValue == JFileChooser.APPROVE_OPTION){
					accessDBFile = openFileChooser.getSelectedFile();
					//System.out.println(inputFile.getCanonicalPath());
					absPath = accessDBFile.getAbsolutePath();
					//System.out.println(lblSelectInputFile.getText());
//					int start = inputFile.getAbsolutePath().lastIndexOf("\\") + 1;
//					System.out.println(start);
//					int end = inputFile.getAbsolutePath().lastIndexOf(".");
//					System.out.println(end);
//					String outfile = openFileChooser.getSelectedFile().getAbsolutePath().substring(start,end) + "_match";
//					txtOutputFileName.setText(outfile);
//					checkReadyToRun();
//				} else {
//					lblSelectInputFile.setText("No File Choosen");
//					//TODO see if it is possible to run matcher with no file choosen
				}
			}
		});

	}

	private void initComponents() {
		// TODO Auto-generated method stub
	       setTitle("Simple example");
	       setSize(300, 200);
	       setLocationRelativeTo(null);
	       setDefaultCloseOperation(EXIT_ON_CLOSE);
	       
			JButton btnFileselector = new JButton("FileSelector");
			getContentPane().add(btnFileselector, BorderLayout.CENTER);
	}
}