package berack96.games.minefield.listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import berack96.games.minefield.frame.GameFrame;
import berack96.games.minefield.object.Decisor;
import berack96.games.minefield.object.Field;

//TODO remove log lines
/**
 * Classe che serve a controllare un {@link Field} generato in un {@link GameFrame}.<br>
 * Questa classe modifica la cella in base all'azione dell'utente tramite i movimenti<br>
 * e i pulsanti generati dal mouse.
 * 
 * @author Jack
 *
 */
public class CellInFieldListener implements MouseListener {
	
	private GameFrame frame;
	private Field field;
	
	public CellInFieldListener(GameFrame frame, Field field)
	{
		this.frame = frame;
		this.field = field;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		if(Decisor.isALoose(field) == false && Decisor.isAWin(field) == false)
		{
			JLabel label = (JLabel) arg0.getSource();
			String[] str = label.getName().split("-");
			
			int x = Integer.parseInt(str[0]);
			int y = Integer.parseInt(str[1]);
			
			System.out.println("User clicked on cell ("+label.getName()+")");	// log line
			
			if(field.cellIsUncovered(x, y) == false)
			{
				if(arg0.getButton() == MouseEvent.BUTTON1)
					field.cellUncover(x, y);
				else if(arg0.getButton() == MouseEvent.BUTTON3)
				{
					if(field.cellIsDangerous(x, y) == false)
						field.cellSetDangerous(x, y);
					else
						field.cellSetNotDangerous(x, y);
				}
				
				if(Decisor.isALoose(field))
				{
					frame.launchEndFrame("HAI PERSO");
					System.out.println("---Game Lost---");	// log line
				}
				else if(Decisor.isAWin(field))
				{
					frame.launchEndFrame("HAI VINTO");
					System.out.println("---Game Won---");	// log line
				}
				
				frame.updateView();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		JLabel label = (JLabel) arg0.getSource();
		String[] str = label.getName().split("-");
		
		int x = Integer.parseInt(str[0]);
		int y = Integer.parseInt(str[1]);
		
		if(field.cellIsUncovered(x, y) == false && field.cellIsDangerous(x, y) == false)
			label.setBackground(Color.GRAY);
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		JLabel label = (JLabel) arg0.getSource();
		String[] str = label.getName().split("-");
		
		int x = Integer.parseInt(str[0]);
		int y = Integer.parseInt(str[1]);
		
		if(field.cellIsUncovered(x, y) == false && field.cellIsDangerous(x, y) == false)
			label.setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// do nothing
	}

}
