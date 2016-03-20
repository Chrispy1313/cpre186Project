package platformer1;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class mainScreen {

	public static void main(String[] args) {
		
		mainScreen b = new mainScreen();
		b.run();
	}
	
	private Sprite sprite;	//TODO change name of sprite class here
	private Animated a;
	private ScreenManager s;
	private Image bg;
	
	private static final DisplayMode modes1[] = {
			new DisplayMode(800,600,32,0),			
			new DisplayMode(800,600,24,0),
			new DisplayMode(800,600,16,0),
			new DisplayMode(640,480,32,0),
			new DisplayMode(640,480,24,0),
			new DisplayMode(640,480,16,0),
			
	};

	// load images and add scenes
	public void loadImages(){
		bg = new ImageIcon("X:\\cpre186\\Chrysanthemum.jpg").getImage();
		Image image1 = new ImageIcon("X:\\cpre186\\Chrysanthemum.jpg").getImage();
		Image image2 = new ImageIcon("X:\\cpre186\\Desert.jpg").getImage();
		//TODO will have to change the routes to get pictures and things 
		a = new Animated();
		a.addScene(image1, 250);
		a.addScene(image2, 250);
		//TODO as a result will need to change the animated scenes too. 
		
		sprite = new Sprite(a); //TODO a = the image that is the sprite and change sprite class name
		sprite.setVelocityX(0.3f);
		sprite.setVelocityY(0.3f);	//these set velocity, TODO names will probably need to be changed
		
	}
	
	//main method called from main
	public void run(){
		s = new ScreenManager();
		try{
			DisplayMode dm = s.findFirstCompatibleMode(modes1);
			s.setFullScreen(dm);
			loadImages();
			movieLoop();
		}finally{
			s.restoreScreen();
		}
	}
	
	// play movie
	public void movieLoop(){
		long startingTime = System.currentTimeMillis();
		long cumTime = startingTime;
		while(cumTime - startingTime < 5000){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			update(timePassed); // TODO updates the sprite movement 
			
			// draw and update the screen
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(20);
			}catch(Exception ex){}
			
		}
	}
	
	//draws graphics
	public void draw(Graphics g){
		g.drawImage(bg, 0, 0, null);
		g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
		//TODO may need to change method names in previous line
	}
	
	//TODO update method for the sprite. maybe need to change names or replace with a better one
	public void update(long timePassed){
		if(sprite.getX() < 0){
			sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
		}
		else if(sprite.getX() + sprite.getWidth() > s.getWidth()){
			sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
		}
		
		if(sprite.getY() < 0){
			sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
		}
		else if(sprite.getY() + sprite.getHeight() > s.getHeight()){
			sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
		}
		
		sprite.update(timePassed);
	}
	
}





