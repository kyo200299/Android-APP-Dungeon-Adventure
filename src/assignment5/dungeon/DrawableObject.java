package assignment5.dungeon;

import android.graphics.Canvas;

// Referred from discussion example
public abstract class DrawableObject {
	
	// Get relative X position according to colon
	public abstract int getRelX();
	
	// Get relative Y position according to row
	public abstract int getRelY();
	
	// Draw objects on screen
	public abstract void draw(Canvas c, DungeonView dv);
	
	// Move objects
	public abstract void move();
	
	// Check collision with Link for some objects
	public abstract int checkCollision(int collisionType, int orientation, boolean linkAtk, boolean gateLocked);
}
