package onegioh;

import java.util.Random;

public enum Color {
	BLU, ROSSO, GIALLO, VERDE;
	
	/**
	 * restituisce un colore a caso
	 * @return un oggetto di tipo Color
	 */
	public static Color getRandom() {
		Random random = new Random();
		return Color.values()[random.nextInt(Color.values().length)];
	}
	
	/**
	 * metodo che restituisce il relativo oggotto di tipo colore
	 * @param string la stringa con il colore richiesto
	 * @return l'oggetto di tipo Color
	 */
	public static Color checkString(String string) {
		for(Color c : Color.values()) 
			if (string.trim().equalsIgnoreCase(c.toString()))
				return c;
		return null;
	}
}
