//import java.util.Arrays; brauch i lei wenn i sort verwenden darf
import java.util.Random;
/*
 * PokerGame2.0
 */
public class MainPokerGame2 {

	static Random rand = new Random();
	static final int anzKarten = 52; // Anzahl Karten 52 und alles groß scheiben weil Variable
	static final int anzSymbole = 13; // final für die Konstaten
	static int[] kartenDeck = new int[anzKarten];
	static final int anzZiehKarten = 5;
	static int paarCount;
	static int paareCount;
	static int dreierCount;

	public static void main(String[] args) {	//for schleifi mit 100.000 machen weil wegen prozent
		int[] gezHand = null;
		fillArray();
		System.out.println("Bei 1000 Durchgängen hast du : ");
		for(int schleifi=0; schleifi<100000; schleifi++) { 
			gezHand = kartenZiehen();
			hoechsteKombi(gezHand);
		}
		
	}

	static void hoechsteKombi(int[] hand) {
		if (royalStreetFlush(hand)) {
			System.out.println("DU HASCH AN ROYAL FLUSH");
		} else if (streetFlush(hand)) {
			System.out.println("Du hast an Straight Flush");
		} else if (vierer(hand)) {
			System.out.println("Du hast an Vierer");
		} else if (fullHaus(hand)) { 	// HUBER YEAH
			System.out.println("Du hast a Volles Haus bro");
		} else if (flush(hand)) {
			System.out.println("Du hast einen Flush");
		} else if (street(hand)) {
			System.out.println("Du hast eine Straße");
		} else if (dreier(hand)) {
			System.out.println("Du hast an Dreier");
		} else if (zweiPaar(hand)) {
			System.out.println("- "+paareCount+" mal 2 Paare.");
		} else if (paar(hand)) {
			System.out.println("- "+paarCount+" mal 1 Paar gezogen.");
		} else {
			System.out.print("Leider nix, aber deine höchte Karte ist: 	 " + symbol(highestKarte(hand)));
		}
	}

	static void fillArray() {
		for (int i = 0; i < anzKarten; i++) {
			kartenDeck[i] = i;
		}
	}

	static int[] kartenZiehen() { // Hier wird die zb 25. karte genommen, auf den letzten platz getan und die
									// letzte karte kommt auf platz 25
		for (int i = 0; i < anzZiehKarten; i++) {
			int zufZahlen = rand.nextInt(anzKarten);
			int temp = kartenDeck[kartenDeck.length - 1 - i];		//bei kartenDeck.length kommt 52 raus
			kartenDeck[kartenDeck.length - 1 - i] = kartenDeck[zufZahlen];
			kartenDeck[zufZahlen] = temp;
		}
		int[] gezHand = new int[anzZiehKarten];
		for (int i = (kartenDeck.length - anzZiehKarten); i < kartenDeck.length; i++) { // i=47;i<=51;
			gezHand[i - kartenDeck.length + anzZiehKarten] = kartenDeck[i]; // 47-52+5=0 YEAH und des läft durch bis 51
																			// und dann 5 karten yeah
		}
		return gezHand; // Was passiert wenn 5 mal die karte 52 gezogen wird ? geht des ? ->ja save oda?
	}

	public static void bubbleSort(int[] a) { 	
		boolean sortiert = false; 				// ein "bubbleSort" wird programmiert wenn man Array.sort() nicht verwenden darf
		int temp;
		while (!sortiert) {
			sortiert = true;
			for (int i = 0; i < a.length - 1; i++) { // es weden immmer die Zahlen verglichen solange immer die vordere
														// kleiner als die darauffolgende ist
				if (a[i] > a[i + 1]) {
					temp = a[i];
					a[i] = a[i + 1];
					a[i + 1] = temp;
					sortiert = false;
				}
			}
		}
	}

	static void kartenDeckAusgeben(int[] deck) {
		for (int i = 0; i < deck.length; i++) {
			System.out.println(deck[i]);
		}
	}

	static String[] kartenWerteAnzeigen(int[] hand) { // was für Farbe und was für Symbol du in da hand hasch
		String[] kartenAnz = new String[anzKarten];
		for (int i = 0; i < hand.length; i++) {
			System.out.print(farbe(hand[i]));
			System.out.print("   ");
			System.out.println(symbol(hand[i]));
		}
		return kartenAnz;
	}

	static String farbe(int kartenWert) {
		String ret = "";
		switch (farbeHerausfinden(kartenWert)) { // 0 bis 3; 0=Herz, 1=Kreuz, 2=Pik, 3=Karo
		case 0:
			ret = "Herz\t";
			break;
		case 1:
			ret = "Kreuz\t";
			break;
		case 2:
			ret = "Pik\t";
			break;
		case 3:
			ret = "Karo\t";
			break;
		}
		return ret;
	}

	static String symbol(int kartenWert) {
		String ret = "";
		switch (symbolHerausfinden(kartenWert)) { // 0 bis 12; 0=2, 1=3, ..., 11=König, 12=Ass
		case 0:
			ret = "2er";
			break;
		case 1:
			ret = "3er";
			break;
		case 2:
			ret = "4er";
			break;
		case 3:
			ret = "5er";
			break;
		case 4:
			ret = "6er";
			break;
		case 5:
			ret = "7er";
			break;
		case 6:
			ret = "8er";
			break;
		case 7:
			ret = "9er";
			break;
		case 8:
			ret = "10er";
			break;
		case 9:
			ret = "Bua";
			break;
		case 10:
			ret = "Dame";
			break;
		case 11:
			ret = "König";
			break;
		case 12:
			ret = "Ass";
			break;
		}
		return ret;
	}

