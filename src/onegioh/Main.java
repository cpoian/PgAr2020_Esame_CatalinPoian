package onegioh;

import java.util.Map;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;

public class Main {

	public static final String[] MENU_OPTIONS = {
			"PRINT DECK'S LIST",
			"LOOK FOR A MAP",
			"ADD A MAP",
			"CHOOSE A MAP"
	};
	
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	
	public static void main(String[] args) {
		
		/**
		 * @author Catalin
		 * ho scelto l'esercizio "uno-Gi-Oh"
		 * come moduli oltre a quello base, ho cercato di fare il A1, A2, A3, B1
		 * purtroppo non ho fatto abbastanza prove, almeno non tutte quelle che avrei voluto
		 */
		
		DeckArchive deckArchive = new DeckArchive();
		deckArchive.insertADeck("STANDART", "nucleoBaseUnoGiOh.xml");
		deckArchive.insertADeck("DRAW TWO CARDS", "Uno_Gi_OhconPescaDue.xml");
		deckArchive.insertADeck("SPECIAL CARDS", "UnoGiOhCarteSpeciali.xml");
		deckArchive.insertADeck("SPECIAL CARDS AND VOID COLOR", "UnoGiOhSenzaCarteNonColorate.xml");
	
		int scelta;
		MyMenu menu = new MyMenu("Choose an option: ", MENU_OPTIONS);
		do {
			scelta = menu.scegli();
			switch (scelta) {
			case 1: 
				deckArchive.printDecksList();
				break;
			case 2:
				if (deckArchive.lookForADeckName(InputDati.leggiStringaNonVuota("Insert deck's name: ")))
					System.out.println("Deck found!!!");
				else
					System.out.println("Deck not found!!!");
				break;
			case 3:
				deckArchive.insertADeck(InputDati.leggiStringaNonVuota("Insert deck's name: "), InputDati.leggiStringaNonVuota("Insert xml file's name: "));
				break;
			case 4:
				deckArchive.printDecksList();
				int choosen = InputDati.leggiIntero("Choose a deck: ", 1, deckArchive.getDeckArchive().size());
				int players = InputDati.leggiIntero("How many players: ", MIN_PLAYERS, MAX_PLAYERS);
				Map<Integer, Map<Integer, Card>> choosenDeck = deckArchive.getDeckArchive().get(choosen-1);
				Battle.battle(choosenDeck, players, choosenDeck);
				break;
			case 0:
				System.out.println("Program closed successfully!");
				break;
			}
		}while(scelta != 0);
	}
}
