package chatApplication.client.chatClient.utils;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class ButtonThread extends Thread{
	
	private JTextArea text;
	private JButton button;
	
	public ButtonThread(JTextArea txt, JButton btn){
		text = txt;
		button = btn;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				if(text.getText().trim().equals("") || text.getText().trim().length()>=300)
					button.setEnabled(false);
				else 
					button.setEnabled(true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
