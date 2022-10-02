package berack96.games.minefield.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import berack96.games.minefield.Mode;
import berack96.games.minefield.frame.strings.StringMode;
import berack96.games.minefield.frame.strings.StringSafe;
import berack96.games.minefield.listener.MenuFrameListener;
import berack96.games.minefield.object.Field;

/**
 * Classe che estende i {@link JPanel} in modo da creare una finestra per il menu' del gioco.<br>
 * Essa e' formata in modo da avere 4 zone: in ognuna di esse l'utente deve fare delle scelte.<br>
 * 
 * @author Jack
 *
 */
public class MenuFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private MenuFrameListener listener;
	
	// TOP
	final private JPanel panelTop;
	final private JRadioButton easy;
	final private JRadioButton medi;
	final private JRadioButton hard;
	final private JRadioButton cust;
	final private JLabel modeMsg;
	private Mode mode;
	
	// MID TOP
	final private JPanel panelMidTop;
	final private JTextField textColumns;
	final private JTextField textLines;
	final private JTextField textMines;
	final private JPanel customPanel;
	
	// MID BOT
	final private JPanel panelMidBot;
	final private JRadioButton yes;
	final private JRadioButton no;
	final private JLabel safeMsg;
	private boolean safe;
	
	// BOT
	final private JPanel panelBot;
	final private JLabel error;
	
	/**
	 * Costruttore: crea la finestra menu'
	 */
	public MenuFrame()
	{
		// set initial value for variable of JRadioButton
		this.mode = Mode.EASY;
		this.safe = true;
		
		// add the listener
		listener = new MenuFrameListener(this);
		
		// basic things for the frame
		setTitle("MINEFIELD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout( new GridLayout(4, 1));
		Dimension dim = new Dimension(350, 400);
		
		// TOP frame
		panelTop = new JPanel();
		JPanel panelTopLabel = new JPanel();
		JPanel panelTopButtons = new JPanel();
		JLabel labelTop = new JLabel("Scegli una difficolta'");
		easy = new JRadioButton("EASY", true);
		medi = new JRadioButton("MEDIUM", false);
		hard = new JRadioButton("HARD", false);
		cust = new JRadioButton("CUSTOM", false);
		ButtonGroup groupTop = new ButtonGroup();
		
		easy.addActionListener(this.listener);
		medi.addActionListener(this.listener);
		hard.addActionListener(this.listener);
		cust.addActionListener(this.listener);
		
		groupTop.add(easy);
		groupTop.add(medi);
		groupTop.add(hard);
		groupTop.add(cust);
		
		panelTopButtons.add(easy);
		panelTopButtons.add(medi);
		panelTopButtons.add(hard);
		panelTopButtons.add(cust);
		
		panelTopLabel.add(labelTop);
		
		panelTop.setLayout(new BoxLayout(this.panelTop, BoxLayout.Y_AXIS));
		panelTop.setBorder(BorderFactory.createLineBorder(Color.black));
		panelTop.add(panelTopLabel);
		panelTop.add(panelTopButtons);
		
		add(panelTop);
		
		// MID TOP frame
		panelMidTop = new JPanel();
		modeMsg = new JLabel(StringMode.easy);
		customPanel = new JPanel();
		JLabel labelLength = new JLabel("Colonne:");
		JLabel labelHeight = new JLabel("Righe:");
		JLabel labelMines = new JLabel("Numero di mine:");
		textColumns = new JTextField();
		textLines = new JTextField();
		textMines = new JTextField();
		
		customPanel.setLayout(new GridLayout(3, 3));
		
		customPanel.add(labelLength);
		customPanel.add(textColumns);
		customPanel.add(labelHeight);
		customPanel.add(textLines);
		customPanel.add(labelMines);
		customPanel.add(textMines);
		
		panelMidTop.setLayout(new FlowLayout());
		panelMidTop.setBorder(BorderFactory.createLineBorder(Color.black));
		panelMidTop.add(modeMsg);
		// not adding custom panel 'cause there is the private function when needed.
		
		add(panelMidTop);
		
		// MID BOT frame
		panelMidBot = new JPanel();
		JPanel panelMidBotButtons = new JPanel();
		JPanel panelMidBotMsg = new JPanel();
		JLabel labelMidBot = new JLabel("Vuoi la partenza safe?");
		yes = new JRadioButton("YES", true);
		no = new JRadioButton("NO", false);
		safeMsg = new JLabel(StringSafe.yes);
		ButtonGroup groupMid = new ButtonGroup();
		
		yes.addActionListener(listener);
		no.addActionListener(listener);
		
		groupMid.add(yes);
		groupMid.add(no);
		
		panelMidBotButtons.add(yes);
		panelMidBotButtons.add(no);
		
		panelMidBotMsg.add(safeMsg);
		
		panelMidBot.setLayout(new BoxLayout(panelMidBot, BoxLayout.Y_AXIS));
		panelMidBot.setBorder(BorderFactory.createLineBorder(Color.black));
		panelMidBot.add(labelMidBot);
		panelMidBot.add(panelMidBotButtons);
		panelMidBot.add(panelMidBotMsg);
		
		add(panelMidBot);
		
		// BOT frame
		panelBot = new JPanel();
		JPanel panelBotLabel = new JPanel();
		JPanel panelBotButton = new JPanel();
		error = new JLabel();
		JButton next = new JButton("NEXT");
		
		next.addActionListener(listener);
		
		panelBotLabel.add(error);
		
		panelBotButton.add(next);
		
		panelBot.setLayout(new BorderLayout());
		panelBot.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panelBot.add(panelBotLabel, BorderLayout.CENTER);
		panelBot.add(panelBotButton, BorderLayout.EAST);
		
		add(panelBot);
		
		// last things to do for the frame
		setSize(dim);
		
		// centering window
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dimScreen.width - dim.width)/2;
		int y = (dimScreen.height - dim.height)/2;
		setLocation(x, y);
		
		setVisible(true);
	}
	
	/**
	 * Funzione che serve al listener di questo frame {@link MenuFrameListener}<br>
	 * per aggiornare le frasi dei label e le varie variabili del menu'
	 */
	public void updateView()
	{
		if(easy.isSelected())
		{
			if(mode == Mode.CUSTOM)
				removeCustomPanel();
			mode = Mode.EASY;
			modeMsg.setText(StringMode.easy);
		}
		else if(medi.isSelected())
		{
			if(mode == Mode.CUSTOM)
				removeCustomPanel();
			mode = Mode.MEDIUM;
			modeMsg.setText(StringMode.medium);
		}
		else if(hard.isSelected())
		{
			if(mode == Mode.CUSTOM)
				removeCustomPanel();
			mode = Mode.HARD;
			modeMsg.setText(StringMode.hard);
		}
		else
		{
			if(mode != Mode.CUSTOM)
				addCustomPanel();
			mode = Mode.CUSTOM;
			modeMsg.setText(StringMode.custom);
		}
		
		if(yes.isSelected())
		{
			safe = true;
			safeMsg.setText(StringSafe.yes);
		}
		else
		{
			safe = false;
			safeMsg.setText(StringSafe.no);
		}
	}
	
	/**
	 * Restituisce la stringa che si trova nel pannello delle colonne.
	 * 
	 * @return La stringa inserita dall'utente
	 */
	public String getStringColumnsTextPanel()
	{
		return textColumns.getText();
	}
	
	/**
	 * Restituisce la stringa che si trova nel pannello delle linee.
	 * 
	 * @return La stringa inserita dall'utente
	 */
	public String getStringLinesTextPanel()
	{
		return textLines.getText();
	}
	
	/**
	 * Restituisce la stringa che si trova nel pannello delle mine.
	 * 
	 * @return La stringa inserita dall'utente
	 */
	public String getStringMinesTextPanel()
	{
		return textMines.getText();
	}
	
	/**
	 * Restituisce il valore attuale del parametro safe.
	 * 
	 * @return true/false
	 */
	public boolean getSafeValue()
	{
		return safe;
	}
	
	/**
	 * Restituisce il valore attuale della modalita' di gioco.
	 * 
	 * @return {@link Mode}
	 */
	public Mode getModeValue()
	{
		return mode;
	}
	
	/**
	 * Se inserita una stinga verra' mostrata di colore rosso nel pannello.
	 * 
	 * @param error La stringa da mettere in evidenza
	 */
	public void setError(String error)
	{
		this.error.setText("<html><font color='red'>"+error+"</font></html>");
	}
	
	/**
	 * Se inserito un campo, verra' creata una finestra di gioco {@link GameFrame}<br>
	 * e questa finestra menu' verra' nascosta tramite {@link #setVisible(boolean)}.
	 * 
	 * @param field Il campo del gioco che si vuole giocare
	 */
	public void startNewGameFrame(Field field)
	{
		setVisible(false);
		GameFrame gameFrame = new GameFrame(this, field);
		gameFrame.setVisible(true);
	}
	
	/**
	 * Resetta ogni variabile del pannello e ogni oggetto grafico.
	 */
	public void resetVariablePanel()
	{
		easy.setSelected(true);
		medi.setSelected(false);
		hard.setSelected(false);
		cust.setSelected(false);
		
		yes.setSelected(true);
		no.setSelected(false);
		
		updateView();
		
		setError("");
		
	}
	
	/**
	 * Mostra il pannello per la personalizzazione della partita.
	 */
	private void addCustomPanel()
	{
		panelMidTop.add(customPanel);
		customPanel.setVisible(true);
	}
	
	/**
	 * Nasconde il pannello per la personalizzazione della partita.
	 */
	private void removeCustomPanel()
	{
		customPanel.setVisible(false);
		panelMidTop.remove(customPanel);
		//panelMidTop.validate();
	}
}