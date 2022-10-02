package berack96.games.minefield.object.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import berack96.games.minefield.object.Cell;
import berack96.games.minefield.object.CellStatus;

/**
 * Classe che serve a restituire graficamente (stringa o jlabel) la cella che si desidera.
 * 
 * @author Jack
 *
 */
public class CellView implements Observer {
	
	private final Cell cell;
	
	private String string;
	private JLabel label;
	
	/**
	 * Costruttore: la cella passata, sara' ora osservvata dall'istanza creata
	 * 
	 * @param cell La cella
	 */
	public CellView(Cell cell)
	{
		this.cell = cell;
		cell.addObserver(this);
		
		string = CellView.toString(cell);
		label = CellView.toJLabel(cell);
	}
	
	/**
	 * Ritorna la rappresentazione della cella sottoforma di stringa:<br>
	 * [ ] - {@link CellStatus#UNCOVERED} 0 mine vicine<br>
	 * [n] - {@link CellStatus#UNCOVERED} con "n" mine vicine<br>
	 * [D] - {@link CellStatus#DANGEROUS}<br>
	 * [*] - {@link CellStatus#EXPLODED}<br>
	 * [V] - {@link CellStatus#GOTRIGHT}<br>
	 * [X] - {@link CellStatus#GOTWRONG}<br>
	 * [#] - {@link CellStatus#COVERED}<br>
	 * 
	 * @return String
	 */
	public String toString()
	{
		return string;
	}
	
	/**
	 * Ritorna la rappresentazione della cella sottoforma di JLabel:<br>
	 * {@link CellStatus#UNCOVERED} bianco con eventuale numero<br>
	 * {@link CellStatus#DANGEROUS} con icona di una bandiera<br>
	 * {@link CellStatus#EXPLODED} con una icona di una mina<br>
	 * {@link CellStatus#GOTRIGHT} con una icona di una bandierina cerchiata di verde<br>
	 * {@link CellStatus#GOTWRONG} - con una icona di una bandierina crociata di rosso<br>
	 * {@link CellStatus#COVERED} - grigio chiaro<br>
	 * 
	 * @return JLabel
	 */
	public JLabel toJLabel()
	{
		return label;
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		CellStatus status = cell.getStatus();
		
		if(status == CellStatus.UNCOVERED)
		{
			label.setBackground(Color.WHITE);
			label.setIcon(null);
			
			if(cell.nearMine == 0)
				string = "[ ]";
			else
			{
				string = "["+cell.nearMine+"]";
				switch(cell.nearMine)
				{
					case 1:
						label.setIcon(CellIcons.one);
					break;
					case 2:
						label.setIcon(CellIcons.two);
					break;
					case 3:
						label.setIcon(CellIcons.three);
					break;
					case 4:
						label.setIcon(CellIcons.four);
					break;
					case 5:
						label.setIcon(CellIcons.five);
					break;
					case 6:
						label.setIcon(CellIcons.six);
					break;
					case 7:
						label.setIcon(CellIcons.seven);
					break;
					case 8:
						label.setIcon(CellIcons.eight);
					break;
				}
			}
		}
		else if(status == CellStatus.DANGEROUS)
		{
			string = "[D]";
			label.setBackground(Color.LIGHT_GRAY);
			label.setIcon(CellIcons.flag);
		}
		else if(status == CellStatus.EXPLODED)
		{
			string = "[*]";
			label.setBackground(Color.LIGHT_GRAY);
			label.setIcon(CellIcons.mine);
		}
		else if(status == CellStatus.GOTRIGHT)
		{
			string = "[V]";
			label.setBackground(Color.LIGHT_GRAY);
			label.setIcon(CellIcons.flagRight);
		}
		else if(status == CellStatus.GOTWRONG)
		{
			string = "[X]";
			label.setBackground(Color.LIGHT_GRAY);
			label.setIcon(CellIcons.flagWrong);
		}
		else
		{
			string = "[#]";
			label.setBackground(Color.LIGHT_GRAY);
			label.setIcon(null);
		}
	}
	
	/**
	 * Metodo statico che serve, passata una cella, a trasformarla in stringa<br>
	 * secondo {@link #toString()}.
	 * 
	 * @param cell La cella da vedere
	 * @return String
	 */
	public static String toString(Cell cell)
	{
		CellStatus status = cell.getStatus();
		
		if(status == CellStatus.UNCOVERED)
		{
			if(cell.nearMine == 0)
				return "[ ]";
			else
				return "["+cell.nearMine+"]";
		}
		else if(status == CellStatus.DANGEROUS)
			return "[D]";
		else if(status == CellStatus.EXPLODED)
			return "[*]";
		else if(status == CellStatus.GOTRIGHT)
			return "[V]";
		else
			return "[#]";
	}
	
	/**
	 * Metodo statico che serve, passata una cella, a trasformarla in jpanel<br>
	 * secondo {@link #toJLabel()}.
	 * 
	 * @param cell La cella da vedere
	 * @return JLabel
	 */
	public static JLabel toJLabel(Cell cell)
	{
		CellStatus status = cell.getStatus();
		JLabel label = new JLabel();
		
		label.setOpaque(true);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setPreferredSize(new Dimension(25, 25));
		label.setSize(label.getPreferredSize());
		label.setVisible(true);
		
		if(status == CellStatus.UNCOVERED)
		{
			label.setBackground(Color.WHITE);
			if(cell.nearMine != 0)
				label.setText("["+cell.nearMine+"]");
		}
		else if(status == CellStatus.DANGEROUS)
			label.setBackground(Color.BLACK);
		else if(status == CellStatus.EXPLODED)
			label.setBackground(Color.RED);
		else if(status == CellStatus.GOTRIGHT)
			label.setBackground(Color.GREEN);
		else
			label.setBackground(Color.LIGHT_GRAY);
		
		return label;
	}
}
