package onegioh;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlUtility {

	private static final String FILE_READING_ERROR = "Errore nella lettura del file";
	private static final String READER_ERROR = "Errore nell'inizializzazione del reader: ";

	/**
	 * metodo che inizializza un reader
	 * @param filename il nome del file dove dobbiamo leggere
	 * @return restituisce l'oggetto xmlstreamreader
	 */
	public static XMLStreamReader initializeXmlReader(String fileName) {
		XMLInputFactory xmlinput = null;
		XMLStreamReader xmlreader = null;
		try {
			xmlinput = XMLInputFactory.newInstance();
			xmlreader = xmlinput.createXMLStreamReader(fileName, new FileInputStream(fileName));
		}catch (Exception e) {
			System.out.println(READER_ERROR);
			System.out.println(e.getMessage());
		}
		return xmlreader;
	}
	
	/**
	 * metodo che estrapola i dati delle delle carte e crea una mappa con al interno altre mappe con dentro delle carte
	 * @param fileName il nome del file xml da dove prendere i dati secondo un formato pre stabilito
	 * @return un ogetto di tipo mappa 
	 */
	public static Map<Integer, Map<Integer, Card>> extractMapFromXml(String fileName){
		
		XMLStreamReader xmlReader = initializeXmlReader(fileName);
		Map<Integer, Card> subDeck;
		Map<Integer, Map<Integer, Card>> deck = new HashMap<Integer, Map<Integer, Card>>();
		int subDeckId;
		Color color;
		int cardNum;
		CardType type;
		Symbol value;
		
		try {
			while(xmlReader.hasNext()) {
				if(xmlReader.getEventType() == XMLStreamConstants.START_ELEMENT && xmlReader.getLocalName().equalsIgnoreCase("sottomazzo")) {
					subDeckId = Integer.parseInt(xmlReader.getAttributeValue(0));
					color = Color.checkString(xmlReader.getAttributeValue(1));
					cardNum = Integer.parseInt(xmlReader.getAttributeValue(2));
					subDeck = new HashMap<Integer, Card>();
					xmlReader.nextTag();
					for (int i = 0; i<cardNum; i++) {
						type = CardType.checkString(xmlReader.getAttributeValue(1));
						value = Symbol.getSymbol(xmlReader.getAttributeValue(2));
						Card card = new Card(color, type, value);
						subDeck.put(i, card);
						xmlReader.nextTag();
						xmlReader.nextTag();
					}
					deck.put(subDeckId, subDeck);
				}
				xmlReader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println(FILE_READING_ERROR);
			System.out.println(e.getMessage());
		}	

		return deck;
	}
}
