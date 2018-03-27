import java.awt.Color;
import java.awt.Graphics;

public class Missile {
	private Location target;
	private Location current;
	private Location start;
	private boolean reachedTarget;
	private int size;
	private double dX, dY;
	private final double SPEED = 10;
	private int maxSize;

	public Missile(Location start, Location target) {
		this.start = new Location(start.x, start.y);
		this.current = start;
		this.target = target;
		reachedTarget = false;
		size = 1;
		maxSize = 120;
		dX = target.x - start.x;
		dY = target.y - start.y;
		double length = Math.sqrt(dX * dX + dY * dY);
		dX /= length;
		dY /= length;
	}

	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		if (!reachedTarget) {
			g.drawLine((int) start.x, (int) start.y, (int) current.x,
					(int) current.y);
		} else {
			if (reachedTarget = true) {
				g.fillOval((int) target.x-size/2, (int) target.y-size/2, size, size);
			}
		}
	}
	
	public Location getLocation(){ return current;}
	
	public int getSize(){
		if(!reachedTarget) return 0;
		else return size;
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

		}else if ( size < maxSize) {
			size+= 2;
		}else{
			return false;
		}
		return true;

	
	}

	
}

