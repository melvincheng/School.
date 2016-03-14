import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class userInputWin{
	JFrame mainFrame;
	JFrame superFrame;
	String fromServer;
	String toServer;


	public userInputWin(JFrame frame, String fromServer, String toServer){
		superFrame = frame;
		superFrame.setVisible(false);
		this.fromServer = fromServer;
		this.toServer = toServer;		
		init();
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	public void init(){
		mainFrame = new JFrame("Dialpad");
		mainFrame.setSize(300,300);
		mainFrame.setLayout(new GridLayout(4,3,1,1));
	}

	public void display(){
		JButton button;
		JButton sendButton = new JButton("Send");
		JButton backButton = new JButton("back");
		JLabel input = new JLabel("");
		JLabel output = new JLabel("");
		JLabel blank = new JLabel("");
		ArrayList<String> inputString = new ArrayList<String>();

		
		button = new JButton("1");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("1");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		button = new JButton("2");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("2");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		button = new JButton("3");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("3");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		mainFrame.add(input);

		button = new JButton("4");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("4");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		button = new JButton("5");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("5");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		button = new JButton("6");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("6");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		mainFrame.add(output);

		button = new JButton("7");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("7");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		button = new JButton("8");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("8");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		button = new JButton("9");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("9");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		mainFrame.add(blank);

		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				superFrame.setVisible(true);
				mainFrame.dispose();
			}
		});
		mainFrame.add(backButton);

		button = new JButton("0");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputString.add("0");
				input.setText(output(inputString));
			}
		});
		mainFrame.add(button);

		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				output.setText("");
				toServer = output(inputString);
				input.setText("");
				inputString.clear();
				output.setText(fromServer);
			}
		});

		mainFrame.add(sendButton);



		mainFrame.setVisible(true);
	}
	public String output(ArrayList<String> list){
		String finalNum="";
		for(String num:list){
			finalNum = finalNum+num;
		}
		return finalNum;
	}
}