import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Client{
	Socket commsocket;
	InputStream input;
	OutputStream output;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	String login;

	static public void main(String[] args) throws Exception{
		Client c = new Client(args[0],Integer.parseInt(args[1]),args[2]);
		c.connect();
	}

	public Client(String hostname, int socket, String name) throws Exception{
		try{
			commsocket = new Socket(hostname,socket);
		}catch(Exception e){
			System.err.println("Unable to connect to host");
			System.exit(-1);
		}
		input = commsocket.getInputStream();
		output = commsocket.getOutputStream();
		dataIn = new DataInputStream(input);
		dataOut = new DataOutputStream(output);
		login = name;
	}

	public void connect() throws Exception{
		Thread interact = new Interact(this.dataOut,this.dataIn,login);
		interact.start();
		interact.join();
	}

	static class Interact extends Thread{
		DataOutputStream dataOut;
		DataInputStream dataIn;
		String login;
		String match = "(?<command>[A-Za-z]+)[\\s]?+(?<other>(.+))?[\\s]?+(?<message>(.+))?";

		public Interact(DataOutputStream dataOut, DataInputStream dataIn, String login){
			this.dataOut = dataOut;
			this.dataIn = dataIn;
			this.login = login;
		}
		public void run(){
			Scanner scanner = new Scanner(System.in);
			Pattern pattern = Pattern.compile(match);
			Matcher matcher;
			String line, in, serverMessage;
			int numMess;
			try{
				dataOut.writeUTF(login);
				System.out.println(dataIn.readUTF());
				if(!dataIn.readBoolean()){
					System.exit(0);
				}
			}catch(Exception e){
				System.err.println("Login error");
				System.exit(-1);
			}
			System.out.println("Ready");
			while(true){
				try{
					line = scanner.nextLine();
					matcher = pattern.matcher(line);
					if(matcher.find()){
						if(matcher.group("command").compareToIgnoreCase("fetch")==0){
							dataOut.writeUTF("fetch");
							numMess = dataIn.readInt();
							if(numMess == 0){
								System.out.println(dataIn.readUTF());
							}
							for(int x=0;x<numMess;x++){
								System.out.println(dataIn.readUTF());
							}
						}else if(matcher.group("command").compareToIgnoreCase("login")==0){
							dataOut.writeUTF(line);
							System.out.println(dataIn.readUTF());
						}
						else if(matcher.group("command").compareToIgnoreCase("send")==0){
							dataOut.writeUTF(line);
							System.out.println(dataIn.readUTF());
						}else{
							System.out.println("Invalid command");
						}
					}else{
						System.out.println("Invalid command");
					}
				}catch(NoSuchElementException e){
					System.out.println("Exiting program");
					break;
				}catch(IOException e){
					System.out.println("An error has occur with the connection to the server\nExiting program");
					break;
				}
			}
		}
	}
}
