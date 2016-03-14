import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class ConnectServerWin{
	JFrame mainFrame;
	Client c;
	public ConnectServerWin(Client c){
		init();
		this.c = c;
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	public void init(){
		mainFrame = new JFrame("Connect to server");
		mainFrame.setSize(500,200);
		mainFrame.setLayout(new FlowLayout());
	}

	public void connectServer(){
		JButton connect = new JButton("Connect");
		JLabel server = new JLabel("Server name:");
		JTextField serverName = new JTextField(6);
		JLabel portNum = new JLabel("Port number:");
		JTextField port = new JTextField(6);
		JLabel status = new JLabel("");
		JPanel panel = new JPanel();
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					c.clientSetup(serverName.getText(), 
						Integer.parseInt(port.getText()));
					mainFrame.setVisible(false);
				}catch(Exception err){
					status.setText("Unable to connect to server");
				}
			}
		});
		mainFrame.add(server);
		mainFrame.add(serverName);
		mainFrame.add(portNum);
		mainFrame.add(port);
		mainFrame.add(connect);
		mainFrame.add(status);
		mainFrame.setVisible(true);
	}
}