package onegioh;

public class Card {
	
	private Color color;
	private Symbol value;
	private CardType type; 
	
	public Card(Color color, CardType type, Symbol value) {
		this.color = color;
		this.value = value;
		this.type = type;
	}
	
	@Override
	public String toString() {
		String string = "";
		if (getType() == null)
			string += "null ";
		else
			string += getType().toString() + " ";
		
		if (getValue() == null)
			string += "null ";
		else {
			if (getType().equals(CardType.NUMERO))
				string += getValue().toString().substring(1) + " ";
			else
				string += getValue().toString() + " ";
		}
		
		if (getColor() == null)
			string += "vuoto ";
		else
			string += getColor().toString() + " ";
		
		
		return string;
	}

	//GETTERS AND SETTERS
	
	
	public Color getColor() {
		return color;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Symbol getValue() {
		return value;
	}

	public void setValue(Symbol value) {
		this.value = value;
	}
	
	
}
