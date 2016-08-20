package chatApplication.client.chatClient.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chatApplication.client.chatClient.chatClient.ConnectToServer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Connect extends JFrame {

	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPort;

	private JFrame getThisWindow(){
		return this;
	}
	
	/**
	 * Create the frame.
	 */
	public Connect() {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		setResizable(false);
		setTitle("Connect to Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setBounds(23, 48, 70, 14);
		contentPane.add(lblServerIp);
		
		txtIP = new JTextField();
		txtIP.setBounds(112, 45, 157, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblPort = new JLabel("Server Port");
		lblPort.setBounds(23, 92, 70, 14);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setBounds(112, 89, 157, 20);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtIP.getText().trim().equals("") || txtPort.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "Empty Fields.");
				}
				else{
					try {
						new ConnectToServer(txtIP.getText().trim(), Integer.parseInt(txtPort.getText().trim()), getThisWindow()).start();
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Input an valid port number.");
						e.printStackTrace();
					}
				}
			}
		});
		btnConnect.setBounds(112, 145, 89, 23);
		contentPane.add(btnConnect);
	}
}
