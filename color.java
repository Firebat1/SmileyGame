import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class color extends JFrame implements KeyListener, Runnable{
	private int totalSize = 0;
	private int averageXLoc = 0;
	private int averageYLoc = 0;
	private int reduceSpeed = 1;
	private Smiley character;
	private boolean[] keys = new boolean[6];
	private ArrayList<Smiley> characters = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private BufferedImage back;
	private boolean doCombine = true;
	JPanel p = new JPanel();
	boolean split = true;
	public static void main(String[] args)
	{
		
		
		new color();
	}
	public color() {
		super("color");
		Color c = Color.black;
		this.addKeyListener(this);
		new Thread(this).start();
		
 
		p.setBackground(c);
 
		setSize(2000, 1600);
		character = new Smiley(100, 1, 100, 100, "SIDEWAYS",getWidth(),getHeight());
		characters.add(character);
		add(p);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	@Override
	public void paint(Graphics g) {
		Graphics2D twoDGraph = (Graphics2D)g;
		if ((int)(Math.random()*100) == 99) {
			enemies.add(new Enemy(1));
		}
	
		if(back==null) {
			   back = (BufferedImage)(createImage(getWidth(),getHeight()));
		}
		Graphics graphToBack = back.createGraphics();

		g.setColor(Color.black);
		graphToBack.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < enemies.size(); i ++) {
			Enemy j = enemies.get(i).split();
			if (j != null) {
				enemies.add(j);
			}
		}
		graphToBack.setColor(Color.yellow);
		for (Smiley i: characters) {
			i.draw(graphToBack);
		}
		for (Enemy i: enemies) {
			i.draw(graphToBack);
			i.move();
		}
		if (keys[0]) {
			for (Smiley i: characters) {
				i.move("LEFT");
			}
		}
		if (keys[1]) {
			for (Smiley i: characters) {
				i.move("RIGHT");
			}
		}
		if (keys[2]) {
			for (Smiley i: characters) {
				i.move("UP");
			}
		}
		if (keys[3]) {
			for (Smiley i: characters) {
				i.move("DOWN");
			}
		}
		if (keys[4] && split) {
			split = false;
			for (int i = characters.size()-1; i >= 0; i --) {
				if (characters.get(i).getSize() >= 10) {
				characters.add(characters.get(i).split(graphToBack, characters.get(i)));
				}
			}
		}
		if (!keys[4]) {
			split = true;
		}
		if (keys[5] && doCombine) {
			doCombine = false;
			for (Smiley i: characters) {
				averageXLoc += i.getXLoc();
				averageYLoc += i.getYLoc();
				totalSize += i.getSize();
			}
			for (int i = 0; i < characters.size(); i ++) {
				reduceSpeed *= 2;
			}
			averageXLoc /= characters.size();
			averageYLoc /= characters.size();
			characters = new ArrayList<>();
			characters.add(new Smiley(totalSize, 1, averageXLoc, averageYLoc, "SIDEWAYS", getWidth(), getHeight()));
			totalSize = 0;
			averageXLoc = 0;
			averageYLoc = 0;
		}
		if (! keys[5]) {
			doCombine = true;
		}

		// Next part decides whether they get eaten or not
		for (int i = 0; i < characters.size(); i ++) {
			for (int j = 0; j < enemies.size(); j ++) {
				Smiley currentSmiley = characters.get(i);
				Enemy currentEnemy = enemies.get(j);
				int smileyXCenter = currentSmiley.getXLoc() + currentSmiley.getSize()/2;
				int smileyYCenter = currentSmiley.getYLoc() + currentSmiley.getSize()/2;
				int enemyXCenter = currentEnemy.getXLoc() + currentEnemy.getSize()/2;
				int enemyYCenter = currentEnemy.getYLoc() + currentEnemy.getSize()/2;
				boolean intersects = Math.hypot(smileyXCenter - enemyXCenter, smileyYCenter - enemyYCenter) <= (currentSmiley.getSize()/2 + currentEnemy.getSize()/2)+20;
				if (intersects) {
					if (currentSmiley.getSize() > currentEnemy.getSize() + 10) {
						currentSmiley.modifySize(enemies.remove(j).getSize()/2);
					}
					else if (currentSmiley.getSize()  + 10 < currentEnemy.getSize()) {
						currentEnemy.modifySize(characters.remove(i).getSize()/2);
					}
				}
			}
		}
		for (int i = 0; i < enemies.size(); i ++) {
			for (int j = i + 1; j < enemies.size(); j ++) {
				Enemy enemy1 = enemies.get(i);
				Enemy enemy2 = enemies.get(j);
				int enemy1XCenter = enemy1.getXLoc() + enemy1.getSize()/2;
				int enemy1YCenter = enemy1.getYLoc() + enemy2.getSize()/2;
				int enemy2XCenter = enemy2.getXLoc() + enemy2.getSize()/2;
				int enemy2YCenter = enemy2.getYLoc() + enemy2.getSize()/2;
				boolean intersects = Math.hypot(enemy1XCenter - enemy2XCenter, enemy1YCenter - enemy2YCenter) <= (enemy1.getSize()/2 + enemy2.getSize()/2)+20;
				if (intersects) {
					if (enemy1.getSize() > enemy2.getSize() + 10) {
						enemy1.modifySize(enemies.remove(j).getSize()/4);
					}
					else if (enemy1.getSize()  + 10 < enemy2.getSize()) {
						enemy2.modifySize(enemies.remove(i).getSize()/4);
					}
				}
			}
		}


        if (characters.size() == 0) {
            characters = new ArrayList<>();
            enemies = new ArrayList<>();
            characters.add(new Smiley(100, 1, 100, 100, "SIDEWAYS",getWidth(),getHeight()));
        }
        twoDGraph.drawImage(back, null, 0, 0);  
		repaint();
	}










	public void keyPressed(KeyEvent e)
{
	if (e.getKeyCode() == KeyEvent.VK_LEFT)
	{
		keys[0] = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	{
		keys[1] = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_UP)
	{
		keys[2] = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_DOWN)
	{
		keys[3] = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_SPACE)
	{
		keys[4] = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		keys[5] = true;
	}
	repaint();
}

public void keyReleased(KeyEvent e)
{
	if (e.getKeyCode() == KeyEvent.VK_LEFT)
	{
		keys[0] = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	{
		keys[1] = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_UP)
	{
		keys[2] = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_DOWN)
	{
		keys[3] = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_SPACE)
	{
		keys[4] = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		keys[5] = false;
	}
}

public void keyTyped(KeyEvent e)
{
}
public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(5);
            repaint();
         }
      }catch(Exception e)
      {
      }
  	}
}
