package berack96.games.minefield.object;

import java.util.Iterator;

import berack96.games.minefield.runexception.OutOfBoundsException;
import berack96.games.minefield.runexception.TooHighValueException;

/**
 * Classe astratta in cui manca la funzione che genera il campo.<br>
 * Essa contiene un tot numero di celle della classe {@link Cell}<br>
 * 
 * @author Jack
 *
 */
public abstract class Field implements Iterable<Cell> {
	
	/**
	 * Valore massimo del capo
	 */
	public static final int MAX_VAL = 100;
	
	/**
	 * Una delle grandezze del campo
	 */
	public final int lines;
	
	/**
	 * Una delle grandezze del campo
	 */
	public final int columns;
	
	/**
	 * Valore che inizialmente e' settato a <b>false</b> e che<br>
	 * se messo a <b>true</b>, non fara' piu richiamare la funzione<br>
	 * {@link #generateField(int, int)} quando la funzione<br>
	 * {@link #cellUncover(int, int)} e' usata.
	 */
	protected boolean isGenerated;
	
	private int numMines;
	private Cell [][]matrix;
	
	private int numCoveredCell;
	private int numDangerousCell;
	
	/**
	 * Costruttore che crea un campo senza mine o altro.<br>
	 * Genera solamente la base per futuri cambiamenti in esso.
	 * 
	 * @param columns Una delle grandezze del campo (larghezza)
	 * @param lines Una delle grandezze del campo (altezza)
	 * @throws IllegalArgumentException nel caso in cui si inseriscano parametri sbagliati
	 * @throws TooHighValueException nel caso in cui i parametri siano maggiori di 100
	 */
	public Field(int columns, int lines) {
		if(lines<0 || columns<0)
			throw new java.lang.IllegalArgumentException("columns and lines must be greater than 0.");
		if(lines>MAX_VAL || columns>MAX_VAL)
			throw new TooHighValueException("columns and lines must be less than 100.");
		
		this.isGenerated = false;
		
		this.lines = lines;
		this.columns = columns;
		
		numMines = 0;
		numDangerousCell = 0;
		numCoveredCell = lines*columns;
		
		matrix = new Cell[lines][columns];
		
		for(int i=0; i<lines; i++)
			for(int j=0; j<columns; j++)
				this.matrix[i][j] = new Cell();
	}
	
	/**
	 * Scopre la cella dalle coordinate selezionate.<br>
	 * Se la cella e' in uno stato di {@link CellStatus}.DANGEROUS,<br>
	 * essa non verra' scoperta e la funzione terminara'.<br>
	 * Se il campo non e' ancora stato creato, viene automaticamente creato.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public void cellUncover(int x, int y) {
		checkCoordinates(x, y);
		
		if(!isGenerated)
			generateField(x, y);
		
		if(!cellIsUncovered(x, y) && matrix[x][y].getStatus() != CellStatus.DANGEROUS) {
			matrix[x][y].uncover();
			numCoveredCell--;
			if(matrix[x][y].nearMine == 0 && !matrix[x][y].isMine())
				cellUncoverNeighbors(x, y);
		}
	}
	
	/**
	 * Scopre la cella dalle coordinate selezionate.<br>
	 * Se la cella e' in uno stato di {@link CellStatus}.DANGEROUS,<br>
	 * essa <b>VERRA'</b> comunque scoperta.<br>
	 * Se il campo non e' ancora stato creato, viene automaticamente creato.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public void cellUncoverIngnoringDangerousState(int x, int y) {
		checkCoordinates(x, y);
		
		if(!isGenerated)
			generateField(x, y);
		
		if(!cellIsUncovered(x, y)) {
			matrix[x][y].uncover();
			numCoveredCell--;
			if(matrix[x][y].nearMine == 0 && matrix[x][y].isMine() == false)
				cellUncoverNeighbors(x, y);
		}
	}
	
	/**
	 * Setta una determinata cella come {@link CellStatus}.DANGEROUS<br>
	 * Se la cella e' gia stata scoperta non succedera' nulla.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public void cellSetDangerous(int x, int y) {
		checkCoordinates(x, y);
		
		if(numMines != numDangerousCell && matrix[x][y].getStatus() == CellStatus.COVERED) {
			matrix[x][y].setDangerous();
			numDangerousCell++;
		}
	}
	
	/**
	 * Toglie alla cella lo status di {@link CellStatus}.DANGEROUS<br>
	 * Se la cella non e' in stato di DANGEROUS, la funzione non fa nulla.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public void cellSetNotDangerous(int x, int y) {
		checkCoordinates(x, y);
		
		if(matrix[x][y].getStatus() == CellStatus.DANGEROUS) {
			matrix[x][y].setNotDangerous();
			numDangerousCell--;
		}
	}
	
	/**
	 * Dice se la cella e' stata scoperta o meno.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return true se e' stata scoperta
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public boolean cellIsUncovered(int x, int y) {
		checkCoordinates(x, y);
		return matrix[x][y].isUncovered();
	}
	
	/**
	 * Indica se la cella e' nello stato pericoloso.<br>
	 * Per info: {@link CellStatus}.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return true se lo e'
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public boolean cellIsDangerous(int x, int y) {
		return matrix[x][y].getStatus() == CellStatus.DANGEROUS;
	}
	
	/**
	 * Indica se la cella e' esplosa.<br>
	 * Per info: {@link CellStatus}.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return true se lo e'
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public boolean cellIsExploded(int x, int y) {
		return matrix[x][y].getStatus() == CellStatus.EXPLODED;
	}
	
	/**
	 * Indica se la cella e' stata marchiata come pericolosa,<br>
	 * e alla fine del gioco e' risultata essere una mina.<br>
	 * Per info: {@link CellStatus}.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return true se lo e'
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public boolean cellIsGotRight(int x, int y) {
		return matrix[x][y].getStatus() == CellStatus.GOTRIGHT;
	}
	
	/**
	 * Indica se la cella e' una mina o meno.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return true se e' una mina
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public boolean cellIsMine(int x, int y) {
		checkCoordinates(x, y);
		return matrix[x][y].isMine();
	}
	
	/**
	 * Serve ad avere lo stato della cella.<br>
	 * Vedi {@link CellStatus} per i possibili valori.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return lo stato della cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public CellStatus cellGetStatus(int x, int y) {
		checkCoordinates(x, y);
		return matrix[x][y].getStatus();
	}
	
	/**
	 * Indica il numero di mine vicine alla cella richiesta.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return il numero di mine vicine alla cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public int cellGetNumNearMines(int x, int y) {
		checkCoordinates(x, y);
		return matrix[x][y].nearMine;
	}
	
	/**
	 * @return il numero delle mine che il campo contiene
	 */
	public int getNumMines() {
		return numMines;
	}
	