	static int symbolHerausfinden(int kartenWert) { // 0 bis 12; 0=2, 1=3, ..., 11=Koenig, 12=Ass
		return kartenWert % anzSymbole;
	}

	static int[] convertHandSymbol(int[] hand) { // i nimm mei array und find die symbole in der hand heraus
		int[] newHand = new int[hand.length];
		for (int i = 0; i < hand.length; i++) {
			newHand[i] = symbolHerausfinden(hand[i]);
		}
		return newHand;
	}

	static int farbeHerausfinden(int kartenWert) { // 0 bis 3; 0=Herz, 1=Kreuz, 2=Pik, 3=Karo
		return kartenWert / anzSymbole;
	}

	static int[] convertHandFarbe(int[] hand) { // es macht keinen unterschied ob da jetzt int[] hand oder int hand[]
												// steht oda
		int[] newHand = new int[hand.length];
		for (int i = 0; i < hand.length; i++) {
			newHand[i] = farbeHerausfinden(hand[i]);
		}
		return newHand;
	}

	static int highestKarte(int[] hand) {
		int[] gezSymbole = convertHandSymbol(hand);
		bubbleSort(gezSymbole);
		return symbolHerausfinden(gezSymbole[gezSymbole.length - 1]);
	}

	static boolean paar(int[] gezHand) {
		int[] gezSymbole = convertHandSymbol(gezHand); // da sind jetzt lei meine symbole (können doppelt vorkommen)
		bubbleSort(gezSymbole); // alle schön in der reihenfolge
		int anzahlPaare = 0; 
		for (int i = 0; i < gezSymbole.length - 1; i++) { // des mach i mit 3erling und 4erlin no
			if (gezSymbole[i] == gezSymbole[i + 1]) { // geschaut ob der einz weiter es gleiche is wie i
				anzahlPaare++; // wenn ganze Hand dann in ein switch mit dem stärksten beginnend
			}
		}
		if (anzahlPaare==1) {
			paarCount++;
			return true;
		}
		return false;
	}
	static boolean zweiPaar(int[] gezHand) {
		int[] gezSymbole = convertHandSymbol(gezHand); // da sind jetzt lei meine symbole (können doppelt vorkommen)
		bubbleSort(gezSymbole); // alle schön in der reihenfolge
		int anzahlZweiPaare=0;
		for (int i = 0; i < gezSymbole.length - 1; i++) { // des mach i mit 3erling und 4erlin no
			if (gezSymbole[i] == gezSymbole[i + 1]) { // geschaut ob der einz weiter es gleiche is wie i
				anzahlZweiPaare++; // wenn ganze Hand dann in ein switch mit dem stärksten beginnend
			}
		}
		if (anzahlZweiPaare==2) {
			paareCount++;
			return true;
		}
		return false;
	}
	static boolean dreier(int[] gezHand) {
		int[] gezSymbole = convertHandSymbol(gezHand);
		bubbleSort(gezSymbole);
		for (int i = 0; i < gezSymbole.length - 2; i++) {
			if ((gezSymbole[i] == gezSymbole[i + 1]) && (gezSymbole[i] == gezSymbole[i + 2])) {
				dreierCount++;
				return true;
			}
		}
		return false;
	}

	static boolean vierer(int[] gezHand) {
		int[] gezSymbole = convertHandSymbol(gezHand);
		bubbleSort(gezSymbole);
		for (int i = 0; i < gezSymbole.length - 3; i++) {
			if ((gezSymbole[i] == gezSymbole[i + 1]) && (gezSymbole[i] == gezSymbole[i + 2])
					&& (gezSymbole[i] == gezSymbole[i + 3])) {
				return true;
			}
		}
		return false;
	}

	static boolean fullHaus(int[] hand) { //nur kombinieren 
		if (dreier(hand) && (paar(hand))) {
			return true;
		}
		return false;
	}

	static boolean street(int[] hand) {
		int[] gezSymbole = convertHandSymbol(hand);
		bubbleSort(gezSymbole);
		if ((gezSymbole[0] == gezSymbole[1] - 1) && (gezSymbole[1] == gezSymbole[2] - 1)
				&& (gezSymbole[2] == gezSymbole[3] - 1) && (gezSymbole[3] == gezSymbole[4] - 1)) {
			return true;
		}
		return false;
	}

	static boolean flush(int[] hand) { // ganze hand gleichfarbig
		int[] gezFarbe = convertHandFarbe(hand);
		if ((gezFarbe[0] == gezFarbe[1]) && (gezFarbe[1] == gezFarbe[2]) && (gezFarbe[2] == gezFarbe[3])
				&& (gezFarbe[3] == gezFarbe[4])) {
			return true;
		}
		return false;
	}

	static boolean streetFlush(int[] hand) {	//ab jetzt nur noch methoden kombis von oben kombinieren
		if (street(hand) && flush(hand)) {
			return true;
		}
		return false;
	}

	static boolean royalStreetFlush(int[] hand) {
		if (!flush(hand)) {
			return false;
		}
		int[] gezSymbole = convertHandSymbol(hand);
		bubbleSort(gezSymbole);
		if ((gezSymbole[0] == 8) && (gezSymbole[1] == 9) && (gezSymbole[2] == 10) && (gezSymbole[3] == 11)
				&& (gezSymbole[4] == 12)) {
			return true;
		}
		return false;
	}
}

/*
 * Lösung: i hab 5 karten in da hand und schau was i damit in da hand hab
 * 
 * strg+shiffen+f = macht schön
 * 
 * schwitch oben schön machen
 * 
 *	Bubblesort = eigentlich wie "kartenZiehen" aber angepasst für die anderen??
 */
