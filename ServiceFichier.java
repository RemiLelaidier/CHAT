package chat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ServiceFichier {
	
	// Attributs
	private FileWriter fw;
	private BufferedWriter output;
	private String adrFichier;

	// Constructeur
	public ServiceFichier(){
		
	}
	
	// Methode
	public String enregistrer(String texte, String note) throws IOException{
		adrFichier = System.getProperty("user.dir") + "/Sauvegarde.txt";
		fw = new FileWriter(adrFichier, true);
		output = new BufferedWriter(fw);
		String texteNote = texte +"\nNote : " + note + "/20";
		output.write(texteNote);
		output.flush();
		output.close();
		return adrFichier;
	}
}
