package berack96.games.minefield.object;

import berack96.games.minefield.runexception.TooHighValueException;

/**
 * Classe che crea un campo di celle {@link Cell}.<br>
 * Quando l'utente scopre la prima cella, essa NON puo essere una mina,<br>
 * e non puo avere mine intorno.
 * 
 * @author Jack
 *
 */
public class FieldSafe extends Field{
	
	private final int numMines;
	
	/**
	 * Crea il campo vouto. Esso si riempira' non appena verra'<br>
	 * scoperta la prima cella con al funzione {@link #cellUncover(int, int)}
	 * @param columns Una delle grandezze del campo.
	 * 
	 * @param lines Una delle grandezze del campo
	 * @param numMines Quante mine deve contenere il campo
	 * @throws IllegalArgumentException nel caso in cui si inseriscano parametri sbagliati
	 * @throws TooHighValueException nel caso in cui i parametri siano maggiori di 100
	 */
	public FieldSafe(int columns, int lines, int numMines) {
		super(columns, lines);
		
		if(numMines<=0)
			throw new java.lang.IllegalArgumentException("the mines must be greater than 0.");
		if(numMines>=columns*lines-8)
			throw new java.lang.IllegalArgumentException("the mines must be less than the size of the whole field -8 (for the safe start).");
		
		this.numMines = numMines;
	}

	/**
	 * Genera il campo non creando mine vicino alla cella indicata dalle due coordinate  
	 */
	@Override
	protected void generateField(int x, int y) {
		
		isGenerated = true;
		
		while(this.getNumMines()<numMines)	// insert mines
		{
			int randX = (int)(Math.random()*lines);
			int randY = (int)(Math.random()*columns);
			
			if((randX>=x-1 && randX<=x+1 && randY>=y-1 && randY<=y+1) == false)
				this.insertMine(randX, randY);
		}
		
		for(int i=0; i<lines; i++)
			for(int j=0; j<columns; j++)
				this.updateNumNearMines(i, j);	// update neighbor
	}
	
}
