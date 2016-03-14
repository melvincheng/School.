import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class textInputWin{
	JFrame mainFrame;
	JFrame superFrame;
	String toServer;
	String fromServer;


	public textInputWin(String fromServer, String toServer){
		this.toServer = toServer;		
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
		JButton sendButton = new JButton("Send");
		JLabel input = new JLabel("Input");
		JLabel output = new JLabel("");
		JLabel outputLabel = new JLabel("Output");
		JTextField textField = new JTextField(10);

		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				output.setText("");
				toServer = textField.getText();
				try{
					Thread.sleep(1000);
				}catch(Exception err){
					System.exit(-1);
				}
				output.setText(fromServer);
			}
		});
		
		mainFrame.add(input);
		mainFrame.add(textField);
		mainFrame.add(sendButton);
		mainFrame.add(outputLabel);
		mainFrame.add(output);
		



		mainFrame.setVisible(true);
	}
}