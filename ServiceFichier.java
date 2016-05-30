package TchatJAVA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceFichier {
	
	// Attributs
	private Date madate;
	private Date monheure;
	private FileWriter fw;
	private BufferedWriter output;
	private String adrFichier;
	private String txtdate;
	private String txtheure;

	// Constructeur
	public ServiceFichier(){
		
	}
	
	// Methode
	public String enregistrer(String texte, String note) throws IOException{
		madate = new Date();
		monheure = new Date();
		txtdate = new SimpleDateFormat("yyyy_MM_dd", Locale.FRANCE).format(madate);
		txtheure = new SimpleDateFormat("hh_mm", Locale.FRANCE).format(madate);
		adrFichier = System.getProperty("user.dir") + "/Sauvegarde_du_"+ txtdate.toString() + "_" + txtheure.toString() +".txt";
		fw = new FileWriter(adrFichier, true);
		output = new BufferedWriter(fw);
		String texteNote = texte +"\nNote : " + note + "/20" ;
		output.write(texteNote);
		output.flush();
		output.close();
		return adrFichier;
	}
}