	/**
	 * @return il numero di celle ancora da scoprire
	 */
	public int getNumCoveredCell() {
		return numCoveredCell;
	}
	
	/**
	 * @return il numero delle celle gia scoperto
	 */
	public int getNumUncoveredCell() {
		return ( lines * columns ) - numCoveredCell;
	}
	
	/**
	 * @return il numero delle celle segnate pericolose
	 */
	public int getNumDagerousCell() {
		return numDangerousCell;
	}
	
	/**
	 * Restituisce la cella desiderata. Restituendo la cella<br>
	 * questa funzione puo' esser considerata un modo per<br>
	 * rendere le cose piu' facili.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return la cella desiderata
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	public Cell getCell(int x, int y) {
		checkCoordinates(x, y);
		return matrix[x][y];
	}
	
	/**
	 * Funzione che inserisce nel campo gia creato mine e numeri.<br>
	 * La funzione <b>DEVE</b> aggiornare il campo {@link #isGenerated},<br>
	 * in modo che sia generato una sola volta il campo.<br>
	 * Per creare il campo si possono usare le funzioni<br>
	 * {@link #insertMine(int, int)} e {@link #updateNumNearMines(int, int)} gia fornite.<br>
	 * Nel caso non si usasse questa funzione nel costruttore,<br>
	 * essa viene richiamata nella funzione {@link #cellUncover(int, int)}.<br>
	 * Il campo isGenerated evita che venga ripetuta l'operazione piu' volte,<br>
	 * quindi e' necessario che venga aggiornata in questa funzione.<br>
	 * Le coordinate sono fornite per passare, per esempio,<br>
	 * il punto della cella da cui l'utente ha iniziato a giocare.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 */
	protected abstract void generateField(int x, int y);
	
	/**
	 * Marchia la cella come mina se non lo e' gia<br>
	 * Aaggiorna inoltre quante mine ci sono nel campo<br>
	 * reperibili tramite {@link #getNumMines()}.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return true se la mina e' stata piazzata, false se la mina era gia presente
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	protected boolean insertMine(int x, int y) {
		checkCoordinates(x, y);
		
		if(!matrix[x][y].isMine()) {
			matrix[x][y].setMine();
			numMines++;
			return true;
		}
		return false;
	}
	
	/**
	 * Aggiorna il numero di mine che si trovano vicino alla cella richiesta.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @return Il numero di mine che la cella ha intorno
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	protected int updateNumNearMines(int x, int y) {
		checkCoordinates(x, y);
		
		for(int i=x-1; i<=x+1; i++)
			for(int j=y-1; j<=y+1; j++) try {
				checkCoordinates(i, j);
				
				if(matrix[i][j].isMine() && (i!=x || j!=y))
					matrix[x][y].nearMine++;
			} catch(OutOfBoundsException e) { /* do nothing and go on */ }
		
		return matrix[x][y].nearMine;
	}
	
	/**
	 * Funzione che serve a controllare se le coordinate sono consone al campo.<br>
	 * Nel caso non lo siano, il programma lancera' una eccezione.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 * @throws OutOfBoundsException nel caso in cui si inseriscano coordinate sbagliate
	 */
	private void checkCoordinates(int x, int y) {
		if(x<0 || y<0 || x>=lines || y>=columns)
			throw new OutOfBoundsException("coordinates must be inside the field x(0-"+lines+"), y(0-"+columns+").\tX = "+x+" Y = "+y);
	}
	
	/**
	 * Scopre i vicini della cella che si passa.
	 * 
	 * @param x Una coordinata della cella
	 * @param y Una coordinata della cella
	 */
	private void cellUncoverNeighbors(int x, int y) {
		for(int i=x-1; i<=x+1; i++)
			for(int j=y-1; j<=y+1; j++)
				if(!(i==x && j==y)) try {
					this.cellUncover(i, j);
				} catch(OutOfBoundsException e) { /* do nothing and go on */ }
	}
	
	@Override
	public Iterator<Cell> iterator() {
		return new FieldIter(this);
	}
	
	
	private class FieldIter implements Iterator<Cell> {
		
		private final Field field;
		private int x = 0;
		private int y = 0;
		
		private FieldIter(Field field) {
			this.field = field;
		}
		
		@Override
		public boolean hasNext() {
			return y + 1 < field.columns;
		}

		@Override
		public Cell next() {
			if(x + 1 == field.lines) {
				x = 0;
				y++;
			}
			else
				x++;
			return field.getCell(x, y);
		}
	}
}
