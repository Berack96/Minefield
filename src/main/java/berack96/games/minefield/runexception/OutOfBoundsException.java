package berack96.games.minefield.runexception;

/**
 * Lanciata perche' le coordinate indicano una area al di fuori del campo.
 * 
 * @author Jack
 *
 */
public class OutOfBoundsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public OutOfBoundsException()
	{
		super();
	}

	public OutOfBoundsException(String arg0)
	{
		super(arg0);
	}

}
