import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class OutputWin{
	JFrame mainFrame;
	JFrame superFrame;
	String fromServer;


	public OutputWin(JFrame frame, String fromServer){
		superFrame = frame;
		superFrame.setVisible(false);
		this.fromServer = fromServer;		
		init();
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	public void init(){
		mainFrame = new JFrame("Textbox");
		mainFrame.setSize(300,300);
		mainFrame.setLayout(new FlowLayout());
	}

	public void display(){
		JLabel outputLabel = new JLabel("Output");

		JLabel output = new JLabel("");
		while(true){
			output.setText(fromServer);
		}
		
		mainFrame.add(outputLabel);
		mainFrame.add(output);

		mainFrame.setVisible(true);
	}
}