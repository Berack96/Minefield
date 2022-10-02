package berack96.games.minefield;

import berack96.games.minefield.object.Decisor;

/**
 * Serve a scegliere una delle modalita' di gioco senza inserire ogni singolo parametro.<br>
 * Insomma serve a velocizzare la partenza del gioco.<br>
 * Da usare con {@link Decisor}
 * 
 * @author Jack
 *
 */
public enum Mode {
	
	/**
	 * Modalita' di gioco facile
	 */
	EASY,
	
	/**
	 * Modalita' di gioco media
	 */
	MEDIUM,
	
	/**
	 * Modalita' di gioco difficile
	 */
	HARD,
	
	/**
	 * Modalita' di gioco personalizzata
	 */
	CUSTOM
}
