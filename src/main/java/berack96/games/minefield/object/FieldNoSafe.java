package berack96.games.minefield.object;

import berack96.games.minefield.runexception.TooHighValueException;

/**
 * Classe che crea un campo di celle {@link Cell}.<br>
 * Quando l'utente scopre la prima cella, essa puo essere anche una mina.
 * 
 * @author Jack
 *
 */
public class FieldNoSafe extends Field{
	
	private final int numMines;
	
	/**
	 * Costruisce il campo, contenente le mine e quante mine ogni cella ha intorno.
	 * 
	 * @param columns Una delle grandezze del campo (lunghezza)
	 * @param lines Una delle grandezze del campo (altezza)
	 * @param numMines Quante mine deve contenere il campo
	 * @throws IllegalArgumentException nel caso in cui si inseriscano parametri sbagliati
	 * @throws TooHighValueException nel caso in cui i parametri siano maggiori di 100
	 */
	public FieldNoSafe(int columns, int lines, int numMines)
	{
		super(columns, lines);
		
		if(numMines<=0)
			throw new java.lang.IllegalArgumentException("the mines must be greater than 0.");
		if(numMines>=columns*lines)
			throw new java.lang.IllegalArgumentException("the mines must be less than the size of the whole field.");
		
		this.numMines = numMines;
		generateField(0, 0);	// use of this function here because don't need safe start
	}
	
	/**
	 * Genera il campo senza considerare le due coordinate in entrata 
	 */
	@Override
	protected void generateField(int x, int y)
	{
		
		// i will not use the argument, don't need it
		
		super.isGenerated = true;
		
		while(getNumMines()<numMines)	// insert mines
		{
			int randX = (int)(Math.random()*lines);
			int randY = (int)(Math.random()*columns);
			
			insertMine(randX, randY);
		}
		
		for(int i=0; i<lines; i++)	// update neighbor
			for(int j=0; j<columns; j++)
				updateNumNearMines(i, j);
	}
}
