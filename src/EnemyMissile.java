import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class EnemyMissile {
	private Location target;
	private Location current;
	private Location start;
	private boolean reachedTarget;
	private double dX, dY;
	private final double SPEED = 4.3;
	private ArrayList<EnemyMissile> allEnemyMissiles;

	public EnemyMissile(Location start, Location target) {
		allEnemyMissiles = new ArrayList<EnemyMissile>();
		this.start = new Location(start.x, start.y);
		this.current = start;
		this.target = target;
		reachedTarget = false;
		dX = target.x - start.x;
		dY = target.y - start.y;
		double length = Math.sqrt(dX * dX + dY * dY);
		dX /= length;
		dY /= length;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		if (!reachedTarget) {
			g.drawLine((int) start.x, (int) start.y, (int) current.x,
					(int) current.y);
		}
	}

	public boolean update() {
		if (!reachedTarget) {
			current.x += dX * SPEED;
			current.y += dY * SPEED;
			double dX2 = target.x - current.x;
			double dY2 = target.y - current.y;
			double length = Math.sqrt(dX2 * dX2 + dY2 * dY2);
			if (length < 2 * SPEED) {
				reachedTarget = true;
			}
			return true;
		}
		return false;
	}

	public boolean hasHit(Missile m) {
		double size = m.getSize();
		if(size == 0)return false;
		double dX = this.current.x - m.getLocation().x;
		double dY = this.current.y - m.getLocation().y;
		double distance = Math.sqrt(dX*dX + dY*dY);
		return distance<size/2;
	}
	
	public boolean hasHit(City c) {
		if(this.current.y < Game.getTheHeight() - 50) return false;
		if(this.current.x < c.getLocation()) return false;
		if(this.current.x > c.getLocation()+c.getSize()) return false;
		
		return true;
	}

	public int getSize() {
		return allEnemyMissiles.size();

	}

	public Location getLocation() {
		return current;
		
	}
	}

