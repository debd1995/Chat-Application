//this Thread will handle each client

package chatApplication.server.chatServer.chatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import chatApplication.server.chatServer.utils.MessageForward;

public class CHAT_Server_Child extends Thread{

	private Socket client;
	private String uname;
	private int maxUsers,thisID;
	private CHAT_Server_Child clientThreads[];
	private static final String codeConnectionSuccessfull = "2k1609";
	private static final String seperator = "||";
	private static final String codeMessageBody = "Connection Successfull.";
	
	public CHAT_Server_Child(Socket client, CHAT_Server_Child clients[],int id) {
		this.client = client;
		clientThreads = clients;
		maxUsers = clientThreads.length;
		thisID = id;		
	}
	
	public Socket getClient(){
		return client;
	}
	
	@Override
	protected void finalize() throws Throwable {
		client.close();
		super.finalize();
	}
	
	@Override
	public void run() {
		
		DataInputStream in = null;
		DataOutputStream out = null;		
		
		try {
			out = new DataOutputStream(client.getOutputStream());
			in = new DataInputStream(client.getInputStream());
			out.writeUTF(codeConnectionSuccessfull+seperator+codeMessageBody); // send header
			uname = in.readUTF();
			System.out.println("new User Connected "+uname);
			out.writeUTF("Start Chatting...");
			new MessageForward(clientThreads,"New User : "+uname+" is connected",thisID).start(); //let others know 
			
			//chatting
			while(client.isConnected()){
				String msg = in.readUTF();
				new MessageForward(clientThreads, uname+"-->"+msg, thisID).start();
			}
			
			
			
			
		} catch(SocketException e){
			JOptionPane.showMessageDialog(null, "Connection Closed Externally\nUser : "+uname);
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection Failed with "+client.getRemoteSocketAddress()+" IO ERROR "+e);
			e.printStackTrace();
		}finally {
			clientThreads[thisID] = null; //clear the current Thread from thread pool
		} /*finally {
			try {
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
				if(client!=null)
					client.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			clientThreads[thisID] = null; //clear the current Thread from thread pool
		}*/
	}

}
