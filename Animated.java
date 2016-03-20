/*
 * certain pieces of the following code are derived or are directly from youtube videos,
 * one of which is this (but he also has many others):
 * https://www.youtube.com/watch?v=vElmkRPpev8&list=PLA331A6709F40B79D&index=4
 * by thenewboston
 */
package platformer1;
import java.awt.Image;
import java.util.ArrayList;

public class Animated {
	
	private ArrayList scenes;
	private int scenesIndex;
	private long aniTime;
	private long totalTime;
	
	public Animated(){
		scenes = new ArrayList();
		totalTime = 0;
		start();
		
	}
	
	public synchronized void addScene(Image im, long time){
		
		totalTime += time;
		scenes.add(new OneScene(im, totalTime));
		
	}
	
	public synchronized void start(){
		aniTime = 0;
		scenesIndex = 0;
	}
	
	public synchronized void update(long timePassed){
		if (scenes.size() > 1){
			aniTime += timePassed;
			if(aniTime >= totalTime){
				aniTime = 0;
				scenesIndex = 0;
			}
			while(aniTime > getScene(scenesIndex).endTime){
				scenesIndex++;
			}
		}
	}
	
	public synchronized Image getImage(){
		if(scenes.size() == 0){
			return null;
		}
		else{
			return getScene(scenesIndex).pic;
		}
	}
	
	private OneScene getScene(int x){
		return (OneScene)scenes.get(x);
	}
	
	
	
///// Private Inner Class
	
	private class OneScene{
		Image pic;
		long endTime;
		
		public OneScene(Image pic, long endTime){
			this.pic = pic;
			this.endTime = endTime;
		}
		
	}
	
}







