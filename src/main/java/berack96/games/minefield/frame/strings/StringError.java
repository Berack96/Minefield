package berack96.games.minefield.frame.strings;

public class StringError {
	
	public static final String noValue = "Inserisci dei numeri nei campi indicati";
	
	public static final String noPositiveValue = "Inserisci dei valori positivi<br>"
			+ "per la grandezza del campo";
	
	public static final String minesLessThanZero = "Inserisci dei valori positivi<br>"
			+ "per le mine";
	
	public static final String minesGreaterThanField = "Inserisci un valore minore per le mine<br>"
			+ "siccome non possono essere maggiori<br>"
			+ "della grandezza del campo";
	
	public static final String minesGreaterThanFieldSafe = "Inserisci un valore minore per le mine<br>"
			+ "siccome non possono essere maggiori<br>"
			+ "della grandezza del campo-8<br>"
			+ "(perche' la prima cella non<br>"
			+ "puo avere mine intorno)";
	
	public static final String valueTooHigh = "Inserisci un valore minore per le grandezze<br>"
			+ "siccome entrambe devono avere un valore<br>"
			+ "minore di 100";
}
