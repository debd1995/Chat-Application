package chatApplication.client.chatClient.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ReadMessageFromServer extends Thread{

	private InputStream in;
	private JTextArea textArea;
	
	public ReadMessageFromServer(InputStream i,JTextArea text){
		in = i;
		textArea = text;
	}
	
	@Override
	public void run() {
		DataInputStream input = new DataInputStream(in);
		while(true){
			String msg;
			try {
				msg = input.readUTF();
				textArea.append(msg+"\n");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Failed to read message from server.Try to connect Again.");
				e.printStackTrace();
				break;
			}			
		}
	}
}
