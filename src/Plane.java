import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Plane {
	private BufferedImage myPlane;
	private Location target;
	private Location current;
	private Location start;

	
	public Plane(){
		this.start = new Location(start.x, start.y);
		this.current = start;
		this.target = target;
		myPlane = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
			try {
				myPlane = ImageIO.read(getClass().getResourceAsStream("/resources/Plane.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void draw(Graphics g) {
		g.drawImage(myPlane, 500,500, null);
}
	public boolean hasHit(Missile m) {
		double size = m.getSize();
		if(size == 0)return false;
		double dX = this.current.x - m.getLocation().x;
		double dY = this.current.y - m.getLocation().y;
		double distance = Math.sqrt(dX*dX + dY*dY);
		return distance<size/2;
}
	public Location getLocation() {
		return current;
}
}
