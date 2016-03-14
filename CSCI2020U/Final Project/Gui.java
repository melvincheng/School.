import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Gui{
	public static void main(String[] args) throws Exception{
		Boolean status = false;
		String fromServer = new String("fromServer");
		String toServer = new String("toServer");
		Client c = new Client(fromServer,toServer);
		ConnectServerWin serverWin = new ConnectServerWin(c);
		MainMenuWin mainMenu = new MainMenuWin(fromServer,toServer);
		serverWin.connectServer();
		System.out.print("");
		while(!status){
			System.out.print("");
			if(c.status){
				mainMenu.mainMenu();
				status=true;
				try{
					c.connect();
				}catch(Exception e){
					System.exit(-1);
					break;
				}	
			}
		}
	}
}
