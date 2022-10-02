package berack96.games.minefield.frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import berack96.games.minefield.listener.CellInFieldListener;
import berack96.games.minefield.object.Decisor;
import berack96.games.minefield.object.Field;
import berack96.games.minefield.object.FieldSafe;
import berack96.games.minefield.object.view.FieldView;

//TODO remove log lines
/**
 * Classe che estende i {@link JPanel} in modo da creare una finestra del gioco.<br>
 * Essa si serve di un {@link MenuFrame} per la finestra finale {@link EndFrame}<br>
 * e anche di un {@link Field} gia' creato, in modo da creare una rappresentazione<br>
 * grafica del gioco.
 * 
 * @author Jack
 *
 */
public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	final private MenuFrame menu;
	
	private Field field;
	private JPanel gamePanel;
	private JLabel label;
	
	/**
	 * Costruttore: crea una finestra di gioco a partire dal parametro field.
	 * 
	 * @param menu Il menu' che si vuole mostrare eventualmente alla fine del gioco
	 * @param field Il campo di gioco
	 */
	public GameFrame(MenuFrame menu, Field field)
	{
		this.field = field;
		this.menu = menu;
		
		setTitle("MINEFIELD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		label = new JLabel(String.valueOf(field.getNumMines()-field.getNumDagerousCell()));
		label.setVisible(true);
		
		gamePanel = new FieldView(field, new CellInFieldListener(this, field)).toJPanel();
		gamePanel.setVisible(true);
		
		add(label);
		add(gamePanel);
		
		// centering window
		Dimension dim = gamePanel.getPreferredSize();
		dim.height += 57;	// doing this for the flowlayout & for the label
		dim.width += 10;	// same here
		
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dimScreen.width - dim.width)/2;
		int y = (dimScreen.height - dim.height)/2;
		setLocation(x, y);
		
		setSize(dim);
		setVisible(true);
	}
	
	public void updateView()
	{
		label.setText(String.valueOf(field.getNumMines()-field.getNumDagerousCell()));
	}
	
	/**
	 * Crea una nuova finestra {@link EndFrame} settata con endMsg.
	 * 
	 * @param endMsg Il messaggio che si vuole mostrare
	 */
	public void launchEndFrame(String endMsg)
	{
		Decisor.uncoverAllMinesCells(field);
		Decisor.uncoverAllDangerousCells(field);
		
		EndFrame end = new EndFrame(this, menu);
		end.setEndMsg(endMsg);
	}
	
	/**
	 * Resetta la griglia di gioco.<br>
	 * In questo modo si puo' giocare alla stessa difficolta' senza passare dal menu'.
	 */
	public void retryGame()
	{
		field = Decisor.getNewField(field.getClass() == FieldSafe.class, field.columns, field.lines, field.getNumMines());
		
		gamePanel.setVisible(false);
		remove(gamePanel);	// remove all the component in the Frame
		
		gamePanel = new FieldView(field, new CellInFieldListener(this, field)).toJPanel();
		
		gamePanel.setVisible(true);
		add(gamePanel);
		
		updateView();
		setVisible(true);
		
		System.out.println("\n---New game started---"); // log line
		
	}
}
