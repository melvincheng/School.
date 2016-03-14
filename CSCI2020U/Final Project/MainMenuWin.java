import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class MainMenuWin{
	JFrame mainFrame;
	String fromServer;
	String toServer;
	public MainMenuWin( String fromServer, String toServer){
		init();
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	public void init(){
		mainFrame = new JFrame("Choose");
		mainFrame.setSize(300,300);
		mainFrame.setLayout(new GridLayout(2,0));
	}

	public void mainMenu(){
		JFileChooser fileChooser = new JFileChooser();
		JButton manualButton = new JButton("Manual input");
		JButton fileButton = new JButton("Open file");


		manualButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				(new userInputWin(mainFrame,fromServer,toServer)).display();
				(new textInputWin(fromServer,toServer)).display();
			}
		});
		fileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int returnVal = fileChooser.showOpenDialog(mainFrame);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
				}
			}
		});

		mainFrame.add(manualButton);
		mainFrame.add(fileButton);
		mainFrame.setVisible(true);

	}
}