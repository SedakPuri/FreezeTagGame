/*
 * Authors: Sedak Puri 
 * Programming Project 6: Freeze Tag Part 2
 * 17 November 2017
 * 
 */

//Import block
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;

public class MovingRectangle {
	
	//Instance variable declaration.
	private double xVelocity, yVelocity, tempX, tempY;
	private int x, y, height, width, r, g, b, clickAmmount;
	private Random rng;
	private boolean collisionCheck;
	
	public MovingRectangle() {
		
	}
	
	//Constructor
	public MovingRectangle(int x, int y, int width, int height, double xVelocity, double yVelocity, int clickAmmount) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.width = width/2;
		this.height = height/2;
		this.clickAmmount = clickAmmount;
		
		//Constructor automatically randomizes colors initially.
		rng = new Random();
		randomColors();
	}
	
	public void draw()
	{
		//Draws the rectangle with random color unless frozen.
		StdDraw.setPenColor(r, g, b);
		if (isStopped()) {
			StdDraw.setPenColor(StdDraw.RED);
		}
		StdDraw.filledRectangle(x, y, width/2, height/2);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(x, y, Integer.toString(clickAmmount));
	}
	
	//Checks if mouse clicks within rectangle
	public boolean boxClicked(double x, double y)
	{
		return (x > (getX() - getWidth()/2) && x < (getX() + getWidth()/2) &&
			y < (getY() + getHeight()/2) && y > getY() - getHeight()/2);
	}

	//Makes rectangles move
	public void update()
	{
		x += xVelocity;
		y += yVelocity;
		
		//If the rectangles hit canvas
		if (x + width/2 >= 500) {
			randomColors();
			xVelocity *= -1;
			x = 500 - (width/2);
		} else if (x - width/2 <= 0) {
			randomColors();
			xVelocity *= -1;
			x = width/2;
		} else if (y + height/2 >= 500) {
			randomColors();
			yVelocity *= -1;
			y = 500 - (height/2);
		} else if (y - height/2 <= 0) {
			randomColors();
			yVelocity *= -1;
			y = height/2;
		}
		
	}
	
	//Returns movement status of rectangles.
	public boolean isStopped()
	{
		return xVelocity == 0 && yVelocity == 0;
	}
	
	//Randomizes rectangle color.
	public void randomColors()
	{
		r = rng.nextInt(255) + 1;
		g = rng.nextInt(255) + 1;
		b = rng.nextInt(255) + 1;
	}
	
	//Stops the rectangle.
	public void freezeRectangle()
	{
		tempX = xVelocity;
		tempY = yVelocity;
		xVelocity = 0; 
		yVelocity = 0;
	}
	
	//Unfreezes Rectangle with random clickAmmount
	public void unfreezeRectangle() {
		xVelocity = tempX;
		yVelocity = tempY;
		clickAmmount = rng.nextInt(3) + 1;
	}
	
	//Getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public int getClickAmmount() {
		return clickAmmount;
	}
	
	//Setters
	public void setClickAmmount(int x) {
		clickAmmount += x;
	}
	public void lowerClickAmmount() {
		clickAmmount--;
		if (clickAmmount <= 0) {
			clickAmmount = 0;
			freezeRectangle();
		}
	}
	
	//To check if there is a collision between a stopped rectangle and a moving one
	public void getCollisionCheck(MovingRectangle newObject) {
		if (isStopped()) {
			int xcenter1 = getX();
			int ycenter1 = getY();
			int halfheight1 = getHeight() / 2;
			int halfwidth1 = getWidth() / 2;
			int xcenter2 = newObject.getX();
			int ycenter2 =  newObject.getY();
			int halfheight2 = newObject.getHeight() / 2;
			int halfwidth2 =  newObject.getWidth() / 2;
			clickAmmount = 0;
			
			//rectangle #1 is to the left of #2
			if (xcenter1 + halfwidth1 < xcenter2 - halfwidth2) {
				collisionCheck = false;
			//rectangle #2 is to the left of #1
			} else if (xcenter2 + halfwidth2 < xcenter1 - halfwidth1) {
				collisionCheck = false;
			//If X-Lines are touching	
			} else if (xcenter1 + halfwidth1 == xcenter2 - halfwidth2) {
				collisionCheck = false;
			//If rectangle #1 is below rectangle #2
			} else if (ycenter1 + halfheight1 < ycenter2 - halfheight2){
				collisionCheck = false;
			//If rectangle #2 is above rectangle #1
			} else if (ycenter2 + halfheight2 < ycenter1 - halfheight1){
				collisionCheck = false;
			//If y-lines are touching
			} else if (ycenter1 + halfheight1 == ycenter2 - halfheight2) {
				collisionCheck = false;
			//The rectangles are overlapping
			} else {
				collisionCheck = true;
			}
		
		//If there is a collision, the frozen rectangle is unfrozen
		if (collisionCheck == true) 
			unfreezeRectangle();
		 else 
			return;	
		}
	}
}
