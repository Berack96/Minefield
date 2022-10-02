package berack96.games.minefield;

import berack96.games.minefield.frame.MenuFrame;
import berack96.games.minefield.object.Decisor;
import berack96.games.minefield.object.Field;
import berack96.games.minefield.object.view.FieldView;
import berack96.games.minefield.runexception.OutOfBoundsException;
import jbook.util.Input;

/**
 * Classe che utilizza {@link Field} e {@link Decisor} per creare una interazione con l'utente.<br>
 * Il gioco puo' esser fatto partire per console o come finestra.<br>
 * Senza argomenti il gioco partira' in finestra.<br>
 * <br>
 * Lista dei possibili argomenti da passare al programma:<br>
 * -nogui indica che il gioco partira' su console.<br>
 * -safe=(true/false)<br>
 * -mode=(EASY/MEDIUM/HARD/CUSTOM)<br>
 * -dim=(colonne),(righe)<br>
 * -mines=(num)<br>
 * 
 * Questi argomenti per ora funzionano solo con il gioco in console<br>
 * ovvero solo se si usa anche -nogui
 * 
 * @author Jack
 *
 */
public class Game {
	
	private static Field field;
	private static boolean graphic = true;
	
	private static Boolean safe = null;
	private static Mode mode = null;
	private static Integer columns = null;
	private static Integer lines = null;
	private static Integer mines = null;
	
	public static void main(String[] arg) {
		for(String s: arg)	// looking for arguments
			try {
				
				if(s.matches("-nogui"))
					graphic = false;
				else if(s.matches("-safe=(.*)")) {
					String temp[] = s.split("=");
					safe = Boolean.getBoolean(temp[1]);
				}
				else if(s.matches("-mode=(.*)")) {
					String temp2[] = s.split("=");
					mode = Mode.valueOf(temp2[1]);
				}
				else if(s.matches("-dim=(.*)")) {
					String temp3[] = s.split("=");
					String t[] = temp3[1].split(",");
					columns = Integer.valueOf(t[0]);
					lines = Integer.valueOf(t[1]);
				}
				else if(s.matches("-mines=(.*)")) {
					String temp5[] = s.split("=");
					mines = Integer.valueOf(temp5[1]);
				}
			} catch(Exception e) {}
		
		if(mode == null && (columns != null || lines != null || mines != null))
			mode = Mode.CUSTOM;
		
		if(graphic)
			Game.startGraphic();
		else
			Game.startConsole();
	}
	
	private static void startConsole() {
		String str;
		
		// PARTENZA PROTETTA O MENO
		if(safe == null) {
			System.out.println("Vuoi la partenza protetta? (non troverai mine intorno alla prima cella scoperta)");
			do {
				System.out.print("[s/N] ");
				
				str = Input.readString();
			} while(!str.matches("(s|S|N|n)"));
			
			safe = str.matches("(s|S)");
		}
		
		// DIFFICOLTA DEL GIOCO
		if(mode == null) {
			System.out.println("A che difficolta' vuoi giocare? ");
			do {
				System.out.println("1 - Facile.");
				System.out.println("2 - Medio.");
				System.out.println("3 - Difficile.");
				System.out.println("4 - Personalizzata.");
				
				str = Input.readString();
			} while(!str.matches("(1|2|3|4)"));
			
			if(str.matches("1"))
				mode = Mode.EASY;
			else if(str.matches("2"))
				mode = Mode.MEDIUM;
			else if(str.matches("3"))
				mode = Mode.HARD;
			else if(str.matches("4"))
				mode = Mode.CUSTOM;
		}
		
		// DIFFICOLTA PERSONALIZATA
		if(mode == Mode.CUSTOM) do {
			
			if(columns == null)
				columns = getNum("Inserisci quante colonne deve avere il campo: ", "Colonne = ", 1, Field.MAX_VAL);
			if(lines == null)
				lines = getNum("Inserisci quante righe deve avere il campo; ", "Righe = ", 1, Field.MAX_VAL);
			if(mines == null)
				mines = getNum("Inserisci le mine presenti nel campo: ", "Mine = ", 1, Field.MAX_VAL);
			
			try {
				field = Decisor.getNewField(safe, columns, lines, mines);
			} catch(Exception e) {
				System.out.println(e.getMessage());
				mines = null;
			}
			
		} while(field == null);
		else
			field = Decisor.getNewField(safe, mode);
		
		// code game
		do {
			System.out.println(FieldView.toString(field));
			
			// coordinates used for interacting with user
			int x = getNum(null, "X = ", 0, Integer.MAX_VALUE);
			int y = getNum(null, "Y = ", 0, Integer.MAX_VALUE);;
			
			System.out.println("Vuoi Scoprire la cella(S),\nSettarla come pericolosa(P),\nO rimuoverla dallo stato di pericolosa(R)?");
			str = null;
			
			do {
				System.out.print("Inserisci scelta: ");
				
				str = Input.readString();
			} while(str.matches("(S|s|P|p|R|r)") == false);
			
			try {
				if(str.matches("(s|S)"))
					field.cellUncover(x, y);
				else if(str.matches("(P|p)"))
					field.cellSetDangerous(x, y);
				else
					field.cellSetNotDangerous(x, y);
			} catch (OutOfBoundsException e) {
				System.out.println("Inserisci delle coordinate valide...");
			}
		} while(!Decisor.isALoose(field) && !Decisor.isAWin(field));
		
		Decisor.uncoverAllMinesCells(field);
		Decisor.uncoverAllDangerousCells(field);
		
		System.out.println(FieldView.toString(field));
		
		if(Decisor.isALoose(field))
			System.out.println("Hai perso. SCARSO AHAHAHHA!");
		else
			System.out.println("Hai vinto.");
	}
	
	private static void startGraphic() {
		MenuFrame menuFr = new MenuFrame();
		menuFr.setVisible(true);
	}
	
	
	private static int getNum(String messaggio, String campo, int min, int max) {
		if(messaggio != null)
			System.out.println(messaggio);
		
		int num = min - 1;
		do {
			try {
				num = Input.readInt(campo);
				if(num<min) {
					System.out.println("Inserire un numero che sia almeno " + min);
					num = -1;
				}
				if(num>max) {
					System.out.println("Inserire un numero minore di " + max);
					num = -1;
				}
			} catch(java.lang.NumberFormatException e) {
				System.out.println("Inserire un numero.");
			}
		} while(num < min);
		
		return num;
	}
}
