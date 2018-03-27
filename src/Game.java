import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends JFrame implements MouseMotionListener, Runnable,
		KeyListener {

	private static final long serialVersionUID = 1L;
	private static int width, height;
	private City[] myCitys;
	public Graphics offscreen;
	private BufferedImage bi;
	BufferedImage Crosshair;
	private Location mouseLoc;
	private ArrayList<Missile> allMissiles;
	private ArrayList<EnemyMissile> allEnemyMissiles;
	private ArrayList<Particle> allParticles;
	private int tooManyMissiles;
	private boolean gameOver;
	BufferedImage myGameOver;

	// private Plane myPlane;

	public Game() {
		gameOver = false;
		tooManyMissiles = 50;
		allMissiles = new ArrayList<Missile>();
		allEnemyMissiles = new ArrayList<EnemyMissile>();
		allParticles = new ArrayList<Particle>();
		mouseLoc = new Location(WIDTH / 2, HEIGHT / 2);
		// myPlane = new Plane();
		myCitys = new City[6];
		myCitys[0] = new City(225);
		myCitys[1] = new City(425);
		myCitys[2] = new City(625);
		myCitys[3] = new City(1110);
		myCitys[4] = new City(1310);
		myCitys[5] = new City(1510);
		myGameOver = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		try {
			myGameOver = ImageIO.read(getClass().getResourceAsStream(
					"/resources/GameOver.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Crosshair = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		try {
			Crosshair = ImageIO.read(getClass().getResourceAsStream(
					"/resources/Crosshair.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

	}

	public static int getTheWidth() {
		return width;
	}

	public static int getTheHeight() {
		return height;
	}

	public static void main(String[] args) {
		Game thisGame = new Game();
		thisGame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		thisGame.setUndecorated(true);
		thisGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisGame.setCursor(thisGame.getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));
		// thisGame.setSize(WIDTH, HEIGHT);
		new Thread(thisGame).start();
		thisGame.setVisible(true);
	}

	public void paint(Graphics g) {
		if (bi == null) {
			width = this.getWidth();
			height = this.getHeight();
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			offscreen = bi.getGraphics();
			Graphics2D g2 = (Graphics2D) offscreen;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		offscreen.setColor(Color.black);
		offscreen.fillRect(0, 0, this.getWidth(), this.getHeight());
		// draw the batteries
		offscreen.setColor(Color.white);
		offscreen.fillOval(50, 1025, 135, 135);
		offscreen.fillOval(872, 1015, 175, 175);
		offscreen.fillOval(1715, 1025, 135, 135);
		for (Particle p : allParticles)
			p.draw(offscreen);

		gameOver = true;
		for (City myCity : myCitys)
			if (myCity != null) {
				myCity.draw(offscreen);
				gameOver = false;
			}
		if (gameOver)
			offscreen.drawImage(myGameOver, 625, 400, null);
		// if(!gameOver)myPlane.draw(offscreen);
		if (!gameOver)
			offscreen.drawImage(Crosshair,
					(int) mouseLoc.x - Crosshair.getWidth() / 2,
					(int) mouseLoc.y - Crosshair.getHeight() / 2, null);
		for (Missile m : allMissiles)
			if (!gameOver)
				m.draw(offscreen);
		for (EnemyMissile em : allEnemyMissiles)
			if (!gameOver)
				em.draw(offscreen);

		g.drawImage(bi, 0, 0, null); // this must be the last line in the paint

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_ESCAPE)System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Location source = null;
		if (e.getKeyCode() == KeyEvent.VK_A) {
			source = new Location(115, 1025);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			source = new Location(957, 1015);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			source = new Location(1780, 1030);
		}
		// TODO Auto-generated method stub
		Location target = new Location(mouseLoc.x, mouseLoc.y);
		if (source != null && allMissiles.size() < 10) {
			allMissiles.add(new Missile(source, target));
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while (true) {
			if (gameOver) {
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
			tooManyMissiles--;
			if (Math.random() < 0.1) {
				Location start = new Location(Math.random() * width, 0);
				Location target = new Location(Math.random() * width, height);
				if (tooManyMissiles < 0) {
					tooManyMissiles = 50;
					allEnemyMissiles.add(new EnemyMissile(start, target));
				}
			}

			for (int i = allEnemyMissiles.size() - 1; i >= 0; i--) {
				EnemyMissile m = allEnemyMissiles.get(i);
				if (!m.update())
					allEnemyMissiles.remove(m);
			}

			for (int i = allMissiles.size() - 1; i >= 0; i--) {
				Missile m = allMissiles.get(i);
				if (!m.update())
					allMissiles.remove(m);
			}

			for (int i = 0; i < allEnemyMissiles.size(); i++) {

				EnemyMissile em = allEnemyMissiles.get(i);
				for (Missile m : allMissiles) {
					if (em.hasHit(m))
						allEnemyMissiles.remove(em);
				}

				for (City c : myCitys) {
					if (c != null && em.hasHit(c)) {
						for (int j = 0; j < 500; j++) {
							if (allParticles == null)
								System.out.println("allParticles");
							else if (em == null)
								System.out.println("em");
							else
								allParticles
										.add(new Particle(em.getLocation().x,
												em.getLocation().y));
						}
						allEnemyMissiles.remove(em);
					}
				}

				for (int j = 0; j < myCitys.length; j++) {
					if (myCitys[j] != null && em.hasHit(myCitys[j]))
						myCitys[j] = null;
				}
			}
			for (int i = 0; i < allParticles.size(); i++) {
				Particle p = allParticles.get(i);
				p.update();
				if (p.isOffScreen())
					allParticles.remove(p);
			}

			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLoc.x = e.getX();
		mouseLoc.y = e.getY();

	}

}