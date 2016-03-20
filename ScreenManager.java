/*
 * certain pieces of the following code are derived or are directly from youtube videos,
 * one of which is this (but he also has many others):
 * https://www.youtube.com/watch?v=vElmkRPpev8&list=PLA331A6709F40B79D&index=4
 * by thenewboston
 */
package platformer1;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

public class ScreenManager {

	private GraphicsDevice video;
	
	//gives video access to screen
	public ScreenManager(){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		video = e.getDefaultScreenDevice();
	}
	
	//get all compatible Displays modes
	public DisplayMode[] getCompatibleDisplayModes(){
		return video.getDisplayModes();
	}
	
	//compares Display modes to video to see if it is a match
	public DisplayMode findFirstCompatibleMode(DisplayMode modes[]){
		DisplayMode goodModes[] = video.getDisplayModes();
		for(int x = 0; x < modes.length; x++){
			for(int y = 0; y < goodModes.length; y++){
				if(displayModesMatch(modes[x], goodModes[y])){
					return modes[x];
				}
			}
		}
		return null;
	}
	
	//get current display mode
	public DisplayMode getCurrentDisplayMode(){
		return video.getDisplayMode();
	}
	
	//checks if modes match each other
	public boolean displayModesMatch(DisplayMode m1, DisplayMode m2){
		if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()){
			return false;
		}
		if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()){
			 return false;
		}
		if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate()){
			return false;
		}
		
		return true;
	}
	
	// make frame full screen
	public void setFullScreen(DisplayMode dm){
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		video.setFullScreenWindow(f);
		
		if(dm != null && video.isDisplayChangeSupported()){
			try{
				video.setDisplayMode(dm);
			}catch(Exception ex){}
			
		}
		f.createBufferStrategy(2);
		
	}
	
	//set graphics object = to this 
	public Graphics2D getGraphics(){
		Window w = video.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			return (Graphics2D)s.getDrawGraphics();
		}
		else{
			return null;
		}
		
	}
	
	//updates display
	public void update(){
		Window w = video.getFullScreenWindow();
		
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			if(!s.contentsLost()){
				s.show();
			}
		}
	}
	
	// returns full screen window
	public Window getFullScreenWindow(){
		return video.getFullScreenWindow();
	}
	
	//get width
	public int getWidth(){
		Window w = video.getFullScreenWindow();
		if(w != null){
			return w.getWidth();
		}
		else{
			return 0;
		}
	}
	
	//get height
	public int getHeight(){
		Window w = video.getFullScreenWindow();
		if(w != null){
			return w.getHeight();
		}
		else{
			return 0;
		}
	}
	
	//get out of fullscreen
	public void restoreScreen(){
		Window w = video.getFullScreenWindow();
		if(w != null){
			w.dispose();
		}
		video.setFullScreenWindow(null);
	}
	
	//create image compatible with monitor
	public BufferedImage createCompatibleImage(int w, int h, int t){
		Window win = video.getFullScreenWindow();
		if(win != null){
			GraphicsConfiguration gc = win.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, t);
		}
		return null;
	}
	
}







