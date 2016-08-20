package chatApplication.client.chatClient.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JOptionPane;

public class SendMessageToServer extends Thread{
	
	private OutputStream out;
	private String msg;
	
	public SendMessageToServer(OutputStream ot, String ms){
		out = ot;
		msg = ms;
	}
	 @Override
	public void run() {
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.writeUTF(msg);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to Send Message.\n\tBODY\n"+msg);
			e.printStackTrace();
		}
	}
	
}
