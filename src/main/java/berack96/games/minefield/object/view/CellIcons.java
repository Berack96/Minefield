package berack96.games.minefield.object.view;

import javax.swing.ImageIcon;

/**
 * Classe che contiene solamente le immagini da mostrare nel caso le si vogliano usare
 * 
 * @author Jack
 *
 */
public class CellIcons
{
	public static final ImageIcon mine = createImageIcon("mine.png");
	
	public static final ImageIcon flag = createImageIcon("flag.png");
	public static final ImageIcon flagRight = createImageIcon("flagRight.png");
	public static final ImageIcon flagWrong = createImageIcon("flagWrong.png");
	
	public static final ImageIcon one = createImageIcon("1one.png");
	public static final ImageIcon two = createImageIcon("2two.png");
	public static final ImageIcon three = createImageIcon("3three.png");
	public static final ImageIcon four = createImageIcon("4four.png");
	public static final ImageIcon five = createImageIcon("5five.png");
	public static final ImageIcon six = createImageIcon("6six.png");
	public static final ImageIcon seven = createImageIcon("7seven.png");
	public static final ImageIcon eight = createImageIcon("8eight.png");
	
	private static final String path = "berack96/games/minefield/assets/icons/";
	
	private static ImageIcon createImageIcon(String name)
	{
		java.net.URL imgURL = ClassLoader.getSystemResource(path+name);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
		{
			System.err.println("Couldn't find file: " + name);
			return null;
		}
	}
}