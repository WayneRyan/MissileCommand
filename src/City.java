import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class City {

	BufferedImage myCity;
	int myLocation;

	public City(int loc) {
		myLocation = loc;
		myCity = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		try {
			myCity = ImageIO.read(getClass().getResourceAsStream(
					"/resources/MissileCommandCity.png"));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		g.drawImage(myCity, myLocation, Game.getTheHeight()-myCity.getHeight(), null);
	}
	

	
	public int getSize(){
		 return myCity.getWidth();
	}
	public int getLocation(){ return myLocation;}
}

