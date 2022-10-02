package berack96.games.minefield.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import berack96.games.minefield.Mode;
import berack96.games.minefield.frame.MenuFrame;
import berack96.games.minefield.frame.strings.StringError;
import berack96.games.minefield.object.Decisor;
import berack96.games.minefield.object.Field;
import berack96.games.minefield.runexception.TooHighValueException;

/**
 * Classe che contolla la finestra {@link MenuFrame} e in base a che bottone<br>
 * viene premuto o a che pulsante Radio viene scelto, viene effettuata una scelta.
 * 
 * @author Jack
 *
 */
public class MenuFrameListener implements ActionListener {
	
	private MenuFrame frame;
	
	public MenuFrameListener(MenuFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		frame.updateView();
		if(frame.getModeValue() != Mode.CUSTOM)
			frame.setError("");
		
		int columns = 0,
			lines = 0,
			mines = 0;
		
		try{
			JButton button = (JButton) arg0.getSource();
			
			if(button.getText().matches("NEXT"))
			{
				Field field;
				
				if(frame.getModeValue() == Mode.CUSTOM)
				{
					columns = Integer.parseInt(this.frame.getStringColumnsTextPanel());
					lines = Integer.parseInt(this.frame.getStringLinesTextPanel());
					mines = Integer.parseInt(this.frame.getStringMinesTextPanel());
						
					field = Decisor.getNewField(frame.getSafeValue(), columns, lines, mines);
				}
				else
					field = Decisor.getNewField(frame.getSafeValue(), frame.getModeValue());
				
				frame.startNewGameFrame(field);
			}
			
		}
		catch(ClassCastException e)	// not a JButton generated the action (in this case a JRadioButton have)
		{
			// DO NOTHING
		}
		catch(NumberFormatException e)	// not a number is inserted in the text
		{
			frame.setError(StringError.noValue);
		}
		catch(IllegalArgumentException e)	// the numbers inserted is not valid
		{
			if(columns<=0 || lines<=0)
				frame.setError(StringError.noPositiveValue);
			else if(mines<=0)
				frame.setError(StringError.minesLessThanZero);
			else if(frame.getSafeValue() && mines>=lines*columns-8)
				frame.setError(StringError.minesGreaterThanFieldSafe);
			else if(mines>= lines*columns)
				frame.setError(StringError.minesGreaterThanField);
		}
		catch(TooHighValueException e)
		{
			frame.setError(StringError.valueTooHigh);
		}
	}

}
