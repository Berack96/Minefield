package berack96.games.minefield.runexception;

/**
 * Lanciata perche' il valore inserito e' torppo alto
 * 
 * @author Jack
 *
 */
public class TooHighValueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TooHighValueException()
	{
		super();
	}

	public TooHighValueException(String arg0)
	{
		super(arg0);
	}

}
