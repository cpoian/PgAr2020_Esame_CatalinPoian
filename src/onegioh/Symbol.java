package onegioh;

public enum Symbol {
	 N0, N1, N2, N3, N4, N5, N6, N7, N8, N9, PESCADUE, STOP, CAMBIOGIRO, PESCAQUATTRO, CAMBIACOLORE, CAMBIOCARTE;
	
	public static Symbol getSymbol(String string) {
		for (Symbol s : Symbol.values()) {
			if (s.toString().contains(string.toUpperCase().trim()))
				return s;
		}
		return null;
	}
}
