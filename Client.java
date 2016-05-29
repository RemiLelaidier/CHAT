package chat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Client extends JFrame {

	// Interface Graphique
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnF;
	private JMenuItem mntmConnexion;
	private JMenuItem mntmDeconnexion;
	private JMenuItem mntmFermer;
	private JSeparator separator_1;
	private JMenuItem mntmAide;
	private JMenuItem mntmAPropos;
	private JMenu mnNewMenu;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JButton btnRecp;
	
	// Communication
	private ServiceReseau serviceReseau;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		serviceReseau = new ServiceReseau();
		
		setTitle("Chat (Client)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnF = new JMenu("Actions");
		menuBar.add(mnF);
		
		mntmConnexion = new JMenuItem("Connexion");
		mntmConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ip = JOptionPane.showInputDialog(null,
						"Entrez l'adresse IP :\n",
						"Connexion",
						JOptionPane.OK_CANCEL_OPTION
						);
				try{
					String retour = serviceReseau.connection_Client(ip);
					textArea.append(retour + "\n");
					mntmConnexion.setEnabled(false);
					mntmDeconnexion.setEnabled(true);
					btnNewButton.setEnabled(true);
					btnRecp.setEnabled(true);
				}catch(UnknownHostException e){
					textArea.append(e.getMessage());
				}catch(IOException e){
					textArea.append(e.getMessage());
				}
			}
		});
		mnF.add(mntmConnexion);
		
		mntmDeconnexion = new JMenuItem("Deconnexion");
		mntmDeconnexion.setEnabled(false);
		mntmDeconnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String retour = serviceReseau.deco_Client();
					textArea.append(retour+"\n");
					mntmConnexion.setEnabled(true);
					mntmDeconnexion.setEnabled(false);
					btnNewButton.setEnabled(false);
					btnRecp.setEnabled(false);
				}catch(IOException arg0){
					textArea.append(arg0.getMessage());
				}
					
			}
		});
		mnF.add(mntmDeconnexion);
		
		separator_1 = new JSeparator();
		mnF.add(separator_1);
		
		mntmFermer = new JMenuItem("Fermer");
		mntmFermer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					serviceReseau.deco_Client();
					System.exit(0);
				}catch(IOException arg1){
					textArea.append(arg1.getMessage() + "\n");
				}
			}
		});
		mnF.add(mntmFermer);
		
		mnNewMenu = new JMenu("?");
		menuBar.add(mnNewMenu);
		
		mntmAide = new JMenuItem("Aide");
		mnNewMenu.add(mntmAide);
		
		mntmAPropos = new JMenuItem("A propos");
		mntmAPropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Auteur : Remi Lelaidier\n"
						+ "Version : Client V 0.1\n"
						+ "Contact : r.lelaidier@hotmail.fr"
						,"A propos", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnNewMenu.add(mntmAPropos);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(12, 12, 424, 161);
		
		textField = new JTextField();
		textField.setBounds(12, 182, 321, 58);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Envoi");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(textField.getText().length() != 0){
						serviceReseau.envoi(textField.getText());
				        textArea.append(textField.getText() + "\n");
				        textField.setText("");
					}
				}catch(IOException e){
					textArea.append(e.getMessage() + "\n");
				}
			}
		});
		btnNewButton.setBounds(345, 182, 91, 25);
		contentPane.add(btnNewButton);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(12, 12, 424, 161);
		contentPane.add(scrollPane);
		
		btnRecp = new JButton("Recept");
		btnRecp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String reception = serviceReseau.reception();
					if (reception != null){
						textArea.append("Professeur : " + reception + "\n");
					}
				}catch(IOException e){
					textArea.append(e.getMessage() + "\n");
				}
			}
		});
		btnRecp.setEnabled(false);
		btnRecp.setBounds(345, 215, 91, 25);
		contentPane.add(btnRecp);
	}	
}
