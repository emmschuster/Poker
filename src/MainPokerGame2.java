import java.util.Arrays; //brauch i lei wenn i sort verwenden darf
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/*
 * PokerGame2.0
 */
public class MainPokerGame2 {

	static Random rand = new Random();
	static final int anzKarten = 52; 			// Anzahl Karten 52 und alles gro� scheiben weil Variable
	static final int anzSymbole = 13; 			// final f�r die Konstaten
	static int[] kartenDeck = new int[anzKarten];
	static final int anzZiehKarten = 5;
	static int SCHLEIFENDURCHLAEUFE;		
	static int paarCount;
	static int paareCount;
	static int dreierCount;
	static int viererCount;
	static int fullHausCount;
	static int strasseCount;
	static int streetAu�nahmeCount;
	static int flushCount;
	static int streetFlushCount;
	static int royalStreetFlushCount;
	static DecimalFormat numberFormat = new DecimalFormat("#.000000");

	public static void main(String[] args) {	
		Scanner sc = new Scanner (System.in);
		try {
			System.out.println("Wie viele Schleifendruchl�ufe willst du ?");
			SCHLEIFENDURCHLAEUFE=sc.nextInt();
			int[] gezHand = null;
			fillArray();
			System.out.println("Bei "+SCHLEIFENDURCHLAEUFE+" Durchg�ngen hast du : ");
			for(int schleifi=0; schleifi<SCHLEIFENDURCHLAEUFE; schleifi++) { 
				gezHand = kartenZiehen();
				if (checkForOnePair(gezHand)) {paarCount++;}
				if (zweiPaar(gezHand)) {paareCount++;}
				if (dreier(gezHand)) {dreierCount++;}
				if (street(gezHand)) {strasseCount++;}
				if (streetAu�nahme(gezHand)) {streetAu�nahmeCount++;}
				if (flush(gezHand)) {flushCount++;}
				if (fullHaus(gezHand)) {fullHausCount++;}
				if (vierer(gezHand)) {viererCount++;}
				if (streetFlush(gezHand)) {streetFlushCount++;}
				if(royalStreetFlush(gezHand)) {royalStreetFlushCount++;}
			}
			System.out.println(paarCount+"\t mal ein Paar. Wahrscheinlichekit von \t\t"+numberFormat.format((((paarCount/(double)SCHLEIFENDURCHLAEUFE)*100)))+"%");
			System.out.println(paareCount+"\t mal zwei Paare. Wahrscheinlichekit von \t"+numberFormat.format(((paareCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println(dreierCount+"\t mal einen Dreier. Wahrscheinlichekit von \t"+numberFormat.format(((dreierCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println((strasseCount+streetAu�nahmeCount)+"\t mal eine Strasse. Wahrscheinlichekit von \t"+"0"+numberFormat.format((((strasseCount+streetAu�nahmeCount)/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println(flushCount+"\t mal einen Flush. Wahrscheinlichekit von \t"+"0"+numberFormat.format(((flushCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println(fullHausCount+"\t mal ein Volles Haus. Wahrscheinlichekit von \t"+"0"+numberFormat.format(((fullHausCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println(viererCount+"\t mal einen Vierer. Wahrscheinlichekit von \t"+"0"+numberFormat.format(((viererCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println(streetFlushCount+"\t mal ein Straight Flush. Wahrscheinlichekit von "+"0"+numberFormat.format(((streetFlushCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
			System.out.println(royalStreetFlushCount+"\t mal einen Royal Flush. Wahrscheinlichekit von \t"+"0"+numberFormat.format(((royalStreetFlushCount/(double)SCHLEIFENDURCHLAEUFE)*100))+"%");
		} catch (Exception e) {
			System.out.println("Gib bitte nur ganzahlige Werte ein!");
		}
	}
	static void fillArray() {
		for (int i = 0; i < anzKarten; i++) {
			kartenDeck[i] = i;
		}
	}

	static int[] kartenZiehen() { 
		for (int i = 0; i < anzZiehKarten; i++) {
			int zufZahlen = rand.nextInt(anzKarten);
			int temp = kartenDeck[kartenDeck.length - 1 - i];		
			kartenDeck[kartenDeck.length - 1 - i] = kartenDeck[zufZahlen];
			kartenDeck[zufZahlen] = temp;
		}
		int[] gezHand = new int[anzZiehKarten];
		for (int i = (kartenDeck.length - anzZiehKarten); i < kartenDeck.length; i++) { 
			gezHand[i - kartenDeck.length + anzZiehKarten] = kartenDeck[i]; 

		}
		return gezHand; 
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
		Arrays.sort(gezSymbole);
		return symbolHerausfinden(gezSymbole[gezSymbole.length - 1]);
	}

	static int countDuplicateSymbole(int cards[]) { //vom Flash geklaut
		/*int amountOfDuplicates = 0;
		for (int i = 0; i < cards.length-1; i++) {
			for (int j = i+1; j < cards.length; j++) {
				if (((cards[i] % anzSymbole) == (cards[j] % anzSymbole))) {
					amountOfDuplicates++; //des sollten aussen wo gez�hlt wean
				}
			}
		}
		return amountOfDuplicates;*/
		int amountOfDuplicates = 0;
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards.length; j++) {
				if ((cards[i] % anzSymbole == cards[j] % anzSymbole) && i != j) {
					amountOfDuplicates++;
				}
			}
		}
		return amountOfDuplicates;
	}

	static boolean checkForOnePair(int[] cards) {
		if (countDuplicateSymbole(cards) == 2) {	//bei meiner auskommentierten methode 1 und bei 2 2 und bei 3 3 und bei 4 4
			return true;
		}
		return false;
	}

	static boolean zweiPaar(int[] gezHand) {
		if (countDuplicateSymbole(gezHand) == 4) {	
			return true;
		}
		return false;
	}

	static boolean dreier(int[] gezHand) {
		if (countDuplicateSymbole(gezHand) == 6) {	
			return true;
		}
		return false;
	}

	static boolean vierer(int[] gezHand) {
		if (countDuplicateSymbole(gezHand) == 12) {	
			return true;
		}
		return false;
	}

	static boolean fullHaus(int[] hand) { 					
		/*if (dreier(hand) && checkForOnePair(hand)) {		
			return true;
		}
		return false;*/
		return countDuplicateSymbole(hand) == 8;
	}

	static boolean street(int[] hand) { 	
		int[] gezSymbole = convertHandSymbol(hand);
		Arrays.sort(gezSymbole);
		for (int i = 0; i<hand.length-1;i++) {
			if (!((gezSymbole[i+1]-gezSymbole[i])==1)) {return false;}
		}
		return true;
	}

	static boolean streetAu�nahme(int[] hand) {
		int[] gezSymbole = convertHandSymbol(hand);
		Arrays.sort(gezSymbole);
		if ((gezSymbole[0]==0) && (gezSymbole[1]==1) && (gezSymbole[2]==2) && (gezSymbole[3]==3) &&(gezSymbole[4]==12)) {
			return true;
		}
		return false;
	}

	static boolean flush(int[] hand) { // ganze hand gleichfarbig
		int[] gezFarbe = convertHandFarbe(hand);
		Arrays.sort(gezFarbe);
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
		Arrays.sort(gezSymbole);
		if ((gezSymbole[0] == 8) && (gezSymbole[1] == 9) && (gezSymbole[2] == 10) && (gezSymbole[3] == 11)
				&& (gezSymbole[4] == 12)) {
			return true;
		}
		return false;
	}
}

/*
 * L�sung: i hab 5 karten in da hand und schau was i damit in da hand hab
 * 
 * strg+shiffen+f = macht sch�n
 * 
 * Fn+F3+die methode anklicken => spring zum urspung der methode
 * 
 * schwitch oben sch�n machen
 * 
 *	Bubblesort = eigentlich wie "kartenZiehen" aber angepasst f�r die anderen??
 */
