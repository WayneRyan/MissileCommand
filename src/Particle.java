import java.awt.Color;
import java.awt.Graphics;


public class Particle {
	protected double x, y, dx, dy;
	protected int size;
	private float r, g, b;
	
	protected void setSpeed(double x, double y){
		dx = x;
		dy = y;
	}
	
	public Particle(int myX, int myY, Color theColor){
		r = theColor.getRed()/ 256f;
		g = theColor.getGreen()/ 256f;
		b = theColor.getBlue()/ 256f;
		x = myX;
		y = myY;
		size = 3;
		dx = (Math.random() -0.5)*10;
		dy = (Math.random() -0.5)*10;
		double length = Math.sqrt(dx*dx+dy*dy);
		while(length>2.5){
			dx = (Math.random() -0.5)*10;
			dy = (Math.random() -0.5)*10;
		    length = Math.sqrt(dx*dx+dy*dy);
		
		
		}
	}

	public Particle(double myX, double myY){
		r = (float)Math.random();
		g = (float)Math.random();
		b = (float)Math.random();
		x = myX;
		y = myY;
		size = 3;
		dx = (Math.random() -0.5)*15;
		dy = (Math.random() -0.5)*15;
		double length = Math.sqrt(dx*dx+dy*dy);
		while(length>5.5){
			dx = (Math.random() -0.5)*15;
			dy = (Math.random() -0.5)*15;
		    length = Math.sqrt(dx*dx+dy*dy);
		    
		}
	}
		
	
	public boolean isOffScreen(){
		if(r+g+b <0.1)return true;
		if(x<0) return true;
		if(y<0) return true;
		if(x>Game.getTheWidth()) return true;
		if(y>Game.getTheHeight()) return true;
		return false;
	}
	public void draw(Graphics g2){
		g2.setColor(new Color(r,g,b));
		g2.fillOval((int) x, (int) y, size, size);
	}
	
	public void update(){
		x += dx;
		y += dy;
		dy += .1;
		dx *= .99;
		dy *= .99;
		r *= .97;
		g *= .97;
	    b *= .97;
	}
}
