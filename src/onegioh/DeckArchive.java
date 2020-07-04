package onegioh;

import java.util.ArrayList;
import java.util.Map;

public class DeckArchive {
	
	private ArrayList<String> menu; 
	private ArrayList<Map<Integer, Map<Integer, Card>>> deckArchive;
	
	public DeckArchive() {
		this.menu = new ArrayList<String>();
		this.deckArchive = new ArrayList<Map<Integer, Map<Integer, Card>>>();
	}

	/**
	 * metodo che permette l'inserimento di un nuovo deck
	 * @param deckName nome del deck da visualizzare
	 * @param fileXmlName nome del rispettivo file xml
	 */
	public void insertADeck(String deckName, String fileXmlName) {
		this.menu.add(deckName);
		this.getDeckArchive().add(XmlUtility.extractMapFromXml(fileXmlName));
	}
	
	/**
	 * stampa tutti i nomi dei deck presenti a sistema
	 */
	public void printDecksList() {
		for (int i = 0; i < this.getMenu().size(); i++)
			System.out.println((i+1) + "° " + this.getMenu().get(i));
	}
	/**
	 * controlla se è presente un nome nella lista
	 * @param deckName nome daricercare
	 * @return true in caso positivo, false altrimenti
	 */
	public boolean lookForADeckName(String deckName) {
		for(int i = 0; i < this.getMenu().size(); i++)
			if (deckName.trim().equalsIgnoreCase(this.getMenu().get(i)))
				return true;
		return false;
	}
	
	//GETTERS AND SETTERS
	public ArrayList<String> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<String> menu) {
		this.menu = menu;
	}

	public ArrayList<Map<Integer, Map<Integer, Card>>> getDeckArchive() {
		return deckArchive;
	}
	
	public void setDeckArchive(ArrayList<Map<Integer, Map<Integer, Card>>> deckArchive) {
		this.deckArchive = deckArchive;
	}
	
	
}
