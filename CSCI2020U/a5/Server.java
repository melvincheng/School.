import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Server{
	ServerSocket serverSocket;
	public Server(int port) throws Exception{
		serverSocket = new ServerSocket(port);
	}
	static public void main(String[] args) throws Exception{
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		server.serverRun();
	}

	public void serverRun() throws Exception{
		System.out.println("Server is running");
		while(true){
			Socket socket = serverSocket.accept();
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			DataInputStream dataIn = new DataInputStream(input);
			DataOutputStream dataOut = new DataOutputStream(output);
			(new Recieve(dataIn,dataOut)).start();

		}
	}

	static class Recieve extends Thread{
		DataInputStream dataIn;
		DataOutputStream dataOut;
		static Map<String,ArrayList<String>> storage = new HashMap<String,ArrayList<String>>();
		String match = "(?<command>[A-Za-z]+)[\\s]*(?<other>([\\S]+))?[\\s]*(?<message>(.+))?";
		String login;
		Boolean status = true;
		public Recieve(DataInputStream dataIn, DataOutputStream dataOut){
			this.dataOut = dataOut;
			this.dataIn = dataIn;
			try{
				login = this.dataIn.readUTF();
				synchronized(this.storage){
					if(storage.containsKey(login)){
						dataOut.writeUTF("Username already exists.\nExiting program");
						status=false;
						dataOut.writeBoolean(status);
						return;
					}
					dataOut.writeUTF("You are logged in as " + login);
					storage.put(login,(new ArrayList<String>()));
					dataOut.writeBoolean(status);
				}
				System.out.println(login+" has connected");
			}catch(Exception e){
				System.out.println("User has login error");
			}
		}
		public void run(){
			String in,message,tempUsername;
			ArrayList<String> tempMess;
			Scanner scanner = new Scanner(System.in);
			Pattern pattern = Pattern.compile(match);
			Matcher matcher;
			while(status){
				try{
					in = dataIn.readUTF();
					matcher = pattern.matcher(in);
					matcher.find();
					if(matcher.group("command").compareToIgnoreCase("login")==0){
						synchronized(this.storage){
							tempUsername = matcher.group("other");
							if(tempUsername==null){
								dataOut.writeUTF("Invalid username");
							}
							else if(!storage.containsKey(tempUsername)){
								storage.put(tempUsername,(new ArrayList<String>()));
								storage.remove(login);
								System.out.println(login + " has changed username to " + tempUsername);
								login = tempUsername;
								dataOut.writeUTF("Your username has been changed to " + login);
							}else{
								dataOut.writeUTF("User already exists");
							}
						}
					}else if(matcher.group("command").compareToIgnoreCase("send")==0){
						synchronized(this.storage){
							tempUsername = matcher.group("other");
							if(storage.containsKey(tempUsername)){
								tempMess = storage.get(tempUsername);
								message = matcher.group("message");
								if(message==null){
									message = " ";
								}
								tempMess.add(login + ": " + message);
								storage.put(tempUsername,tempMess);
								dataOut.writeUTF("Message sent");
							}else{
								dataOut.writeUTF("User not found");
							}
						}
					}else if(matcher.group("command").compareToIgnoreCase("fetch")==0){
						synchronized(this.storage){
							tempMess = storage.get(login);
							dataOut.writeInt(tempMess.size());
							if(tempMess.isEmpty()==true){
								dataOut.writeUTF("No new messages");
							}else{
								for(String temp:tempMess){
									dataOut.writeUTF(temp);
								}
								storage.put(login,(new ArrayList<String>()));
							}
						}
					}
				}catch(Exception e){
					System.out.println(login + " has disconnected");
					storage.remove(login);
					break;
				}
			}
		}
	}
}