package chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class Serveur extends JFrame {

	// Interface Graphique
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnRer;
	private JMenuItem mntmDemmarerServeur;
	private JMenuItem mntmArreterServeur;
	private JMenu mnActions;
	private JMenuItem mntmEnregistrer;
	private JMenuItem mntmFermer;
	private JMenu menu;
	private JMenuItem mntmAide;
	private JMenuItem mntmAPropos;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JButton btnReception;
	private JMenuItem mntmRechercheClient;
	private JScrollPane scrollPane;
	private JSeparator separator;
	
	// Communication
	private ServiceReseau serviceReseau;
	
	// Fichiers
	private ServiceFichier serviceFichier;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Serveur frame = new Serveur();
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
	public Serveur() {
		serviceReseau = new ServiceReseau();
		serviceFichier = new ServiceFichier();
		
		setTitle("Chat (Serveur)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnRer = new JMenu("Reseau");
		menuBar.add(mnRer);
		
		mntmDemmarerServeur = new JMenuItem("Demarrer Serveur");
		mntmDemmarerServeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String addrLocal = serviceReseau.fournitIP();
					textArea.append("L'adresse du serveur est : " + addrLocal + "\n");
					String text= serviceReseau.connection_Serveur();
					textArea.append(text + "\n");
					mntmArreterServeur.setEnabled(true);
					mntmDemmarerServeur.setEnabled(false);
					mntmRechercheClient.setEnabled(true);
				}
				catch (IOException arg0){
					textArea.append(arg0.getMessage() + "\n");
				}
			}
		});
		mnRer.add(mntmDemmarerServeur);
		
		mntmArreterServeur = new JMenuItem("Arreter Serveur");
		mntmArreterServeur.setEnabled(false);
		mntmArreterServeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					textArea.append("Extinction en cours\n");
					String text = serviceReseau.deco_Serveur();
			        textArea.append(text + "\n");
			        mntmRechercheClient.setEnabled(false);
			        mntmArreterServeur.setEnabled(false);
					mntmDemmarerServeur.setEnabled(true);
					mntmEnregistrer.setEnabled(false);
					btnNewButton.setEnabled(false);
					btnReception.setEnabled(false);
				}
				catch (IOException arg0){
					textArea.append(arg0.getMessage() + "\n");
				}
			}
		});
		mnRer.add(mntmArreterServeur);
		
		separator = new JSeparator();
		mnRer.add(separator);
		
		mntmRechercheClient = new JMenuItem("Recherche Client");
		mntmRechercheClient.setEnabled(false);
		mntmRechercheClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String text = serviceReseau.recherche();
					textArea.append(text + "\n");
					mntmRechercheClient.setEnabled(false);
					mntmEnregistrer.setEnabled(true);
					btnNewButton.setEnabled(true);
					btnReception.setEnabled(true);
				}catch(IOException arg1){
					textArea.append(arg1.getMessage() + "\n");
				}
			}
		});
		mnRer.add(mntmRechercheClient);
		
		mnActions = new JMenu("Actions");
		menuBar.add(mnActions);
		
		mntmEnregistrer = new JMenuItem("Enregistrer");
		mntmEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String note = JOptionPane.showInputDialog(null,
						"Entrez la note /20 :\n",
						"Note",
						JOptionPane.OK_CANCEL_OPTION
						);
				try{
					String addresse = serviceFichier.enregistrer(textArea.getText(), note);
					textArea.append("Enregistrement termine dans " + addresse + "\n");
				}catch(IOException arg2){
					textArea.append(arg2.getMessage() + "\n");
				}
				
			}
		});
		mntmEnregistrer.setEnabled(false);
		mnActions.add(mntmEnregistrer);
		
		mntmFermer = new JMenuItem("Fermer");
		mntmFermer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					serviceReseau.deco_Serveur();
					System.exit(0);
				}
				catch (IOException arg0){
					textArea.append(arg0.getMessage() + "\n");
				}
			}
		});
		mnActions.add(mntmFermer);
		
		menu = new JMenu("?");
		menuBar.add(menu);
		
		mntmAide = new JMenuItem("Aide");
		menu.add(mntmAide);
		
		mntmAPropos = new JMenuItem("A propos");
		mntmAPropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Auteur : Remi Lelaidier\n"
						+ "Version : Serveur V 0.1\n"
						+ "Contact : r.lelaidier@hotmail.fr"
						,"A propos", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(mntmAPropos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(12, 12, 424, 161);
		
		textField = new JTextField();
		textField.setBounds(12, 185, 315, 55);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Envoi");
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
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(339, 185, 97, 25);
		contentPane.add(btnNewButton);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(12, 12, 424, 161);
		contentPane.add(scrollPane);
		
		btnReception = new JButton("Recept");
		btnReception.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String text = serviceReseau.reception();
					if (text != null){
						textArea.append("Eleve : " + text + "\n");
					}
				}catch(IOException e){
					textArea.append(e.getMessage() + "\n");
				}
			}
		});
		btnReception.setEnabled(false);
		btnReception.setBounds(339, 215, 97, 25);
		contentPane.add(btnReception);
	}
}
