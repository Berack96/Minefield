package berack96.games.minefield.object;

/**
 * Serve a comunicare lo stato della cella.<br>
 * Essa puo' assumere questi stati in base al<br>
 * fatto di essere o meno stata scoperrta.
 * 
 * @author Jack
 *
 */
public enum CellStatus {
	/**
	 * Se la cella non e' ancora stata scoperta.
	 */
	COVERED,
	
	/**
	 * Se la cella e' marchiata pericolosa.
	 */
	DANGEROUS,
	
	/**
	 * Dopo aver scoperto la cella: se essa e' una mina.
	 */
	EXPLODED,
	
	/**
	 * Dopo aver scoperto la cella, se essa non e' una mina
	 */
	UNCOVERED,
	
	/**
	 * Dopo aver scoperto la cella: se essa, marchiata precedentemente come pericolosa, e' effettivamente una mina.
	 */
	GOTRIGHT,
	
	/**
	 * Dopo aver scoperto la cella: se essa, marchiata precedentemente cvome pericolosa, non e' una mina.
	 */
	GOTWRONG
}
