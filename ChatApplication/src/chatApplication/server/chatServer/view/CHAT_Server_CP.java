package chatApplication.server.chatServer.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chatApplication.server.chatServer.chatServer.CHAT_Server;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CHAT_Server_CP extends JFrame implements Runnable{

	private JPanel contentPane;	
	private JTextField txtmax;
	private JTextField txtIP;
	private JTextField txtPort;
	private CHAT_Server chatServer;
	private JButton btnStartChatServer;
	private JLabel lblStatus;

	/**
	 * Create the frame.
	 */
	public void setWindowComponentDisable(){
		txtIP.setEditable(false);
		txtmax.setEditable(false);
		txtPort.setEditable(false);
		btnStartChatServer.setText("Stop Chat Server");
		lblStatus.setText("Online");
	}
	public CHAT_Server_CP getThisWindow(){
		return this;
	}
	
	@Override
	public void run() {		
		this.setVisible(true);
	}
	
	public CHAT_Server_CP() {
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setResizable(false);
		setTitle("Chat Server Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("Offline");
		lblStatus.setBounds(150, 25, 46, 14);
		contentPane.add(lblStatus);
		
		btnStartChatServer = new JButton("Start Chat Server");
		btnStartChatServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ch = btnStartChatServer.getText().trim();				
					switch(ch){
						case "Start Chat Server":
							
							if(txtmax.getText().trim().equals("") || txtIP.getText().trim().equals("") || txtPort.getText().trim().equals("")){
								JOptionPane.showMessageDialog(null, "Empty Fields.");
								//break;
							}
							else {
								try {
									int max = Integer.parseInt(txtmax.getText().trim());
									int prt = Integer.parseInt(txtPort.getText().trim());
									chatServer = new CHAT_Server(max,txtIP.getText().trim(),prt,getThisWindow());
									chatServer.start();									
								} catch (NumberFormatException  e) {						
									JOptionPane.showMessageDialog(null, "Invalid Input\neg.\nMaxUser : maximum concurrent user in Server\n"
											+ "IP : 212.113.26.33\nport : 5555");
									e.printStackTrace();
								}
							}
							break;
						case "Stop Chat Server":
							chatServer.stop();
							txtIP.setEditable(true);
							txtmax.setEditable(true);
							txtPort.setEditable(true);
							btnStartChatServer.setText("Start Chat Server");
							lblStatus.setText("Offline");
							break;
						default :
							JOptionPane.showMessageDialog(null, "Some Problem Occured.\nClose the Program and run again");
							
							
							
							
					}
				
			}
		});
		btnStartChatServer.setBounds(85, 174, 154, 23);
		contentPane.add(btnStartChatServer);
		
		txtmax = new JTextField();
		txtmax.setBounds(196, 57, 125, 20);
		contentPane.add(txtmax);
		txtmax.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Max Concurrent User");
		lblNewLabel.setBounds(33, 60, 118, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setBounds(33, 95, 108, 14);
		contentPane.add(lblServerIp);
		
		txtIP = new JTextField();
		txtIP.setBounds(196, 92, 125, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblServerPort = new JLabel("Server Port");
		lblServerPort.setBounds(33, 131, 108, 14);
		contentPane.add(lblServerPort);
		
		txtPort = new JTextField();
		txtPort.setBounds(196, 128, 125, 20);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblServerStatus = new JLabel("Server Status :");
		lblServerStatus.setBounds(47, 25, 85, 14);
		contentPane.add(lblServerStatus);			
	}
}
