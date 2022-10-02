package berack96.games.minefield.object.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import berack96.games.minefield.listener.CellInFieldListener;
import berack96.games.minefield.object.Field;

/**
 * Classe che mostra il campo da gioco in modo grafico (tramite stringhe o jpanel).
 * 
 * @author Jack
 *
 */
public class FieldView {
	
	private final Field field;
	
	private String string;
	private JPanel panel;
	
	/**
	 * Costruttore in cui si inserisce il campo e un eventuale listener<br>
	 * su ogni cella del campo
	 * 
	 * @param field Il campo da vedere
	 * @param listener il controllore delle celle
	 */
	public FieldView(Field field, CellInFieldListener listener)
	{
		this.field = field;
		
		string = toString(field);
		panel = toJPanel(field, listener);
	}
	
	/**
	 * Restituisce il campo sottoforma di stringa.<br>
	 * Per valori celle vedi {@link CellView#toString()}.
	 * 
	 * @return String
	 */
	public String toString()
	{
		string = toString(field);
		return string;
	}
	
	/**
	 * Restituisce il campo sottoforma di jpanel.<br>
	 * Per valori celle vedi {@link CellView#toJLabel()}.
	 * 
	 * @return JPanel
	 */
	public JPanel toJPanel()
	{
		return panel;
	}
	
	/**
	 * Metodo statico che serve, passato un campo, a trasformarlo in stringa<br>
	 * secondo {@link #toString()}.
	 * 
	 * @param field il campo da vedere
	 * @return String
	 */
	public static String toString(Field field)
	{
		String string = new String();
		
		for(int i=0; i<field.lines; i++)
		{
			for(int j=0; j<field.columns; j++)
				string += CellView.toString(field.getCell(i, j));
			
			string += "\n";
		}
		
		return string;
	}
	
	/**
	 * Metodo statico che serve, passato un campo, a trasformarlo in jpanel<br>
	 * secondo {@link #toJPanel()}.
	 * 
	 * @param field il campo da vedere
	 * @param listener eventuale controller da applicare ad ogni cella
	 * @return JPanel
	 */
	public static JPanel toJPanel(Field field, CellInFieldListener listener)
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(field.lines, field.columns));
		panel.setVisible(true);
		
		for(int i=0; i<field.lines; i++)
			for(int j=0; j<field.columns; j++)
			{
				JLabel cell = new CellView(field.getCell(i, j)).toJLabel();
				
				cell.setName(i+"-"+j);
				if(listener != null)
					cell.addMouseListener(listener);
				
				panel.add(cell);
			}
		
		Dimension dim = CellView.toJLabel(field.getCell(0, 0)).getPreferredSize();
		
		panel.setPreferredSize(new Dimension(dim.height*field.columns, dim.width*field.lines));
		panel.setSize(panel.getPreferredSize());
		
		return panel;
	}
	
}
