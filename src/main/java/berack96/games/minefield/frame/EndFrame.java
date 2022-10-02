package berack96.games.minefield.frame;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import berack96.games.minefield.listener.EndFrameListener;

/**
 * Classe che estende i {@link JPanel} in modo da creare una piccola finestra che appare alla fine del gioco.<br>
 * <br>
 * Essa contiene tre bottoni:<br>
 * Menu che fa tornare ad un {@link MenuFrame},<br>
 * Retry che fa riprovare la modalita' di gioco che era nel pannello {@link GameFrame},<br>
 * Exit che serve a chiudere il gioco.
 * 
 * @author Jack
 *
 */
public class EndFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel labelMsg;
	
	/**
	 * Costruttore: bisogna passargli la finestra di gioco corrente e una finestra menu.
	 * 
	 * @param game Il gioco corrente che e' finito
	 * @param menu Il menu' che si vuole mostrare alla fine
	 */
	public EndFrame(GameFrame game, MenuFrame menu)
	{
		setTitle("MINEFIELD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(new GridLayout(2,1));
		
		JPanel labelPanel = new JPanel();
		labelMsg = new JLabel();
		JPanel buttons = new JPanel();
		JButton toMenu = new JButton("MENU");
		JButton retry = new JButton("RETRY");
		JButton quit = new JButton("EXIT");
		
		EndFrameListener controller = new EndFrameListener(this, game, menu);
		toMenu.addActionListener(controller);
		retry.addActionListener(controller);
		quit.addActionListener(controller);
		
		labelPanel.add(labelMsg);
		
		buttons.add(toMenu);
		buttons.add(retry);
		buttons.add(quit);
		
		add(labelPanel);
		add(buttons);
		
		// centering window
		Dimension size = new Dimension(240, 110);
		int x = game.getLocation().x + game.getWidth()/2 - size.width/2;
		int y = game.getLocation().y + game.getHeight()/2 - size.height/2;
		
		setLocation(x, y);
		setSize(size);
		setVisible(true);
	}
	
	/**
	 * Setta il messaggio che si vuole mostrare nel pannello
	 * 
	 * @param msg La stringa da mostrare
	 */
	public void setEndMsg(String msg)
	{
		labelMsg.setText("<html><center>"+msg+"</center></html>");
	}
}
