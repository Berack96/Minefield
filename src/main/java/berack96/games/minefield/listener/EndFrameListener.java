package berack96.games.minefield.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import berack96.games.minefield.frame.EndFrame;
import berack96.games.minefield.frame.GameFrame;
import berack96.games.minefield.frame.MenuFrame;

/**
 * Classe che contolla la finestra {@link EndFrame} e in base a che bottone<br>
 * viene premuto viene effettuata una scelta.
 * 
 * @author Jack
 *
 */
public class EndFrameListener implements ActionListener {
	
	private EndFrame end;
	private GameFrame game;
	private MenuFrame menu;
	
	public EndFrameListener(EndFrame end, GameFrame game, MenuFrame menu)
	{
		this.end = end;
		this.game = game;
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton) e.getSource();
		String s = button.getText();
		
		if(s.matches("EXIT"))
		{
			System.exit(0);
		}
		else if(s.matches("RETRY"))
		{
			game.retryGame();
		}
		else if(s.matches("MENU"))
		{
			game.setVisible(false);
			
			menu.resetVariablePanel();
			menu.setVisible(true);
			
			game.dispose();
		}
		
		end.setVisible(false);
		end.dispose();
	}
	
}
