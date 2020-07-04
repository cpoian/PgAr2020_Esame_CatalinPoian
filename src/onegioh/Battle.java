package onegioh;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;

import it.unibs.ing.fp.mylib.InputDati;

public class Battle {
	
	private static final String INVALID_CARD_MESSAGE = "You must choose a card with the same color or symbol!";
	private static final String CHOOSE_CARD_MESSAGE = "###Play your card: ";
	public static final int MAX_CARD_IN_HAND = 7;

	/**
	 * metodo che gestisce la battaglia tra vari giocatori
	 * @param deck da modificare durante la partita
	 * @param players il numero totale dei giocatori
	 * @param choosenDeck un copia del deck al completo da non modificare
	 */
	public static void battle(Map<Integer, Map<Integer, Card>> deck, int players, Map<Integer, Map<Integer, Card>> choosenDeck) {
		
		ArrayList<ArrayList<Card>> hand = new ArrayList<ArrayList<Card>>();
		for (int i = 0; i < players; i++) 
			hand.add(initHand(deck));
		
		Deque<Card> stack = new ArrayDeque<Card>();
		stack.add(Deck.getRandomCard(deck));
		do {
			boolean changeOrder = true;
			for(int i = 0; i < hand.size() && i >= 0;) {
				boolean changedLastCard = false;
				if (changeOrder)
					i++;
				else
					i--;
				//CONTROLLO SE IL DECK NON E' VUOTO
				if (Deck.deckEmpty(deck))
					Deck.resetDeck(hand, stack, choosenDeck);
				
				//CONTROLLO DELLE CARTE SPECIALI
				if (stack.getLast().getType().equals(CardType.SPECIALE)) {
					//PESCA DUE
					if (stack.getLast().getValue().equals(Symbol.PESCADUE)) {
						hand.get(i).add(Deck.getRandomCard(deck));
						hand.get(i).add(Deck.getRandomCard(deck));
					}
					//CAMBIA COLORE E PESCA QUATTRO
					else if (stack.getLast().getValue().equals(Symbol.CAMBIACOLORE) || stack.getLast().getValue().equals(Symbol.PESCAQUATTRO)) {
						String color = InputDati.leggiStringaNonVuota("Choose a color: ");
						System.out.printf("%nColor on top ---> " + color + "<---%n");
						changedLastCard = true;
						Card lastCard = stack.getLast();
						stack.getLast().setColor(Color.checkString(color));
						if (nextMove(hand.get(i), stack)) {
							Card recentCard = stack.getLast();
							stack.removeLast();
							stack.removeLast();
							stack.add(lastCard);
							stack.add(recentCard);
						}
						else {
							hand.get(i).add(Deck.getRandomCard(deck));
							if (nextMove(hand.get(i), stack)) {
								Card recentCard = stack.getLast();
								stack.removeLast();
								stack.removeLast();
								stack.add(lastCard);
								stack.add(recentCard);
							}
						}
						if (stack.getLast().getValue().equals(Symbol.PESCAQUATTRO)) {
							for (int j = 0; j < 4; j++)
								hand.get(i).add(Deck.getRandomCard(deck));
							i++;
							if (i == hand.size())
								i = 0;
						}
						
					}
					//STOP
					else if (stack.getLast().getValue().equals(Symbol.STOP)) {
						i++;
						if (i == hand.size())
							i = 0;
					}
					//CAMBIO GIRO
					else if (stack.getLast().getValue().equals(Symbol.CAMBIOGIRO)) {
						changeOrder = !changeOrder;
					}
					//CAMBIO CARTE
					else if (stack.getLast().getValue().equals(Symbol.CAMBIOCARTE)){
						changeHands(hand, changeOrder);
					}
						
				}
				if (changedLastCard == false)
					System.out.printf("%nCard on top ---> " + stack.getLast().toString() + "<---%n");
				System.out.printf("%nPlayer %d: %n", i+1);
			
				if (!nextMove(hand.get(i), stack)) {
						hand.get(i).add(Deck.getRandomCard(deck));
						nextMove(hand.get(i), stack);
				}
				if (checkForAnEmptyHand(hand) != -1)
					break;
				
				
			}
		}while (checkForAnEmptyHand(hand) == -1);
		System.out.printf("%n%nPlayer %d wins!!!%n%n", checkForAnEmptyHand(hand));
		System.out.println("Going back to the main menu.%n");
	}
	
	/**
	 * metodo che scmabia le mani dei giocatori tenendo conto del ordine di gioco
	 * @param hand le mani di tutti i giocatori
	 * @param changeOrder l'ordine di gioco
	 */
	public static void changeHands(ArrayList<ArrayList<Card>> hand, boolean changeOrder) {
		ArrayList<Card> firstHand;
		if (changeOrder) {
			firstHand = hand.get(0);
			for (int i = 0; i < hand.size()-1; i++) {
				hand.set(i, hand.get(i+1));
			}
			hand.set(hand.size()-1, firstHand);
		}
		else {
			firstHand = hand.get(hand.size()-1);
			for (int i = hand.size()-1; i > 0; i--) {
				hand.set(i, hand.get(i-1));
			}
			hand.set(0, firstHand);
		}
	}
	/**
	 * metodo per controllare se qualcuno ha finito le carte in mano
	 * @param hand l'arrayList di tutte le mani dei giocatori
	 * @return il numero del giocatore, -1 altrimenti
	 */
	public static int checkForAnEmptyHand(ArrayList<ArrayList<Card>> hand) {
		for(int i = 0; i < hand.size(); i++)
			if (hand.get(i).isEmpty())
				return i;
		return -1;
	}
	
	/**
	 * metodo per inizializzare la partita 
	 * @param deck il mazzo da dove prelevare le carte
	 * @return la mano completa di un giocatore
	 */
	public static ArrayList<Card> initHand(Map<Integer, Map<Integer, Card>> deck){
		ArrayList<Card> hand = new ArrayList<Card>();
		for (int i = 0; i < MAX_CARD_IN_HAND; i++)
			hand.add(Deck.getRandomCard(deck));
		return hand;
	}
	
	/**
	 * calcola la prossima mossa di un giocatore
	 * @param hand la mano del giocatore
	 * @param stack la pila di carte scartate
	 * @return true nel caso sia stato scartato qualcosa, false altrimenti
	 */
	public static boolean nextMove(ArrayList<Card> hand, Deque<Card> stack) {
		Color color = stack.getLast().getColor();
		Symbol value = stack.getLast().getValue();
		Card card = new Card(null, null, null);
		int move;
		for(;;) {
			printHand(hand);
			move = InputDati.leggiIntero(CHOOSE_CARD_MESSAGE, 0, hand.size());
			if (move == hand.size())
				break;
			else
				card = hand.get(move);
			if (!card.getColor().equals(color) && !card.getValue().equals(value) && !card.getColor().equals(null))
				System.out.println(INVALID_CARD_MESSAGE);
			
			if (card.getColor().equals(color) || card.getValue().equals(value))
				break;
		}
		if (move == hand.size())
				return false;
		else {
			stack.add(card);
			hand.remove(card);
			return true;
		}
	}
	
	/**
	 * stampa le carte in mano di un giocatore
	 * @param hand la mano del giocatore
	 */
	public static void printHand(ArrayList<Card> hand){
		for(int i = 0; i < hand.size(); i++)
			System.out.printf("%d# %s%n", i, hand.get(i).toString());
		System.out.printf("%d° %s%n", hand.size(), "DRAW CARD");
	}
}