package onegioh;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Deck {
	
	/**
	 * prende una carta casuale dal mazzo rimanente
	 * @param deck mazzo aggiornato
	 * @return una carta casuale
	 */
	public static Card getRandomCard(Map<Integer, Map<Integer, Card>> deck) {
		Random random = new Random();
		int subDeckId;
		
		do {
			subDeckId = random.nextInt(deck.size());
		}while (deck.get(subDeckId).isEmpty());
		int cardId = random.nextInt(deck.get(subDeckId).keySet().size());
		Card card = deck.get(subDeckId).get(deck.get(subDeckId).keySet().toArray()[cardId]);
		deck.get(subDeckId).remove(cardId);
		
		return card;
	}
	
	/**
	 * rimuove una carta dal mazzo
	 * @param deck il mazzo aggiornato
	 * @param card la carta da cercare e rimuovere
	 */
	public static void removeCard(Map<Integer, Map<Integer, Card>> deck, Card card) {
		
		for (int i = 0; i<deck.size(); i++) {
			if (deck.get(i).get(0).getColor().equals(card.getColor()))
				for (int j = 0; j < deck.get(i).size(); j++) {
					if(deck.get(i).get(j).getValue().equals(card.getValue())) {
						deck.get(i).remove(j);
						System.out.println("card removed");
					}
				}
		}
		
	}
	
	/**
	 * controlla se il mazzo è vuoto
	 * @param deck il mazzo aggiornato
	 * @return true in caso positivo, false altrimenti
	 */
	public static boolean deckEmpty(Map<Integer, Map<Integer, Card>> deck) {
		for(int i = 0; i < deck.size(); i++) 
			if (deck.get(i).size() == 0)
				return true;
		return false;
		
	}
	
	/**
	 * resetta il mazzo quando si svuota, considerando le mani dei giocatori e lo stack
	 * @param hand tutte le mani dei giocatori
	 * @param stack la pila degli scarti
	 * @param choosenDeck il mazzo originale
	 * @return il mazzo aggiornato
	 */
	public static Map<Integer, Map<Integer, Card>> resetDeck(ArrayList<ArrayList<Card>> hand, Deque<Card> stack, Map<Integer, Map<Integer, Card>> choosenDeck){

		for (int i = 0; i < hand.size(); i++)
			for (int j = 0; j < hand.get(i).size(); j++)
				Deck.removeCard(choosenDeck, hand.get(i).get(j));
		
		Iterator<Card> iter = stack.iterator();
		Card lastCard = stack.peekLast();
		while(iter.hasNext()) {
			Deck.removeCard(choosenDeck, iter.next());
			iter.remove();
		}
		stack.add(lastCard);
		return choosenDeck;
	}
	
	
	
}
