/*
 * Authors: Sedak Puri 
 * Programming Project 6: Freeze Tag Part 2
 * 17 November 2017
 * 
 */

//Import block.
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;

public class MovingRectangleDriver {
	public static final int canvas = 500;
	public static void main(String[] args){
		
		//Canvas Setup
		StdDraw.setCanvasSize(canvas, canvas);
		StdDraw.setXscale(0, canvas);
		StdDraw.setYscale(0, canvas);
		
		//Creation of random object and rectangles array.
		MovingRectangle[] rectangles = new MovingRectangle[5];
		Random rng = new Random();
		int clickAmmount;
		int clickStatus;
		
		//Enable Double Buffering to make game smoother ;)
		StdDraw.enableDoubleBuffering();
		
		//Populating the array.
		for (int i = 0; i < rectangles.length; ++i) {
			int height = rng.nextInt(69) + 70;
			int width = rng.nextInt(69) + 70;
			int x = rng.nextInt(410) + (width/2) + 1;
			int y = rng.nextInt(410) + (height/2) + 1;
			int xVelocity = rng.nextInt(3) + 1;
			int yVelocity = rng.nextInt(3) + 1;
			clickAmmount = rng.nextInt(3) + 1;
			
			//Constructing Rectangle objects.
			rectangles[i] = new MovingRectangle(x, y, width, height, xVelocity, yVelocity, clickAmmount);
		}
		
		//Loop for Moving Rectangles
		while(true)
		{
			//Check for Collisions
			for (int h = 0; h < rectangles.length; ++h) {
				for (int j = h + 1; j < rectangles.length; ++j) {
					rectangles[j].getCollisionCheck(rectangles[h]);
					rectangles[h].getCollisionCheck(rectangles[j]);
				}
			}
		
			//Decrease click ammount of box is clicked
			int count = 0;
			StdDraw.clear();
			for(int i = 0; i < rectangles.length; ++i){
				rectangles[i].update();
				
				//Checks to see if the mouse is clicked within the rectangle
				if (StdDraw.mousePressed() && rectangles[i].boxClicked(StdDraw.mouseX(), StdDraw.mouseY())) { 
					while(true) {
						clickStatus = 0;
						if (StdDraw.mousePressed() && clickStatus == 0) {
							clickStatus = 1;
							//Code keep running
							
						} else if (!StdDraw.mousePressed()){
							clickStatus = 0;
							rectangles[i].lowerClickAmmount();
							break;
						}
					} 
				}
				
				//If the rectangle is stopped
				if(rectangles[i].isStopped())
				{	
					//Increment the amount of rectangles that are stopped
					++count;	
					
					//If all rectangles are stopped
					if(count == rectangles.length) {
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text(canvas/2, canvas/2, "You win!");
					}
				}
				rectangles[i].draw();
			}
			
			//Show + DoubleBuffer make the game smoother 
			StdDraw.show();
			StdDraw.pause(5);
		}
		
		}
	}