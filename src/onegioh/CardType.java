package onegioh;

public enum CardType {
	NUMERO, SPECIALE;
	
	/**
	 * metodo che restituisce il relativo oggotto di tipo CardType
	 * @param string la stringa con il tipo richiesto
	 * @return l'oggetto di tipo Color
	 */
	public static CardType checkString(String string) {
		for(CardType c : CardType.values()) 
			if (string.trim().equalsIgnoreCase(c.toString()))
				return c;
		return null;
	}
}
