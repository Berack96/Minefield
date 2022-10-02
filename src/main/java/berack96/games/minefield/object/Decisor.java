package berack96.games.minefield.object;

import berack96.games.minefield.Mode;

/**
 * Classe utile ad automatizzare alcuni meccanismi di gioco, come per esempio<br>
 * {@link #getNewField(boolean, Mode)} che crea un nuovo campo data la modalita'.
 * 
 * @author Jack
 *
 */
public class Decisor {
	
	/**
	 * Crea un nuovo campo di gioco data la modalita' {@link Mode}.
	 * 
	 * @param safe Se si vuole che il campo sia {@link FieldSafe}
	 * @param mode La modalita' di gioco
	 * @return Un nuovo campo
	 */
	public static Field getNewField(boolean safe, Mode mode) {
		if(mode == Mode.EASY)
			return getNewFieldEasy(safe);
		else if(mode == Mode.MEDIUM)
			return getNewFieldMedium(safe);
		else if(mode == Mode.HARD)
			return getNewFieldHard(safe);
		else
			throw new java.lang.IllegalArgumentException("mode must be Easy, Medium or Hard");
	}
	
	/**
	 * Serve a creare un nuovo campo dati i parametri richiesti.
	 * 
	 * @param safe Se si vuole che il campo sia {@link FieldSafe}
	 * @param columns Una grandezza del campo (larghezza)
	 * @param lines Una grandezza del campo (altezza)
	 * @param mines Quante mine ci sono nel campo
	 * @return Un nuovo campo
	 */
	public static Field getNewField(boolean safe, int columns, int lines, int mines) {
		if(safe)
			return new FieldSafe(columns, lines, mines);
		return new FieldNoSafe(columns, lines, mines);
	}
	
	/**
	 * Serve a creare un campo in modalita' facile
	 * 
	 * @param safe Se si vuole che il campo sia {@link FieldSafe}
	 * @return Un nuovo campo
	 */
	public static Field getNewFieldEasy(boolean safe) {
		return getNewField(safe, 8, 8, 10);
	}
	
	/**
	 * Serve a creare un campo in modalita' media
	 * 
	 * @param safe Se si vuole che il campo sia {@link FieldSafe}
	 * @return Un nuovo campo
	 */
	public static Field getNewFieldMedium(boolean safe) {
		return getNewField(safe, 16, 16, 40);
	}
	
	/**
	 * Serve a creare un campo in modalita' difficile
	 * 
	 * @param safe Se si vuole che il campo sia {@link FieldSafe}
	 * @return Un nuovo campo
	 */
	public static Field getNewFieldHard(boolean safe) {
		return getNewField(safe, 32, 16, 80);
	}
	
	/**
	 * Indica se nel campo inserito si e' riusciti a vincere.
	 * 
	 * @param field Il campo da controllare
	 * @return true se si ha vinto
	 */
	public static boolean isAWin(Field field) {
		// Basic
		if(!field.isGenerated)
			return false;
		
		// First Condition
		if(field.getNumMines() == field.getNumCoveredCell())
			return true;
		
		// Second Condition
		int minesGot = 0;
		for(Cell cell : field)
			if(cell.isMine() && cell.getStatus() == CellStatus.DANGEROUS)
				minesGot++;
		
		if(minesGot == field.getNumMines())
			return true;
		
		// Else
		return false;
	}
	
	/**
	 * Indica se nel campo inserito si ha perso.
	 * 
	 * @param field Il campo da controllare
	 * @return true se si ha perso
	 */
	public static boolean isALoose(Field field) {
		for(Cell cell : field)
			if(cell.isUncovered() && cell.isMine())
				return true;
		return false;
	}
	
	/**
	 * Scopre tutte le celle del campo.
	 * 
	 * @param field Il campo da scoprire
	 */
	public static void uncoverAllCells(Field field) {
		for(Cell cell : field)
			cell.uncover();
	}
	
	/**
	 * Scopre tutte celle pericolose del campo.
	 * 
	 * @param field Il campo da scoprire
	 */
	public static void uncoverAllDangerousCells(Field field) {
		for(Cell cell : field)
			if(cell.getStatus() == CellStatus.DANGEROUS)
				cell.uncover();
	}
	
	/**
	 * Scopre tutte le mine del campo.
	 * 
	 * @param field Il campo da scoprire
	 */
	public static void uncoverAllMinesCells(Field field) {
		for(Cell cell : field)
			if(cell.isMine())
				cell.uncover();
	}
	
	/**
	 * Scopre tutte le celle che non sono mine del campo.
	 * 
	 * @param field Il campo da scoprire
	 */
	public static void uncoverAllNotMinesCells(Field field) {
		for(Cell cell : field)
			if(!cell.isMine())
				cell.uncover();
	}
}
