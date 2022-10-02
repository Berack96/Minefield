package berack96.games.minefield.object;

import java.util.Observable;

/**
 * Classe che serve a creare una cella per il campo.<br>
 * Tramite le funzioni principali della cella si<br>
 * possono sapere 4 cose:<br>
 * - il numero di mine vicine {@link #nearMine}<br>
 * - se la cella e' una mina {@link #isMine()}<br>
 * - lo stato della cella {@link #getStatus()}<br>
 * 
 * @author Jack
 * 
 */
public class Cell extends Observable {
	
	/**
	 * Indica le mine che si trovano vicino a questa cella
	 */
	public int nearMine;
	
	private boolean isMine;
	private boolean uncovered;
	private CellStatus status;
	
	/**
	 * Costruttore: serve creare una nuova cella coperta con 0 mine intorno.
	 */
	public Cell() {
		nearMine = 0;
		isMine = false;
		uncovered = false;
		status = CellStatus.COVERED;
	}
	
	/**
	 * Serve a mettere la cella nello stato di "scoperta"<br>
	 * Una volta fatto la cella non potra' esser piu cambiata.<br>
	 * Essa potra' avere i seguenti valori:<br>
	 * - EXPLODED<br>
	 * - UNCOVERED<br>
	 * - GOTRIGHT<br>
	 * - GOTWRONG<br>
	 * <br>
	 * Per altro vedere {@link CellStatus}
	 */
	public void uncover() {
		if(!this.uncovered) {
			if(isMine) {
				if(status == CellStatus.DANGEROUS)
					status = CellStatus.GOTRIGHT;
				else
					status = CellStatus.EXPLODED;
			}
			else {
				if(status == CellStatus.DANGEROUS)
					status = CellStatus.GOTWRONG;
				else
					status = CellStatus.UNCOVERED;
			}
			uncovered = true;
			
			setChanged();
			notifyObservers();
			clearChanged();
		}
	}
	
	/**
	 * Serve ad avere lo stato della cella.<br>
	 * Vedi {@link CellStatus} per i possibili valori.
	 * 
	 * @return CellStatus
	 */
	public CellStatus getStatus() {
		return status;
	}
	
	/**
	 * Setta la cella come una mina solamente se<br>
	 * quest'ultima non e' stata scoperta.<br>
	 * Questa azione e' irreversibile.
	 */
	public void setMine() {
		if(!uncovered)
			isMine = true;
	}
	
	/**
	 * Setta la cella nello stato di {@link CellStatus}.DANGEROUS<br>
	 * Se La funzione {@link #uncover()} e' gia stata chiamata<br>
	 * la cella non verra' settata come pericolosa.
	 */
	public void setDangerous() {
		if(status == CellStatus.COVERED) {
			status = CellStatus.DANGEROUS;
			
			setChanged();
			notifyObservers();
			clearChanged();
		}
	}
	
	/**
	 * Toglie la cella dallo stato {@link CellStatus}.DANGEROUS<br>
	 * e la mette come COVERED.<br>
	 * Se la cella non e' in stato DANGEROUS, la funzione<br>
	 * non fa nulla.
	 */
	public void setNotDangerous() {
		if(status == CellStatus.DANGEROUS) {
			status = CellStatus.COVERED;
			
			setChanged();
			notifyObservers();
			clearChanged();
		}
	}
	
	/**
	 * Dice se la cella e' una mina o meno.
	 * 
	 * @return true se lo e'
	 */
	public boolean isMine() {
		return isMine;
	}
	
	/**
	 * Dice se la cella e' gia stata scoperta.<br>
	 * AKA se la funzione {@link #uncover()} e' gia stata chiamata.
	 * 
	 * @return true se non e' piu {@link CellStatus#COVERED}
	 */
	public boolean isUncovered() {
		return uncovered;
	}
}
