package chatApplication.server.httpServer.view;

import java.awt.Color;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import chatApplication.server.httpServer.httpServer.HTTP_Server;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HTTP_Server_CP extends JFrame {

	private JPanel contentPane;	
	private Thread httpServerThread;
	private HTTP_Server server;
	private PopupMenu startStop;
	
	private JFrame getCPObject(){
		return this;
	}
	
	public HTTP_Server_CP() {
				
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		startStop = new PopupMenu();
		MenuItem start = new MenuItem("Start");
		MenuItem stop = new MenuItem("Stop");
		startStop.add(start);
		startStop.add(stop);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		   }catch(Exception e){
			e.printStackTrace();
		    }
				
		
		setResizable(false);
		setTitle("HTTP Server Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 269, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStartHttpServer = new JButton("Start HTTP Server");
		btnStartHttpServer.add(startStop);
		btnStartHttpServer.setOpaque(false);
		btnStartHttpServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				switch(btnStartHttpServer.getText()){
					
				case "Start HTTP Server":
								// pass JFrame(this) object HTTP_Server(getCPObject())- When a Server is already running
					server = new HTTP_Server(getCPObject()); //a message will be shown and Window will be closed.
					httpServerThread = new Thread(server);
					System.out.println("HTTP_Server Thread Started.");
					httpServerThread.start();
					btnStartHttpServer.setText("Stop HTTP Server");
					btnStartHttpServer.setBackground(Color.RED);					
					break;
					
				case "Stop HTTP Server":
					System.out.println("Requesting to Close HTTP_Server socket");
					server.stopServer();  // call to Close the Socket.								
					httpServerThread.stop(); //then close the server 
					System.out.println("HTTP_Server Thread Stopped.");
					btnStartHttpServer.setText("Start HTTP Server");
					btnStartHttpServer.setBackground(Color.GREEN);					
				
				}
				
			}
		});
		btnStartHttpServer.setBounds(41, 56, 156, 23);
		contentPane.add(btnStartHttpServer);
		
		System.out.println("HTTP_Server Control Panel is now visible");
	}
}
